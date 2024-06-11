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
 * @Description: 游戏房间模板
 * @Author: jeecg-boot
 * @Date:   2024-04-28
 * @Version: V1.0
 */
@Data
@TableName("game_room_tpl")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="game_room_tpl对象", description="游戏房间模板")
public class GameRoomTpl implements Serializable {
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
	/**房间名称*/
	
    @ApiModelProperty(value = "房间名称")
    private String roomName;
	/**带入记分牌*/
	
    @ApiModelProperty(value = "带入记分牌")
    private String bringIn;
	/**记分牌上限，-1不限制*/
	
    @ApiModelProperty(value = "记分牌上限，-1不限制")
    private String bringInUp;
	/**用户数*/
	
    @ApiModelProperty(value = "用户数")
    private Integer userNum;
	/**活跃度积分*/
	
    @ApiModelProperty(value = "活跃度积分")
    private Integer activityScore;
	/**赌注*/
	
    @ApiModelProperty(value = "赌注")
    private Integer ante;
	/**straddle状态*/
	
    @ApiModelProperty(value = "straddle状态")
    private Integer straddle;
}
