package com.learnnote.example;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by Zyh on 2019/9/14.
 */
@SuppressWarnings("all")
@Slf4j
public class Example15 {

    public static void main(String[] args) {
        /**
         * 当进程有代码能够执行时,CPU将持续保持100%.但是当前进程不会达到100%,这是因为操作系统会执行进程
         * 调度,不会一直执行当前进程.
         */
        while (true) {
            System.out.println("aaaa");
        }
    }
}
