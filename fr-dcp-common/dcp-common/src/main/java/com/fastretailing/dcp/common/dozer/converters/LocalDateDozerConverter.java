/**
 * @(#)LocalDateDozerConverter.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.dozer.converters;

import java.time.LocalDate;

import org.dozer.DozerConverter;

/**
 * Dozer's converter for java type {@link LocalDate}.<br>
 * Because Dozer was not support JSR-310 Date and Time API.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public class LocalDateDozerConverter extends DozerConverter<LocalDate, LocalDate> {

    /**
     * Constructor.
     */
    public LocalDateDozerConverter() {
        super(LocalDate.class, LocalDate.class);
    }

    /**
     * convert to method for converter interface.
     * @param source source
     * @param destination destination
     * @return result
     */
    @Override
    public LocalDate convertTo(final LocalDate source, final LocalDate destination) {

        if (source == null) {
            return null;
        }

        return LocalDate.of(
                source.getYear(),
                source.getMonth(),
                source.getDayOfMonth());
    }

    /**
     * convert from method for converter interface.
     * @param source source
     * @param destination destination
     * @return result
     */
    @Override
    public LocalDate convertFrom(final LocalDate source,
                                 final LocalDate destination) {

        if (source == null) {
            return null;
        }

        return LocalDate.of(
                source.getYear(),
                source.getMonth(),
                source.getDayOfMonth());
    }

}
