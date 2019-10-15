package com.example.mockmybatis;

import com.example.mockmybatis.config.MapperScan;
import com.example.mockmybatis.dao.DemoInvocationHandler;
import com.example.mockmybatis.service.DemoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.reflect.Proxy;

/**
 * 待整理问题：spring创建Bean的时候是如何判断使用哪一个构造方法的
 */
@SpringBootApplication
@MapperScan
public class MockMybatisApplication {

    @SuppressWarnings("all")
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MockMybatisApplication.class, args);
        /**
         * 问题：mybatis是如何整合到spring中的,换句话说是如何将生成的Mapper放入容器中,从而可以让spring
         * 自动注入Mapper
         *
         * mybatis生成proxy对象 -> 将对象放入spring容器当中进行管理
         * 最主要的问题在于,由于mybatis生成的proxy对象是不确定的,所以不能使用类似于@Bean方法来将对象交给
         * spring进行管理
         *
         * 将某个完整的对象交给spring管理的方式
         * 1 @Bean
         * 2 直接将对象放入beanFactory中
         *      context.getBeanFactory().registerSingleton(String beanName, Object beanObject);
         * 3 借助FactoryBean在容器的启动过程中放入构建好的对象
         * 方式1和方式3的局限性在于每次只能传入一个Bean,但是一个项目中有很多的Mapper,这个时候如果一个一个的注入
         * 则太蠢了一点
         *
         * 区别：方式一和方式三是在spring上下文的启动过程中将创建好的Bean交给容器管理,可以由程序员自定义创建过程;而方式二则是在容器启动完
         *      成之后才将对象放入容器;这两者的区别核心在于方式二不能在创建Bean的过程中注入到其他对象中,而1和3可以;方式1的局限性在于必须
         *      和@Configuration联合使用以及必须在spring的扫描范围之内;以上就是为什么mybatis使用factoryBean构建mapper的原因
         * 问题：@Bean和FactoryBean传入Bean的区别是什么？
         *
         */

        // 方式2,这种方法是向spring容器中放入Bean的一种方式,但是这种方式是不行的,因为这种方式的调用时机
        // 是spring上下文加载完成后,也就是说spring在创建Bean的过程中无法为Bean中引入了DemoDao的Bean进行
        // 自动注入从而会报错,原因是因为spring启动过程中并未创建出DemoDao
        //DemoDao demoDao = getMapper(DemoDao.class);
        //context.getBeanFactory().registerSingleton("demoDao", demoDao);

        // spring beanName的命名方式为类的首字母小写
        DemoService demoService = (DemoService) context.getBean("demoService");
        demoService.query();

    }

    public static <T> T getMapper(Class<T> dao) {
        /**
         * 基于动态代理实现Dao,返回动态代理对象
         * Classloader：将动态生成的.class文件加载到JVM中
         * Demo.class:基于反射可以获取到接口中的方法等信息,基于这些信息来动态生成类;一个动态代理可以代理多个接口
         * 所以传入数组,最终生成的类中会实现所有的接口
         * InvocationHandler:动态代理中代理方法的具体实现;代理类中的所有方法的实现都是这个InvocationHandler
         * 中的invoke方法
         *
         * 动态代理生成的方法的返回值是Object对象,可以用任意实现的接口来强转并接收
         */
        Class[] clazz = new Class[]{dao};
        T proxy = (T) Proxy.newProxyInstance(
                MockMybatisApplication.class.getClassLoader(),
                clazz, new DemoInvocationHandler());
        return proxy;
    }
}
