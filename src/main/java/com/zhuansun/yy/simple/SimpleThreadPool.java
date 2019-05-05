package com.zhuansun.yy.simple;

import java.util.LinkedList;
import java.util.List;

/**
 * 自定义简单线程池
 *
 * @author zhuansunpengcheng
 * @create 2019-05-05 4:29 PM
 **/
public class SimpleThreadPool {


    /**
     * 任务队列
     */
    private final static List<Runnable> TASK_QUEUE = new LinkedList<>();

    /**
     * 线程队列
     */
    private final static List<WorkerThread> THREAD_QUEUE = new LinkedList<>();

    /**
     * 最小线程数
     */

    private int min;

    /**
     * 最大线程数
     */

    private int max;

    /**
     * 默认线程数
     */

    private int active;

    /**
     * 任务队列的最大任务数，超过最大任务数之后，就会触发拒绝策略
     */

    private int taskQueueSize;

    /**
     * 拒绝策略
     */
    private final DiscardPolicy discardPolicy;

    public SimpleThreadPool() {

        this(4,8,12,20,new DefaultDiscardPolicy());
    }

    public SimpleThreadPool(int min, int max, int active, int taskQueueSize, DiscardPolicy discardPolicy) {
        this.min = min;
        this.max = max;
        this.active = active;
        this.taskQueueSize = taskQueueSize;
        this.discardPolicy = discardPolicy;
    }





}
