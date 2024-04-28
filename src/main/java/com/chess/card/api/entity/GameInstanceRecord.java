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
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 游戏实例
 * @Author: jeecg-boot
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
	/**实例*/
	@Excel(name = "实例", width = 15)
    @ApiModelProperty(value = "实例")
    private java.lang.String instanceId;
	/**第几圈*/
	@Excel(name = "第几圈", width = 15)
    @ApiModelProperty(value = "第几圈")
    private java.lang.String stepIndex;
	/**步骤名称*/
	@Excel(name = "步骤名称", width = 15)
    @ApiModelProperty(value = "步骤名称")
    private java.lang.String stepName;
	/**手数*/
	@Excel(name = "手数", width = 15)
    @ApiModelProperty(value = "手数")
    private java.lang.Integer playHand;
	/**带入金额*/
	@Excel(name = "带入金额", width = 15)
    @ApiModelProperty(value = "带入金额")
    private java.math.BigDecimal addBet;
	/**池底*/
	@Excel(name = "池底", width = 15)
    @ApiModelProperty(value = "池底")
    private java.math.BigDecimal baseBet;
}
