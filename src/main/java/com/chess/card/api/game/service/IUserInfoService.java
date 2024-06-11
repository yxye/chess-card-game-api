package com.chess.ws.api.game.service;


import com.chess.ws.api.game.entity.UserInfo;
import com.chess.ws.api.bean.RetrievePasswordBean;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.AuthenticationException;

/**
 * @Description: 玩家信息
 * @Author: jeecg-boot
 * @Date:   2024-04-28
 * @Version: V1.0
 */
public interface IUserInfoService extends IService<UserInfo> {


    /**
     * 注册用户
     * @param userInfo
     * @return
     */
    public UserInfo userRegister(UserInfo userInfo);

    /**
     *  使用手机号查询用户
     * @param mobile
     * @return
     */
    public UserInfo getUserByMobile(String mobile);


    /**
     * 找回密码
     * @param retrievePassword
     */
    public boolean retrievePassword(RetrievePasswordBean retrievePassword);


    public UserInfo findByUserId(String userId) ;

    UserInfo findByAccount(String account);

    /**
     * 用户账号或邮箱或手机号登录
     * @param account
     * @return
     */
    public UserInfo userLogin(String account);

    public void validateUserCredentials(String password, UserInfo authUser) throws AuthenticationException;
}
