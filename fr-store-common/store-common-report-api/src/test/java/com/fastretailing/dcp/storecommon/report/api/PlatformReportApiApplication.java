/**
 * @(#)PlatformReportApiApplication.java
 * 
 *                                       Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Report api application.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
@SpringBootApplication(scanBasePackages = {"com.fastretailing.dcp.storecommon",
        "com.fastretailing.dcp.common.util", "com.fastretailing.dcp.common.api.client",
        "com.fastretailing.dcp.common.api.hmac", "com.fastretailing.dcp.common.api.log",
        "com.fastretailing.dcp.common.web.handler", "com.fastretailing.dcp.common.web.interceptor"})
public class PlatformReportApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlatformReportApiApplication.class, args);
    }
}
