/**
 * @(#)SystemDateTimeApplication.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.util;

import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class SystemDateTimeApplication {

    private SystemDateTime systemDateTime;

    public SystemDateTimeApplication(SystemDateTime systemDateTime) {
        this.systemDateTime = systemDateTime;
    }

    public OffsetDateTime get(){
        return systemDateTime.now();
    }
}
