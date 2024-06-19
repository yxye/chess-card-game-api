package com.chess.card.api.service;

import com.chess.card.api.bean.room.RoomInfoVo;
import com.chess.card.api.security.model.SecurityUser;

import java.util.Map;

public interface IChessRoomService {
    /**
     * 进入房间
     * @param roomId
     * @param securityUser
     */
    public void enterRoom(String roomId, SecurityUser securityUser);

    public RoomInfoVo getRoomInfo(String roomId);

    /**
     * 加入房间
     * @param roomId
     * @param securityUser
     * @param seatNo
     * @return
     */
    public RoomInfoVo joinRoom(String roomId, SecurityUser securityUser, Integer seatNo);




    /**
     * 开始游戏
     * @param roomId
     * @return
     */
    public RoomInfoVo startGame(String userId,String roomId);


    public String getUserRoomId(String userId);


    public Map<String,String> getAllUserRoomIds();
}
