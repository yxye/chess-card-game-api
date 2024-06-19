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

    }
    @Scheduled(fixedRate = 5000) //每个5秒提取一次
    @SendTo("/receive/message") //广播所有用户
    public String sendAllMessage () {

        return "callback";
    }
}

作者：逝水鎏金
链接：https://juejin.cn/post/6844903816320516110
来源：稀土掘金
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。