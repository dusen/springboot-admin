package com.fastretailing.dcp.storecommon.generaldatadelete;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <P>
 * Application execute class.
 * </P>
 */
@SpringBootApplication(scanBasePackages = {"com.fastretailing.dcp.storecommon",
        "com.fastretailing.dcp.dcpcommon", "com.fastretailing.dcp.common.web.handler",
        "com.fastretailing.dcp.common.web.controller", "com.fastretailing.dcp.common.util",
        "com.fastretailing.dcp.storecommon.generaldatadelete"})
public class GeneralDataDeleteApplication {

    /** Constructor. */
    public GeneralDataDeleteApplication() {}

    /**
     * Application execute.
     * 
     * @param args Application parameter.
     */
    public static void main(final String[] args) {
        SpringApplication.run(GeneralDataDeleteApplication.class, args);
    }
}
