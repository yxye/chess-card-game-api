package com.chess.card.api.game.service.impl;

import com.chess.card.api.game.entity.GameInstance;
import com.chess.card.api.game.mapper.GameInstanceMapper;
import com.chess.card.api.game.service.IGameInstanceService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 游戏实例
 * @Author: yxye
 * @Date:   2024-04-28
 * @Version: V1.0
 */
@Service
public class GameInstanceServiceImpl extends ServiceImpl<GameInstanceMapper, GameInstance> implements IGameInstanceService {

}
