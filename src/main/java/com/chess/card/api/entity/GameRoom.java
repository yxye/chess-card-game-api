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
 * @Description: 游戏房间
 * @Author: jeecg-boot
 * @Date:   2024-04-28
 * @Version: V1.0
 */
@Data
@TableName("game_room")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="game_room对象", description="游戏房间")
public class GameRoom implements Serializable {
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
	/**用户数*/
	@Excel(name = "用户数", width = 15)
    @ApiModelProperty(value = "用户数")
    private java.lang.Integer userNum;
	/**游戏类型*/
	@Excel(name = "游戏类型", width = 15)
    @ApiModelProperty(value = "游戏类型")
    private java.lang.String gameType;
	/**房间名称*/
	@Excel(name = "房间名称", width = 15)
    @ApiModelProperty(value = "房间名称")
    private java.lang.String roomName;
	/**带入记分牌*/
	@Excel(name = "带入记分牌", width = 15)
    @ApiModelProperty(value = "带入记分牌")
    private java.lang.String bringIn;
	/**带入记仇牌上限-1不限制*/
	@Excel(name = "带入记仇牌上限-1不限制", width = 15)
    @ApiModelProperty(value = "带入记仇牌上限-1不限制")
    private java.lang.String bringInUp;
	/**活跃度积分*/
	@Excel(name = "活跃度积分", width = 15)
    @ApiModelProperty(value = "活跃度积分")
    private java.lang.Integer activityScore;
	/**赌注*/
	@Excel(name = "赌注", width = 15)
    @ApiModelProperty(value = "赌注")
    private java.lang.Integer ante;
	/**straddle状态*/
	@Excel(name = "straddle状态", width = 15)
    @ApiModelProperty(value = "straddle状态")
    private java.lang.Integer straddle;
	/**小盲/大盲*/
	@Excel(name = "小盲/大盲", width = 15)
    @ApiModelProperty(value = "小盲/大盲")
    private java.lang.String rate;
	/**时长(单位小时)*/
	@Excel(name = "时长(单位小时)", width = 15)
    @ApiModelProperty(value = "时长(单位小时)")
    private java.lang.Integer duration;
	/**All in 后支持发两次*/
	@Excel(name = "All in 后支持发两次", width = 15)
    @ApiModelProperty(value = "All in 后支持发两次")
    private java.lang.Integer allInTwo;
	/**延迟看牌*/
	@Excel(name = "延迟看牌", width = 15)
    @ApiModelProperty(value = "延迟看牌")
    private java.lang.Integer delayedCardRead;
	/**随机入座*/
	@Excel(name = "随机入座", width = 15)
    @ApiModelProperty(value = "随机入座")
    private java.lang.Integer randomSeating;
	/**旁观者禁言*/
	@Excel(name = "旁观者禁言", width = 15)
    @ApiModelProperty(value = "旁观者禁言")
    private java.lang.Integer bystanderSilence;
	/**GPS和IP限制*/
	@Excel(name = "GPS和IP限制", width = 15)
    @ApiModelProperty(value = "GPS和IP限制")
    private java.lang.Integer gpsIpRestrictions;
	/**禁用模拟器*/
	@Excel(name = "禁用模拟器", width = 15)
    @ApiModelProperty(value = "禁用模拟器")
    private java.lang.Integer disablingSimulator;
}
