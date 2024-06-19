package com.chess.card.api.game.service.impl;

import com.chess.card.api.game.entity.GameInfo;
import com.chess.card.api.game.mapper.GameInfoMapper;
import com.chess.card.api.game.service.IGameInfoService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 游戏信息
 * @Author: yxye
 * @Date:   2024-06-15
 * @Version: V1.0
 */
@Service
public class GameInfoServiceImpl extends ServiceImpl<GameInfoMapper, GameInfo> implements IGameInfoService {

}
