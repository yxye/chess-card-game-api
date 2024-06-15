package com.chess.card.api.controller.api;

import com.chess.card.api.game.entity.UserInfo;
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

/**
 *
 */

@Api(tags = "游戏服务接口(ws)")
@Slf4j
@WebSocketApiService
@RestController
public class GameWsHandler {

    @Autowired
    private DefaultWsContextService defaultWsContextService;

    @Autowired
    private IUserInfoService userInfoService;


    @ApiOperation(value = "查询用户信息", notes = "用于登录后查询用户信息！")
    @WebSocketApiHandler("gameList")
    public UserInfo gameList(){
        SecurityUser securityUser = defaultWsContextService.getSecurityUser();
        return  userInfoService.getById(securityUser.getId());
    }




}
