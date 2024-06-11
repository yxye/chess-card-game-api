/**
 * Copyright © 2016-2023 The Thingsboard Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.chess.card.api.plugin.websocket;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;


@Service

@Slf4j
public class TbWebSocketHandler extends TextWebSocketHandler implements WebSocketMsgEndpoint {

    @Override
    public void send(WebSocketSessionRef sessionRef, int subscriptionId, String msg) throws IOException {

    }

    @Override
    public void sendPing(WebSocketSessionRef sessionRef, long currentTime) throws IOException {

    }

    @Override
    public void close(WebSocketSessionRef sessionRef, CloseStatus withReason) throws IOException {

    }
}
