package com.chess.card.api.game.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 房间实例
 * @Author: jeecg-boot
 * @Date:   2024-06-16
 * @Version: V1.0
 */
@Data
@TableName(value = "room_instance",resultMap = "extResultMap")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="room_instance对象", description="房间实例")
public class RoomInstance implements Serializable {
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

	/**房间号*/
    @ApiModelProperty(value = "房间号")
    private java.lang.String roomId;

	/**第几圈*/
    @ApiModelProperty(value = "第几圈")
    private java.lang.Integer lapsNum;

	/**当前用户*/
    @ApiModelProperty(value = "当前用户")
    private java.lang.String actUser;

	/**将池金额*/
    @ApiModelProperty(value = "将池金额")
    private java.math.BigDecimal prizePool;

    /**
     * 游戏结束时间
     */
    private Date gameOverTime;

    /**
     * 状态
     */
    private String status;

    @TableField(exist = false)
    private GameRoom gameRoom;
}
