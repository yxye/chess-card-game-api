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
 * @Description: 游戏记录详情
 * @Author: jeecg-boot
 * @Date:   2024-04-28
 * @Version: V1.0
 */
@Data
@TableName("instance_item_record")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="instance_item_record对象", description="游戏记录详情")
public class InstanceItemRecord implements Serializable {
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
	/**游戏记录*/
	@Excel(name = "游戏记录", width = 15)
    @ApiModelProperty(value = "游戏记录")
    private java.lang.String gameRecordId;
	/**用户*/
	@Excel(name = "用户", width = 15)
    @ApiModelProperty(value = "用户")
    private java.lang.String userId;
	/**当前步骤*/
	@Excel(name = "当前步骤", width = 15)
    @ApiModelProperty(value = "当前步骤")
    private java.lang.String stepName;
	/**动作：1下注、2加注、、3盖牌、4放弃*/
	@Excel(name = "动作：1下注、2加注、、3盖牌、4放弃", width = 15)
    @ApiModelProperty(value = "动作：1下注、2加注、、3盖牌、4放弃")
    private java.lang.Integer action;
	/**下注或加注金额*/
	@Excel(name = "下注或加注金额", width = 15)
    @ApiModelProperty(value = "下注或加注金额")
    private java.math.BigDecimal betAmount;
	/**房间*/
	@Excel(name = "房间", width = 15)
    @ApiModelProperty(value = "房间")
    private java.lang.String roomId;
}