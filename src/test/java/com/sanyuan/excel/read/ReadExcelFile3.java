package com.sanyuan.excel.read;

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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedHashMap;
import java.util.List;


@SpringBootTest
public class ReadExcelFile3 {

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
        readerBuilder.file("E:\\项目资料\\三元项目\\中奖用户\\中奖用户20240119_2.xlsx");
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
                int offset = 1;
                //读取一行数据回调
                if (o instanceof LinkedHashMap) {
                    LinkedHashMap dataMap = (LinkedHashMap) o;
                    String mdseName = MapUtils.getString(dataMap, 1+offset);//MapUtils.getString(dataMap, 1);
                    String userName = MapUtils.getString(dataMap, 2+offset);
                    String mobile = MapUtils.getString(dataMap, 3+offset);

                    List<SyLogistics> logisticsList = queryLogisticsList(userName, mobile);
                    SyUserPrize userPrize = null;
                    for (SyLogistics logistics : logisticsList) {
                        SyUserPrize findPrizeItem = queryUserPrize(logistics.getId());
                        String prizeName = findPrizeItem.getPrizeName();
                        if(StringUtils.isNotBlank(findPrizeItem.getLogisticsNo()) || StringUtils.isNotBlank(findPrizeItem.getLogisticsName())){
                            continue;
                        }

                        if(prizeName.contains(mdseName) || mdseName.contains(prizeName)){
                            userPrize = findPrizeItem;
                        }
                    }

                    String logisticsName = MapUtils.getString(dataMap, 8+offset);
                    String logisticsNo = MapUtils.getString(dataMap, 9+offset);
                    if (userPrize != null && StringUtils.isNotBlank(logisticsName) && StringUtils.isNotBlank(logisticsNo)) {
                        userPrize.setLogisticsName(logisticsName);
                        userPrize.setLogisticsNo(logisticsNo);
                        userPrizeService.updateById(userPrize);
                        System.out.println("userPrize=" + userPrize.getId());
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
