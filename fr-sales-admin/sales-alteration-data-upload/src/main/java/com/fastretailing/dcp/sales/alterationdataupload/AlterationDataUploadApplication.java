/**
 * @(#)AlterationDataUploadApplication.java
 *
 *                                          Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.alterationdataupload;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * Launch application class.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.fastretailing.dcp.storecommon",
        "com.fastretailing.dcp.common.util", "com.fastretailing.dcp.sales.alterationdataupload",
        "com.fastretailing.dcp.storecommon.screen.config", "com.fastretailing.dcp.common.api.uri",
        "com.fastretailing.dcp.common.api.client", "com.fastretailing.dcp.common.api.hmac",
        "com.fastretailing.dcp.common.web.handler", "com.fastretailing.dcp.common.web.interceptor",
        "com.fastretailing.dcp.sales.common",
        "com.fastretailing.dcp.sales.importtransaction.component",
        "com.fastretailing.dcp.sales.importtransaction.converter"})
@EnableAutoConfiguration(
        exclude = {org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class})
public class AlterationDataUploadApplication {

    /**
     * Execute application.
     *
     * @param args Application parameter.
     */
    public static void main(String[] args) {
        SpringApplication.run(AlterationDataUploadApplication.class, args);
    }

    /**
     * Create model mapper and set MatchingStrategies.STRICT.
     *
     * @return Model mapper instance.
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setFullTypeMatchingRequired(true);
        return modelMapper;
    }
}
