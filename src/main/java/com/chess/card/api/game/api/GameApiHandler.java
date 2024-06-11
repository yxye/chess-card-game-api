package com.chess.ws.api.game.api;


import com.chess.ws.api.ws.annotation.WebSocketApiHandler;
import com.chess.ws.api.ws.annotation.WebSocketApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 *
 */
@Slf4j
@WebSocketApiService
@Component
public class GameApiHandler {

    @WebSocketApiHandler("getGameName")
    public String getGameName(String name){
        log.info("getGameName name={}"+name);
        return "getGameName"+name;
    }

}
