package com.chess.ws.api.game.service.impl;

import com.chess.ws.api.game.entity.InstanceUser;
import com.chess.ws.api.game.mapper.InstanceUserMapper;
import com.chess.ws.api.game.service.IInstanceUserService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 实例用户
 * @Author: jeecg-boot
 * @Date:   2024-04-28
 * @Version: V1.0
 */
@Service
public class InstanceUserServiceImpl extends ServiceImpl<InstanceUserMapper, InstanceUser> implements IInstanceUserService {

}
