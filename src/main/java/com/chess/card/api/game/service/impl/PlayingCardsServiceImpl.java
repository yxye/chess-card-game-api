package com.chess.ws.api.game.service.impl;

import com.chess.ws.api.game.entity.PlayingCards;
import com.chess.ws.api.game.mapper.PlayingCardsMapper;
import com.chess.ws.api.game.service.IPlayingCardsService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 扑克牌
 * @Author: jeecg-boot
 * @Date:   2024-04-28
 * @Version: V1.0
 */
@Service
public class PlayingCardsServiceImpl extends ServiceImpl<PlayingCardsMapper, PlayingCards> implements IPlayingCardsService {

}
