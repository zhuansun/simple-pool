package com.zhuansun.yy.simple;

/**
 * 默认的拒绝策略
 *
 * @author zhuansunpengcheng
 * @create 2019-05-05 4:53 PM
 **/
public class DefaultDiscardPolicy implements DiscardPolicy{
    @Override
    public void discard() throws DiscardException {
        throw new DiscardException("当前任务队列已满，拒绝再添加任务");
    }
}
