package com.chess.card.api.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 积分记录
 * @author yxye
 * @Date:   2023-12-23
 * @Version: V1.0
 */
@Data
@TableName("sy_integral_record")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="sy_integral_record对象", description="积分记录")
public class SyIntegralRecord implements Serializable {
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
	/**交易后积分*/
	
    @ApiModelProperty(value = "交易后积分")
    private Integer afterIntegral;
	/**交易前积分*/
	
    @ApiModelProperty(value = "交易前积分")
    private Integer beforeIntegral;

	/**2打卡，4看视频，6看视频，8游戏*/
    @ApiModelProperty(value = "2打卡，4看视频，6看视频，8游戏")
    private Integer tranType;

    /**用户*/
    @ApiModelProperty(value = "用户")
    private String userId;

}
