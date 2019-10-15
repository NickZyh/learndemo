package com.learnnote.designpattern.builder;

import lombok.Data;

/**
 * Created by Zyh on 2019/9/8.
 */
@Data
@SuppressWarnings("all")
public class Person {

    /**
     * 构建者模式的好处有两个
     *      1 明确每个构造参数的意思,防止弄混参数造成运行时未知的异常
     *      2 对象未被创建成功时不能够被使用,防止出现线程安全问题
     */

    //必传参数
    private String name;
    // 0 - 男,1 - 女
    private int sex;

    // 可选参数
    private int age;
    private String address;

    /**
     * 重叠构造器模式创建
     */
    public Person(String name, int sex) {
        this(name, sex, 0);
    }

    public Person(String name, int sex, int age) {
        this(name, sex, age, null);
    }

    public Person(String name, int sex, int age, String address) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.address = address;
    }

    /**
     * Builder模式
     */
    // 私有构造方法,提供给Builder创建
    private Person(Builder builder) {
        this.name = builder.name;
        this.sex = builder.sex;
        this.age = builder.age;
        this.address = builder.address;
    }

    // 提供的外部构造器
    public static Builder builder(String name, int sex) {
        return new Builder(name, sex);
    }

    public static class Builder {
        // 必传参数,并且一旦被赋值就不允许修改
        private final String name;
        private final int sex;

        // 可选参数
        private int age;
        private String address;

        // 构造方法中直接限制必传参数
        private Builder(String name, int sex) {
            // 参数校验
            if (sex < 0 || sex > 1) {
                throw new IllegalArgumentException("0 - man,1 - woman");
            }

            this.name = name;
            this.sex = sex;
        }

        // 可选参数,提供方法赋值
        public Builder age(int age) {
            // 参数校验
            if (age < 0 || age > 100) {
                throw new IllegalArgumentException("0 < age < 100");
            }

            this.age = age;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        // 提供一个构建方法
        public Person build() {
            // 这里的this是Builder对象,调用的是Person的私有构造方法
            return new Person(this);
        }
    }
}
