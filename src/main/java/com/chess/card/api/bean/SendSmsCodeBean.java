package com.chess.card.api.bean;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value="手机号码",name="mobile",example="13873243456",notes = "防止人为刷短信，一个ip一天10次，一个手机号一天5次",required = true)
    @NotBlank(message = "请输入手机号码")
    @Pattern(regexp = "^1[0-9]{10}$",message = "请输入正确的手机号码！")
    private String mobile;

    /**
     * 图形验证码
     */
    @ApiModelProperty(value="图形验证码",name="verificationCode",example="e03z",required = true)
    @NotBlank(message = "请输入图形验证码")
    @Pattern(regexp = "^[0-9a-zA-Z]{4}$",message = "请输入正确的图形验证码！")
    private String verificationCode;

    /**
     * 类型参数
     * register 注册发送验证码
     * reset 重置密码
     */
    @ApiModelProperty(value="类型参数",name="type",example="可选值[register|reset],register注册，reset找加密码",notes = "register注册，reset找加密码 可选值[register|reset]！",required = true)
    @NotBlank(message = "请选择类型参数")
    @Pattern(regexp = "(register|reset)",message = "type参数有误，可选值[register|reset]！")
    private String type;
}
