package com.xuecheng.manage_cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(value="com.xuecheng.framework.domain.cms")
@ComponentScan("com.xuecheng.api.cms")
@ComponentScan("com.xuecheng.api.config")
@ComponentScan("com.xuecheng.manage_cms")
public class ManageCmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageCmsApplication.class,args);
    }
}
