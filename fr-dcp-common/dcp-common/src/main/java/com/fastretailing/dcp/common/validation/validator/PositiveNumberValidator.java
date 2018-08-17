/**
 * @(#)PositiveNumberValidator.java
 * 
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.validation.validator;

import com.fastretailing.dcp.common.util.CommonUtility;
import com.fastretailing.dcp.common.validation.annotation.PositiveNumber;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * <pre>
 * check whether check target is positive.
 * This is validator class of {@link PositiveNumber}
 *
 * </pre>
 * @author Fast Retailing
 * @version $Revision$
 */
public class PositiveNumberValidator implements
        ConstraintValidator<PositiveNumber, Object> {

    /**
     * define supported classes's validators.
     */
    private static final Map<Class, Predicate<Object>> validators = new HashMap<>();

    /**
     * initialize member variables when load class.
     */
    @PostConstruct
    private void initializeMemberVariables() {
        // add predicate lambda for all types
        validators.put(BigDecimal.class, value -> {
            BigDecimal num = (BigDecimal) value;
            return 0 <= num.compareTo(BigDecimal.valueOf(0)); });

        validators.put(BigInteger.class, value -> {
            BigInteger num = (BigInteger) value;
            return 0 <= num.compareTo(BigInteger.valueOf(0)); });

        validators.put(Long.class, value -> 0 <= (long) value);

        validators.put(Integer.class, value -> 0 <= (int) value);

        validators.put(Short.class, value -> 0 <= (short) value);

        validators.put(byte[].class, value -> 0 <= ByteBuffer.wrap((byte[]) value).getLong());

        validators.put(Byte[].class, value -> 0 <= CommonUtility.byteArrayToLong((Byte[]) value));
    }

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
    public void initialize(PositiveNumber annotation) {
    }

    /**
     * Implements the validation logic.
     * The state of {@code value} must not be altered.
     * <p/>
     * This method can be accessed concurrently, thread-safety must be ensured
     * by the implementation.
     *
     * @param value object to validate
     * @param context context in which the constraint is evaluated
     *
     * @return {@code false} if {@code value} does not pass the constraint
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        } else {
            return isValid(value, validators.get(value.getClass()));
        }
    }

    /**
     * do check.
     * @param value check target
     * @param validator check method(lambda)
     * @return is valid?
     */
    private boolean isValid(Object value, Predicate<Object> validator) {
        if (validator == null) {
            // if the value's type is not supported, then return true
            return true;
        } else {
            return validator.test(value);
        }
    }


}
