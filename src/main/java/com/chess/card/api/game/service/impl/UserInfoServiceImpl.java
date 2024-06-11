package com.chess.ws.api.game.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.chess.ws.api.game.entity.UserInfo;
import com.chess.ws.api.game.mapper.UserInfoMapper;
import com.chess.ws.api.bean.RetrievePasswordBean;
import com.chess.ws.api.exception.BuziException;
import com.chess.ws.api.game.service.IUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 玩家信息
 * @Author: jeecg-boot
 * @Date:   2024-04-28
 * @Version: V1.0
 */
@Slf4j
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserInfo userRegister(UserInfo userInfo) {
        String password = userInfo.getPassword();
        userInfo.setPassword(passwordEncoder.encode(password));
        this.save(userInfo);
        return userInfo;
    }

    @Override
    public UserInfo getUserByMobile(String mobile) {
        LambdaQueryWrapper<UserInfo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserInfo::getMobile,mobile);
        return this.getOne(queryWrapper);
    }

    @Override
    public boolean retrievePassword(RetrievePasswordBean retrievePassword) {
        String mobile = retrievePassword.getMobile();
        String password = retrievePassword.getPassword();
        if(this.getUserByMobile(mobile) == null){
            log.error("找回密码失败，用户不存在 mobile={}",mobile);
            throw new BuziException("用户不存在");
        }
        LambdaQueryWrapper<UserInfo> upWrapper = Wrappers.lambdaQuery();
        upWrapper.eq(UserInfo::getMobile,mobile);
        UserInfo upData = new UserInfo();
        upData.setPassword(passwordEncoder.encode(password));
        return this.update(upData,upWrapper);
    }

    @Override
    public UserInfo findByUserId(String userId) {
        return getById(userId);
    }

    @Override
    public UserInfo userLogin(String account) {
        LambdaQueryWrapper<UserInfo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq( UserInfo::getAccount,account).or((e)->{
            e.eq(UserInfo::getEmail,account);
        }).or((e)->{
            e.eq(UserInfo::getMobile,account);
        });
        return this.getOne(queryWrapper);
    }

    @Override
    public UserInfo findByAccount(String account) {
        LambdaQueryWrapper<UserInfo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq( UserInfo::getAccount,account);
        return this.getOne(queryWrapper);
    }

    public void validateUserCredentials(String password, UserInfo userInfo) throws AuthenticationException {
        String userPassword = passwordEncoder.encode(password);
        log.info("userPassword={}", userPassword);
        if (!passwordEncoder.matches(password, userInfo.getPassword())) {
            log.error("Authentication Failed. password={}，account={},dbPassword={}",password,userInfo.getAccount(),userInfo.getPassword());
            throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
        }

    }
}
