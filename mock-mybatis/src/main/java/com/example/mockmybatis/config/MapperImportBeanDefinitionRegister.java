package com.example.mockmybatis.config;

import com.example.mockmybatis.dao.DemoDao;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @Author Zyh
 * @Date 2019/8/7 22:01
 * @Description  ImportBeanDefinitionRegistrar是spring提供的用于扫描包的一个扩展点
 * @Note  为了实例化BeanFactory,使用ImportBeanDefinitionRegistrar来放入该Bean;
 *          之所以不用@Bean的原因在于@Bean是必须要基于@Configuration的,也就是说如果使用
 *          @Configuration + @Bean的方式的话,那么就一定要保证FactoryBean的实现类必须要位于能够
 *          被spring扫描到的包下.但是要注意,FactoryBean的实现类是Mybatis实现的,而mybatis的包是
 *          不固定的,即spring不一定能扫描到.所以使用ImportBeanDefinitionRegistrar配合@Import
 *          来向容器中注入;由于上面的包的不固定的原因,所以也不能使用@Component族的注解来标时
 *          ImportBeanDefinitionRegistrar;所以mybatis提供了@MapperScan配合@Import的方式
 *          来加载当前这个类为Bean;这就是为什么@MapperScan必须要在spring的扫描范围之内;@Import的
 *          作用是指定某个类由spring来创建Bean,这种方式配合注解来使用的话特别的灵活,容易扩展,并不局限于
 *          要加载的类一定要在spring的扫描范围之内,只需要让@Import在扫描范围之内就可以.springboot就是
 *          基于@Import来实现的插件式功能.
 *
 * @Import提供出来主要是用于扩展的，因为它不局限于spring扫描范围
 *
 *
 */
public class MapperImportBeanDefinitionRegister implements ImportBeanDefinitionRegistrar {

    /**
     * 从下面的这个方法就能够猜得出,ImportBeanDefinitionRegistrar这个类就是用来将一些需要创建的Bean的genericBeanDefinition放入
     * beanDefinitionRegistry中,所以这个方法的调用时机应该为构建GenericBeanDefinition的时候
     * @param annotationMetadata
     * @param beanDefinitionRegistry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        // TODO:扫描到MapperScan,然后获取其中的包名属性,基于包名获取到所有接口的Class

        // 遍历需要扫描的包,对包中的每个mapper都创建一个对应的FactoryBean,此处为模拟
        Class[] classes = new Class[]{DemoDao.class};
        for(Class clazz : classes) {
            // 构建BeanDefinition的Builder
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DemoFactory.class);
            // 构建DemoFactory的BeanDefinition
            GenericBeanDefinition genericBeanDefinition = (GenericBeanDefinition) builder.getBeanDefinition();

            // 当前这个类的核心目的就是为了创建DemoFactory,然后每需要创建一个mapper就要创建一个DemoFactory;那么问题来了,怎么为
            // 每一个mapper都创建一个对应的DemoFactory呢？由于一个DemoFactory中都基于一个Class对象来创建对应的mapper,所以此时
            // 的核心问题就是：如何把对应的Class对象传给DemoFactory呢？
            /**
             *   public static final int AUTOWIRE_NO = 0;
             *     public static final int AUTOWIRE_BY_NAME = 1;
             *     public static final int AUTOWIRE_BY_TYPE = 2;
             *     public static final int AUTOWIRE_CONSTRUCTOR = 3;  // 基于构造方法来为属性注入
             *
             *     mybatis的做法是使用构造方法注入的,所以有一个前提,mybatis的DemoFactory必须要提供一个有参的构造方法
             *     但是这个时候又会有问题,因为基于xml的情况下,mybatis是基于set的方式来注入的,所以DemoFactory必须要提供
             *     两个构造方法,一个有参数一个无参数
             */
            genericBeanDefinition.setAutowireMode(3);
            // 向构造方法中传入参数
            genericBeanDefinition.getConstructorArgumentValues().addGenericArgumentValue(clazz);

            // 将BeanDefinition放入BeanDefinitionRegistry中,这样操作之后,spring就会创建DemoFactoryBean,然后执行里面的逻辑创建Bean
            // 要注意,创建的每一个FactoryBean都只与唯一一个mapper对应,所以名字一定要不同,否则genericBeanDefinition会被覆盖
            beanDefinitionRegistry.registerBeanDefinition(clazz.getSimpleName(), genericBeanDefinition);
        }

        /**
         * 当全部遍历完成之后,此时所有的mapper对应的DemoFactory都被构建完毕,接下来spring会在创建Bean的过程中调用DemoFactory将所有的mapper
         * 代理类创建完毕
         */

    }
}
