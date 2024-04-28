//package com.sanyuan.excel.read;
//
//import com.alibaba.excel.EasyExcel;
//import com.alibaba.excel.ExcelReader;
//import com.alibaba.excel.context.AnalysisContext;
//import com.alibaba.excel.event.AnalysisEventListener;
//import com.alibaba.excel.read.builder.ExcelReaderBuilder;
//import com.alibaba.excel.read.metadata.ReadSheet;
//import com.alibaba.excel.support.ExcelTypeEnum;
//import com.sanyuan.entity.MqlUserInfo;
//import com.sanyuan.service.IMqlUserInfoService;
//import org.apache.commons.collections4.MapUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Date;
//import java.util.LinkedHashMap;
//
//
//@SpringBootTest
//public class ReadExcelFile2 {
//
//    @Autowired
//    private IMqlUserInfoService userInfoService;
//
//    @Test
//    public void addExcelData() throws Exception{
//        ///创建ExcelReaderBuilder对象
//        ExcelReaderBuilder readerBuilder = EasyExcel.read();
//        //获取文件对象
//        readerBuilder.file("E:\\项目资料\\米其林\\data3.xlsx");
//        //指定sheet
//        readerBuilder.sheet(1);
//        //自动关闭输入流
//        readerBuilder.autoCloseStream(true);
//        //设置excel格式
//        readerBuilder.excelType(ExcelTypeEnum.XLSX);
//        //注册监听器，进行数据解析
//        readerBuilder.registerReadListener(new AnalysisEventListener() {
//            @Override
//            public void invoke(Object o, AnalysisContext analysisContext) {
//                //读取一行数据回调
//                if(o instanceof LinkedHashMap) {
//                    LinkedHashMap dataMap = (LinkedHashMap)o;
//                    String code= MapUtils.getString(dataMap,1);
//                    String name = MapUtils.getString(dataMap,2);
//                    if(StringUtils.isNotBlank(code)){
//                        MqlUserInfo userInfo = new MqlUserInfo();
//                        userInfo.setCode(code);
//                        userInfo.setName(name);
//                        userInfo.setCreateTime(new Date());
//                        userInfo.setPlace("Other");
//                        System.out.println("========="+code+"=============");
//                        try {
//                            userInfoService.save(userInfo);
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//                    }
//
//                }
//            }
//
//            @Override
//            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
//                //数据读取完毕
//                System.out.println("数据读取完毕");
//            }
//        });
//        //创建读取器
//        ExcelReader reader = readerBuilder.build();
//        //读取数据
//        //reader.readAll();
//        reader.read(new ReadSheet(0));
//        //读取完毕
//        reader.finish();
//    }
//}
