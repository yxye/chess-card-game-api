package com.sanyuan.excel.read;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;

import java.util.Calendar;
import java.util.Date;

public class TimeTest {
    public static void main(String[] args) {
//        Calendar cal = Calendar.getInstance();
//        cal.setTimeInMillis(1703573686859L);
//
//        Calendar ca = Calendar.getInstance();
//        ca.add(Calendar.MINUTE, 1);
//        ca.add(Calendar.SECOND, 4);
//
//        System.out.println(DateUtil.format(cal.getTime(), DatePattern.CHINESE_DATE_TIME_PATTERN));

        int hour = DateUtil.hour(new Date(), true);
        System.out.println(hour);

        DateTime nowTime = DateUtil.offsetDay(new Date(),-1);
        DateTime actDate = DateUtil.parse("2024-02-03", "yyyy-MM-dd");

        System.out.println(DateUtil.format(nowTime,"yyyy-MM-dd"));
    }
}
