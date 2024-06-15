package com.chess.card.api.game.service.impl;

import cn.hutool.core.lang.Validator;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.chess.card.api.game.entity.UserInfo;
import com.chess.card.api.game.mapper.UserInfoMapper;
import com.chess.card.api.bean.RetrievePasswordBean;
import com.chess.card.api.exception.BuziException;
import com.chess.card.api.game.service.IUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
        String email = userInfo.getEmail();
        if(StringUtils.isBlank(email)){
            log.error("userRegister 注册失败 邮箱不能为空 email=null");
            throw new BuziException("邮箱格式有误！");
        }

        if(!Validator.isEmail(email)){
            log.error("userRegister 注册失败 邮箱格式有误 email={}",email);
            throw new BuziException("邮箱格式有误！");
        }

        String password = userInfo.getPassword();
        if(StringUtils.isBlank(password)){
            log.error("userRegister 注册失败 密码不能为空 email={}",email);
            throw new BuziException("密码不能为空！");
        }
        if(password.length() > 18 || password.length() < 6){
            log.error("userRegister 注册失败 密码长度必须在6到18位之间 password={}",password);
            throw new BuziException("密码长度必须在6到18位之间！");
        }
        userInfo.setPassword(passwordEncoder.encode(password));
        this.save(userInfo);

        userInfo.setAccount(userInfo.getId());

        this.updateById(userInfo);

        return userInfo;
    }

    @Override
    public UserInfo getUserByMobile(String mobile) {
        LambdaQueryWrapper<UserInfo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserInfo::getMobile,mobile);
        return this.getOne(queryWrapper);
    }

    @Override
    public UserInfo getUserByEmail(String email) {
        LambdaQueryWrapper<UserInfo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserInfo::getEmail,email);
        return this.getOne(queryWrapper);
    }

    @Override
    public boolean retrievePassword(RetrievePasswordBean retrievePassword) {
        String email = retrievePassword.getEmail();
        String password = retrievePassword.getPassword();
        if(this.getUserByEmail(email) == null){
            log.error("找回密码失败，用户不存在 email={}",email);
            throw new BuziException("用户不存在");
        }
        LambdaQueryWrapper<UserInfo> upWrapper = Wrappers.lambdaQuery();
        upWrapper.eq(UserInfo::getEmail,email);
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
        UserInfo userInfo = this.getUserByEmail(account);

        if(userInfo==null){
            userInfo = this.getUserByMobile(account);
        }

        if(userInfo==null){
            LambdaQueryWrapper<UserInfo> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(UserInfo::getAccount,account);
            userInfo = this.getOne(queryWrapper);

        }

        if(userInfo != null && StringUtils.isBlank(userInfo.getAccount())){
            userInfo.setAccount(userInfo.getId());
        }

        return userInfo;
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
