package com.learnnote.example;

/**
 * @Author Zyh
 * @Date 2019/8/24 17:23
 * @Description
 * @Note
 */
public class Example12 {

    public static void main(String[] args) {
        try {
            // 当抛出异常时,catch和finally都会被调用
            // throw new RuntimeException();

            // 当调用return的时候finally会被调用
            // return;
        } catch (Exception e) {
            System.out.println("execute catch");
        } finally {
            System.out.println("execute finally");
        }
    }
}
