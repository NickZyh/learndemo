package com.learnnote.example;

import java.util.concurrent.locks.LockSupport;

/**
 * @Author nick.zhou
 * @Date 2019/7/25 13:54
 * @Description <p>TODO</p>
 **/
public class Example4 {

    /**
     * 线程如果因为调用park而阻塞的话，能够响应中断请求(中断状态被设置成true),但是不会抛出InterruptedException;
     */

    public static void testPark() {
        Thread thread = Thread.currentThread();
        // park可以理解为获取许可,每个线程有且仅持有一个许可;许可默认是被持有的,如果直接park,则会阻塞;
        // 表示当前获取自己的许可
        LockSupport.park();
        // 会被阻塞,无法执行
        System.out.println("end");
    }

    public static void testUnPark() {
        Thread thread = Thread.currentThread();
        // unpark - 释放指定线程正持有的许可,unpark能够被调用多次,因为结果始终处于可用状态
        LockSupport.unpark(thread);
        LockSupport.park();
        // 未被阻塞,输入end
        System.out.println("end");
    }

    public static void testDoublePark() {
        Thread thread = Thread.currentThread();
        // unpark - 释放指定线程正持有的许可,unpark能够被调用多次,因为结果始终处于可用状态
        LockSupport.unpark(thread);
        LockSupport.park();
        // park是无法被重入的,这和testPark()是一个道理
        LockSupport.park();
        // 阻塞
        System.out.println("end");
    }
}
