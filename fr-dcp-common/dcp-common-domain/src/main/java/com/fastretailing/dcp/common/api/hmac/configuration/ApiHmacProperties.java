/**
 * @(#)ApiHmacProperties.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.hmac.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.fastretailing.dcp.common.hmac.configuration.HmacProperties;

import lombok.Data;

/**
 * the properties info bean of hmac authentication.
 * @author Fast Retailing
 * @version $Revision$
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
@Data
public class ApiHmacProperties extends HmacProperties {
}
