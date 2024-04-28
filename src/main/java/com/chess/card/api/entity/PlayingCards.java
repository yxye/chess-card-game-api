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
 * @Description: 扑克牌
 * @Author: jeecg-boot
 * @Date:   2024-04-28
 * @Version: V1.0
 */
@Data
@TableName("playing_cards")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="playing_cards对象", description="扑克牌")
public class PlayingCards implements Serializable {
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
	/**牌值*/
	
    @ApiModelProperty(value = "牌值")
    private java.lang.String cardId;
	/**排序值*/
	
    @ApiModelProperty(value = "排序值")
    private java.lang.String cardOrder;
	/**经典背景*/
	
    @ApiModelProperty(value = "经典背景")
    private java.lang.String classicBg;
	/**简单背景*/
	
    @ApiModelProperty(value = "简单背景")
    private java.lang.String simpleBg;
	/**花色：H红桃，D方块，C梅花，S黑桃*/
	
    @ApiModelProperty(value = "花色：H红桃，D方块，C梅花，S黑桃")
    private java.lang.String cardSuit;
}
