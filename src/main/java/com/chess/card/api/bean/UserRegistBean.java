package com.chess.card.api.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class UserRegistBean implements Serializable {

    /**
     * 邮箱
     */
    @ApiModelProperty(value="邮箱",name="email",example="test@163.com",required = true)
    @NotBlank(message = "邮箱不能为空！")
    private String email;


//    /**
//     * 手机号码
//     */
//    @ApiModelProperty(value="手机号码",name="mobile",example="13745612336",required = false)
//    @NotBlank(message = "手机号不能为空！")
//    private String mobile;

//    /**
//     * 游戏账号
//     */
//    @ApiModelProperty(value="游戏账号",name="account",example="game001",required = false)
//    @NotBlank(message = "游戏账号不能为空！")
//    private String account;


    /**
     * 验证码
     */
    @ApiModelProperty(value="验证码",name="authCode",example="测试环境默认为888888",notes = "测试环境默认为888888",required = true)
    @NotBlank(message = "验证码不能为空！")
    private String authCode;


    /**
     * 密码
     */
    @ApiModelProperty(value="密码",name="password",example="123456",required = true)
    @NotBlank(message = "密码不能为空！")
    private String password;
}
