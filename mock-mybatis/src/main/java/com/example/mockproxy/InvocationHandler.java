package com.example.mockproxy;

import java.lang.reflect.Method;

/**
 * @Author nick.zhou
 * @Date 2019/7/31 11:42
 * @Description <p>TODO</p>
 **/
public interface InvocationHandler {

    Object invoke(Method method, Object... args);
}
