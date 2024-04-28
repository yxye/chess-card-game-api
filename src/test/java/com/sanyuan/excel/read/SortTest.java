package com.sanyuan.excel.read;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortTest {

    /**
     * Comparator<JSONObject> comparator = Comparator.comparingInt(a -> a.getIntValue("index"));
     * comparator.thenComparingLong(a -> a.getLongValue("upTime");
     * @param args
     */
    public static void main(String[] args) {


        String jsonStr = "[{\"score\":700,\"upTime\":1694662598000,\"code\":\"XA00001158\",\"index\":1,\"user\":true},{\"score\":700,\"upTime\":1694662471000,\"code\":\"XA00000694\",\"index\":2,\"user\":false},{\"score\":666,\"upTime\":0,\"code\":\"XA00001944\",\"index\":3,\"user\":false},{\"score\":67,\"upTime\":0,\"code\":\"XA00001729\",\"index\":4,\"user\":false},{\"score\":55,\"upTime\":0,\"code\":\"XA00001728\",\"index\":5,\"user\":false},{\"score\":14,\"upTime\":0,\"code\":\"XA00001234\",\"index\":6,\"user\":false},{\"score\":11,\"upTime\":0,\"code\":\"XA00001047\",\"index\":7,\"user\":false},{\"score\":9,\"upTime\":0,\"code\":\"XA00001352\",\"index\":8,\"user\":false},{\"score\":7,\"upTime\":0,\"code\":\"XA00001509\",\"index\":9,\"user\":false}]";

        List<JSONObject> itemList = JSON.parseObject(jsonStr,List.class);

        Comparator<JSONObject> comparator = (a,b)->{
                    Long aVal = a.getLongValue("upTime");
                    Long bVal = b.getLongValue("upTime");
                    System.out.println(aVal+" = "+bVal);
                    return aVal.compareTo(bVal);
                };


//        comparator = comparator.thenComparing((a,b)->{
//            Long aVal = a.getLongValue("upTime");
//            Long bVal = b.getLongValue("upTime");
//            System.out.println(aVal+" = "+bVal);
//            return aVal.compareTo(bVal);
//        });

        List<JSONObject> resDataList = itemList.parallelStream().filter(i -> i.getIntValue("score") <= 700)
                .sorted((a,b)->{
                    Integer aScoreVal = a.getIntValue("score");
                    Integer bScoreVal = b.getIntValue("score");
                    int result = bScoreVal.compareTo(aScoreVal);
                    if(result != 0){
                        return result;
                    }
                    Long aVal = a.getLongValue("upTime");
                    Long bVal = b.getLongValue("upTime");
                    System.out.println(aVal+" = "+bVal);
                    return aVal.compareTo(bVal);
                })
                .collect(Collectors.toList());

       for(JSONObject item: resDataList){
           System.out.println(item.get("code")+" ==== "+item.get("score")+" ==== "+item.get("upTime"));
       }
    }
}
