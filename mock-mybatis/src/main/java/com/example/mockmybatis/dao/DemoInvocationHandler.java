package com.example.mockmybatis.dao;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author Zyh
 * @Date 2019/8/7 21:03
 * @Description
 * @Note
 */
public class DemoInvocationHandler implements InvocationHandler {

    /**
     * @param proxy  生成的代理类
     * @param method  这个对象会根据调用不同的方法而传入不同的method对象,这是因为每个代理的方法中都调用了
     *                invoke方法
     * @param args   方法参数
     * @return
     * @throws Throwable
     * 在这个方法中使用JDBC等操作查询数据库就大致的完成了Mybatis的功能
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("假装查询数据库");
        return null;
    }
}
