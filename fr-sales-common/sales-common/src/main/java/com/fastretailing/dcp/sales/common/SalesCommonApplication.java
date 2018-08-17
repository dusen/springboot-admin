package com.fastretailing.dcp.sales.common;

import org.modelmapper.ModelMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Application execute class.
 */
@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = {
        "com.fastretailing.dcp.common.api.client", "com.fastretailing.dcp.common.api.hmac",
        "com.fastretailing.dcp.common.api.uri", "com.fastretailing.dcp.common.util",
        "com.fastretailing.dcp.common.mail", "com.fastretailing.dcp.common.web.handler",
        "com.fastretailing.dcp.common.web.interceptor", "com.fastretailing.dcp.sales.common"})
@MapperScan({"com.fastretailing.dcp.sales.*.repository"})
public class SalesCommonApplication {

    /**
     * Application execute.
     *
     * @param args Application parameter.
     * @throws Exception Exception that occurred.
     */
    public static void main(String[] args) throws Exception {
        new SpringApplication(SalesCommonApplication.class).run(args);
    }

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
