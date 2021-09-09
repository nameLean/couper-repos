package com.example.test8_11.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                // .pathMapping("/")// base，最终调用接口后会和paths拼接在一起
                .select()
                // .paths(Predicates.or(PathSelectors.regex("/api/.*")))//过滤的接口
                .apis(RequestHandlerSelectors.basePackage
                        ("com.example.test8_11.controller")) //过滤的接口
                        //("com/example/test8_11/controller")) //过滤的接口,这种写法不可用
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo getApiInfo() {
        // 定义联系人信息
        Contact contact = new Contact("小明在线联系", "https://fanyi.baidu.com/#en/zh/", "2861966651@qq.com");
        return new ApiInfoBuilder()
                .title("测试 Swagger 的用法") // 标题
                .description("测试Swagger中各种注解的用法") // 描述信息
                .version("1.0.SNAPSHOT") // //版本
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
                .contact(contact)
                .build();
    }

}
