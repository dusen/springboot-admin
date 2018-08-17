/**
 * @(#)SystemDateTime.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.util;

import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.OffsetDateTime;

/**
 * A date-time providing access to the system clock.
 */
@Component
public class SystemDateTime {

    /**
     * The clock converting to date and time using the UTC time-zone.
     */
    private final Clock utcClock = Clock.systemUTC();

    /**
     * Obtains the current date-time from the system clock in the UTC time-zone.
     *
     * @return the current date-time
     */
    public OffsetDateTime now() {
        return OffsetDateTime.now(utcClock);
    }
}
