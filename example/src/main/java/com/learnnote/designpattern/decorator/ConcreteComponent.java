package com.learnnote.designpattern.decorator;

/**
 * Created by Zyh on 2019/9/5.
 * <p>类比为InputStream的具体实现,具体的被装饰角色</p>
 */
public class ConcreteComponent implements Component {
    @Override
    public void doSomething() {
        System.out.println("功能A");
    }
}
