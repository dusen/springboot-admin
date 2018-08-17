/**
 * @(#)SalesAdminApplication.java
 *
 *                                Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import com.fastretailing.dcp.common.api.jvm.OmsConfigurationInitializer;

/**
 * Sales admin application start class.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.fastretailing.dcp.common.api.client",
        "com.fastretailing.dcp.common.api.hmac", "com.fastretailing.dcp.common.api.uri",
        "com.fastretailing.dcp.common.util", "com.fastretailing.dcp.common.mail",
        "com.fastretailing.dcp.common.web.interceptor",
        "com.fastretailing.dcp.common.web.controller", "com.fastretailing.dcp.storecommon",
        "com.fastretailing.dcp.sales"})
public class SalesAdminApplication {

    /**
     * Start the application.
     * 
     * @param args Application arguments.
     */
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SalesAdminApplication.class);
        application.addInitializers(new OmsConfigurationInitializer());
        application.run(args);
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
