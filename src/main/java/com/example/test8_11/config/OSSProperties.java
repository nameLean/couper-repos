package com.example.test8_11.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "oss")
@Data
public class OSSProperties {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;



}
