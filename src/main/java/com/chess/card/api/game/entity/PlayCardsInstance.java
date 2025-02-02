package com.chess.card.api.game.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 实例下的牌
 * @Author: yxye
 * @Date:   2024-04-28
 * @Version: V1.0
 */
@Data
@TableName("play_cards_instance")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="play_cards_instance对象", description="实例下的牌")
public class PlayCardsInstance implements Serializable {
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

	/**房间*/
    @ApiModelProperty(value = "房间")
    private String roomId;

	/**用户ID：-1公共牌*/
    @ApiModelProperty(value = "用户ID：-1公共牌")
    private String userId;

	/**扑克牌*/
    @ApiModelProperty(value = "扑克牌")
    private String cardId;

	/**排序*/
    @ApiModelProperty(value = "排序")
    private String cardOrder;

    /**花色：H红桃，D方块，C梅花，S黑桃*/
    @ApiModelProperty(value = "花色：H红桃，D方块，C梅花，S黑桃")
    private String cardSuit;
}
