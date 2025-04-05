package com.jakoes.wxoauth2.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 微信授权token响应实体
 */
@Data
public class WxAccessTokenResponse {
    
    /**
     * 网页授权接口调用凭证
     */
    @JsonProperty("access_token")
    private String accessToken;
    
    /**
     * access_token接口调用凭证超时时间，单位（秒）
     */
    @JsonProperty("expires_in")
    private Integer expiresIn;
    
    /**
     * 用户刷新access_token
     */
    @JsonProperty("refresh_token")
    private String refreshToken;
    
    /**
     * 用户唯一标识
     */
    private String openid;
    
    /**
     * 用户授权的作用域，使用逗号分隔
     */
    private String scope;
    
    /**
     * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段
     */
    private String unionid;
    
    /**
     * 错误码
     */
    private Integer errcode;
    
    /**
     * 错误信息
     */
    private String errmsg;
} 