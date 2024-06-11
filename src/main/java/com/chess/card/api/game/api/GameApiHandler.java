package com.chess.card.api.game.api;


import com.chess.card.api.ws.annotation.WebSocketApiHandler;
import com.chess.card.api.ws.annotation.WebSocketApiService;
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
