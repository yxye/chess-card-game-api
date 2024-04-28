package com.sanyuan.excel.read;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EasyExcelExample {
    public static void main(String[] args) {
        // 定义生成的 Excel 文件路径
        String filePath = "example.xlsx";

        // 创建 ExcelWriterBuilder 对象
        ExcelWriterBuilder excelWriterBuilder = EasyExcel.write(filePath);

        // 创建 Sheet1
        ExcelWriterSheetBuilder sheet1Builder = excelWriterBuilder.sheet("Sheet1");

        // 数据写入到 Sheet1
        List<List<String>> dataSheet1 = new ArrayList<>();
        dataSheet1.add(Arrays.asList("Sheet1Name", "Age", "City"));
        dataSheet1.add(Arrays.asList("John", "25", "New York"));
        dataSheet1.add(Arrays.asList("Alice", "30", "London"));
        sheet1Builder.doWrite(dataSheet1);

        // 创建 Sheet2
        ExcelWriterSheetBuilder sheet2Builder = excelWriterBuilder.sheet("Sheet2");

        // 数据写入到 Sheet2
        List<List<String>> dataSheet2 = new ArrayList<>();
        dataSheet2.add(Arrays.asList("ID", "Product", "Price"));
        dataSheet2.add(Arrays.asList("101", "Laptop", "1200"));
        dataSheet2.add(Arrays.asList("102", "Phone", "800"));
        sheet2Builder.doWrite(dataSheet2);

        System.out.println("Excel file has been generated successfully.");
    }
}
