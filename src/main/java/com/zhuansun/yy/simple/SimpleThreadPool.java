package com.zhuansun.yy.simple;

import java.util.LinkedList;

/**
 * 自定义简单线程池
 *
 * @author zhuansunpengcheng
 * @create 2019-05-05 4:29 PM
 **/
public class SimpleThreadPool extends Thread {


    /**
     * 任务队列
     */
    private final static LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();

    /**
     * 线程队列
     */
    private final static LinkedList<WorkerThread> THREAD_QUEUE = new LinkedList<>();

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


    /**
     * 线程组
     */
    private final static ThreadGroup GROUP = new ThreadGroup("pool_default_froup");

    /**
     * 线程的名字
     */
    private static volatile int seq = 0;

    /**
     * 线程名字的前缀
     */
    private final String THREAD_PREFIX = "simple-pool-thread-";


    public SimpleThreadPool() {
        this(4, 8, 12, 10, new DefaultDiscardPolicy());
    }

    public SimpleThreadPool(int min, int max, int active, int taskQueueSize, DiscardPolicy discardPolicy) {
        this.min = min;
        this.max = max;
        this.active = active;
        this.taskQueueSize = taskQueueSize;
        this.discardPolicy = discardPolicy;
        init();
    }


    private void init() {
        for (int i = 0; i < this.min; i++) {
            createWorkerThread();
        }
        this.start();
    }


    private void createWorkerThread() {
        WorkerThread thread = new WorkerThread(GROUP, THREAD_PREFIX + (seq++));
        thread.start();
        THREAD_QUEUE.addLast(thread);
    }


    @Override
    public void run() {
        //线程池也是作为一个线程，在init之后，需要启动，启动之后，线程队列中的多个线程去读取任务队列

        synchronized (TASK_QUEUE) {
            if (TASK_QUEUE.isEmpty()) {

            }


        }
    }

    /**
     * 提交任务到线程池的任务队列中
     * @param runnable
     */
    public void submit(Runnable runnable) {

        //任务队列一边读，一遍写，所以需要加锁
        synchronized (TASK_QUEUE) {
            if (TASK_QUEUE.size() > this.taskQueueSize) {
                //触发拒绝策略
                discardPolicy.discard();
            } else {
                //添加任务进去，并且唤醒所有的线程
                TASK_QUEUE.addLast(runnable);
                TASK_QUEUE.notifyAll();
            }
        }


    }


    /**
     * 停止线程池，销毁所有的线程
     */
    public void shutdown() throws InterruptedException {

        if (!TASK_QUEUE.isEmpty()){
            Thread.sleep(50);
        }

        synchronized (THREAD_QUEUE){
            int size = THREAD_QUEUE.size();

            while (size > 0){
                for (WorkerThread workerThread : THREAD_QUEUE){
                    if (workerThread.getThreadStatus() == ThreadStatus.BLOCKED){
                        //如果是阻塞中的线程，中断并停止它,会抛出中断异常的，注意观察一下这个中断异常在哪里捕获的？
                        //在哪里wait的，就是在哪里捕获的
                        workerThread.interrupt();
                        workerThread.close();
                        size--;
                    }else {
                        Thread.sleep(10);
                    }
                }
            }
        }
        System.out.println("线程池已销毁");
    }



    class WorkerThread extends Thread {

        private volatile ThreadStatus threadStatus = ThreadStatus.INIT;


        WorkerThread(ThreadGroup group, String name) {
            super(group, name);
        }


        /**
         * 获取当前工作者的状态
         */
        public ThreadStatus getThreadStatus() {
            return threadStatus;
        }

        public void close(){
            this.threadStatus = ThreadStatus.DEAD;
        }

        /**
         * 每一个工作中的线程启动之后，都要置为阻塞状态
         */
        @Override
        public void run() {
            OUTER:
            while (this.threadStatus != ThreadStatus.DEAD) {
                Runnable runnable;
                //任务队列一边读，一边写，所以需要加锁
                synchronized (TASK_QUEUE) {
                    if (TASK_QUEUE.isEmpty()) {
                        try {
                            threadStatus = ThreadStatus.BLOCKED;
                            //wait是释放锁的，
                            TASK_QUEUE.wait();
                        } catch (InterruptedException e) {
                            break OUTER;
                        }
                    }
                    //当任务的数量，小于线程的数量，就会报错
                    runnable = TASK_QUEUE.removeFirst();
                }

                if (runnable != null) {
                    threadStatus = ThreadStatus.RUNNING;
                    runnable.run();
                    threadStatus = ThreadStatus.INIT;
                }
            }
        }
    }


}
