package com.chess.card.api.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chess.card.api.annotation.AddType;
import com.chess.card.api.annotation.UpdateType;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @Description: 用户收货信息
 * @author yxye
 * @Date:   2023-12-27
 * @Version: V1.0
 */
@Data
@TableName("sy_logistics")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="sy_logistics对象", description="用户收货信息")
public class SyLogistics implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键",hidden = true)
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人",hidden = true)
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期",hidden = true)
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人",hidden = true)
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期",hidden = true)
    private java.util.Date updateTime;

	/**收货手机号*/
    @NotBlank(message = "请输入收货手机号",groups = {AddType.class, UpdateType.class})
    @Pattern(regexp = "^1[0-9]{10}$",message = "手机号码有误！",groups = {AddType.class, UpdateType.class})
    @ApiModelProperty(value = "收货手机号",required = true)
    private java.lang.String mobile;

	/**收货人*/
    @NotBlank(message = "请输入收货人",groups = {AddType.class, UpdateType.class})
    @ApiModelProperty(value = "收货人",required = true)
    private java.lang.String userName;

	/**地址*/
    @NotBlank(message = "请输入收货地址",groups = {AddType.class, UpdateType.class})
    @ApiModelProperty(value = "地址",required = true)
    private java.lang.String address;

	/**物流单号*/
    @ApiModelProperty(value = "物流单号",hidden = true)
    private java.lang.String logisticsNo;
	/**快递名称*/
	
    @ApiModelProperty(value = "快递名称",hidden = true)
    private java.lang.String logisticsName;

	/**所属用户*/
    @ApiModelProperty(value = "所属用户",hidden = true)
    private java.lang.String userId;

    /**省份*/
    @NotBlank(message = "请选择省份",groups = {AddType.class, UpdateType.class})
    @ApiModelProperty(value = "省份",required = true)
    private java.lang.String provinceName;

    /**城市*/
    @NotBlank(message = "请选择城市",groups = {AddType.class, UpdateType.class})
    @ApiModelProperty(value = "城市",required = true)
    private java.lang.String cityName;

    /**地区*/
    @NotBlank(message = "请选择地区",groups = {AddType.class, UpdateType.class})
    @ApiModelProperty(value = "地区",required = true)
    private java.lang.String districtName;

    @NotBlank(message = "请选择省份",groups = {AddType.class, UpdateType.class})
    @ApiModelProperty(value = "省份",required = true)
    private java.lang.String provinceCode;

    @NotBlank(message = "请选择城市",groups = {AddType.class, UpdateType.class})
    @ApiModelProperty(value = "城市",required = true)
    private java.lang.String cityCode;

    @NotBlank(message = "请选择地区",groups = {AddType.class, UpdateType.class})
    @ApiModelProperty(value = "地区",required = true)
    private java.lang.String districtCode;;

}
