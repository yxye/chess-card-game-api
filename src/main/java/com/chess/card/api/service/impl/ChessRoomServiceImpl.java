package com.chess.card.api.service.impl;

import com.chess.card.api.bean.room.RoomInfoVo;
import com.chess.card.api.exception.BuziException;

import com.chess.card.api.game.entity.PlayCardsInstance;
import com.chess.card.api.game.entity.RoomInstance;
import com.chess.card.api.game.entity.RoomUser;
import com.chess.card.api.game.entity.UserInstance;
import com.chess.card.api.game.service.*;
import com.chess.card.api.security.model.SecurityUser;
import com.chess.card.api.service.IChessRoomService;
import com.chess.card.api.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@Service
public class ChessRoomServiceImpl implements IChessRoomService {

    @Autowired
    private IRoomInstanceService roomInstanceService;

    @Autowired
    private IUserInstanceService userInstanceService;

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private IPlayCardsInstanceService playCardsInstanceService;

    @Autowired
    private IGameRoomService gameRoomService;

    @Autowired
    private IRoomUserService roomUserService;

    @Autowired
    private RedisUtil redisUtil;

    final String CHESS_ROOM_USER_LIST ="chess:room:user:list";

    public void enterRoom(String roomId, SecurityUser securityUser){
        String userId = securityUser.getId();

        if (userInstanceService.userInRoomInstance(userId)) {
            log.error("用户已在游戏中无须重新进入 userId={},roomId={}", userId,roomId);
            return;
        }

        RoomUser roomUser = roomUserService.queryRoomUser(roomId, userId);
        if(roomUser != null){
            roomUser.setStatus(1);
            roomUser.setUserType(1);
            roomUserService.updateById(roomUser);
        }
        String sessionId = securityUser.getSessionId();
        roomUser = new RoomUser();
        roomUser.setUserId(userId);
        roomUser.setRoomId(roomId);
        roomUser.setSessionId(sessionId);
        roomUser.setStatus(1);
        roomUser.setUserType(1);
        roomUserService.save(roomUser);
        redisUtil.put(CHESS_ROOM_USER_LIST,userId,roomId);
    }

    public String getUserRoomIdForDb(String userId){
        RoomUser roomUser = roomUserService.queryRoomId(userId);
        if(roomUser == null){
            return null;
        }
        return roomUser.getRoomId();
    }
    public String getUserRoomId(String userId){
        String roomId = redisUtil.hget(CHESS_ROOM_USER_LIST,userId);
        if(StringUtils.isNotBlank(roomId)){
            return roomId;
        }
        return this.getUserRoomIdForDb(roomId);
    }

    public Map<String,String> getAllUserRoomIds(){
        return redisUtil.hmget(CHESS_ROOM_USER_LIST);
    }

    public RoomInfoVo initRoomInfo(String roomId) {
        RoomInfoVo roomInfoVo = new RoomInfoVo();
        RoomInstance roomInstance = roomInstanceService.initRoom(roomId);
        roomInfoVo.initRoomInfo(roomInstance);
        userInstanceService.initUser(roomId, roomInstance.getId());
        playCardsInstanceService.initPlayCards(roomId, roomInstance.getId());
        roomInfoVo.setCards(new ArrayList<>());
        roomInfoVo.setUsers(new ArrayList<>());
        return roomInfoVo;
    }


    public RoomInfoVo doGetRoomInfo(String roomId) {
        RoomInfoVo roomInfoVo = new RoomInfoVo();
        RoomInstance roomInstance = roomInstanceService.getRoomInstance(roomId);
        roomInfoVo.initRoomInfo(roomInstance);
        List<PlayCardsInstance> playCardsInstances = playCardsInstanceService.queryPlayCards(roomId, roomInstance.getId());
        List<UserInstance> userInstances = userInstanceService.queryUser(roomId, roomInstance.getId());
        roomInfoVo.buildUserInfo(userInstances, playCardsInstances);
        //更新缓存
        redisUtil.set("chess:room:" + roomId, roomInfoVo);
        return roomInfoVo;
    }


    @Transactional
    public RoomInfoVo joinRoom(String roomId, SecurityUser securityUser, Integer seatNo) {
        String userId = securityUser.getId();
        if (!roomInstanceService.hasRoomInstance(roomId)) {
            this.initRoomInfo(roomId);
        }
        if (userInstanceService.userInRoomInstance(userId)) {
            log.error("用户已在其它房间内 userId={},seatNo={}", userId,seatNo);
            throw new BuziException("用户已在其它房间内");
        }

        RoomInstance roomInstance = roomInstanceService.getRoomInstance(roomId);

        String status = roomInstance.getStatus();
        if(!StringUtils.equals(status,"NA")){
            log.error("加入失败 userId={}，status={}", userId,status);
            throw new BuziException("加入失败");
        }

        this.userInstanceService.joinRoom(roomInstance, securityUser, seatNo);

        return doGetRoomInfo(roomId);
    }


    public RoomInfoVo getRoomInfo(String roomId) {
        String cacheKey = "chess:room:" + roomId;
        RoomInfoVo roomInfoVo = redisUtil.get(cacheKey);
        if (roomInfoVo == null && !roomInstanceService.hasRoomInstance(roomId)) {
            roomInfoVo = initRoomInfo(roomId);
            redisUtil.set(cacheKey, roomInfoVo);
            return roomInfoVo;
        }

        return this.doGetRoomInfo(roomId);
    }

    private void receiveCard(String roomId) {
        RoomInstance roomInstance = this.roomInstanceService.getRoomInstance(roomId);
        String roomInstanceId = roomInstance.getId();
        List<PlayCardsInstance> playCardsInstances = this.playCardsInstanceService.queryPlayCards(roomId, roomInstanceId);
        List<PlayCardsInstance> availableList = playCardsInstances.stream().filter(i -> i.getUserId() == null).collect(Collectors.toList());

        Collections.shuffle(availableList);
        List<UserInstance> userInstances = this.userInstanceService.queryUser(roomId, roomInstanceId);

        List<PlayCardsInstance> updateList = new ArrayList<>();
        int index = 0;
        for (UserInstance userInstance : userInstances) {
            for (int x = 0; x < 2; x++) {
                PlayCardsInstance playCardsInstance = availableList.get(index);
                playCardsInstance.setUserId(userInstance.getUserId());
                updateList.add(playCardsInstance);
                index++;
            }
        }

        if (CollectionUtils.isNotEmpty(updateList)) {
            this.playCardsInstanceService.saveBatch(updateList);
        }
    }


    @Override
    @Transactional
    public RoomInfoVo startGame(String userId,String roomId) {
        RoomInstance roomInstance = this.roomInstanceService.getRoomInstance(roomId);

        if(roomInstance == null){
            log.error("房间不存在 roomId={}", roomId);
            throw new BuziException("房间不存在");
        }


        long playerCount = this.userInstanceService.queryRoomUserCount(roomId);

        if (playerCount <= 1) {
            log.error("Not Enough Players roomId={},playerCount={}", roomId,playerCount);
            throw new BuziException("Not Enough Players");
        }

        if (playerCount > 10) {
            log.error("Game already started roomId={},playerCount={}", roomId,playerCount);
            throw new BuziException("Game already started");
        }


        UserInstance userInstance = this.userInstanceService.queryByUserId(roomId, userId);
        if(userInstance.getSeatNo() != 0){
            log.error("非庄家用户不允许操作 roomId={}，userId={}", roomId,userId);
            throw new BuziException("非庄家用户不允许操作");
        }



        //为用户发牌
        receiveCard(roomId);

        return this.doGetRoomInfo(roomId);
    }
}
