/**
 * @(#)WebCommonConfiguration.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.web.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * the common configuration of web.
 * @author Fast Retailing
 * @version $Revision$
 */
@Component
@Configuration
@ComponentScan(basePackages = {
        "com.fastretailing.dcp.common.dozer",
        "com.fastretailing.dcp.common.hmac",
        "com.fastretailing.dcp.common.web",
        "com.fastretailing.dcp.common.api",
        "com.fastretailing.dcp.common.util",
        "com.fastretailing.dcp.common.mail",
        "com.fastretailing.dcp.common.aws"
})
public class WebCommonConfiguration {
}
