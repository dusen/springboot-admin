/**
 * @(#)OmsApiRestTemplateConfiguration.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.client;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.fastretailing.dcp.common.client.OmsRestTemplateConfiguration;

/**
 * Api RestTemplate's configuration.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@ComponentScan(basePackages = {
        "com.fastretailing.dcp.common.api.client",
        "com.fastretailing.dcp.common.client",
        "com.fastretailing.dcp.common.hmac"
})
@Component
public class OmsApiRestTemplateConfiguration extends OmsRestTemplateConfiguration {

}
