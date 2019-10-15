package com.example.springboot.sync;

/**
 * @Author Zyh
 * @Date 2019/8/19 21:16
 * @Description
 * @Note
 */
public class SyncTest {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("主线程执行");

        // 异步执行入口
        new SyncExecutors().execute();

        Thread.sleep(1000L);
        System.out.println("主线程执行结束");
    }
}
