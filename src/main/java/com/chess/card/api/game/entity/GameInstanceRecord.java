package com.chess.card.api.game.entity;

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
 * @Description: 游戏实例
 * @Author: yxye
 * @Date:   2024-04-28
 * @Version: V1.0
 */
@Data
@TableName("game_instance_record")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="game_instance_record对象", description="游戏实例")
public class GameInstanceRecord implements Serializable {
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
	/**实例*/
	
    @ApiModelProperty(value = "实例")
    private String instanceId;
	/**第几圈*/
	
    @ApiModelProperty(value = "第几圈")
    private String stepIndex;
	/**步骤名称*/
	
    @ApiModelProperty(value = "步骤名称")
    private String stepName;
	/**手数*/
	
    @ApiModelProperty(value = "手数")
    private Integer playHand;
	/**带入金额*/
	
    @ApiModelProperty(value = "带入金额")
    private BigDecimal addBet;
	/**池底*/
	
    @ApiModelProperty(value = "池底")
    private BigDecimal baseBet;
}
