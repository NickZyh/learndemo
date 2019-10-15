package com.example.springboot.asycn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @Author Zyh
 * @Date 2019/8/5 22:01
 * @Description
 * @Note
 */
@Component
public class AsyncTest {

    @Autowired
    private ApplicationContext context;

    public void testSync() {
        System.out.println("开始调用异步方法");
        doTestSync();
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程执行结束");
}

    /**
     * 同类中调用代理的异步方法
     */
    public void doTestSync() {
        AsyncTest asyncTest = context.getBean(AsyncTest.class);
        //asyncTest.syncMethod();
        asyncTest.syncException();
    }

    @Async
    public void syncMethod() {
        System.out.println("我是异步执行的");
    }

    @Async
    public void syncException() {
        throw new RuntimeException("模拟运行异常");
    }
}
