package com.example.mockmybatis.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Import(MapperImportBeanDefinitionRegister.class)
public @interface MapperScan {

    /**
     * 配置扫描的包名
     * @return
     */
    String[] scanPackages() default "";
}
