package com.learnnote.example;

/**
 * @Author nick.zhou
 * @Date 2019/7/26 15:53
 * @Description <p>TODO</p>
 **/
public class Example5 {

    private void param(int i) {
        i = 1;
        System.out.println(i);
    }

    public static void main(String[] args) {
        int i = 0;
        // 结果：1，0
        new Example5().param(i);
        System.out.println(i);
    }
}
