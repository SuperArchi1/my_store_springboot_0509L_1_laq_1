package com.tedu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.tedu.dao")
public class SpringBootApplicationContext {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplicationContext.class, args);
    }
}
