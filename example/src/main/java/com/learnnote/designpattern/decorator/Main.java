package com.learnnote.designpattern.decorator;

/**
 * Created by Zyh on 2019/9/5.
 */
public class Main {

    public static void main(String[] args) {
        Component component = new ConcreteDecorator2(new ConcreteDecorator1(
           new ConcreteComponent()
        ));

        // 让一个具体的需要被装饰的角色能被其他装饰者进行装饰
        component.doSomething();
    }
}
