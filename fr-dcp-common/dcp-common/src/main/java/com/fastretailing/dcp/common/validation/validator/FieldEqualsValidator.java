/**
 * @(#)FieldEqualsValidator.java
 * 
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.validation.validator;

import com.fastretailing.dcp.common.validation.annotation.FieldEquals;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * <pre>
 * check whether input information is identical.
 * This is validator class of {@link FieldEquals}
 *
 * </pre>
 * @author Fast Retailing
 * @version $Revision$
 */
public class FieldEqualsValidator implements
        ConstraintValidator<FieldEquals, Object> {

    /** check filed1. */
    private String firstField;

    /** check filed2. */
    private String secondField;

    /** error message. */
    private String message;

    /** replacement part words of message. */
    private String[] messageParam;

    /** message definition. */
    @Autowired
    private MessageSource messageSource;

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
    public void initialize(FieldEquals annotation) {
        firstField = annotation.firstField();
        secondField = annotation.secondField();
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
        BeanWrapper beanWrapper = new BeanWrapperImpl(value);
        Object firstValue = beanWrapper.getPropertyValue(firstField);
        Object secondValue = beanWrapper.getPropertyValue(secondField);

        if (firstValue == null && secondValue == null) {
            return true;
        } else if (Objects.equals(firstValue, secondValue)) {
            return true;
        } else {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    ValidationMessageHelper.createValidationMessage(message,
                            messageParam, messageSource))
                    .addPropertyNode(firstField)
                    .addConstraintViolation();
            return false;
        }
    }

}
