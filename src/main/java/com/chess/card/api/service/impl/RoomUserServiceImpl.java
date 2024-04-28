package com.chess.card.api.service.impl;

import com.chess.card.api.entity.RoomUser;
import com.chess.card.api.mapper.RoomUserMapper;
import com.chess.card.api.service.IRoomUserService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 房间用户
 * @Author: jeecg-boot
 * @Date:   2024-04-28
 * @Version: V1.0
 */
@Service
public class RoomUserServiceImpl extends ServiceImpl<RoomUserMapper, RoomUser> implements IRoomUserService {

}
