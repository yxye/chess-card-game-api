package com.chess.card.api.game.service.impl;

import com.chess.card.api.game.entity.PlayCards;
import com.chess.card.api.game.mapper.PlayCardsMapper;
import com.chess.card.api.game.service.IPlayCardsService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 扑克牌
 * @Author: jeecg-boot
 * @Date:   2024-04-28
 * @Version: V1.0
 */
@Service
public class PlayCardsServiceImpl extends ServiceImpl<PlayCardsMapper, PlayCards> implements IPlayCardsService {

}
