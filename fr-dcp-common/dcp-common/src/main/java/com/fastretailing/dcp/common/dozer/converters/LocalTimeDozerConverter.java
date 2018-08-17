/**
 * @(#)LocalTimeDozerConverter.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.dozer.converters;

import java.time.LocalTime;

import org.dozer.DozerConverter;

/**
 * Dozer's converter for java type {@link LocalTime}.<br>
 * Because Dozer was not support JSR-310 Date and Time API.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public class LocalTimeDozerConverter extends DozerConverter<LocalTime, LocalTime> {

    /**
     * Constructor.
     */
    public LocalTimeDozerConverter() {
        super(LocalTime.class, LocalTime.class);
    }

    /**
     * convert to method for converter interface.
     * @param source source
     * @param destination destination
     * @return result
     */
    @Override
    public LocalTime convertTo(final LocalTime source, final LocalTime destination) {

        if (source == null) {
            return null;
        }

        return LocalTime.of(
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
    public LocalTime convertFrom(final LocalTime source,
                                 final LocalTime destination) {

        if (source == null) {
            return null;
        }

        return LocalTime.of(
                source.getHour(),
                source.getMinute(),
                source.getSecond(),
                source.getNano());
    }

}
