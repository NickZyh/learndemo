package com.learnnote.designpattern.observer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Zyh
 * @Date 2019/8/25 16:08
 * @Description
 * @Note
 */
@SuppressWarnings("all")
public class Demo2 {
    public static void main(String[] args) {
        Child child = new Child();
        // 添加Listener
        child.addListener(new DogListener());
        // 模拟事件发生
        child.cry(LocalDateTime.now());
    }
}

@SuppressWarnings("all")
// 将事件进行封装,Listener根据事件中的内容来进行处理
class Event {
    // 比如说孩子哭的时间
    private LocalDateTime localDateTime;

    public Event setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
        return this;
    }

    public LocalDateTime getLocalDateTime() {
        return this.localDateTime;
    }
}

interface ChildListener {
    void actionOnCry(Event event);
}

class Child {

    // 定义容器存放所有监听者
    private List<ChildListener> listeners = new ArrayList<>();

    // 构造代码块添加监听者,执行于构造方法之前;实际上可以读取配置文件的方式来动态的创建对象放入容器
    {
        listeners.add(new MotherListener());
    }

    // 用于在代码中能够动态的向容器添加资源
    public void addListener(ChildListener childListener) {
        if (childListener == null) {
            return;
        }
        listeners.add(childListener);
    }

    public void cry(LocalDateTime localDateTime) {
        System.out.println("小孩哭" + localDateTime.toString());
        executeListener(new Event().setLocalDateTime(localDateTime));
    }

    // 遍历调用所有的listener
    private void executeListener(Event event) {
        for (ChildListener childListener : listeners) {
            childListener.actionOnCry(event);
        }
    }
}

class MotherListener implements ChildListener {

    private void feed() {
        System.out.println("mother feed");
    }

    @Override
    public void actionOnCry(Event event) {
        feed();
    }
}

class DogListener implements ChildListener {

    private void bark() {
        System.out.println("dog barking");
    }

    @Override
    public void actionOnCry(Event event) {
        bark();
    }
}