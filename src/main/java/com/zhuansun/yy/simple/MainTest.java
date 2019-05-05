package com.zhuansun.yy.simple;


/**
 * @author zhuansunpengcheng
 * @create 2019-05-05 5:40 PM
 **/
public class MainTest {

    public static void main(String[] args) throws InterruptedException {

        SimpleThreadPool threadPool = new SimpleThreadPool();
        for (int i = 0; i < 4; i++) {
            threadPool.submit(() -> {
                System.out.println("The runnable  be serviced by " + Thread.currentThread() + " start.");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("The runnable be serviced by " + Thread.currentThread() + " finished.");
            });
        }

        Thread.sleep(5000);

        threadPool.shutdown();

//
//        SimpleThreadPool simpleThreadPool = new SimpleThreadPool();
//
//        simpleThreadPool.submit(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("1111");
//            }
//        });



    }

}
