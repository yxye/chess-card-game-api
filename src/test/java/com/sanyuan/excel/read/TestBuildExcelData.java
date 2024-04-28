package com.sanyuan.excel.read;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.chess.card.api.bean.ExcelDataBean;
import com.chess.card.api.excel.ExcelServiceImpl;
import com.chess.card.api.service.ISyEmailDataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class TestBuildExcelData {

    @Autowired
    private ISyEmailDataService emailDataService;

    @Autowired
    private ExcelServiceImpl excelService;

    @Test
    public void buildExcelData()throws Exception{
        Map<String, Object> paramsMap = new HashMap<>();
        DateTime actDate = DateUtil.parse("2024-02-03", "yyyy-MM-dd");
        paramsMap.put("startTime", DateUtil.beginOfDay(actDate));
        paramsMap.put("endTime", DateUtil.endOfDay(actDate));
        Map<String, ExcelDataBean> result = emailDataService.queryEmailDataMap("1000", paramsMap);

       excelService.createApiCheckExeclFile(result);


    }
}
