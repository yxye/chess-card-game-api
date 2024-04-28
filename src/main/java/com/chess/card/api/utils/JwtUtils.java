package com.chess.card.api.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.chess.card.api.bean.UserInfoVo;
import com.chess.card.api.exception.UnLoginException;
import com.chess.card.api.exception.BuziException;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * @author yxye
 * @Date 2018-07-12 14:23
 * @Desc JWT工具类
 **/
@Slf4j
public class JwtUtils {

    // Token过期时间1小时（用户登录过期时间是此时间的两倍，以token在reids缓存时间为准）
    public static final long EXPIRE_TIME = 60 * 60 * 1000;

    public static final String SUBJECT = "onehee";

    //秘钥
    public static final String APPSECRET = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkFTX6eEuiNLUqXBzpF9ysxh7h+YhYELjOsTQoQFLxOKw+MVtcebLCl5LW5OuWOEkj1IZLZV8SsATswE4+busX7nzGG+ot0Z15lRfnrajJ/2vmTX66shlJViXQZR/bpUJ72gyqIZM4LXGxgu0I/EUPgoS/9WsUzLYmddAqvVpptzxEDJI97fvtC0n+aLGVX4Vg2+fDS/IztUR0MbL6xj+hUFIhb6Og3mYvY7D6mRk5EtrJhj0CLKQt0bB5sHUiWZ+0KTp2UgRnBdrG02wcATgBPZLbhYgDgZ6OH3R/7y7Wk3VLIPmvi1i676gTdV5UM4x6bLbHaSmMF7bN5toBcsbBwIDAQAB";


    /**
     * 生成jwt
     *
     * @param userInfo
     * @return
     */
    public static String createJwt(UserInfoVo userInfo) {

        if (userInfo == null) {
            return null;
        }
        String userId = userInfo.getId();
        String openId = userInfo.getOpenId();
        userInfo.setUuid(UUID.randomUUID().toString().replace("-", ""));
        if (StringUtils.isBlank(openId)) {
            log.error("createJwt 用户登录失败 openId=null,userId={}", userId);
            throw new UnLoginException("用户登录失败");
        }

        JwtBuilder claim = Jwts.builder().setSubject(SUBJECT)
                .claim("userId", StringUtils.defaultIfBlank(userId,""))
                .claim("nickName", StringUtils.defaultIfBlank(userInfo.getNickName(),""))
                .claim("openId", StringUtils.defaultIfBlank(openId,""))
                .claim("mobile", StringUtils.defaultIfBlank(userInfo.getMobile(),""))
                .claim("sessionKey", userInfo.getSessionKey())
                .claim("uuid", StringUtils.defaultIfBlank(userInfo.getUuid(),""));



        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);

        String token = claim.setIssuedAt(new Date()).setExpiration(cal.getTime()).signWith(SignatureAlgorithm.HS256, APPSECRET).compact();
        return token;
    }


    public static Claims parseJwt(String token) {
        try {
            if(StringUtils.isBlank(token)){
                return null;
            }
            final Claims claims = Jwts.parser().setSigningKey(APPSECRET).parseClaimsJws(token).getBody();
            return claims;
        } catch (ExpiredJwtException e) {
            log.warn("登录过期 message:{}",e.getMessage());
            return null;
        }catch (Exception e) {
            log.error("parseJwt,message:{}",e.getMessage(),e);
            return null;
        }
    }

    public static UserInfoVo parseJwtToUserInfo(String accessToken) {
        try {
            final Claims claims = Jwts.parser().setSigningKey(APPSECRET).parseClaimsJws(accessToken).getBody();
            if ( claims == null) {
                throw new UnLoginException("用户未登录");
            }
            log.debug("parseJwt accessToken={}",accessToken);
            UserInfoVo userInfo = new UserInfoVo();
            userInfo.setId(String.valueOf(claims.get("userId")));
            userInfo.setNickName(String.valueOf(claims.get("nickName")));
            userInfo.setOpenId(String.valueOf(claims.get("openId")));
            userInfo.setSessionKey(String.valueOf(claims.get("sessionKey")));
            userInfo.setNickName(String.valueOf(claims.get("nickName")));
            userInfo.setUuid(String.valueOf(claims.get("uuid")));
            userInfo.setMobile(String.valueOf(claims.get("mobile")));
            userInfo.setToken(accessToken);
            return userInfo;
        } catch (Exception e) {
            throw new UnLoginException("用户未登录");
        }
    }

    public static UserInfoVo parseJwt(HttpServletRequest request) {
        return parseJwt(request, true);
    }

    public static UserInfoVo parseJwt(HttpServletRequest request, boolean isCheck) {
        try {
            String accessToken = request.getHeader("token");
            final Claims claims = Jwts.parser().setSigningKey(APPSECRET).parseClaimsJws(accessToken).getBody();
            if (isCheck && claims == null) {
                throw new UnLoginException("用户未登录");
            }

            if (!isCheck && claims == null) {
                return null;
            }
            log.debug("parseJwt accessToken={}",accessToken);
            UserInfoVo userInfo = new UserInfoVo();
            userInfo.setId(String.valueOf(claims.get("userId")));
            userInfo.setNickName(String.valueOf(claims.get("nickName")));
            userInfo.setOpenId(String.valueOf(claims.get("openId")));
            userInfo.setSessionKey(String.valueOf(claims.get("sessionKey")));
            userInfo.setNickName(String.valueOf(claims.get("nickName")));
            userInfo.setUuid(String.valueOf(claims.get("uuid")));
            userInfo.setMobile(String.valueOf(claims.get("mobile")));
            userInfo.setToken(accessToken);
            return userInfo;
        } catch (Exception e) {
            if (isCheck) {
                throw new UnLoginException("用户未登录");
            }
            return null;
        }
    }

    public static UserInfoVo parseJwtNoCheck(HttpServletRequest request) {
        try {
            String accessToken = request.getHeader("token");
            final Claims claims = Jwts.parser().setSigningKey(APPSECRET).parseClaimsJws(accessToken).getBody();
            if (claims == null) {
                return null;
            }
            UserInfoVo userInfo = new UserInfoVo();
            userInfo.setId(String.valueOf(claims.get("userId")));
            userInfo.setNickName(String.valueOf(claims.get("nickName")));
            userInfo.setOpenId(String.valueOf(claims.get("openId")));
            return userInfo;
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isLogin(HttpServletRequest request){
        String accessToken = request.getHeader("token");
        if(StringUtils.isBlank(accessToken)){
            return false;
        }

        final Claims claims = Jwts.parser().setSigningKey(APPSECRET).parseClaimsJws(accessToken).getBody();
        if (claims == null) {
           return false;
        }
        return true;
    }


    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String username, String secret) {
        try {
            // 根据密码生成JWT效验器
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).withClaim("username", username).build();
            // 效验TOKEN
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getUserOpenId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("openid").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名,5min后过期
     *
     * @param openid 用户名
     * @param secret 用户的密码
     * @return 加密的token
     */
    public static String sign(String openid, String secret) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 附带username信息
        return JWT.create().withClaim("openid", openid).withExpiresAt(date).sign(algorithm);

    }


    /**
     * 根据request中的token获取用户账号
     *
     * @param request
     * @return
     * @throws BuziException
     */
    public static String getOpenIdByToken(HttpServletRequest request) throws BuziException {
        String accessToken = request.getHeader("X-Access-Token");
        String openId = getUserOpenId(accessToken);
        if (StringUtils.isEmpty(openId)) {
            throw new BuziException("未获取到用户");
        }
        return openId;
    }

    /**
     * 从session中获取变量
     *
     * @param key
     * @return
     */
    public static String getSessionData(String key) {
        //${myVar}%
        //得到${} 后面的值
        String moshi = "";
        if (key.indexOf("}") != -1) {
            moshi = key.substring(key.indexOf("}") + 1);
        }
        String returnValue = null;
        if (key.contains("#{")) {
            key = key.substring(2, key.indexOf("}"));
        }
        if (oConvertUtils.isNotEmpty(key)) {
            HttpSession session = SpringContextUtils.getHttpServletRequest().getSession();
            returnValue = (String) session.getAttribute(key);
        }
        //结果加上${} 后面的值
        if (returnValue != null) {
            returnValue = returnValue + moshi;
        }
        return returnValue;
    }
}
