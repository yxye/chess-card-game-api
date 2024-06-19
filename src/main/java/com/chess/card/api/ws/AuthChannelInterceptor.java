package com.chess.card.api.ws;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.List;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class AuthChannelInterceptor implements ChannelInterceptor {
  /**
   * 连接前监听
   *
   * @param message
   * @param channel
   * @return
   */
  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {
      StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
      //1、判断是否首次连接
      if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
          //2、判断token
          List<String> nativeHeader = accessor.getNativeHeader("Authorization");
          if (nativeHeader != null && !nativeHeader.isEmpty()) {
              String token = nativeHeader.get(0);
              if (StringUtils.isNotBlank(token)) {
                  //todo,通过token获取用户信息，下方用loginUser来代替


                  if (token != null) {
                      //如果存在用户信息，将用户名赋值，后期发送时，可以指定用户名即可发送到对应用户
                      Principal principal = new Principal() {
                          @Override
                          public String getName() {
                              return "test";
                          }
                      };
                      accessor.setUser(principal);
                      return message;
                  }
              }
          }
          return null;
      }
      //不是首次连接，已经登陆成功
      return message;
  }
}
