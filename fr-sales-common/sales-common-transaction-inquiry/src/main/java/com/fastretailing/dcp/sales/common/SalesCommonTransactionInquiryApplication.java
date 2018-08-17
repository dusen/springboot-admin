/**
 * @(#)SalesCommonTransactionInquiryApplication.java
 *
 *                                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common;

import org.modelmapper.ModelMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * Application execute class.
 */
@SpringBootApplication
@ComponentScan(
        basePackages = {"com.fastretailing.dcp.sales", "com.fastretailing.dcp.storecommon.message",
                "com.fastretailing.dcp.common.api.log", "com.fastretailing.dcp.common.web.handler",
                "com.fastretailing.dcp.common.util", "com.fastretailing.dcp.common.api.uri",
                "com.fastretailing.dcp.common.api.client", "com.fastretailing.dcp.common.api.hmac",
                "com.fastretailing.dcp.common.web.controller", "com.fastretailing.dcp.storecommon"})
@MapperScan({"com.fastretailing.dcp.sales.*.repository"})
public class SalesCommonTransactionInquiryApplication {

    /**
     * Application execute.
     * 
     * @param args Application parameter.
     * @throws Exception Exception that occurred.
     */
    public static void main(String[] args) throws Exception {
        new SpringApplication(SalesCommonTransactionInquiryApplication.class).run(args);
    }

    /**
     * Create model mapper.
     *
     * @return Model mapper instance.
     */
    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
