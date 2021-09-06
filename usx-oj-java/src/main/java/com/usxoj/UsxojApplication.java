package com.usxoj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.usxoj.mapper")
public class UsxojApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsxojApplication.class, args);
    }

}
