package com.chess.ws.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Slf4j
@Controller
@ResponseBody
public class BaseController {

    public static final String RESET_TYPE = "reset";

    public static final String REGISTER_TYPE = "register";

    public static HttpSession getSession() {
        return getSession(true);
    }

    protected static HttpSession getSession(boolean flag) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getSession(flag);
    }

    protected  HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    protected String buildSmsCacheKey(String mobile,String type){
        return   "USER:OPERATION:AUTH:CODE:" + mobile + ":" + type + ":" + getSession().getId();
    }

    /**
     * 获取ip地址
     * @return IP地址
     */
    protected String getIpAddr(HttpServletRequest request) {
        String ip = "";
        try {
            ip = request.getHeader("Cdn-Src-Ip");
            log.info("Cdn-Src-Ip: " + ip);

            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Ali-Cdn-Real-Ip");
                log.info("Ali-Cdn-Real-Ip: " + ip);
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("x-forwarded-for");
                log.info("x-forwarded-for" + ip);
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("x-real-ip");
                log.info("x-real-ip=" + ip);
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
                log.info("Proxy-Client-IP=" + ip);
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
                log.info("WL-Proxy-Client-IP=" + ip);
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
                log.info("getRemoteAddr()=" + ip);
            }
        } catch (Exception e) {
            log.error("WSY_ERROR_MONITOR_获取IP地址异常！", e);
        }
        return ip;
    }
}
