package com.chess.ws.api.bean;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class UserRegistBean implements Serializable {

    /**
     * 手机号码
     */
    @NotBlank(message = "手机号不能为空！")
    private String mobile;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空！")
    private String verificationCode;


    /**
     * 验证码
     */
    @NotBlank(message = "短信验证不能为空！")
    private String smsCode;

    /**
     * 游戏账号
     */
    @NotBlank(message = "游戏账号不能为空！")
    private String account;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空！")
    private String password;
}
