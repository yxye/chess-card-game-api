package com.chess.ws.api.game.service;

public interface ISmsService {

    /**
     * 发送验证码
     * @param phone
     * @param code
     * @param ip
     */
    public void sendSms(String phone,String code,String ip);


    public void checkLimitAndAdd(String mobile, String ip);
}
