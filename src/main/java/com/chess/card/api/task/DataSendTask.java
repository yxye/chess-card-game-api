package com.chess.card.api.task;

import com.chess.card.api.mail.MailServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataSendTask {

    @Autowired
    private MailServiceImpl mailService;

    //@Scheduled(cron ="0 0 3 * * ?")
    public void sendEmailToUserTask(){
        try {
            log.info("sendEmailToUserTask start");
            mailService.sendEmailToUser();
        }catch (Exception e){
            log.error("Error sending email",e);
        }finally {
            log.info("sendEmailToUserTask end");
        }
    }
}
