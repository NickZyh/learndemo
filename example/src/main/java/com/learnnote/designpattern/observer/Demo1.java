package com.learnnote.designpattern.observer;

/**
 * @Author Zyh
 * @Date 2019/8/25 15:48
 * @Description 观察者模式,用来处理事件的,事件驱动,常与责任链一起使用
 * @Note
 */
@SuppressWarnings("all")
public class Demo1 {
    /**
     * 观察者模式的理解就是当发生某件事的时候,就会触发监听了对应事件的listener的方法
     * 观察者的三个要素
     * 1 事件源(发生事件的地方)
     * 2 事件(发生什么事件)
     * 3 观察者(Listener,当事件发生时就会触发)
     * 观察者的核心就是一个一个的调用Listener
     *
     * 当前例子是一个最基本的观察者模型,这个模型有几个问题
     *  1 每个事件源可能有多个观察者,当需要加入观察者的时候,这个时候需要修改代码,耦合性太高.
     *  2 每个观察者不一定指观察一个事件源上面,因为可能有多个事件源发生同一个事件.
     *  3 一个事件的发生会根据事件的属性不同,而进行不同的操作
     *
     * 观察者模式中,一定会有
     *      1 事件发生后调用的方法,会被抽象成接口,所以Listener一定会实现这个接口.
     *      2 一个事件对象,Listener根据事件对象中的属性做出不同的动作.
     *      3 事件源对象中一定会有一个容器存储所有的Listener,且一定是遍历调用
     *      4 一定会有向容器中动态添加Listener的方法,可能是一个addListener的方法,也可能是在配置文件中读取
     */

    public static void main(String[] args) {
        /**
         * 逻辑：当小孩哭事件发生,会触发监听者Dog和Mother的动作
         */
        // 创建小孩对象
        Demo1.Child child = new Demo1().new Child();
        // 触发小孩哭事件
        child.cry();
    }

    // 小孩哭泣的监听者
    interface ChildListener {
        void actionOnCry();
    }

    class Child {
        public void cry() {
            System.out.println("小孩哭事件");

            // 小孩哭触发了监听者
            new Dog().actionOnCry();
            new Mother().actionOnCry();
        }
    }

    class Dog implements ChildListener {
        @Override
        public void actionOnCry() {
            System.out.println("狗叫");
        }
    }

    class Mother implements ChildListener {
        @Override
        public void actionOnCry() {
            System.out.println("妈妈哄");
        }
    }
}
