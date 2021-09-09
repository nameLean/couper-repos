package com.example.test8_11.config;


import com.example.test8_11.util.SnowflakeIdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration//是告诉IOC容器把SnowflakeConfig交给他管理，只有先把类交给他，下面的 @Bean才能被扫描到
public class SnowflakeConfig {

    @Bean//把方法的返回值交给IOC容器
    public SnowflakeIdWorker snowflakeIdWorker(){
        return new SnowflakeIdWorker(3,7);
    }
}
