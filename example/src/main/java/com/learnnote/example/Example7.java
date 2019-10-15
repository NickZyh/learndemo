package com.learnnote.example;

/**
 * @Author Zyh
 * @Date 2019/8/10 22:03
 * @Description
 * @Note
 */
public class Example7 {

    public static void main(String[] args) {
        /**
         * intern()作用：如果运行时常量池中已经包含一个等于此String对象内容的字符
         * 串，则返回常量池中该字符串的引用；如果没有，则在常量池中创建与此 String
         * 内容相同的字符串，并返回常量池中创建的字符串的引用。
         */
        String s1 = new String("计算机");
        String s2 = s1.intern();
        String s3 = "计算机";
        //计算机
        System.out.println(s2);
        //false，因为一个是堆内存中的String对象，一个是常量池中的String对象
        System.out.println(s1 == s2);
        //true，因为两个都是常量池中的String对象
        System.out.println(s3 == s2);
    }
}
