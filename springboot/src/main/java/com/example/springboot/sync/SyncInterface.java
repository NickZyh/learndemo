package com.example.springboot.sync;

import java.util.concurrent.*;

/**
 * @Author Zyh
 * @Date 2019/8/19 21:31
 * @Description
 * @Note  这个接口就可以模拟框架中封装出来的给程序员自己去实现的具体的方法
 */
public interface SyncInterface {
    /**
     * 实际需要执行的方法
     */
    void syncMethod();

    /**
     * 异步执行成功的回调方法
     */
    void successCallback();

    /**
     * 异步执行失败的回调方法
     */
    void failedCallback(Exception e);

    /**
     * 程序员自定义实现线程接口,使用default,即程序员不自定义就使用默认线程池
     */
    default Executor getExecutors() {
        System.out.println("使用默认的线程池");
        return new ThreadPoolExecutor(2, 10,
                1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));
    }
}
