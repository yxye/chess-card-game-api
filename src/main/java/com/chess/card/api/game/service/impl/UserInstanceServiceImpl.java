package com.chess.card.api.game.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chess.card.api.exception.BuziException;
import com.chess.card.api.game.entity.GameRoom;
import com.chess.card.api.game.entity.RoomInstance;
import com.chess.card.api.game.entity.UserInfo;
import com.chess.card.api.game.entity.UserInstance;
import com.chess.card.api.game.mapper.UserInstanceMapper;
import com.chess.card.api.game.service.IUserInfoService;
import com.chess.card.api.game.service.IUserInstanceService;
import com.chess.card.api.security.model.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description: 用户实例
 * @Author: jeecg-boot
 * @Date:   2024-06-16
 * @Version: V1.0
 */
@Slf4j
@Service
public class UserInstanceServiceImpl extends ServiceImpl<UserInstanceMapper, UserInstance> implements IUserInstanceService {

    @Autowired
    private IUserInfoService userInfoService;

    public List<UserInstance> queryUser(String roomId,String roomInstanceId){
        LambdaQueryWrapper<UserInstance> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserInstance::getRoomId,roomId);
        queryWrapper.eq(UserInstance::getRoomInstanceId,roomInstanceId);
        return this.list(queryWrapper);
    }

    public UserInstance queryByUserId(String roomId,String userId){
        LambdaQueryWrapper<UserInstance> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserInstance::getRoomId,roomId);
        queryWrapper.eq(UserInstance::getUserId,userId);
        return this.getOne(queryWrapper);

    }

    public boolean initUser(String roomId,String roomInstanceId){
        LambdaQueryWrapper<UserInstance> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserInstance::getRoomId,roomId);
        queryWrapper.eq(UserInstance::getRoomInstanceId,roomInstanceId);
        return this.remove(queryWrapper);
    }

    @Override
    public boolean userInRoomInstance(String userId) {
        LambdaQueryWrapper<UserInstance> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserInstance::getUserId,userId);
        return this.count(queryWrapper) > 0;
    }

    @Override
    public long queryRoomUserCount(String roomId) {
        LambdaQueryWrapper<UserInstance> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserInstance::getRoomId,roomId);
        return this.count(queryWrapper);
    }

    public UserInstance getSeatNoUser(String userId,Integer seatNo) {
        LambdaQueryWrapper<UserInstance> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserInstance::getUserId,userId);
        queryWrapper.eq(UserInstance::getSeatNo,seatNo);
        return this.getOne(queryWrapper);
    }


    public void joinRoom(RoomInstance roomInstance,SecurityUser securityUser,Integer seatNo){
        if(securityUser == null){
            log.error("请刷新页面后再试,securityUser=null");
            throw new BuziException("请刷新页面后再试");
        }

        String userId = securityUser.getId();

        UserInfo userInfo = userInfoService.getById(userId);
        if(userInfo == null){
            log.error("用户不存在,userId={},seatNo={}",userId,seatNo);
            throw new BuziException("用户不存在");
        }

        GameRoom gameRoom = roomInstance.getGameRoom();
        Integer userNum = gameRoom.getUserNum();
        if(seatNo < 0 || userNum < seatNo){
            log.error("座位号有误,userId={},seatNo={}",userId,seatNo);
            throw new BuziException("座位号有误");
        }
        UserInstance seatNoUser = this.getSeatNoUser(userId, seatNo);
        if(seatNoUser != null){
            log.error("座位已有用户存在,userId={},seatNo={},seatUserId={}",userId,seatNo,seatNoUser.getUserId());
            throw new BuziException("座位已有用户存在");
        }
        String roomId = gameRoom.getId();
        BigDecimal bringIn = gameRoom.getBringIn();
        if(bringIn==null || bringIn.compareTo(BigDecimal.ZERO) <= 0){
            log.error("带入记分牌不能为空 roomId={},bringIn={}",roomId,bringIn);
            throw new BuziException("带入记分牌不能为空");
        }

        UserInstance userInstance = new UserInstance();
        userInstance.setUserId(userId);
        userInstance.setRoomId(roomId);
        userInstance.setSeatNo(seatNo);
        userInstance.setSessionId(securityUser.getSessionId());
        userInstance.setRoomInstanceId(roomInstance.getId());
        userInstance.setBuyScore(bringIn);
        userInstance.setCurrentScore(bringIn);
        userInstance.setChangeScore(BigDecimal.ZERO);
        userInstance.setIsAdmin(seatNo==0?1:0);
        this.save(userInstance);
    }
}
