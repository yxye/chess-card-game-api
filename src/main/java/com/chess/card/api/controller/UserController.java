package com.chess.card.api.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.chess.card.api.bean.Result;
import com.chess.card.api.bean.RetrievePasswordBean;
import com.chess.card.api.bean.SendSmsCodeBean;
import com.chess.card.api.bean.UserRegistBean;
import com.chess.card.api.game.entity.UserInfo;
import com.chess.card.api.game.service.IUserInfoService;
import com.chess.card.api.bean.*;
import com.chess.card.api.exception.BuziException;
import com.chess.card.api.game.constant.CommonConstant;
import com.chess.card.api.game.service.ISmsService;
import com.chess.card.api.utils.Md5Util;
import com.chess.card.api.utils.RandImageUtil;
import com.chess.card.api.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Api(tags = "用户服务接口")
@Slf4j
@RequestMapping("/chess/user")
@RestController
public class UserController extends BaseController {

    private String signatureSecret = "dd05f1c54d63749eda95f9fa6d49v442a";

    private final String BASE_CHECK_CODES = "qwertyuiplkjhgfdsazxcvbnmQWERTYUPLKJHGFDSAZXCVBNM1234567890";

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ISmsService smsService;

    @Value("${spring.profiles.active}")
    private String active;

    /**
     * 用户注册
     *
     * @param userRegistBean
     */
    @ApiOperation(value = "用户注册", notes = "用于用户注册，注册后可登录！")
    @PostMapping("/register")
    public Result<String> userRegister(@Validated @RequestBody UserRegistBean userRegistBean) {
        //验证短信验证码
        checkSmsCode(userRegistBean.getMobile(),userRegistBean.getSmsCode(), REGISTER_TYPE);
        UserInfo userInfo = new UserInfo();
        BeanUtil.copyProperties(userRegistBean, userInfo);
        userInfoService.userRegister(userInfo);
        return Result.ok("注册成功！");
    }


    /**
     * 找回密码
     * 修改密码后需要注销账号登录的会话
     * @param retrievePassword
     */

    @ApiOperation(value = "找回密码", notes = "忘记密码时，找加密码！")
    @PostMapping("/retrievePassword")
    public Result<String> retrievePassword(@Validated @RequestBody RetrievePasswordBean retrievePassword) {
        //验证短信验证码
        checkSmsCode(retrievePassword.getMobile(),retrievePassword.getSmsCode(), RESET_TYPE);

        userInfoService.retrievePassword(retrievePassword);

        return Result.ok("密码已重置！");
    }

    /**
     * 发送短信验证码
     * @param sendSmsCodeBean
     * @param request
     * @return
     */
    @ApiOperation(value = "发送短信验证码", notes = "找回密码与注册时都调用此交接口发送验证码！")
    @PostMapping(value = "/sendSmsCode")
    public Result<String> sendSmsCode(@Validated @RequestBody SendSmsCodeBean sendSmsCodeBean, HttpServletRequest request) {
        String mobile = sendSmsCodeBean.getMobile();
        String type = sendSmsCodeBean.getType();
        UserInfo userByMobile = this.userInfoService.getUserByMobile(mobile);
        if (RESET_TYPE.equals(type)) {
            if (userByMobile == null) {
                log.error("用户不存在 mobile={}", mobile);
                Result.error("用户不存在！");
            }
        } else if (REGISTER_TYPE.equals(type)) {
            if (userByMobile != null) {
                String account = userByMobile.getAccount();
                log.error("用户账号已存 mobile={},account={}", mobile, account);
                Result.error("用户账号已存！！");
            }
        }

        checkVerificationCode(sendSmsCodeBean.getVerificationCode());
        String ip = this.getIpAddr(request);
        String code = RandomStringUtils.random(6, "1234567890");
        boolean envState = "online".equals(active);
        if (!envState) {
            code = "888888";
        } else {
            this.smsService.checkLimitAndAdd(mobile, ip);
        }

        String cacheKey = buildSmsCacheKey(mobile,type);

        if (redisUtil.hasKey(cacheKey)) {
            return Result.ok("验证码已发送，请注意查收");
        }

        if (envState) {
            log.info("发送验证码 sendauthCode mobile={},code={}", mobile, code);
            smsService.sendSms(mobile, code, ip);
        }
        redisUtil.set(cacheKey, code, 60 * 30);
        return Result.ok("发送成功");
    }


    private void checkSmsCode(String mobile, String smsCode, String type) {
        String cacheKey = buildSmsCacheKey(mobile,type);
        String cacheSmsCode = redisUtil.get(cacheKey);

        if (cacheSmsCode == null || !cacheSmsCode.equals(smsCode)) {
            log.warn("短信验证码错误， checkSmsCode cacheKey={},cacheSmsCode={},smsCode={}",cacheKey, cacheSmsCode, smsCode);
            throw new BuziException(HttpStatus.PRECONDITION_FAILED.value(), "短信验证码错误");
        }
    }

    private void checkVerificationCode(String verificationCode) {
        String lowerCaseCaptcha = verificationCode.toLowerCase();
        String origin = lowerCaseCaptcha + signatureSecret;
        String realKey = Md5Util.md5Encode(origin, "utf-8");
        String checkCode = redisUtil.get(realKey);
        if (checkCode == null || !checkCode.equals(lowerCaseCaptcha)) {
            log.warn("验证码错误 checkVerificationCode lowerCaseCaptcha={},checkCode={}", lowerCaseCaptcha, checkCode);
            throw new BuziException(HttpStatus.PRECONDITION_FAILED.value(), "验证码错误");
        }
    }



    /**
     * 后台生成图形验证码 ：有效
     * @param response
     */
    @ApiOperation("获取验证码")
    @GetMapping(value = "/randomImage")
    public Result<String> randomImage(HttpServletResponse response) {
        Result<String> res = new Result<>();
        try {
            //生成验证码
            String code = RandomUtil.randomString(BASE_CHECK_CODES, 4);
            //存到redis中
            String lowerCaseCode = code.toLowerCase();

            // 加入密钥作为混淆，避免简单的拼接，被外部利用，用户自定义该密钥即可
            String origin = lowerCaseCode + signatureSecret;
            String realKey = Md5Util.md5Encode(origin, "utf-8");
            redisUtil.set(realKey, lowerCaseCode, 60);
            log.info("获取验证码，Redis key = {}，checkCode = {}", realKey, code);
            //返回前端
            String base64 = RandImageUtil.generate(code);
            res.setSuccess(true);
            res.setResult(base64);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res.error500("获取验证码失败,请检查redis配置!");
            return res;
        }
        return res;
    }

    /**
     * 退出登录
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/logout")
    public Result<Object> logout(HttpServletRequest request, HttpServletResponse response) {
        //用户退出逻辑
        String token = request.getHeader(CommonConstant.X_ACCESS_TOKEN);
        if (StringUtils.isBlank(token)) {
            return Result.error("退出登录失败！");
        }

        return Result.ok("退出登录成功！");
    }


}
