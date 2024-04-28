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

/**
 * @Description: 用户积分
 * @author yxye
 * @Date:   2023-12-24
 * @Version: V1.0
 */
@Data
@TableName("sy_user_integral")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="sy_user_integral对象", description="用户积分")
public class SyUserIntegral implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**积分*/
    @ApiModelProperty(value = "积分")
    private Integer integral;
	/**积分类型:1打卡，2看视频，3去商城,4游戏*/
    @ApiModelProperty(value = "积分类型:1打卡,2看视频,3去商城,4游戏")
    private String tranType;

    /**用户*/
    @ApiModelProperty(value = "用户")
    private String userId;

    @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    private Long index;

    /**昵称*/
    @TableField(exist = false)
    @ApiModelProperty(value="昵称",name="nickName")
    private String nickName;
}
