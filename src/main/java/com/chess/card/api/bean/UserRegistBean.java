package com.chess.card.api.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class UserRegistBean implements Serializable {

    /**
     * 手机号码
     */
    @ApiModelProperty(value="手机号码",name="mobile",example="13745612336",required = true)
    @NotBlank(message = "手机号不能为空！")
    private String mobile;

    /**
     * 验证码
     */
    @ApiModelProperty(value="图形验证码",name="verificationCode",example="e03z",required = true)
    @NotBlank(message = "验证码不能为空！")
    private String verificationCode;


    /**
     * 短信验证码
     */
    @ApiModelProperty(value="短信验证码",name="smsCode",example="888888",notes = "测试环境默认为888888",required = true)
    @NotBlank(message = "短信验证码不能为空！")
    private String smsCode;

    /**
     * 游戏账号
     */
    @ApiModelProperty(value="游戏账号",name="account",example="157532423",required = true)
    @NotBlank(message = "游戏账号不能为空！")
    private String account;

    /**
     * 密码
     */
    @ApiModelProperty(value="密码",name="password",example="123456",required = true)
    @NotBlank(message = "密码不能为空！")
    private String password;
}
