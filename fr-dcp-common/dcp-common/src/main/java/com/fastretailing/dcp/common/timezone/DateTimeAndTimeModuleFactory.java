/**
 * @(#)DateTimeAndTimeModuleFactory.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.timezone;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;


/**
 * The factory class for create jackson's module of DateTimeDeserializer/DateTimeSerializer<br>
 *     and TimeDeserializer/TimeSerializer. <br>
 * @author Fast Retailing
 * @version $Revision$
 */
@Component
public class DateTimeAndTimeModuleFactory {

    /**
     * simple module of DateTime.
     */
    private static SimpleModule simpleModuleOfDateTime;

    /**
     * simple module of Time.
     */
    private static SimpleModule simpleModuleOfTime;

    /**
     * Create a singleton instance of LocalDateTime's simple module.<br>
     * @return instance
     */
    public static synchronized SimpleModule createDateTimeSimpleModule() {
        if (simpleModuleOfDateTime == null) {
            SimpleModule simpleModuleLocalDateTime = new SimpleModule();
            simpleModuleLocalDateTime.addDeserializer(LocalDateTime.class,
                    new DateTimeDeserializer());
            simpleModuleLocalDateTime.addSerializer(LocalDateTime.class, new DateTimeSerializer());
            simpleModuleOfDateTime = simpleModuleLocalDateTime;
        }

        return simpleModuleOfDateTime;
    }

    /**
     * Create a singleton instance of LocalTime's simple module.<br>
     * @return instance
     */
    public static synchronized SimpleModule createTimeSimpleModule() {
        if (simpleModuleOfTime == null) {
            SimpleModule simpleModuleLocalTime = new SimpleModule();
            simpleModuleLocalTime.addDeserializer(LocalTime.class, new TimeDeserializer());
            simpleModuleLocalTime.addSerializer(LocalTime.class, new TimeSerializer());
            simpleModuleOfTime = simpleModuleLocalTime;
        }

        return simpleModuleOfTime;
    }

}
