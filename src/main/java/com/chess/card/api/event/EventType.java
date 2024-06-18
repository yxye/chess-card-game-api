package com.chess.card.api.event;

import com.google.common.graph.Network;

public enum EventType {
    ENTER_ROOM,//进入游戏
    JOIN_GAME,//加入游戏
    START_GAME,//开始游戏
    USER_ACTION,//用户动作
    USER_OFFLINE,//用户动作
    SLOW_CONNECTION_SPEED,//网速慢
    NOTIFICATIONS,//系统消息通知
    GAME_OVER;//系统消息通知
}
