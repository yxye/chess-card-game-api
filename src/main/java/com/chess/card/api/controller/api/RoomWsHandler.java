package com.chess.card.api.controller.api;

import com.chess.card.api.event.GameRoomEventProcessor;
import com.chess.card.api.event.RoomEventProducer;
import com.chess.card.api.game.entity.GameRoom;
import com.chess.card.api.game.entity.UserInfo;
import com.chess.card.api.game.service.IGameRoomService;
import com.chess.card.api.game.service.IRoomUserService;
import com.chess.card.api.game.service.IUserInfoService;
import com.chess.card.api.security.model.SecurityUser;
import com.chess.card.api.ws.DefaultWsContextService;
import com.chess.card.api.ws.annotation.WebSocketApiHandler;
import com.chess.card.api.ws.annotation.WebSocketApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 */

@Api(tags = "房间服务接口(ws)")
@Slf4j
@WebSocketApiService
@RestController
public class RoomWsHandler {

    @Autowired
    private DefaultWsContextService defaultWsContextService;

    @Autowired
    private IRoomUserService roomUserService;

    @Autowired
    private IGameRoomService gameRoomService;

    @Autowired
    private GameRoomEventProcessor gameRoomEventProcessor;


    @Autowired
    @ApiOperation(value = "查询游戏对应的房间信息", notes = "查询游戏对应的房间信息！")
    @WebSocketApiHandler("gameRoomList")
    public List<GameRoom> gameRoomList(){
        return  gameRoomService.list();
    }


    /**
     * 订阅房间事件
     * @return
     */
    @ApiOperation(value = "进入游戏房间", notes = "加入游戏房间！")
    @WebSocketApiHandler("enterGameRoom")
    public void enterGameRoom(String roomId){
        SecurityUser securityUser = defaultWsContextService.getSecurityUser();
        gameRoomEventProcessor.subscribe(roomId,securityUser.getId());
    }


    @ApiOperation(value = "加入游戏", notes = "加入游戏房间！")
    @WebSocketApiHandler("joinGame")
    public void joinGame(String roomId){
        RoomEventProducer producer = gameRoomEventProcessor.getProducer(roomId);
        producer.onData(roomId, "");
    }



    /**
     * 一.条件检查
     * 二.给用户发牌
     *
     *
     * @return
     */
    @ApiOperation(value = "开始游戏", notes = "开始游戏！")
    @WebSocketApiHandler("startGame")
    public void startGame(){

    }

}
