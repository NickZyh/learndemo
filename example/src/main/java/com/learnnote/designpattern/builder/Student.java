package com.learnnote.designpattern.builder;

import lombok.Builder;

/**
 * Created by Zyh on 2019/9/8.
 */
@Builder
public class Student {

    /**
     * lombok中集成了@Builder注解,提供了构建者模式的功能
     */

    private String name;
    private int age;
    private String address;
}
