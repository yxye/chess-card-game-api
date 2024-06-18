package com.chess.card.api.bean.room;

import com.chess.card.api.game.entity.PlayCardsInstance;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CardInfoVo implements Serializable {
    /**
     *
     */
    private String cardId;


    /**花色：H红桃，D方块，C梅花，S黑桃*/
    private String cardSuit;

    /**
     * 排序值
     */
    private String cardOrder;

    /**
     * -1为公共牌
     */
    private String userId;

    /**
     *
     */
    private String roomId;

    public static CardInfoVo initialize(PlayCardsInstance playCardsInstance){
        CardInfoVo cardInfoVo = new CardInfoVo();
        cardInfoVo.setCardId(playCardsInstance.getCardId());
        cardInfoVo.setCardSuit(playCardsInstance.getCardSuit());
        cardInfoVo.setCardOrder(playCardsInstance.getCardOrder());
        cardInfoVo.setUserId(playCardsInstance.getUserId());
        cardInfoVo.setRoomId(playCardsInstance.getRoomId());
        return cardInfoVo;
    }
}
