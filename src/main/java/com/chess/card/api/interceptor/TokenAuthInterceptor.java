package com.chess.card.api.interceptor;

import com.alibaba.fastjson.JSON;
import com.chess.card.api.annotation.UnLogin;
import com.chess.card.api.utils.JwtUtils;
import com.chess.card.api.utils.RedisCacheUtils;
import com.chess.card.api.vo.Result;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;

@Slf4j
@Component
public class TokenAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisCacheUtils redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        UnLogin unLogin = method.getAnnotation(UnLogin.class);
        String token = request.getHeader("token");
        if (unLogin == null) {
            Claims claims = JwtUtils.parseJwt(token);
            if (claims == null) {
                PrintWriter writer = null;
                try {
                    //普通用户登录
                    log.info("用户没有登录请先登录 token={}",token);
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json;charset=UTF-8");
                    writer = response.getWriter();
                    writer.print(JSON.toJSONString(Result.error(9000, "请先登录")));

                    return false;
                } catch (Exception e) {
                    log.error("登录异常 ERRMSG:{}", e);
                } finally {
                    if (writer != null) {
                        writer.close();
                        response.flushBuffer();
                    }
                }
            }
            request.setAttribute("user", claims);
        }
        return true;

    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception)throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView view) throws Exception {
    }
}
