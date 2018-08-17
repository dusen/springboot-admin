/**
 * @(#)SystemDateTimeApplicationConfig.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.util;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.fastretailing.dcp.common.util"})
public class SystemDateTimeApplicationConfig {

    @Bean
    public SystemDateTime systemDateTime() {
        return Mockito.spy(SystemDateTime.class);
    }
}
