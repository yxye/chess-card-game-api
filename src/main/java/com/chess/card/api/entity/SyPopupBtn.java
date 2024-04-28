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
 * @Description: 弹窗按钮
 * @author yxye
 * @Date:   2023-12-29
 * @Version: V1.0
 */
@Data
@TableName("sy_popup_btn")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="sy_popup_btn对象", description="弹窗按钮")
public class SyPopupBtn implements Serializable {
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
	/**名称*/
	
    @ApiModelProperty(value = "名称")
    private java.lang.String name;
	/**样式*/
	
    @ApiModelProperty(value = "样式")
    private java.lang.String style;
	/**执行的代码*/
	
    @ApiModelProperty(value = "执行的代码")
    private java.lang.String execCode;
	/**背景图片*/
	
    @ApiModelProperty(value = "背景图片")
    private java.lang.String bgImage;
	/**背景图宽度*/
	
    @ApiModelProperty(value = "背景图宽度")
    private java.lang.String bgWidth;
	/**背景图高度*/
	
    @ApiModelProperty(value = "背景图高度")
    private java.lang.String bgHeight;
	/**宽度*/
	
    @ApiModelProperty(value = "宽度")
    private java.lang.String width;
	/**高度*/
	
    @ApiModelProperty(value = "高度")
    private java.lang.String height;
	/**X坐标*/
	
    @ApiModelProperty(value = "X坐标")
    private java.lang.String locationX;
	/**Y坐标*/
	
    @ApiModelProperty(value = "Y坐标")
    private java.lang.String locationY;

    /**弹窗*/
    @ApiModelProperty(value = "弹窗")
    private java.lang.String popupId;

    /**排序*/
    @ApiModelProperty(value = "排序")
    private java.lang.Integer sort;
}
