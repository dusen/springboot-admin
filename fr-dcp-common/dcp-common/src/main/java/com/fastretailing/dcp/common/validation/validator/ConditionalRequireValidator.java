/**
 * @(#)ConditionalRequireValidator.java
 * 
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.validation.validator;

import com.fastretailing.dcp.common.util.CommonUtility;
import com.fastretailing.dcp.common.validation.annotation.ConditionalRequire;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * <pre>
 * if check condition has inputted, check that whether check target has been inputted
 * This is validator class of {@link ConditionalRequire}.
 *
 * </pre>
 * @author Fast Retailing
 * @version $Revision$
 */
public class ConditionalRequireValidator
                                        implements
        ConstraintValidator<ConditionalRequire, Object> {

    /** field name of check condition. */
    private String condition;

    /** field name of check conditionValue. */
    private String conditionValue;

    /** field name of check . */
    private String target;

    /** error message. */
    private String message;

    /** replacement part words of message. */
    private String[] messageParam;

    /** message definition. */
    @Autowired
    private MessageSource messageSource;

    /**
     * define supported classes's validators.
     */
    private static final Map<Class, BiFunction<Object, String, Boolean>> validators =
            new HashMap<>();

    /**
     * initialize member variables when load class.
     */
    @PostConstruct
    private void initializeMemberVariables() {
        // add predicate lambda to all types
        validators.put(String.class, (conditionInput, conditionValue) ->
                Objects.equals(conditionInput, conditionValue));

        validators.put(BigDecimal.class, (conditionInput, conditionValue) -> {
            BigDecimal value = new BigDecimal(conditionValue);
            return Objects.equals(conditionInput, value); });

        validators.put(BigInteger.class, (conditionInput, conditionValue) -> {
            BigInteger value = new BigInteger(conditionValue);
            return Objects.equals(conditionInput, value); });

        validators.put(Short.class, (conditionInput, conditionValue) -> {
            Short value = new Short(conditionValue);
            return Objects.equals(conditionInput, value); });

        validators.put(Integer.class, (conditionInput, conditionValue) -> {
            Integer value = new Integer(conditionValue);
            return Objects.equals(conditionInput, value); });

        validators.put(Long.class, (conditionInput, conditionValue) -> {
            Long value =  Long.valueOf(conditionValue);
            return Objects.equals(conditionInput, value); });

        validators.put(byte[].class, (conditionInput, conditionValue) -> {
            long input = ByteBuffer.wrap((byte[]) conditionInput).getLong();
            long value = Long.valueOf(conditionValue);
            return Objects.equals(input, value); });

        validators.put(Byte[].class, (conditionInput, conditionValue) -> {
            long input = CommonUtility.byteArrayToLong((Byte[]) conditionInput);
            long value = Long.valueOf(conditionValue);
            return Objects.equals(input, value); });

        validators.put(Boolean.class, (conditionInput, conditionValue) -> {
            Boolean value = Boolean.valueOf(conditionValue);
            return Objects.equals(conditionInput, value); });

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
    public void initialize(ConditionalRequire annotation) {
        condition = annotation.condition();
        conditionValue = annotation.conditionValue();
        target = annotation.target();
        message = annotation.message();
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
        if (StringUtils.isEmpty(condition)
                || StringUtils.isEmpty(conditionValue)
                || StringUtils.isEmpty(target)) {
            return true;
        } else {
            BeanWrapper beanWrapper = new BeanWrapperImpl(value);
            Object conditionInput = beanWrapper.getPropertyValue(condition);
            Object targetInput = beanWrapper.getPropertyValue(target);

            if (Objects.isNull(conditionInput)) {
                return true;
            }

            boolean valid = true;
            if (hasInputted(conditionInput, validators.get(conditionInput.getClass()))) {
                if (targetInput instanceof String) {
                    valid = StringUtils.hasText((String) targetInput);
                } else {
                    valid = Objects.nonNull(targetInput);
                }
            }

            if (!valid) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                        ValidationMessageHelper.createValidationMessage(message,
                                messageParam, messageSource)).addPropertyNode(
                        target).addConstraintViolation();
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * <pre>
     * compare the value of input check condition
     * with check condition value,decide whether the value check target need or not
     * if the value of input check condition equals
     * with check condition value, check target is necessary
     * if the value of input check condition does not
     * equals with check condition value, check target is not necessary.
     *
     * Types of check condition allowed as follows:
     * ・String
     * ・BigDecimal
     * ・BigInteger
     * ・Short
     * ・Integer
     * ・Long
     * ・byte[]
     * ・Byte[]
     * ・Boolean
     * ・boolean
     * </pre>
     * @param conditionInput check target
     * @param validator check method(lambda)
     * @return has inputted?
     */
    private boolean hasInputted(Object conditionInput,
                            BiFunction<Object, String, Boolean> validator) {
        if (validator == null) {
            // if the value's type is not supported, then return true
            return true;
        } else {

            if (validator.apply(conditionInput, conditionValue)) {
                return true;
            } else {
                return false;
            }
        }
    }

}
