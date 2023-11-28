package com.yc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
//@EnableSwagger2
@MapperScan("com.yc.dao")
@EnableRedisHttpSession
@EnableDiscoveryClient
//9000
public class ResfoodApp {
    public static void main(String[] args) {
        SpringApplication.run(ResfoodApp.class,args);
    }
}
