package com.chess.card.api.service.impl;

import com.chess.card.api.entity.UserInfo;
import com.chess.card.api.mapper.UserInfoMapper;
import com.chess.card.api.service.IUserInfoService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 玩家信息
 * @Author: jeecg-boot
 * @Date:   2024-04-28
 * @Version: V1.0
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

}
