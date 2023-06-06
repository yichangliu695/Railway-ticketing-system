package com.hjnu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * spring boot 启动类
 *
 */
@SpringBootApplication
@MapperScan("org.sang.mapper")
@EnableCaching
@EnableScheduling
public class SystemBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemBackApplication.class, args);
    }

}
