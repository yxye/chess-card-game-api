package com.chess.ws.api.utils;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 账户相关属性验证工具
 *
 */
public class ValidatorUtil {
    /**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^[\\.a-zA-Z0-9\u4e00-\u9fa5]{1,20}$";


    /**
     * 正则表达式：验证密码 ^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}
     */
    public static final String REGEX_PASSWORD = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";


    /**
     * 正则表达式：支付密码
     */
    public static final String REGEX_PAY_PASSWORD = "^[0-9]{6}$";

    /**
     * 正则表达式：正整数积分
     */
    public static final String REGEX_TOPUP_INTEGRAL = "^[0-9]{1,7}$";


    /**
     * 正则表达式：汇款账号
     */
    public static final String REGEX_BANKCARD = "^[0-9]{15,25}$";



    public static final String REGEX_EXCHANGE_NUMBER = "^[0-9]{1,15}$";

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^1[0-9]{10}$";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "^(\\d{15}|^\\d{18}|\\d{17}(\\d|X|x))$";

    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";


    public static final String REGEX_INTEGRAL_AMOUNT =  "^\\d+(\\.\\d+)?$";

    /**
     * 开票金额
     */
    public static final String REGEX_INVOICE_AMOUNT =  "^\\d{1,9}(\\.\\d{1,2})?$";

    /**
     * 校验用户名
     *
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUsername(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }

    /**
     * 校验密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    /**
     *
     * @param payPwd
     * @return
     */
    public static boolean isPayPwd(String payPwd){
        return Pattern.matches(REGEX_PAY_PASSWORD, payPwd);
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 校验邮箱
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }


    /**
     * 充值积分
     * @param integral
     * @return
     */
    public static boolean isTopupIntegral(String integral) {
        return Pattern.matches(REGEX_TOPUP_INTEGRAL, integral);
    }


    /**
     * 校验汉字
     *
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }

    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        if(!Pattern.matches(REGEX_ID_CARD, idCard)){
            return false;
        }


        String[] code = idCard.split("");
        // ∑(ai×Wi)(mod 11)
        // 加权因子
        List<String> factor = Arrays.asList("7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2");
        // 校验位
        List<String> parity = Arrays.asList("1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2", "x");
        int sum = 0;
        int ai = 0;
        int wi = 0;
        for (int i = 0; i < 17; i++) {
            ai = Integer.valueOf(code[i]);
            wi = Integer.valueOf(factor.get(i));
            sum += ai * wi;
        }
        if (!StringUtils.endsWithIgnoreCase(parity.get(sum % 11),code[17])) {
            return false;
        }
        return true;
    }

    /**
     * 校验URL
     *
     * @param url
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUrl(String url) {
        return Pattern.matches(REGEX_URL, url);
    }

    /**
     * 校验IP地址
     *
     * @param ipAddr
     * @return
     */
    public static boolean isIPAddr(String ipAddr) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }

    /**
     * 校验汇款账号
     * @param bankcard
     * @return
     */
    public static boolean isBankcard(String bankcard) {
        return Pattern.matches(REGEX_BANKCARD, bankcard);
    }


    public static boolean isExchangeNumber(String exchangeNumber) {
        return Pattern.matches(REGEX_EXCHANGE_NUMBER, exchangeNumber);
    }

    /**
     * 验证积分
     * @param integral
     * @return
     */
    public static boolean isIntegralAmount(String integral) {
        return Pattern.matches(REGEX_INTEGRAL_AMOUNT, integral);
    }

    /**
     * 验证开票金额
     * @param integral
     * @return
     */
    public static boolean isInvoiceAmount(String integral) {
        return Pattern.matches(REGEX_INVOICE_AMOUNT, integral);
    }

    /**
     *
     * @param reg
     * @param val
     * @return
     */
    public static boolean checkVal(String reg, String val){
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(val);
        return m.matches();
    }


}