/**
 * @(#)SalesCommonDomainApplication.java
 *
 *                                       Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.repository;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.fastretailing.dcp.common.api.jvm.OmsConfigurationInitializer;

/**
 * Sales common domain application start class.
 */
@SpringBootApplication(scanBasePackages = {"com.fastretailing.dcp.common.api.log",
        "com.fastretailing.dcp.common.api.client", "com.fastretailing.dcp.common.api.hmac",
        "com.fastretailing.dcp.common.api.uri", "com.fastretailing.dcp.common.util",
        "com.fastretailing.dcp.common.mail", "com.fastretailing.dcp.common.web.handler",
        "com.fastretailing.dcp.common.web.interceptor",
        "com.fastretailing.dcp.common.web.controller", "com.fastretailing.dcp.storecommon",
        "com.fastretailing.dcp.sales"})
@MapperScan(basePackages = {"com.fastretailing.dcp.sales.*.repository",
        "com.fastretailing.dcp.sales.common.repository.optional"})
public class SalesCommonDomainApplication {

    /**
     * Start the application.
     * 
     * @param args Application arguments.
     */
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SalesCommonDomainApplication.class);
        application.addInitializers(new OmsConfigurationInitializer());
        application.run(args);
    }
}
