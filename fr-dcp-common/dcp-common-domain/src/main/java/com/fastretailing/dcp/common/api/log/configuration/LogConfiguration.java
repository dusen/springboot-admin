/**
 * @(#)LogConfiguration.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.log.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * the configuration of log.
 * @author Fast Retailing
 * @version $Revision$
 */
@Component
@Configuration
@ComponentScan(basePackages = {"com.fastretailing.dcp.common.api.log"})
public class LogConfiguration {
}
