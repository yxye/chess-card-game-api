package com.chess.ws.api.game.service.impl;

import com.chess.ws.api.game.entity.RoomUser;
import com.chess.ws.api.game.mapper.RoomUserMapper;
import com.chess.ws.api.game.service.IRoomUserService;
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
