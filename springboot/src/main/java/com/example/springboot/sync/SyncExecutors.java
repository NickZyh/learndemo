package com.example.springboot.sync;

/**
 * @Author Zyh
 * @Date 2019/8/19 21:50
 * @Description
 * @Note
 */
public class SyncExecutors extends AbstractSyncExecutors {

    @Override
    public void syncMethod() {
        throw new RuntimeException("异步方法执行抛出异常");
    }
}
