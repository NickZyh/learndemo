package com.example.springboot.asycn;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;

/**
 * @Author Zyh
 * @Date 2019/8/5 22:14
 * @Description
 * @Note
 */
@Configuration
@EnableAsync
public class AsyncConfig extends AsyncConfigurerSupport {

    @Override
    public Executor getAsyncExecutor() {
        // 配置自定义线程池
        return super.getAsyncExecutor();
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
//        return new AsyncUncaughtExceptionHandler() {
//            @Override
//            public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
//
//            }
//        };

        /**
         * 异常对象,方法Method对象,参数Objects
         */
        return (throwable, method, objects) -> {
//            开始调用异步方法
//            syncException:异步执行出错;Msg:模拟运行异常
//            主线程执行结束
            System.out.println(method.getName() + ":异步执行出错;Msg:" + throwable.getMessage());
        };
    }
}
