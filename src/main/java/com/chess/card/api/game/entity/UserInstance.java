package com.chess.card.api.game.entity;

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

/**
 * @Description: 用户实例
 * @Author: yxye
 * @Date:   2024-06-16
 * @Version: V1.0
 */
@Data
@TableName(value = "user_instance",resultMap = "extResultMap")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="user_instance对象", description="用户实例")
public class UserInstance implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;

	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;

	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;

	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;

	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;

	/**座位号*/
    @ApiModelProperty(value = "座位号")
    private java.lang.Integer seatNo;

	/**会话ID*/
    @ApiModelProperty(value = "会话ID")
    private java.lang.String sessionId;

    /**用户ID*/
    @ApiModelProperty(value = "用户ID")
    private java.lang.String userId;

	/**用户IP*/
    @ApiModelProperty(value = "用户IP")
    private java.lang.String ip;
	/**用户动作*/
	
    @ApiModelProperty(value = "用户动作")
    private java.lang.String userAct;
	/**总分*/
	
    @ApiModelProperty(value = "总分")
    private java.math.BigDecimal buyScore;

	/**当前分*/
    @ApiModelProperty(value = "当前分")
    private java.math.BigDecimal currentScore;

    @ApiModelProperty(value = "变动分")
    private java.math.BigDecimal changeScore;

	/**是否庄家*/
    @ApiModelProperty(value = "是否庄家")
    private java.lang.Integer isAdmin;

	/**房间号*/
    @ApiModelProperty(value = "房间号")
    private java.lang.String roomId;

	/**房间实例号*/
    @ApiModelProperty(value = "房间实例号")
    private java.lang.String roomInstanceId;

    @TableField(exist = false)
    private UserInfo userInfo;
}
