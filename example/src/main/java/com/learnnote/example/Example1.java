package com.learnnote.example;

/**
 * @Author nick.zhou
 * @Date 2019/7/24 10:12
 * @Description <p>TODO</p>
 **/
public class Example1 {

    @SuppressWarnings("all")
    public static void main(String[] args) {
        /**
         * i++会优先执行其他操作并赋值,再自增
         * ++i会先自增,在赋值
         */

        int a = 5;

        int b = a++; // 先执行b = a,再执行a + 1
        System.out.println(b); // 5
        System.out.println(a); // 6

        int c = ++a; // 先执行a + 1,再执行c = a
        System.out.println(c); // 7
        System.out.println(a); // 7

        int d = b * a++; // 先执行b * a,再执行a + 1
        System.out.println(d); // 35
        System.out.println(a); // 8

        int e = d * ++a; // 先执行a + 1,再执行d * a
        System.out.println(e); // 315
        System.out.println(a); // 9
    }

    private static void printBinaryString(int num) {
        System.out.println(Integer.toBinaryString(num));
    }
}
