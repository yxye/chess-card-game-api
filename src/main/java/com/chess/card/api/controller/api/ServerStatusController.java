package com.chess.card.api.controller.api;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
@EnableScheduling
public class ServerStatusController  {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    //当浏览器向服务端发送请求时，通过@MessageMapping映射的地址，类似于@RequestMapping
    @MessageMapping("/serverStatus")
    @SendTo("/receive/message") //广播所有用户
    //传递的参数会自动的被注入到userbean中
    public String serverStatus (JSONObject receiveBean) throws InterruptedException {
        return "tst";
    }

    @MessageMapping("/test")
    @SendTo("/receive/message")
    //传递的参数会自动的被注入到userbean中
    public String test (String name) throws InterruptedException {
        System.out.println("name=============="+name);
        return "name=============="+name;
    }
    @Scheduled(fixedRate = 5000) //每个5秒提取一次
    @SendTo("/receive/message") //广播所有用户
    public String sendAllMessage () {
        return "callback";
    }
}
