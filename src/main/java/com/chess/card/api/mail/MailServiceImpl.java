package com.chess.card.api.mail;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.chess.card.api.entity.SyEmail;
import com.chess.card.api.excel.ExcelServiceImpl;
import com.chess.card.api.service.ISyEmailService;
import com.chess.card.api.bean.ExcelDataBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class MailServiceImpl {

    @Autowired
    JavaMailSenderImpl mailSender;//实施邮件发送的对象

    @Autowired
    private ExcelServiceImpl excelService;
    @Autowired
    private ISyEmailService emailService;

    public void sendEmailToUser() {
        this.sendEmailToUser(null);
    }

    public void sendEmailToUser(Map<String, Object> paramsMap) {
        if(paramsMap==null){
            paramsMap = new HashMap<>();
        }

        if(!paramsMap.containsKey("startTime") || !paramsMap.containsKey("endTime")){
            DateTime nowTime = DateUtil.offsetDay(new Date(),-1);
            paramsMap.put("startTime", DateUtil.beginOfDay(nowTime));
            paramsMap.put("endTime", DateUtil.endOfDay(nowTime));
        }

        List<SyEmail> emailList = emailService.queryEmailList(paramsMap);
        if(CollectionUtils.isEmpty(emailList)){
            log.warn("sendEmailToUser error emailList=null,paramsMap={}", JSON.toJSONString(paramsMap));
            return;
        }
        DateTime startTime = (DateTime) paramsMap.get("startTime");
        DateTime endTime = (DateTime) paramsMap.get("endTime");
        final String fileName  = "用户中奖数据" + DateUtil.format(startTime, "yyyyMMddHHmmss")+"-"+DateUtil.format(endTime, "yyyyMMddHHmmss")+ ".xlsx";
        for(SyEmail email:emailList){
            doSendEmailToUser(email,fileName);
        }
    }

    private void doSendEmailToUser(SyEmail email,String fileName) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            //收件人
            messageHelper.setTo(email.getRecipients().split(","));
            //发件人
            messageHelper.setFrom("17521346629@163.com");

            //标题
            messageHelper.setSubject("游戏数据推送");

            //发送html
            String html = String.format("<html><body><h1>游戏数据</h1><h2>日期：%s</h2></body></html>", DateUtil.today());
            messageHelper.setText(html, true);
            Map<String, ExcelDataBean> excelDataMap = email.getExcelDataMap();
            if(excelDataMap==null || excelDataMap.size()==0){
                log.warn("doSendEmailToUser fail,excelDataMap=null");
                return;
            }
            //附件
            InputStream fileInputStream = excelService.createApiCheckExeclFile(excelDataMap);

            messageHelper.addAttachment(fileName, new ByteArrayResource(IOUtils.toByteArray(fileInputStream)));
            mailSender.send(message);
        } catch (Exception e) {
            log.error("doSendEmailToUser error",e);
        }
    }

}
