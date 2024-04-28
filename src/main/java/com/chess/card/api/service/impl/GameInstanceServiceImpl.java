package com.chess.card.api.service.impl;

import com.chess.card.api.entity.GameInstance;
import com.chess.card.api.mapper.GameInstanceMapper;
import com.chess.card.api.service.IGameInstanceService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 游戏实例
 * @Author: jeecg-boot
 * @Date:   2024-04-28
 * @Version: V1.0
 */
@Service
public class GameInstanceServiceImpl extends ServiceImpl<GameInstanceMapper, GameInstance> implements IGameInstanceService {

}
