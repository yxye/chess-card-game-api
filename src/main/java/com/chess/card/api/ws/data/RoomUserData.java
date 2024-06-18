package com.chess.card.api.ws.data;

import com.chess.card.api.ws.WebSocketSessionRef;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Data
public class RoomUserData implements Serializable {

    private List<WebSocketSessionRef> roomUser = new CopyOnWriteArrayList<>();


    private List<WebSocketSessionRef> lookerUser = new CopyOnWriteArrayList<>();


    public void addLookerUser(WebSocketSessionRef webSocketSessionRef){
        lookerUser.removeIf(item->StringUtils.equals(item.getSessionId(),webSocketSessionRef.getSessionId()));
        lookerUser.add(webSocketSessionRef);
    }


    public void addRoomUser(WebSocketSessionRef webSocketSessionRef){
        this.removeUser(webSocketSessionRef);
        roomUser.add(webSocketSessionRef);
    }

    public void addUser(WebSocketSessionRef webSocketSessionRef){
        String seatId = webSocketSessionRef.getSeatId();
        this.removeUser(webSocketSessionRef);
        if(StringUtils.isNotBlank(seatId)){
            roomUser.add(webSocketSessionRef);
        }else{
            lookerUser.add(webSocketSessionRef);
        }
    }

    public void removeUser(WebSocketSessionRef webSocketSessionRef){
        roomUser.removeIf(item->StringUtils.equals(item.getSessionId(),webSocketSessionRef.getSessionId()));
        lookerUser.removeIf(item->StringUtils.equals(item.getSessionId(),webSocketSessionRef.getSessionId()));
    }

    public boolean isEmpty() {
        return roomUser.isEmpty() && lookerUser.isEmpty();
    }
}
