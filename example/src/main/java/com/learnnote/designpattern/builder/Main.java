package com.learnnote.designpattern.builder;

/**
 * Created by Zyh on 2019/9/8.
 */
public class Main {

    public static void main(String[] args) {
        // 重叠构造器创建
        Person person = new Person("张三", 0);

        // Builder创建
        Person person1 = Person.builder("张三", 0)
                .age(20)
                .address("深圳")
                .build();

        // lombok的builder模式限制所有的参数都必须要传
        Student student = Student.builder()
                .name("张三")
                .age(20)
                .address("深圳")
                .build();
    }
}
