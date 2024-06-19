package com.chess.card.api.game.service.impl;

import com.chess.card.api.game.entity.GameInstanceRecord;
import com.chess.card.api.game.mapper.GameInstanceRecordMapper;
import com.chess.card.api.game.service.IGameInstanceRecordService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 游戏实例
 * @Author: yxye
 * @Date:   2024-04-28
 * @Version: V1.0
 */
@Service
public class GameInstanceRecordServiceImpl extends ServiceImpl<GameInstanceRecordMapper, GameInstanceRecord> implements IGameInstanceRecordService {

}
