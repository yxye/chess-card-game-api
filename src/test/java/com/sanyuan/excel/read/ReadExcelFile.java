package com.sanyuan.excel.read;

import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chess.card.api.entity.SyLogistics;
import com.chess.card.api.entity.SyUserPrize;
import com.chess.card.api.service.ISyLogisticsService;
import com.chess.card.api.service.ISyUserPrizeService;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;


@SpringBootTest
public class ReadExcelFile {

    @Autowired
    private ISyUserPrizeService userPrizeService;

    @Autowired
    private ISyLogisticsService logisticsService;

    private List<SyLogistics> queryLogisticsList(String userName, String mobile) {
        LambdaQueryWrapper<SyLogistics> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SyLogistics::getUserName, userName);
        queryWrapper.eq(SyLogistics::getMobile, mobile);
        return logisticsService.list(queryWrapper);
    }

    private SyUserPrize queryUserPrize(String logisticsId) {
        LambdaQueryWrapper<SyUserPrize> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SyUserPrize::getLogisticsId, logisticsId);
        return userPrizeService.getOne(queryWrapper);
    }

    @Test
    public void addExcelData() throws Exception {
        ///创建ExcelReaderBuilder对象
        ExcelReaderBuilder readerBuilder = EasyExcel.read();
        //获取文件对象
        readerBuilder.file("E:\\项目资料\\三元项目\\中奖用户\\导出中奖用户20240202235959单号反馈.xlsx");
        //指定sheet
        readerBuilder.sheet(0);
        //自动关闭输入流
        readerBuilder.autoCloseStream(true);
        //设置excel格式
        readerBuilder.excelType(ExcelTypeEnum.XLSX);
        //注册监听器，进行数据解析
        readerBuilder.registerReadListener(new AnalysisEventListener() {
            @Override
            public void invoke(Object o, AnalysisContext analysisContext) {
                String toDayKey = DateUtil.format(new Date(), "yyyyMMdd");
                //读取一行数据回调
                if (o instanceof LinkedHashMap) {
                    LinkedHashMap dataMap = (LinkedHashMap) o;
                    String id = MapUtils.getString(dataMap, 0);//MapUtils.getString(dataMap, 1);

                    String logisticsName = MapUtils.getString(dataMap, 11);
                    String logisticsNo = MapUtils.getString(dataMap, 12);


                    SyUserPrize syUserPrize = userPrizeService.getById(id);
                    if (syUserPrize != null && StringUtils.isNotBlank(logisticsName) && StringUtils.isNotBlank(logisticsNo) && StringUtils.isBlank(syUserPrize.getLogisticsNo()) && StringUtils.isBlank(syUserPrize.getLogisticsNo())) {
                        syUserPrize.setUpdateTime(new Date());
                        syUserPrize.setUpdateBy(toDayKey);
                        if(!"停发已反馈".equals(logisticsName)){
                            syUserPrize.setLogisticsName(logisticsName);
                            syUserPrize.setLogisticsNo(logisticsNo);
                        }else{
                            System.out.println(logisticsName);
                        }
                        userPrizeService.updateById(syUserPrize);
                    }else{
                        System.out.println("更新失败 id="+id);
                    }
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                //数据读取完毕
                System.out.println("数据读取完毕");
            }
        });
        //创建读取器
        ExcelReader reader = readerBuilder.build();
        //读取数据
        // reader.readAll();
        reader.read(new ReadSheet(0));
        //读取完毕
        reader.finish();
    }
}
