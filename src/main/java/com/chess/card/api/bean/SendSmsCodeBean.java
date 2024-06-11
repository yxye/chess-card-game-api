package com.chess.ws.api.bean;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;


@Data
@ToString
public class SendSmsCodeBean implements Serializable {

    /**
     * 手机号码
     */
    @NotBlank(message = "请输入手机号码")
    @Pattern(regexp = "^1[0-9]{10}$",message = "请输入正确的手机号码！")
    private String mobile;

    /**
     * 图形验证码
     */
    @NotBlank(message = "请输入图形验证码")
    @Pattern(regexp = "^[0-9a-zA-Z]{4}$",message = "请输入正确的图形验证码！")
    private String verificationCode;

    /**
     * 类型参数
     * register 注册发送验证码
     * reset 重置密码
     */
    @NotBlank(message = "请选择类型参数")
    @Pattern(regexp = "(register|reset)",message = "type参数有误，可选值[register|reset]！")
    private String type;
}
