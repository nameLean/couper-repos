package com.example.test8_11;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication//管理IOC容器的
@MapperScan("com.example.test8_11.dao")//dao层下的UserDao没有实现类无法加注解，这里是告诉IOC容器扫描到这里加入IOC容器
@EnableSwagger2
public class Test811Application {

    public static void main(String[] args) {
        SpringApplication.run(Test811Application.class, args);
    }

}
