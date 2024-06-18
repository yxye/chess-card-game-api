package com.chess.card.api.game.service;

import com.chess.card.api.game.entity.RoomInstance;
import com.chess.card.api.game.entity.UserInstance;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chess.card.api.security.model.SecurityUser;

import java.util.List;

/**
 * @Description: 用户实例
 * @Author: jeecg-boot
 * @Date:   2024-06-16
 * @Version: V1.0
 */
public interface IUserInstanceService extends IService<UserInstance> {

    public List<UserInstance> queryUser(String roomId, String roomInstanceId);

    public UserInstance queryByUserId(String roomId, String userId);

    public boolean initUser(String roomId,String roomInstanceId);


    public boolean userInRoomInstance(String userId);


    public void joinRoom(RoomInstance roomInstance, SecurityUser securityUser, Integer seatNo);


    public long queryRoomUserCount(String roomId);
}
