package com.chess.card.api.bean.room;

import com.chess.card.api.bean.UserAction;
import com.chess.card.api.exception.BuziException;
import com.chess.card.api.game.entity.*;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class UserInfoVo implements Serializable {
    /**
     *
     */
    private String nickname;
    /**
     *
     */
    private String userId;

    /**
     *
     */
    private String roomId;

    /**
     *
     */
    private List<UserAction> userActs;

    /**
     *
     */
    private BigDecimal buyScore;

    /**
     *
     */
    private BigDecimal currentScore;

    /**
     *
     */
    private BigDecimal changeScore;

    /**
     *
     */
    private String avatar;

    /**
     *
     */
    private List<CardInfoVo> cards;


    public static UserInfoVo initialize(UserInstance userInstance, Map<String, List<PlayCardsInstance>> userCards){
        UserInfo userInfo = userInstance.getUserInfo();
        String userId = userInfo.getId();
        UserInfoVo userInfoVo = new UserInfoVo();
        userInfoVo.setUserId(userInstance.getId());
        userInfoVo.setNickname(userInfo.getNickName());
        userInfoVo.setBuyScore(userInstance.getBuyScore());
        userInfoVo.setCurrentScore(userInstance.getCurrentScore());
        userInfoVo.setChangeScore(userInstance.getChangeScore());
        userInfoVo.setAvatar(userInfoVo.getAvatar());
        userInfoVo.setAvatar(userInfoVo.getAvatar());
        if(userCards.containsKey(userId)){
            List<PlayCardsInstance> playCardsInstances = userCards.get(userId);
            if(CollectionUtils.isNotEmpty(playCardsInstances)){
                userInfoVo.setCards(playCardsInstances.stream().map(i->CardInfoVo.initialize(i)).collect(Collectors.toList()));
            }
        }
        return userInfoVo;
    }
}
