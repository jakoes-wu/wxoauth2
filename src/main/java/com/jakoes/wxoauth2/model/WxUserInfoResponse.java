package com.jakoes.wxoauth2.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 微信用户信息响应实体
 */
@Data
public class WxUserInfoResponse {
    
    /**
     * 用户的唯一标识
     */
    private String openid;
    
    /**
     * 用户昵称
     */
    private String nickname;
    
    /**
     * 用户头像
     */
    private String headimgurl;
    
    /**
     * 用户统一标识。针对一个微信开放平台账号下的应用，同一用户的unionid是唯一的。
     */
    private String unionid;
    
    /**
     * 用户特权信息，json数组，如微信沃卡用户为（chinaunicom）
     */
    private String[] privilege;
    
    /**
     * 错误码
     */
    private Integer errcode;
    
    /**
     * 错误信息
     */
    private String errmsg;
    
    /**
     * 用户性别 值为1时是男性，值为2时是女性，值为0时是未知
     */
    private Integer sex;
    
    /**
     * 用户所在国家
     */
    private String country;
    
    /**
     * 用户所在省份
     */
    private String province;
    
    /**
     * 用户所在城市
     */
    private String city;
    
    /**
     * 用户的语言，简体中文为zh_CN
     */
    private String language;
} 