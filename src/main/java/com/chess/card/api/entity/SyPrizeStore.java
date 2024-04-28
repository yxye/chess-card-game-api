package com.chess.card.api.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chess.card.api.utils.lucky.ProbabilityOfWinning;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 奖品库存表
 * @author yxye
 * @Date:   2023-12-27
 * @Version: V1.0
 */
@Data
@TableName("sy_prize_store")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="sy_prize_store对象", description="奖品库存表")
public class SyPrizeStore implements Serializable, ProbabilityOfWinning {
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
	/**奖品名称*/
	
    @ApiModelProperty(value = "奖品名称")
    private java.lang.String prizeName;
	/**每日名额*/
	
    @ApiModelProperty(value = "每日名额")
    private java.lang.Integer dailyQuota;
	/**开始时间*/
	
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "开始时间")
    private java.util.Date startTime;
	/**结束时间*/
	
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结束时间")
    private java.util.Date endTime;
	/**1正常，0停用*/
	
    @ApiModelProperty(value = "1正常，0停用")
    private java.lang.String status;
	/**库存*/
	
    @ApiModelProperty(value = "库存")
    private java.lang.Integer inventory;
	/**等级*/
	
    @ApiModelProperty(value = "等级")
    private java.lang.Integer level;

	/**奖品图片*/
    @ApiModelProperty(value = "奖品图片")
    private java.lang.String prizePicture;

	/**概率*/
    @ApiModelProperty(value = "概率")
    private java.math.BigDecimal probability;

	/**备注*/
    @ApiModelProperty(value = "备注")
    private java.lang.String remarks;

    /**已使用库存*/
    @ApiModelProperty(value = "已使用库存")
    private Integer useInventory;

    /**奖品类型:0虚拟,1实物奖品,2道具卡,3卡券奖品，4优惠券*/
    @ApiModelProperty(value = "奖品类型:0虚拟,1实物奖品,2道具卡,3卡券奖品，4优惠券")
    private Integer prizeType;

    /**奖品分类：0默认商品，1谢谢惠顾，2大奖商品*/
    @ApiModelProperty(value = "奖品分类：0默认商品，1谢谢惠顾，2大奖商品")
    private Integer prizeClassify;

    /**奖品组*/
    @ApiModelProperty(value = "奖品组")
    private Integer prizeGroup;
}
