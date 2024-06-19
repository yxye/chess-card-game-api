package com.chess.card.api.game.service.impl;

import com.chess.card.api.game.entity.InstanceUser;
import com.chess.card.api.game.mapper.InstanceUserMapper;
import com.chess.card.api.game.service.IInstanceUserService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 实例用户
 * @Author: yxye
 * @Date:   2024-04-28
 * @Version: V1.0
 */
@Service
public class InstanceUserServiceImpl extends ServiceImpl<InstanceUserMapper, InstanceUser> implements IInstanceUserService {

}
