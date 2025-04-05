package com.jakoes.wxoauth2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 前端展示用的微信用户信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WxUserInfoDTO {
    
    /**
     * 授权码
     */
    private String code;
    
    /**
     * 用户的openid
     */
    private String openid;
    
    /**
     * 网页授权access_token
     */
    private String accessToken;
    
    /**
     * 用户昵称
     */
    private String nickname;
    
    /**
     * 用户头像URL
     */
    private String headimgurl;
    
    /**
     * 统一标识
     */
    private String unionid;
} 