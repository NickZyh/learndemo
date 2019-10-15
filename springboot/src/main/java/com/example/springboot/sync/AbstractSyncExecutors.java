package com.example.springboot.sync;

/**
 * @Author Zyh
 * @Date 2019/8/19 21:39
 * @Description
 * @Note 模板设计模式,抽象类继承了接口的话，如果抽象类没有实现接口方法,那么子类就必须实现;如果抽象类实现
 *      了方法,那么子类就可以不用实现
 */
public abstract class AbstractSyncExecutors implements SyncInterface {

    /**
     * 成功和失败的默认实现
     */
    @Override
    public void successCallback() {
        System.out.println("异步执行成功的默认回调方法");
    }

    @Override
    public void failedCallback(Exception e) {
        System.out.println("异步执行失败的默认回调方法:" + e.getMessage());
    }

    protected void execute() {
        getExecutors().execute(() -> {
            System.out.println("异步开始执行");
            try {
                syncMethod();
            } catch (Exception e) {
                failedCallback(e);
                return;
            }
            successCallback();
        });
    }
}
