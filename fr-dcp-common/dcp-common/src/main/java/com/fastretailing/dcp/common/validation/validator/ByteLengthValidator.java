/**
 * @(#)ByteLengthValidator.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.validation.validator;

import com.fastretailing.dcp.common.validation.annotation.ByteLength;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

/**
 * validator for byte length.
 * @author Fast Retailing
 * @version $Revision$
 */
@Slf4j
public class ByteLengthValidator
        implements ConstraintValidator<ByteLength, String> {

    /** min length. */
    private int min;

    /** max length. */
    private int max;

    /** charset. */
    private String charset;

    /**
     * Initializes the validator in preparation for
     * {@link #isValid(Object, ConstraintValidatorContext)} calls.
     * The constraint annotation for a given constraint declaration
     * is passed.
     * <p/>
     * This method is guaranteed to be called before any use of this instance for
     * validation.
     *
     * @param annotation annotation instance for a given constraint declaration
     */
    @Override
    public void initialize(ByteLength annotation) {
        min = annotation.min();
        max = annotation.max();
        charset = annotation.charset();
    }

    /**
     * Implements the validation logic.
     * The state of {@code value} must not be altered.
     * <p/>
     * This method can be accessed concurrently, thread-safety must be ensured
     * by the implementation.
     *
     * @param value object to validate
     * @param ctx context in which the constraint is evaluated
     *
     * @return {@code false} if {@code value} does not pass the constraint
     */
    @Override
    public boolean isValid(String value,
                           ConstraintValidatorContext ctx) {

        if (value == null) {
            // if null,return true
            return true;
        } else {
            try {
                byte[] b = value.getBytes(Charset.forName(charset));
                return min <= b.length && b.length <= max;
            } catch (UnsupportedCharsetException e) {
                log.error("UnsupportedCharsetException occurred: " + e);
            }
            return false;
        }

    }
}