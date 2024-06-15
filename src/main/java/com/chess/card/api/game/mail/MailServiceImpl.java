package com.chess.card.api.game.mail;

import cn.hutool.core.lang.Validator;
import com.chess.card.api.exception.BuziException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;


@Slf4j
@Service
public class MailServiceImpl {

    @Autowired
    JavaMailSenderImpl mailSender;//实施邮件发送的对象

    /**
     * 发送邮件验证码
     * @param email
     * @param code
     */
    public void sendEmailCode(String email,String code) {
        try {
            if(!Validator.isEmail(email)){
                log.error("sendEmailCode 发送失败 邮箱格式有误 email={}",email);
                throw new BuziException("邮箱格式有误！");
            }

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            //收件人
            messageHelper.setTo(email);
            //发件人
            messageHelper.setFrom("yxyehn@163.com");
            //标题
            messageHelper.setSubject("Your Code - "+code);
            //发送html
            String html = String.format("<html><body><p>Hello</p><p>Your code is: %s.</p><p>If you didn't request this, simply ignore this message.</p></body></html>",code);
            messageHelper.setText(html, true);
            mailSender.send(message);
        }catch (Exception e){
            log.error("sendEmailCode error",e);
        }
    }



}
