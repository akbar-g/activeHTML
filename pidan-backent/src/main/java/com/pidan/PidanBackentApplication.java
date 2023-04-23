package com.pidan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.pidan.mapper")
@EnableScheduling
public class PidanBackentApplication {

    public static void main(String[] args) {
        SpringApplication.run(PidanBackentApplication.class, args);
    }

}
