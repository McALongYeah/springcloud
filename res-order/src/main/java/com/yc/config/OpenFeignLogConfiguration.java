package com.yc.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenFeignLogConfiguration {
    @Bean
    Logger.Level feignLoggerLever(){
        return Logger.Level.FULL;
    }
}
