package com.learnnote.designpattern.decorator;

/**
 * Created by Zyh on 2019/9/5.
 * <p>具体的一个装饰角色</p>
 */
@SuppressWarnings("all")
public class ConcreteDecorator1 extends Decorator {

    public ConcreteDecorator1(Component component) {
        super(component);
    }

    @Override
    public void doSomething() {
        super.doSomething();
        // 被装饰上的功能
        doAnotherThing();
    }

    // 当前装饰者提供的装饰功能
    private void doAnotherThing() {
        System.out.println("功能B");
    }
}
