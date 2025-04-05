package com.jakoes.wxoauth2;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

/**
 * @author jakoes
 * @date 2025/4/5
 * 微信官网技术文档：https://developers.weixin.qq.com/doc/offiaccount/OA_Web_Apps/Wechat_webpage_authorization.html
 */
@Slf4j
@Controller
public class WxController {
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Value("${oauth.wx.appid}")
    private String appid;

    @Value("${oauth.callback}")
    private String redirectUri;

    @Value("${oauth.wx.appsecret}")
    private String appsecret;

    @Value("${oauth.user.confirm}")
    private Integer userConfirm;

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("message", "welcome");
        return "index"; // 移除.ftl后缀，让Spring自动解析
    }
    
    @GetMapping("/wxlogin")
    public String wxLogin() {
        
        String encodedRedirectUri;
        try {
            encodedRedirectUri = URLEncoder.encode(redirectUri, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            encodedRedirectUri = redirectUri;
        }
        
        String scope = userConfirm != null && userConfirm == 1 ? "snsapi_userinfo" : "snsapi_base";
        
        // 第一步：用户同意授权，获取code
        String wxAuthUrl = "https://open.weixin.qq.com/connect/oauth2/authorize" +
                "?appid=" + appid +
                "&redirect_uri=" + encodedRedirectUri +
                "&response_type=code" +
                "&scope=" + scope +
                "&state=STATE" +
                "#wechat_redirect";

        log.info("请求微信URL: {}", wxAuthUrl);
        return "redirect:" + wxAuthUrl;
    }
    
    @GetMapping("/wxcallback")
    public String wxCallback(@RequestParam(value = "code", required = false) String code,
                           @RequestParam(value = "state", required = false) String state,
                           Model model) {
        log.info("微信授权回调 - 收到code: {}", code);
        log.info("微信授权回调 - 收到state: {}", state);
        
        try {
            // 第二步：通过code换取网页授权access_token
            JsonNode tokenInfo = getAccessToken(code);
            log.info("获取到的access_token信息: {}", tokenInfo);
            
            if (tokenInfo != null) {
                String accessToken = tokenInfo.get("access_token").asText();
                String openid = tokenInfo.get("openid").asText();
                
                model.addAttribute("accessToken", accessToken);
                model.addAttribute("openid", openid);
                
                // 第三步：拉取用户信息(仅当scope为 snsapi_userinfo)
                if (userConfirm != null && userConfirm == 1) {
                    JsonNode userInfo = getUserInfo(accessToken, openid);
                    if (userInfo != null) {
                        String nickname = userInfo.get("nickname").asText();
                        String headimgurl = userInfo.get("headimgurl").asText();
                        
                        model.addAttribute("nickname", nickname);
                        model.addAttribute("headimgurl", headimgurl);
                        model.addAttribute("message", "授权成功，欢迎 " + nickname);
                    } else {
                        model.addAttribute("message", "获取用户信息失败");
                    }
                } else {
                    model.addAttribute("message", "授权成功，用户openid: " + openid);
                }
            } else {
                model.addAttribute("message", "获取access_token失败");
            }
        } catch (Exception e) {
            log.error("获取access_token异常: {}", e.getMessage(), e);
            model.addAttribute("message", "获取access_token异常: " + e.getMessage());
        }
        
        model.addAttribute("code", code);
        return "index";
    }

    /**
     * 根据code获取微信access_token
     * @param code 授权码
     * @return 微信返回的JSON结果
     */
    private JsonNode getAccessToken(String code) {
        // 构建请求URL
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=" + appid +
                "&secret=" + appsecret +
                "&code=" + code +
                "&grant_type=authorization_code";
        
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url);
            log.info("请求微信URL: {}", url);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity);
                    log.info("微信返回结果[access_token]: {}", result);
                    return objectMapper.readTree(result);
                }
            }
        } catch (IOException e) {
            log.error("请求微信access_token异常: {}", e.getMessage(), e);
        }
        
        return null;
    }

    /**
     * 获取微信用户信息
     * @param accessToken 访问令牌
     * @param openid 用户openid
     * @return 用户信息
     */
    private JsonNode getUserInfo(String accessToken, String openid) {
        String url = "https://api.weixin.qq.com/sns/userinfo" +
                "?access_token=" + accessToken +
                "&openid=" + openid +
                "&lang=zh_CN";
        
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url);
            log.info("请求微信URL: {}", url);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity);
                    log.info("微信返回结果[userinfo]: {}", result);
                    return objectMapper.readTree(result);
                }
            }
        } catch (IOException e) {
            log.error("获取用户信息异常: {}", e.getMessage(), e);
        }
        return null;
    }
}