package com.chess.ws.api.game.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.chess.ws.api.security.model.Authority;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 玩家信息
 * @Author: jeecg-boot
 * @Date:   2024-04-28
 * @Version: V1.0
 */
@Data
@TableName("user_info")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="user_info对象", description="玩家信息")
public class UserInfo implements Serializable {
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
	/**昵称*/
	
    @ApiModelProperty(value = "昵称")
    private String nickName;
	/**邮箱*/
	
    @ApiModelProperty(value = "邮箱")
    private String email;
	/**密码*/
	
    @ApiModelProperty(value = "密码")
    private String password;
	/**账号*/
	
    @ApiModelProperty(value = "账号")
    private String account;
	/**手机号码*/
	
    @ApiModelProperty(value = "手机号码")
    private String mobile;
	/**性别*/
    @ApiModelProperty(value = "性别")
    private Integer sex;
	/**游戏ID*/
	
    @ApiModelProperty(value = "游戏ID")
    private String gameId;
	/**图像*/
	
    @ApiModelProperty(value = "图像")
    private String avatar;

    private Authority authority;
    public UserInfo(){

    }
    public UserInfo(String id) {
        this.id = id;
    }
    public UserInfo(UserInfo user) {
        this.id = user.getId();
        this.account = user.getAccount();
        this.authority = Authority.SYS_ADMIN;
    }
}
