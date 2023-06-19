package com.zhuansun.yy.simple;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author zhuansunpengcheng
 * @create 2019-05-05 5:40 PM
 **/
public class MainTest {


    private static final int START = 330000;
    private static final int END = 400000;

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        //女娲 https://image.smoba.qq.com/Banner/img/Video/skin/301792.mp4
        //牛魔 https://image.smoba.qq.com/Banner/img/Video/skin/301683.mp4
        //东皇 https://image.smoba.qq.com/Banner/img/Video/skin/301871.mp4


        SimpleThreadPool threadPool = new SimpleThreadPool();


        for (int i = START; i < END; i++) {
            String url = "https://image.smoba.qq.com/Banner/img/Video/skin/" + String.valueOf(i) + ".mp4";
            threadPool.submit(() -> {
                String result = HttpUtils.getPage(url);
                if (!result.contains("not") && !result.contains("server")) {
                    System.out.println(Thread.currentThread().getName() + "---->" + url);
                }
            });

        }

        threadPool.shutdown();
//
//
//        ExecutorService executorService = Executors.newFixedThreadPool(5);
//
//        Future<Integer> submit = executorService.submit(() -> {
//            int a = 10 / 0;
//            System.out.println(a);
//            return a;
//        });
//
//        submit.get();



    }

}
