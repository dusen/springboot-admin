/**
 * @(#)SalesAdminCommonApplication.java
 *
 *                                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = {"com.fastretailing.dcp.common.api.client",
        "com.fastretailing.dcp.common.util", "com.fastretailing.dcp.common.mail",
        "com.fastretailing.dcp.common.web.handler", "com.fastretailing.dcp.common.web.interceptor",
        "com.fastretailing.dcp.sales.common"})
@MapperScan({"com.fastretailing.dcp.sales.*.repository"})
public class SalesAdminCommonApplication {

    public static void main(String[] args) throws Exception {
        new SpringApplication(SalesAdminCommonApplication.class).run(args);
    }
}
