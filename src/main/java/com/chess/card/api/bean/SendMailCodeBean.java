package com.chess.card.api.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;


@Data
@ToString
public class SendMailCodeBean implements Serializable {

    /**
     *电子邮箱
     */
    @ApiModelProperty(value="电子邮箱",name="email",example="test123@163.com",required = true)
    @NotBlank(message = "请输入电子邮箱")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",message = "请输入正确的电子邮箱！")
    private String email;

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
