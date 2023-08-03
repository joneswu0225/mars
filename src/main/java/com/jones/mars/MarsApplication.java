package com.jones.mars;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication

@EnableScheduling
//启用自动配置
//exclude表示自动配置时不包括Multipart配置
//@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
@MapperScan({"com.jones.mars.repository"})
public class MarsApplication {
    public static void main(String[] args) {
        SpringApplication.run(MarsApplication.class, args);
    }
}
