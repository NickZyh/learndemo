package com.learnnote.example.example14;

/**
 * Created by Zyh on 2019/9/9.
 */
public abstract class Person {

    private String name;
    private int age;
    private String identity;

    public Person(String name, int age, String identity) {
        this.name = name;
        this.age = age;
        this.identity = identity;
    }

    public void doSomething() {
        System.out.println("干活");
    }

    @Override
    public String toString() {
        return name + ";" + age + ";" + identity;
    }
}
