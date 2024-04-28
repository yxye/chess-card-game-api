package com.chess.card.api.utils.lucky;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class LuckyDrawUtil {

    public static int getRandomIndex(int size){
        Random random = new Random();
        return random.nextInt(size);
    }

    public  static <T extends ProbabilityOfWinning> T draw(List<?  extends ProbabilityOfWinning> prizeList) {
        List<Double> sortRateList = new ArrayList<>();
        // 计算概率总和
        Double sumRate = 0D;
        for (ProbabilityOfWinning bean : prizeList) {
            sumRate += bean.getProbability().doubleValue();
        }

        if (sumRate != 0) {
            double rate = 0D; // 概率所占比例
            for (ProbabilityOfWinning bean : prizeList) {
                rate += bean.getProbability().doubleValue();
                // 构建一个比例区段组成的集合(避免概率和不为1)(概率/概率和 = 概率占比)
                sortRateList.add(rate / sumRate);
            }
            // 随机生成一个随机数（0-1之间），并排序
            ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
            double random = threadLocalRandom.nextDouble(0, 1);
            int size = prizeList.size();
            for(int i=0;i<size;i++){
                Double itemRate = sortRateList.get(i);
                if(itemRate > random){
                    return (T)prizeList.get(i);
                }
            }
        }
        return null;
    }
}
