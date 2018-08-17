/**
 * @(#)SettlementCorrectionHistoryListApplication.java
 *
 *                                                     Copyright (c) 2018 Fast Retailing
 *                                                     Corporation.
 */

package com.fastretailing.dcp.sales.settlementcorrectionhistory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Application for settlement correction history list.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.fastretailing.dcp.storecommon",
        "com.fastretailing.dcp.common.util",
        "com.fastretailing.dcp.sales.settlementcorrectionhistory",
        "com.fastretailing.dcp.storecommon.screen.config", "com.fastretailing.dcp.common.api.uri",
        "com.fastretailing.dcp.common.api.client", "com.fastretailing.dcp.common.api.hmac",
        "com.fastretailing.dcp.common.web.handler", "com.fastretailing.dcp.common.web.interceptor",
        "com.fastretailing.dcp.sales.common"})
public class SettlementCorrectionHistoryListApplication {

    /**
     * Application execute.
     * 
     * @param args Application parameter.
     */
    public static void main(String[] args) {
        SpringApplication.run(SettlementCorrectionHistoryListApplication.class, args);
    }
}
