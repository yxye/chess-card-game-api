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
 * @Description: 抽奖记录
 * @author yxye
 * @Date:   2023-12-27
 * @Version: V1.0
 */
@Data
@TableName("sy_lucky_draw_records")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="sy_lucky_draw_records对象", description="抽奖记录")
public class SyLuckyDrawRecords implements Serializable {
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
	/**用户*/
	
    @ApiModelProperty(value = "用户")
    private java.lang.String userId;
	/**奖品*/
	
    @ApiModelProperty(value = "奖品")
    private java.lang.String prizeId;
	/**奖品图片*/
	
    @ApiModelProperty(value = "奖品图片")
    private java.lang.String prizePicture;

	/**奖品名称*/
    @ApiModelProperty(value = "奖品名称")
    private java.lang.String prizeName;


    /**奖品类型:1实物，0虚拟*/
    @ApiModelProperty(value = "奖品类型:1实物，0虚拟")
    private Integer prizeType;

    /**0未领取，1已领取*/
    @ApiModelProperty(value = "0未领取，1已领取")
    private java.lang.Integer status;


    /**奖品分类：0默认商品，1谢谢惠顾，2大奖商品*/
    @ApiModelProperty(value = "奖品分类：0默认商品，1谢谢惠顾，2大奖商品")
    private Integer prizeClassify;

    /**奖品组*/
    @ApiModelProperty(value = "奖品组")
    private Integer prizeGroup;
}
