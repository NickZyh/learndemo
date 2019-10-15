package com.learnnote.example;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author Zyh
 * @Date 2019/8/12 16:18
 * @Description
 * @Note
 */
public class Example9 {

    static {
        i = 0;

        // 正确执行
        test();

        // 编译器错误,非法向前引用
        // test1(i);

        // 编译器错误,非法向前引用
        // System.out.println(i);
    }

    static int i = 1;

    static void test1(int i) {
        System.out.println("静态方法中被执行" + i);
    }

    static void test() {
        System.out.println("静态方法中被执行" + i);
    }

    public static void main(String[] args) {
        System.out.println(i);
    }
}
