package com.chess.card.api.game.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.chess.card.api.game.entity.PlayCardsInstance;
import com.chess.card.api.game.mapper.PlayCardsInstanceMapper;
import com.chess.card.api.game.service.IPlayCardsInstanceService;
import com.chess.card.api.game.service.IPlayCardsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 实例下的牌
 * @Author: jeecg-boot
 * @Date:   2024-04-28
 * @Version: V1.0
 */

@Slf4j
@Service
public class PlayCardsInstanceServiceImpl extends ServiceImpl<PlayCardsInstanceMapper, PlayCardsInstance> implements IPlayCardsInstanceService {

    @Autowired
    private IPlayCardsService playCardsService;


    public List<PlayCardsInstance> queryPlayCards(String roomId, String instanceId) {
        LambdaQueryWrapper<PlayCardsInstance> query = Wrappers.lambdaQuery();
        query.eq(PlayCardsInstance::getRoomId,roomId);
        query.eq(PlayCardsInstance::getInstanceId,instanceId);
        return this.list(query);
    }

    public boolean initUserInfo(String roomId, String instanceId) {
        LambdaQueryWrapper<PlayCardsInstance> upWrapper = Wrappers.lambdaQuery();
        upWrapper.eq(PlayCardsInstance::getRoomId,roomId);
        upWrapper.eq(PlayCardsInstance::getInstanceId,instanceId);

        PlayCardsInstance upData = new PlayCardsInstance();
        upData.setUserId(roomId);
        return  this.update(upData,upWrapper);
    }


    @Override
    public List<PlayCardsInstance> initPlayCards(String roomId, String instanceId) {
        List<PlayCardsInstance> playCardsInstances = this.queryPlayCards(roomId, instanceId);
        if(CollectionUtils.isNotEmpty(playCardsInstances)){
            this.initUserInfo(roomId,instanceId);
            return playCardsInstances;
        }

        List<PlayCardsInstance> dataList = playCardsService.list().stream().map(i -> {
            PlayCardsInstance playCardsInstance = new PlayCardsInstance();
            playCardsInstance.setInstanceId(instanceId);
            playCardsInstance.setRoomId(roomId);
            playCardsInstance.setCardOrder(i.getCardOrder());
            playCardsInstance.setCardId(i.getCardId());
            playCardsInstance.setCardSuit(i.getCardSuit());
            return playCardsInstance;
        }).collect(Collectors.toList());
        this.saveBatch(dataList);

        return this.queryPlayCards(roomId,instanceId);
    }
}
