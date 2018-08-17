package com.fastretailing.dcp.sales;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.fastretailing.dcp.storecommon",
        "com.fastretailing.dcp.common.web.handler", "com.fastretailing.dcp.standard.api",
        "com.fastretailing.dcp.common.util", "com.fastretailing.dcp.common.web.controller",
        "com.fastretailing.dcp.sales"})
public class SalesAdminDomainApplication {

    public static void main(String[] args) throws Exception {
        new SpringApplication(SalesAdminDomainApplication.class).run(args);
    }
}
