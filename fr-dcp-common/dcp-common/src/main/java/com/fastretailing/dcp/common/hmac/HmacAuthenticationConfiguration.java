/**
 * @(#)HmacAuthenticationConfiguration.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.hmac;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * the configuration of hmac authentication.
 * @author Fast Retailing
 * @version $Revision$
 */
@Component
@Configuration
@ComponentScan(basePackages = {"com.fastretailing.dcp.common.hmac"})
public class HmacAuthenticationConfiguration {

}
