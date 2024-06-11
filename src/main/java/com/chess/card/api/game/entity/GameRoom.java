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
	/**用户数*/
	
    @ApiModelProperty(value = "用户数")
    private Integer userNum;
	/**游戏类型*/
	
    @ApiModelProperty(value = "游戏类型")
    private String gameType;
	/**房间名称*/
	
    @ApiModelProperty(value = "房间名称")
    private String roomName;
	/**带入记分牌*/
	
    @ApiModelProperty(value = "带入记分牌")
    private String bringIn;
	/**带入记仇牌上限-1不限制*/
	
    @ApiModelProperty(value = "带入记仇牌上限-1不限制")
    private String bringInUp;
	/**活跃度积分*/
	
    @ApiModelProperty(value = "活跃度积分")
    private Integer activityScore;
	/**赌注*/
	
    @ApiModelProperty(value = "赌注")
    private Integer ante;
	/**straddle状态*/
	
    @ApiModelProperty(value = "straddle状态")
    private Integer straddle;
	/**小盲/大盲*/
	
    @ApiModelProperty(value = "小盲/大盲")
    private String rate;
	/**时长(单位小时)*/
	
    @ApiModelProperty(value = "时长(单位小时)")
    private Integer duration;
	/**All in 后支持发两次*/
	
    @ApiModelProperty(value = "All in 后支持发两次")
    private Integer allInTwo;
	/**延迟看牌*/
	
    @ApiModelProperty(value = "延迟看牌")
    private Integer delayedCardRead;
	/**随机入座*/
	
    @ApiModelProperty(value = "随机入座")
    private Integer randomSeating;
	/**旁观者禁言*/
	
    @ApiModelProperty(value = "旁观者禁言")
    private Integer bystanderSilence;
	/**GPS和IP限制*/
	
    @ApiModelProperty(value = "GPS和IP限制")
    private Integer gpsIpRestrictions;
	/**禁用模拟器*/
	
    @ApiModelProperty(value = "禁用模拟器")
    private Integer disablingSimulator;
}
