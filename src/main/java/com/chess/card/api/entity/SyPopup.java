package com.chess.card.api.entity;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 弹窗配置
 * @author yxye
 * @Date:   2023-12-29
 * @Version: V1.0
 */
@Data
@TableName("sy_popup")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="sy_popup对象", description="弹窗配置")
public class SyPopup implements Serializable {
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
	/**弹窗名称*/
	
    @ApiModelProperty(value = "弹窗名称")
    private java.lang.String name;
	/**标题*/
	
    @ApiModelProperty(value = "标题")
    private java.lang.String title;
	/**副标题*/
	
    @ApiModelProperty(value = "副标题")
    private java.lang.String subTitle;
	/**内容*/

    @ApiModelProperty(value = "内容")
    private java.lang.String content;

	/**背景图片*/
    @ApiModelProperty(value = "背景图片")
    private java.lang.String bgImage;
	/**样式*/
	
    @ApiModelProperty(value = "样式")
    private java.lang.String style;
	/**备注*/
	
    @ApiModelProperty(value = "备注")
    private java.lang.String remarks;


    /**内容的样式*/
    @ApiModelProperty(value = "内容的样式")
    private java.lang.String contentStyle;

    /**按钮样式*/
    @ApiModelProperty(value = "按钮样式")
    private java.lang.String btnBoxStyle;

    @TableField(exist = false)
    @ApiModelProperty(hidden = true)
    private List<SyPopupBtn> btnList;
}
