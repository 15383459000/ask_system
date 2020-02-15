package com.yunding.answer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@MapperScan("com.yunding.answer.mapper")
@EnableSwagger2
@SpringBootApplication
public class AskSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(AskSystemApplication.class, args);
    }

}
