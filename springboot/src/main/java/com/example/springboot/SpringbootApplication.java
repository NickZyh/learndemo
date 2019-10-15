package com.example.springboot;

import com.example.springboot.asycn.AsyncTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringbootApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringbootApplication.class, args);

        AsyncTest bean = context.getBean(AsyncTest.class);
        bean.testSync();
    }

}
