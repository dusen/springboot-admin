/**
 * @(#)MasterUpdateApplication.java
 *
 *                                  Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.masterupdate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <P>
 * Application execute class.
 * </P>
 */
@SpringBootApplication(scanBasePackages = {"com.fastretailing.dcp.storecommon",
        "com.fastretailing.dcp.common.api.log", "com.fastretailing.dcp.common.web.handler",
        "com.fastretailing.dcp.common.util", "com.fastretailing.dcp.common.web.controller"})
public class MasterUpdateApplication {

    /** Constructor. */
    public MasterUpdateApplication() {}

    /**
     * Application execute.
     * 
     * @param args Application parameter.
     */
    public static void main(String[] args) {
        SpringApplication.run(MasterUpdateApplication.class, args);
    }
}
