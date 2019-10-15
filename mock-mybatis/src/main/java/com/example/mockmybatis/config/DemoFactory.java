package com.example.mockmybatis.config;

import com.example.mockmybatis.MockMybatisApplication;
import com.example.mockmybatis.dao.DemoDao;
import com.example.mockmybatis.dao.DemoInvocationHandler;
import com.example.mockmybatis.service.DemoService;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Proxy;

/**
 * @Author Zyh
 * @Date 2019/8/7 21:46
 * @Description   向spring容器中放入对象的方式3
 * @Note
 */
//@Configuration  // 使用@Component族的注解标注的原因是因为即使实现了FactoryBean接口,但是spring不会知道
                // 我们是否实现了这个接口,这是因为没有办法反向的推断出实现了一个接口或抽象类的子类
// 由于使用@Configuration的局限性,所以不使用@Configuration;总之如果想让FactoryBean生效,那么当前这个类就必须要让spring创建出Bean
public class DemoFactory implements FactoryBean {

    /**
     * 这两个方法一个是传入一个对象,二一个是以什么类型的方式传入这个对象,这个返回类型可以是一个接口,也可以
     * 就是当前类的类型
     */

    Class mapperInterface;

    @Override
    public Object getObject() throws Exception {
        Class[] clazz = new Class[]{mapperInterface};
        Object proxy = Proxy.newProxyInstance(
                MockMybatisApplication.class.getClassLoader(),
                clazz, new DemoInvocationHandler());
        return proxy;
    }

    @Override
    public Class<?> getObjectType() {
        return mapperInterface;
    }
}
