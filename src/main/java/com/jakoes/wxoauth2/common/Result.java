package com.jakoes.wxoauth2.common;

import lombok.Data;

/**
 * 统一响应结果封装
 * @param <T> 数据类型
 */
@Data
public class Result<T> {
    
    private Integer code;
    private String message;
    private T data;
    
    private Result() {}
    
    private Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
    /**
     * 成功返回结果
     * @param data 返回的数据
     * @param <T> 数据类型
     * @return 结果对象
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "操作成功", data);
    }
    
    /**
     * 成功返回结果
     * @param message 提示信息
     * @param data 返回的数据
     * @param <T> 数据类型
     * @return 结果对象
     */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(200, message, data);
    }
    
    /**
     * 失败返回结果
     * @param message 错误信息
     * @param <T> 数据类型
     * @return 结果对象
     */
    public static <T> Result<T> fail(String message) {
        return new Result<>(500, message, null);
    }
    
    /**
     * 失败返回结果
     * @param code 错误码
     * @param message 错误信息
     * @param <T> 数据类型
     * @return 结果对象
     */
    public static <T> Result<T> fail(Integer code, String message) {
        return new Result<>(code, message, null);
    }
} 