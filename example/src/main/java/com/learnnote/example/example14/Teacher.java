package com.learnnote.example.example14;

/**
 * Created by Zyh on 2019/9/9.
 */
public class Teacher extends Person {

    private String operation;

    public Teacher(String name, int age, String identity, String operation) {
        super(name, age, identity);
        this.operation = operation;
    }

    @Override
    public String toString() {
        return super.toString() + ";" + operation;
    }
}
