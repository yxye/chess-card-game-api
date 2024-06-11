package com.chess.card.api.game.service.impl;

import com.chess.card.api.game.entity.GameRoom;
import com.chess.card.api.game.mapper.GameRoomMapper;
import com.chess.card.api.game.service.IGameRoomService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 游戏房间
 * @Author: jeecg-boot
 * @Date:   2024-04-28
 * @Version: V1.0
 */
@Service
public class GameRoomServiceImpl extends ServiceImpl<GameRoomMapper, GameRoom> implements IGameRoomService {

}
