//package com.sanyuan.excel.read;
//
//import com.alibaba.excel.EasyExcel;
//import com.alibaba.excel.ExcelReader;
//import com.alibaba.excel.context.AnalysisContext;
//import com.alibaba.excel.event.AnalysisEventListener;
//import com.alibaba.excel.read.builder.ExcelReaderBuilder;
//import com.alibaba.excel.read.metadata.ReadSheet;
//import com.alibaba.excel.support.ExcelTypeEnum;
//import com.sanyuan.entity.MqlAnswer;
//import com.sanyuan.entity.MqlQuestion;
//import com.sanyuan.service.IMqlAnswerService;
//import com.sanyuan.service.IMqlQuestionService;
//import org.apache.commons.collections4.MapUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Arrays;
//import java.util.Date;
//import java.util.LinkedHashMap;
//
//
//@SpringBootTest
//public class ReadExcelFileForIBP {
//
//    @Autowired
//    private IMqlQuestionService questionService;
//
//    @Autowired
//    private IMqlAnswerService answerService;
//
//    @Test
//    public void addExcelData() throws Exception{
//        ///创建ExcelReaderBuilder对象
//        ExcelReaderBuilder readerBuilder = EasyExcel.read();
//        //获取文件对象
//        readerBuilder.file("E:\\项目资料\\米其林\\ibp-update5.xlsx");
//        //指定sheet
//        readerBuilder.sheet("For IBP");
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
//                    String classifyName= MapUtils.getString(dataMap,1);
//                    String allTitle = MapUtils.getString(dataMap,2);
//                    String[] allTitles = allTitle.split("\\n");
//
//                    String answer = MapUtils.getString(dataMap,8);
//                    String remarks = StringUtils.defaultIfBlank(MapUtils.getString(dataMap,9),"");
//                    MqlQuestion question = new MqlQuestion();
//                    question.setCnTitle(allTitle);
////                    if(allTitles.length>=2){
////                        question.setEnTitle(allTitles[1]);
////                    }else{
////                        question.setEnTitle(allTitles[0]);
////                    }
//                    question.setClassifyName(classifyName);
//                    question.setAnswer(answer);
//                    question.setRemarks(remarks);
//                    question.setCreateTime(new Date());
//                    question.setQuestionType("1");
//                    question.setPlace("ibp");
//                    questionService.save(question);
//                    String  questionId = question.getId();
//                    Arrays.asList("3","4","5","6","7").forEach(key->{
//                        String answerTitle = MapUtils.getString(dataMap,Integer.valueOf(key));
//                        if(StringUtils.isNotBlank(answerTitle)){
//                            String[] allAnswerTitles =  answerTitle.split("\\n");
//                            MqlAnswer mqlAnswer = new MqlAnswer();
//                            mqlAnswer.setCnTitle(answerTitle);
////                            if(allAnswerTitles.length>=2) {
////                                mqlAnswer.setEnTitle(allAnswerTitles[1]);
////                            }else{
////                                mqlAnswer.setEnTitle(allAnswerTitles[0]);
////                            }
//                            mqlAnswer.setEnFlag(answerTitle.substring(0,1));
//                            mqlAnswer.setCreateTime(new Date());
//                            mqlAnswer.setQuestionId(questionId);
//                            answerService.save(mqlAnswer);
//                        }
//                    });
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
