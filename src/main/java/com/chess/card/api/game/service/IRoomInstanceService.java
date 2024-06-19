package com.chess.card.api.game.service;

import com.chess.card.api.game.entity.RoomInstance;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 房间实例
 * @Author: yxye
 * @Date:   2024-06-16
 * @Version: V1.0
 */
public interface IRoomInstanceService extends IService<RoomInstance> {

    /**
     * 初始化房间
     * @param roomId
     * @return
     */
    public RoomInstance initRoom(String roomId);


    public boolean hasRoomInstance(String roomId);



    public RoomInstance getRoomInstance(String roomId);
}
