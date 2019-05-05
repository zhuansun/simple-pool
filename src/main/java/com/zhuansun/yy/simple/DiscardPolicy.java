package com.zhuansun.yy.simple;

/**
 * 线程的拒绝策略
 *
 * @author zhuansunpengcheng
 * @create 2019-05-05 4:46 PM
 **/
public interface DiscardPolicy {

    void discard() throws DiscardException;

}
