package com.zhuansun.yy.simple;

/**
 * 工作的线程
 *
 * @author zhuansunpengcheng
 * @create 2019-05-05 4:37 PM
 **/
public class WorkerThread extends Thread{

    private volatile ThreadStatus threadStatus = ThreadStatus.INIT;




    @Override
    public void run() {
        super.run();
    }
}
