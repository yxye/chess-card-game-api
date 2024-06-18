package com.chess.card.api.bean.room;

import com.chess.card.api.game.entity.GameRoom;
import com.chess.card.api.game.entity.PlayCardsInstance;
import com.chess.card.api.game.entity.RoomInstance;
import com.chess.card.api.game.entity.UserInstance;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class RoomInfoVo implements Serializable {
    /**
     * 房间ID
     */
    private String roomId;
    /**
     * 房间号
     */
    private String roomNum;

    /**
     * 用户数
     * 房间配置的用户数
     */
    private Integer userNum;

    /**
     * 当前用户数
     */
    private Integer actUser;

    /**
     * 小盲注
     */
    private String smallBlind;
    /**
     * 大盲注
     */
    private BigDecimal bigBlind;

    /**
     * 时长
     */
    private Integer duration;


    /**
     * 游戏结束时间
     */
    private Date gameOverTime;


    /**
     * 奖池金额
     */
    private BigDecimal prizePool;

    /**
     * 房间用户
     */
    private List<UserInfoVo> users;

    /**
     * 公共牌
     */
    private List<CardInfoVo> cards;


    public void initRoomInfo(RoomInstance roomInstance) {
        GameRoom gameRoom = roomInstance.getGameRoom();
        this.setRoomId(roomId);
        this.setRoomNum(gameRoom.getRoomNum());
        this.setDuration(gameRoom.getDuration());
        this.setBigBlind(gameRoom.getBigBlind());
        this.setSmallBlind(gameRoom.getSmallBlind());
        this.setPrizePool(roomInstance.getPrizePool());
        this.setUserNum(gameRoom.getUserNum());
    }


    public void buildUserInfo(List<UserInstance> userInstances, List<PlayCardsInstance> playCardsInstances) {
        final Map<String, List<PlayCardsInstance>> userCards = playCardsInstances.stream().filter(i -> StringUtils.isNotBlank(i.getUserId())).collect(Collectors.groupingBy(PlayCardsInstance::getUserId));
        this.setUsers(userInstances.stream().map(i -> UserInfoVo.initialize(i, userCards)).collect(Collectors.toList()));
    }
}
