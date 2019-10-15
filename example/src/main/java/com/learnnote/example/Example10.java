package com.learnnote.example;

import java.util.concurrent.*;

/**
 * @Author Zyh
 * @Date 2019/8/18 23:08
 * @Description
 * @Note
 */
public class Example10 {

    static class task implements Runnable {

        @Override
        public void run() {
            try {
                //Thread.sleep(1000L);
                System.out.println(Thread.currentThread().getId());
            } catch (Exception e) {

            }
        }
    }

    public static void main(String[] args) {
//        ThreadPoolExecutor executor = new ThreadPoolExecutor(
//                0, 20, 10, TimeUnit.SECONDS,
//                new ArrayBlockingQueue<>(10), Executors.defaultThreadFactory());

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                0, 20, 10, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(), Executors.defaultThreadFactory());

        while(true) {
            executor.execute(new task());
        }

//        for (int i = 0; i < 10; i++) {
//            executor.execute(new task());
//        }
    }
}
