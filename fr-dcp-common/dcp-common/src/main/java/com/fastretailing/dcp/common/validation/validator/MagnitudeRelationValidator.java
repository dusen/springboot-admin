/**
 * @(#)MagnitudeRelationValidator.java
 * 
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.validation.validator;

import com.fastretailing.dcp.common.util.CommonUtility;
import com.fastretailing.dcp.common.validation.annotation.MagnitudeRelation;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * <pre>
 * check whether the value of small is smaller than the value of larger.
 * This is validator class of {@link MagnitudeRelation}
 *
 * </pre>
 * @author Fast Retailing
 * @version $Revision$
 */
public class MagnitudeRelationValidator implements
        ConstraintValidator<MagnitudeRelation, Object> {

    /** target field for small. */
    private String small;

    /** target field for large. */
    private String large;

    /** can small equals large flg. */
    private boolean canEqualFlg;

    /** error message. */
    private String message;

    /** replacement parts of message. */
    private String[] messageParam;

    /** error message. */
    @Autowired
    private MessageSource messageSource;

    /**
     * define supported classes's validators.
     */
    private static Map<Class, BiFunction<Object, Object, Boolean>> validators =
            new HashMap<>();

    /**
     * initialize member variables when load class.
     */
    @PostConstruct
    private void initializeMemberVariables() {
        validators.put(BigDecimal.class, (small, large) -> {
            BigDecimal l = (BigDecimal) large;
            BigDecimal s = (BigDecimal) small;
            return 0 < l.compareTo(s); });

        validators.put(BigInteger.class, (small, large) -> {
            BigInteger l = (BigInteger) large;
            BigInteger s = (BigInteger) small;
            return 0 < l.compareTo(s); });

        validators.put(Long.class, (small, large) -> {
            Long l = (Long) large;
            Long s = (Long) small;
            return 0 < l.compareTo(s); });

        validators.put(Integer.class, (small, large) -> {
            Integer l = (Integer) large;
            Integer s = (Integer) small;
            return 0 < l.compareTo(s); });

        validators.put(Short.class, (small, large) -> {
            Short l = (Short) large;
            Short s = (Short) small;
            return 0 < l.compareTo(s); });

        validators.put(byte[].class, (small, large) -> {
            long s = ByteBuffer.wrap(((byte[])small)).getLong();
            long l = ByteBuffer.wrap(((byte[])large)).getLong();
            return s < l; });

        validators.put(Byte[].class, (small, large) -> {
            long s = CommonUtility.byteArrayToLong(((Byte[])small));
            long l = CommonUtility.byteArrayToLong(((Byte[])large));
            return s < l; });

        validators.put(LocalDateTime.class, (small, large) ->
                0 < ((LocalDateTime)large).compareTo((LocalDateTime)small));

        validators.put(LocalDate.class, (small, large) ->
                0 < ((LocalDate)large).compareTo((LocalDate)small));

        validators.put(LocalTime.class, (small, large) ->
                0 < ((LocalTime)large).compareTo((LocalTime)small));

        validators.put(String.class, (small, large) -> {
            String l = (String) large;
            String s = (String) small;

            if (s.matches("^\\d+$") && l.matches("^\\d+$")) {
                return 0 < l.compareTo(s);
            } else {
                return true;
            }
        });
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
    public void initialize(MagnitudeRelation annotation) {
        small = annotation.small();
        large = annotation.large();
        canEqualFlg = annotation.canEqualFlg();
        message = canEqualFlg ? annotation.message() :
                "{w.common.validation.magnitude-relation-not-equal}";
        messageParam = annotation.messageParam();
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
        if (StringUtils.isEmpty(small) || StringUtils.isEmpty(large)) {
            return true;
        } else {
            BeanWrapper beanWrapper = new BeanWrapperImpl(value);
            Object smallValue = beanWrapper.getPropertyValue(small);
            Object largeValue = beanWrapper.getPropertyValue(large);

            if (smallValue == null || largeValue == null) {
                return true;
            } else if (!smallValue.getClass().equals(largeValue.getClass())) {
                return true;
            } else {
                if (!isValid(smallValue, largeValue, validators.get(smallValue.getClass()))) {
                    // build message
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate(
                            ValidationMessageHelper
                                    .createValidationMessage(message,messageParam, messageSource))
                            .addPropertyNode(small)
                            .addConstraintViolation();
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    /**
     * do check.
     * @param smallValue check target
     * @param largeValue check target
     * @param validator check method(lambda)
     * @return is valid?
     */
    private boolean isValid(Object smallValue, Object largeValue,
                            BiFunction<Object, Object, Boolean> validator) {
        if (validator == null) {
            // if the value's type is not supported, then return true
            return true;
        } else {

            if (validator.apply(smallValue, largeValue)) {
                return true;
            } else {
                if (canEqualFlg && Objects.deepEquals(smallValue, largeValue)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

}
