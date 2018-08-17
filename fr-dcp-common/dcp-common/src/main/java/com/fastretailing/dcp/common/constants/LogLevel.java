/**
 * @(#)LogLabel.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.constants;

/**
 * log level enum.
 * @author Fast Retailing
 * @version $Revision$
 */
public enum LogLevel {

    /**
     * log label: DEBUG.
     */
    DEBUG("D"),

    /**
     * log label: INFO.
     */
    INFO("I"),

    /**
     * log label: WARN.
     */
    WARN("W"),

    /**
     * log label: ERROR.
     */
    ERROR("E"),

    /**
     * log label: NOTICE.
     */
    NOTICE("N"),

    /**
     * log label: CRITICAL.
     */
    CRITICAL("C");

    /**
     * log level.
     */
    private String logLevel;

    /**
     * constructor.
     * @param logLevel logLevel
     */
    LogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    @Override
    public String toString() {
        return this.logLevel;
    }
}
