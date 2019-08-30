package com.wx.rbb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = { "com.wx.rbb.dao" })
public class RbbApplication {
    public static void main(String[] args) {
        SpringApplication.run(RbbApplication.class, args);
    }

}
