package com.chess.card.api.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chess.card.api.entity.SyPrizeStore;
import com.chess.card.api.entity.SySignIn;
import com.chess.card.api.bean.UserInfoVo;
import com.chess.card.api.entity.SyUserInfo;
import com.chess.card.api.exception.BuziException;
import com.chess.card.api.exception.UnLoginException;
import com.chess.card.api.service.ISyLuckyDrawService;
import com.chess.card.api.service.ISySignInService;
import com.chess.card.api.service.ISyUserInfoService;
import com.chess.card.api.utils.JwtUtils;
import com.chess.card.api.utils.RedisCacheUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class BaseController {

    @Autowired
    protected RedisCacheUtils redisUtil;

    @Autowired
    protected ISySignInService signInService;

    @Autowired
    protected ISyLuckyDrawService luckyDrawService;

    @Value("${static.url}")
    private String staticUrl;

    public static HttpSession getSession() {
        return getSession(true);
    }

    @Autowired
    protected ISyUserInfoService userInfoService;


    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static HttpSession getSession(boolean flag) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getSession(flag);
    }

    protected String getReqUrl() {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String scheme = req.getScheme();
        String host = req.getHeader("host"); // 请求服务器
        if (host.contains(":80")) {
            scheme = "http";
            host = host.replace(":80", "");
        } else if (host.contains(":443")) {
            scheme = "https";
            host = host.replace(":443", "");
        }
        String url = scheme + "://"; //请求协议 http 或 https
        url += host;
        url += req.getRequestURI(); // 工程名
        if (req.getQueryString() != null) { //判断请求参数是否为空
            url += "?" + req.getQueryString(); // 参数
        }
        return url;
    }



    protected UserInfoVo getUserInfo() {
        HttpServletRequest request = this.getRequest();
        UserInfoVo cacheUserInfo = JwtUtils.parseJwt(request);
        SyUserInfo stoUser = userInfoService.getById(cacheUserInfo.getId());
        if(stoUser==null){
            log.error("用户不存在 stoUser=null userId:{}",cacheUserInfo.getId());
            throw new UnLoginException("请登录");
        }

        if(StringUtils.equals("01",stoUser.getStatus())){
            log.error("用户已停用  getUserInfo userId:{},status={}",stoUser.getId(),stoUser.getStatus());
            throw new BuziException("用户已停用");
        }

        UserInfoVo instance = UserInfoVo.getInstance(stoUser);
        instance.setSessionKey(cacheUserInfo.getSessionKey());
        instance.setToken(cacheUserInfo.getToken());
        return instance;
    }
    protected boolean isLogin() {
        HttpServletRequest request = this.getRequest();
        return JwtUtils.isLogin(request);
    }

    protected JSONObject packUserInfo(UserInfoVo userInfoVo, String token) {
        JSONObject resutl = new JSONObject();
        if(StringUtils.isBlank(token)){
            token = this.getRequest().getHeader("token");
        }

        if(StringUtils.isBlank(token)){
            throw new UnLoginException("请登录");

        }

        resutl.put("token", token);
        resutl.put("id", userInfoVo.getId());
        resutl.put("nickName", userInfoVo.getNickName());
        resutl.put("gameCount", userInfoVo.getGameCount());
        resutl.put("avatar", userInfoVo.getAvatar());
        resutl.put("mobile", userInfoVo.getMobile());
        resutl.put("integral", userInfoVo.getIntegral());
        resutl.put("sessionKey", userInfoVo.getSessionKey());
        resutl.put("region", userInfoVo.getRegion());
        resutl.put("staticUrl", staticUrl);

        LambdaQueryWrapper<SySignIn> query = new LambdaQueryWrapper<SySignIn>();
        query.eq(SySignIn::getUserId, userInfoVo.getId());
        query.ge(SySignIn::getCreateTime, DateUtil.beginOfDay(new Date()));
        query.le(SySignIn::getCreateTime, DateUtil.endOfDay(new Date()));
        resutl.put("signCount", signInService.count(query));
        return resutl;
    }




    protected List<JSONObject> packPrizes(List<SyPrizeStore> prizeList){
        return prizeList.stream().map(i->this.luckyDrawService.packPrize(i)).collect(Collectors.toList());
    }

    protected  String getAppId() {
        return this.getRequest().getHeader("app-id");
    }




    protected  <T,V> T lockUserRequest(V params,Function<V,String> keyFunction, Function<V,T> callback) {
        String requestURI = this.getRequest().getRequestURI();
        String lockField = keyFunction.apply(params);
        String lockKey = "USER:LOCK:REQUEST:" + StringUtils.upperCase(requestURI) + ":" + StringUtils.upperCase(lockField);
        Boolean lockState = Boolean.FALSE;
        lockState = redisUtil.setNx(lockKey, DateUtil.now(), 30);
        try {
            if (!lockState) {
                log.warn("lockUserRequestByUserId userId={},requestURI={}",lockField,requestURI);
                throw new BuziException("处理中请稍后！");
            }
            return callback.apply(params);
        }finally {
            if(lockState){
                redisUtil.del(lockKey);
            }
        }
    }
}
