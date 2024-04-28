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
 * @Description: 游戏记录
 * @author yxye
 * @Date:   2023-12-23
 * @Version: V1.0
 */
@Data
@TableName("sy_game_records")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="sy_game_records对象", description="游戏记录")
public class SyGameRecords implements Serializable {
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

	/**用户*/
    @ApiModelProperty(value = "用户")
    private String userId;

	/**1玩游戏，2分享奖励，第二位表示分享人数 21分享一位，22分享二位，3每日赠送*/
    @ApiModelProperty(value = "1玩游戏，2分享奖励，3每日赠送,4三十分钟一次赠送")
    private String action;

	/**游戏次数 -1不限制*/
    @ApiModelProperty(value = "游戏次数")
    private int gameCount;
}
