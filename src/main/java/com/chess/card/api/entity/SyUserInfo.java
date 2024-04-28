package com.chess.card.api.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @Description: 用户信息表
 * @author yxye
 * @Date:   2023-12-23
 * @Version: V1.0
 */
@Data
@TableName("sy_user_info")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="sy_user_info对象", description="用户信息表")
public class SyUserInfo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键",hidden = true)

    private String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人",hidden = true)
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期",hidden = true)
    private Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人",hidden = true)
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期",hidden = true)
    private Date updateTime;
	/**积分*/
	
    @ApiModelProperty(value = "积分",hidden = true)
    private Integer integral;
	/**积分OPENID*/
	
    @ApiModelProperty(value = "积分OPENID",hidden = true)
    private String openId;
	/**昵称*/
    @NotBlank(message = "请输入昵称")
    @ApiModelProperty(value="昵称",name="nickName",example="王二",required = true)
    private String nickName;

	/**头像*/
    @NotBlank(message = "请上传头像")
    @ApiModelProperty(value = "头像",hidden = true)
    private String avatar;

	/**排名*/
    @ApiModelProperty(value = "排名",hidden = true)
    private Integer level;

	/**游戏次数*/
    @ApiModelProperty(value = "游戏次数",hidden = true)
    private Integer gameCount;
	/**最后使用时间*/
	
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最后使用时间",hidden = true)
    private Date lastUseTime;

	/**手机号码*/
    @ApiModelProperty(value = "手机号码",required = true)
    private String mobile;

	/**生日*/
    @ApiModelProperty(value = "生日",required = true)
    private String birthday;

	/**地区*/
    @ApiModelProperty(value = "地区",required = true)
    private String region;

	/**性别*/
    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(hidden = true)
    private String token;

    @TableField(exist = false)
    @ApiModelProperty(hidden = true)
    private java.lang.String sessionKey;

    @ApiModelProperty(value = "状态：1启用，0停用")
    @TableField(value = "status")
    private String status;
}
