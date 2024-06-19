package com.chess.card.api.game.service;

import com.chess.card.api.game.entity.PlayCardsInstance;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 实例下的牌
 * @Author: yxye
 * @Date:   2024-04-28
 * @Version: V1.0
 */
public interface IPlayCardsInstanceService extends IService<PlayCardsInstance> {

    /**
     *
     * @param roomId
     * @param instanceId
     * @return
     */
    public List<PlayCardsInstance> queryPlayCards(String roomId, String instanceId);


    /**
     *
     * @param roomId
     * @param instanceId
     * @return
     */
    List<PlayCardsInstance> initPlayCards(String roomId, String instanceId);
}
