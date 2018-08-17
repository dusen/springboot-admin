/**
 * @(#)SalesPayoffDataUpdateApplication.java
 *
 *                                           Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salespayoffdataupdate;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Application for sales payoff data update.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.fastretailing.dcp.storecommon",
        "com.fastretailing.dcp.common.util", "com.fastretailing.dcp.sales.salespayoffdataupdate",
        "com.fastretailing.dcp.storecommon.screen.config", "com.fastretailing.dcp.common.api.uri",
        "com.fastretailing.dcp.common.api.client", "com.fastretailing.dcp.common.api.hmac",
        "com.fastretailing.dcp.common.web.handler", "com.fastretailing.dcp.common.web.interceptor",
        "com.fastretailing.dcp.sales.common"})
@MapperScan({"com.fastretailing.dcp.sales.common.repository"})
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class SalesPayoffDataUpdateApplication {

    /**
     * Application execute.
     * 
     * @param args Application parameter.
     */
    public static void main(String[] args) {
        SpringApplication.run(SalesPayoffDataUpdateApplication.class, args);
    }
}
