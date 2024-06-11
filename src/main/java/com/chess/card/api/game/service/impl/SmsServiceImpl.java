package com.chess.card.api.game.service.impl;

import cn.hutool.core.date.DateUtil;
import com.chess.card.api.exception.BuziException;
import com.chess.card.api.game.service.ISmsService;
import com.chess.card.api.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SmsServiceImpl implements ISmsService {

    private String templateCode;
    private final static int MOBILE_MAX_SEND_COUNT = 10;

    private final static int IP_MAX_SEND_COUNT = 15;

    @Autowired
    private RedisUtil redisUtil;
    public void checkLimitAndAdd(String mobile, String ip) {

        this.execCheckLimit(mobile, MOBILE_MAX_SEND_COUNT, true);

        this.execCheckLimit(ip, IP_MAX_SEND_COUNT, true);
    }

    public void checkLimit(String mobile, String ip) {
        this.execCheckLimit(mobile, MOBILE_MAX_SEND_COUNT, false);

        this.execCheckLimit(ip, IP_MAX_SEND_COUNT, false);
    }

    public void execCheckLimit(String flag, int maxCount, boolean isAdd) {
        String sendCountKey = "USER:SEND:SMS:COUNT:" + DateUtil.today() + ":" + flag;
        Object _count = redisUtil.get(sendCountKey);
        Long count = null;
        if (_count != null) {
            count = Long.valueOf(_count + "");
        }

        if (isAdd) {
            redisUtil.addAndGet(sendCountKey);
        }

        if (count == null) {
            count = 1L;
        }

        if (count > maxCount) {
            log.error("今日验证码发送次数已超上限，请明天再来！ count={},maxCount={}",count, maxCount);
            throw new BuziException("今日验证码发送次数已超上限，请明天再来！");
        }
    }

    public void sendSms(String mobile, String code, String ip) {
        try {
            String parallelLimitKey = "SMS:PARALLEL:LIMIT:" + mobile;
            if (!this.redisUtil.setNx(parallelLimitKey, DateUtil.now(), 60)) {
                log.info("短信并发过快：{}", mobile);
                throw new BuziException("操作过快,请休息会儿再试！");
            }

            if (StringUtils.isBlank(code)) {
                log.error("发送内容为空，取消发送！code=null");
                return;
            }
            //锁定后重新检验，防止并发情况
            this.checkLimit(mobile, ip);

            this.doSend(mobile, code);

            this.redisUtil.del(parallelLimitKey);
        } catch (BuziException e) {
            throw e;
        } catch (Exception e) {
            log.error("短信发送异常！", e);
        }
    }

    private void doSend(String phone,String code) {

        if (StringUtils.isBlank(this.templateCode)) {
            log.error("短信模板代码为空，发送失败！ templateCode=null,code={}", code);
            return;
        }

        if (StringUtils.isBlank(code)) {
            log.error("发送内容为空，取消发送！code=null");
            return;
        }

    }
}
