package com.zhuansun.yy.simple;

/**
 * 拒绝策略的异常
 *
 * @author zhuansunpengcheng
 * @create 2019-05-05 4:46 PM
 **/
public class DiscardException extends RuntimeException{

    public DiscardException(String message) {
        super(message);
    }
}
