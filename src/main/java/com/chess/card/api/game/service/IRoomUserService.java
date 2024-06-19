package com.chess.card.api.game.service;

import com.chess.card.api.game.entity.RoomUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 房间用户
 * @Author: yxye
 * @Date:   2024-04-28
 * @Version: V1.0
 */
public interface IRoomUserService extends IService<RoomUser> {

    public RoomUser queryRoomUser(String roomId,String userId);


    public boolean updateStatus(String userId, Integer status);


    public RoomUser queryRoomId(String userId);
}
