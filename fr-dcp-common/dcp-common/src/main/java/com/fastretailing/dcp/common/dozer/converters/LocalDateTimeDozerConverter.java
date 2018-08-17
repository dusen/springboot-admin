/**
 * @(#)LocalDateTimeDozerConverter.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.dozer.converters;

import java.time.LocalDateTime;

import org.dozer.DozerConverter;

/**
 * Dozer's converter for java type {@link LocalDateTime}.<br>
 * Because Dozer was not support JSR-310 Date and Time API.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public class LocalDateTimeDozerConverter extends DozerConverter<LocalDateTime, LocalDateTime> {

    /**
     * Constructor.
     */
    public LocalDateTimeDozerConverter() {
        super(LocalDateTime.class, LocalDateTime.class);
    }

    /**
     * convert to method for converter interface.
     * @param source source
     * @param destination destination
     * @return result
     */
    @Override
    public LocalDateTime convertTo(final LocalDateTime source, final LocalDateTime destination) {

        if (source == null) {
            return null;
        }

        return LocalDateTime.of(
                source.getYear(),
                source.getMonth(),
                source.getDayOfMonth(),
                source.getHour(),
                source.getMinute(),
                source.getSecond(),
                source.getNano());
    }

    /**
     * convert from method for converter interface.
     * @param source source
     * @param destination destination
     * @return result
     */
    @Override
    public LocalDateTime convertFrom(final LocalDateTime source,
                                     final LocalDateTime destination) {

        if (source == null) {
            return null;
        }

        return LocalDateTime.of(
                source.getYear(),
                source.getMonth(),
                source.getDayOfMonth(),
                source.getHour(),
                source.getMinute(),
                source.getSecond(),
                source.getNano());
    }

}
