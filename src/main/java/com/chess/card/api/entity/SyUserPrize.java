package com.chess.card.api.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @Description: 奖品
 * @author yxye
 * @Date:   2023-12-23
 * @Version: V1.0
 */
@Data
@TableName("sy_user_prize")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="sy_user_prize对象", description="奖品")
public class SyUserPrize implements Serializable {
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

    /**0虚拟奖品,1实物奖品，2道具卡,3抵扣券,4优惠券*/
    @ApiModelProperty(value = "0虚拟奖品,1实物奖品，2道具卡,3抵扣券,4优惠券")
    private Integer prizeType;

	/**奖品内容*/
    @ApiModelProperty(value = "奖品内容")
    private String prizeData;

    /**中奖记录*/
    @ApiModelProperty(value = "中奖记录")
    private String recordsId;

    /**奖品名称*/
    @ApiModelProperty(value = "奖品名称")
    private String prizeName;

	/**0未领取，1已领取*/
    @ApiModelProperty(value = "0未领取，1已领取")
    private Integer status;

    /**使用记录*/
    @ApiModelProperty(value = "使用记录")
    private String useRefId;

    /**过期时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "过期时间")
    private Date expirationTime;

    /**收货信息*/
    @ApiModelProperty(value = "收货信息")
    private String logisticsId;

    /**快递单号*/
    @ApiModelProperty(value = "快递单号")
    private String logisticsNo;

    /**快递名称*/
    @ApiModelProperty(value = "快递名称")
    private String logisticsName;

    @TableField(exist = false)
    @ApiModelProperty(value = "收货信息")
    private SyLogistics logistics;
}
