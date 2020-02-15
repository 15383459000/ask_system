package com.yunding.answer.config;


import com.yunding.answer.constant.AnswerConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger2集成
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {

        //添加head参数start
        ParameterBuilder tokenParam = new ParameterBuilder();
        List<Parameter> params = new ArrayList<Parameter>();
        tokenParam.name(AnswerConstant.HTTP_HEADER_ANSWER_ACCESS_TOKEN).description("accessToken").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        params.add(tokenParam.build());
        //添加head参数end

        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(params)
                .apiInfo(apiInfo())
                .select()
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.yunding.answer"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("ANSWER API")
                .version("1.0")
                .build();
    }

}
