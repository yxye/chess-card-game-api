package com.chess.card.api.bean;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value="电子邮箱",name="email",example="test@163.com",required = true)
    @NotBlank(message = "请输入电子邮箱")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",message = "请输入正确的电子邮箱！")
    private String email;

    /**
     *短信验证码
     */
    @ApiModelProperty(value="短信验证码",name="smsCode",example="测试环境默认为888888",required = true)
    @NotBlank(message = "请输入短信验证码")
    @Pattern(regexp = "^[0-9]{6}$",message = "请输入正确的短信验证码！")
    private String smsCode;

    /**
     * 请输入手机号码
     */
    @ApiModelProperty(value="手机号码",name="mobile",example="13873243456",required = true)
    @NotBlank(message = "请输入手机号码")
    @Pattern(regexp = "^1[0-9]{10}$",message = "请输入正确的手机号码！")
    private String mobile;

    /**
     * 新密码
     */
    @NotBlank(message = "请输入新密码")
    @Min(message = "密码最小6位",value = 6)
    @Max(message = "密码最大18位",value = 18)
    @ApiModelProperty(value="新密码",name="password",example="123456",required = true)
    private String password;

}
