package com.chess.card.api.game.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chess.card.api.game.entity.RoomUser;
import com.chess.card.api.game.mapper.RoomUserMapper;
import com.chess.card.api.game.service.IRoomUserService;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 房间用户
 * @Author: yxye
 * @Date:   2024-04-28
 * @Version: V1.0
 */
@Service
public class RoomUserServiceImpl extends ServiceImpl<RoomUserMapper, RoomUser> implements IRoomUserService {

    @Override
    public RoomUser queryRoomUser(String roomId,String userId) {
        LambdaQueryWrapper<RoomUser> queryWrapper =  Wrappers.lambdaQuery();
        queryWrapper.eq(RoomUser::getRoomId,roomId);
        queryWrapper.eq(RoomUser::getUserId,userId);
        return this.getOne(queryWrapper);
    }

    @Override
    public RoomUser queryRoomId(String userId) {
        LambdaQueryWrapper<RoomUser> queryWrapper =  Wrappers.lambdaQuery();
        queryWrapper.eq(RoomUser::getUserId,userId);
        return this.getOne(queryWrapper);
    }


    @Override
    public boolean updateStatus(String userId, Integer status) {
        LambdaQueryWrapper<RoomUser> queryWrapper =  Wrappers.lambdaQuery();
        queryWrapper.eq(RoomUser::getUserId,userId);
        RoomUser upData = new RoomUser();
        upData.setStatus(status);
        return this.update(upData,queryWrapper);
    }

}
