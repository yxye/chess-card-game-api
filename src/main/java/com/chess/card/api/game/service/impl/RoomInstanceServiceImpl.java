package com.chess.card.api.game.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chess.card.api.exception.BuziException;
import com.chess.card.api.game.entity.GameRoom;
import com.chess.card.api.game.entity.RoomInstance;
import com.chess.card.api.game.mapper.RoomInstanceMapper;
import com.chess.card.api.game.service.IGameRoomService;
import com.chess.card.api.game.service.IRoomInstanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.math.BigDecimal;

/**
 * @Description: 房间实例
 * @Author: yxye
 * @Date:   2024-06-16
 * @Version: V1.0
 */
@Slf4j
@Service
public class RoomInstanceServiceImpl extends ServiceImpl<RoomInstanceMapper, RoomInstance> implements IRoomInstanceService {

    @Autowired
    private IGameRoomService gameRoomService;

    @Override
    public boolean hasRoomInstance(String roomId) {
        LambdaQueryWrapper<RoomInstance> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(RoomInstance::getRoomId,roomId);
        return this.count(queryWrapper) > 0;
    }



    public RoomInstance getRoomInstance(String roomId) {
        GameRoom gameRoom = gameRoomService.getById(roomId);
        if(gameRoom==null){
            log.error("初始化游戏失败,房间不存在，roomId={}",roomId);
            throw new BuziException("初始化游戏失败");
        }
        LambdaQueryWrapper<RoomInstance> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(RoomInstance::getRoomId,roomId);
        RoomInstance roomInstance = this.getOne(queryWrapper);
        if(roomInstance == null){
            log.error("初始化游戏失败,房间不存在，roomId={}",roomId);
            throw new BuziException("初始化游戏失败");
        }
        roomInstance.setGameRoom(gameRoom);

        return roomInstance;
    }


    public RoomInstance initRoom(String roomId){
        GameRoom gameRoom = gameRoomService.getById(roomId);
        if(gameRoom==null){
            log.error("初始化游戏失败,房间不存在，roomId={}",roomId);
            throw new BuziException("初始化游戏失败");
        }

        LambdaQueryWrapper<RoomInstance> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(RoomInstance::getRoomId,roomId);
        RoomInstance result = this.getOne(queryWrapper);
        if(result==null){
            result = new RoomInstance();
            result.setGameOverTime(null);
            result.setPrizePool(BigDecimal.ZERO);
            result.setLapsNum(0);
            result.setRoomId(roomId);
            result.setStatus("NA");
            this.save(result);
        }else if(result != null){
            RoomInstance upData = new RoomInstance();
            upData.setGameOverTime(null);
            upData.setPrizePool(BigDecimal.ZERO);
            upData.setLapsNum(0);
            upData.setActUser(null);
            upData.setStatus("NA");
            this.update(upData,queryWrapper);
        }
        result.setGameRoom(gameRoom);
        return result;

    }
}
