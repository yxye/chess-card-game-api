package com.chess.card.api.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.chess.card.api.bean.ExcelDataBean;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ExcelServiceImpl {

    private List<List<String>> buildTitleList(ExcelDataBean excelDataBean) {
        List<String> titleList = excelDataBean.getTitleList();
        List<List<String>> result = new ArrayList<>();
        for (String title : titleList) {
            List<String> item = ListUtils.newArrayList();
            item.add(title);
            result.add(item);
        }
        return result;
    }

    private List<List<String>> buildDataList(ExcelDataBean excelDataBean) {
        List<String> titleList = excelDataBean.getTitleList();
        List<Map<String, Object>> dataList = excelDataBean.getDataList();
        List<List<String>> result = new ArrayList<>();
        for (Map<String, Object> dataItem : dataList) {
            List<String> row = ListUtils.newArrayList();
            for (String key : titleList) {
                Object val = dataItem.get(key);
                row.add(val!=null?val+"":"");
            }
            result.add(row);
        }
        return result;
    }


    public InputStream createApiCheckExeclFile(Map<String, ExcelDataBean> excelDataMap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (ExcelWriter excelWriter = EasyExcel.write(outputStream).build()) {
            for (String sheetName : excelDataMap.keySet()) {
                ExcelDataBean excelDataBean = excelDataMap.get(sheetName);
                WriteSheet excelSheet = EasyExcel.writerSheet(sheetName).head(buildTitleList(excelDataBean)).build();
                excelWriter.write(buildDataList(excelDataBean),excelSheet);
            }
        }
        InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        return inputStream;
    }

}
