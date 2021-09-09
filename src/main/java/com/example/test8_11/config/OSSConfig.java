package com.example.test8_11.config;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OSSConfig {
    //这里的配置属性就可以通过OSSProperties实体产生调用，而不用在这里写属性了
    @Autowired
    private OSSProperties ossProperties;

    //这里把oss对象放入ico容器方便调用
    @Bean
    public OSS oss(){
        return new OSSClientBuilder().build(ossProperties.getEndpoint(),
                ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret());
    }
}
