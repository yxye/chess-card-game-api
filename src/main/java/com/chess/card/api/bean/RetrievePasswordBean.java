package com.chess.ws.api.bean;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class RetrievePasswordBean implements Serializable {

    /**
     *电子邮箱
     */
    @NotBlank(message = "请输入电子邮箱")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",message = "请输入正确的电子邮箱！")
    private String account;

    /**
     *短信验证码
     */
    @NotBlank(message = "请输入短信验证码")
    @Pattern(regexp = "^[0-9]{6}$",message = "请输入正确的短信验证码！")
    private String smsCode;

    /**
     * 请输入手机号码
     */
    @NotBlank(message = "请输入手机号码")
    @Pattern(regexp = "^1[0-9]{10}$",message = "请输入正确的手机号码！")
    private String mobile;

    /**
     * 新密码
     */
    @NotBlank(message = "请输入新密码")
    @Min(message = "密码最小6位",value = 6)
    @Max(message = "密码最大18位",value = 18)
    private String password;

}
