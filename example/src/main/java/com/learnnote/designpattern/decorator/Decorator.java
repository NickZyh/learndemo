package com.learnnote.designpattern.decorator;

/**
 * Created by Zyh on 2019/9/5.
 * <p>
 *     装饰角色,之所以要有这个角色是为了向不同功能的装饰者提供被装饰者的功能
 *      实现Component是提供能够被装饰的功能
 * </p>
 */
@SuppressWarnings("all")
public class Decorator implements Component {

    // 被装饰角色
    private Component component;

    // 持有被装饰角色
    public Decorator(Component component) {
        this.component = component;
    }

    // 需要存在被装饰角色的动作
    @Override
    public void doSomething() {
        component.doSomething();
    }
}
