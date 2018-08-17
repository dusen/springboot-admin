/**
 * @(#)RequireCorrelationValidator.java
 * 
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.validation.validator;

import com.fastretailing.dcp.common.util.CommonUtility;
import com.fastretailing.dcp.common.validation.annotation.RequireCorrelation;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <pre>
 * if one of the input targets is not null or blank, the other one must be inputted.
 * This is validator class of {@link RequireCorrelation}.
 *
 * </pre>
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
public class RequireCorrelationValidator
        implements ConstraintValidator<RequireCorrelation, Object> {

    /** target filed1. necessary. */
    private String firstField;

    /** target filed1. necessary. */
    private String secondField;

    /** error message. */
    private String message;

    /** replacement parts of message. */

    private String[] messageParam;
    /** error message. */
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
    public void initialize(RequireCorrelation annotation) {
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

        if (StringUtils.isEmpty(firstField) || StringUtils.isEmpty(secondField)) {
            return true;
        }

        BeanWrapper beanWrapper = new BeanWrapperImpl(value);
        Object firstValue = beanWrapper.getPropertyValue(firstField);
        Object secondValue = beanWrapper.getPropertyValue(secondField);

        boolean valid;
        if (!CommonUtility.isNullOrBlank(firstValue)
                && !CommonUtility.isNullOrBlank(secondValue)) {
            // firstValue and secondValue are not null and not blank value
            valid = true;
        } else if (CommonUtility.isNullOrBlank(firstValue)
                && CommonUtility.isNullOrBlank(secondValue)) {
            // firstValue and secondValue are null or blank
            valid = true;
        } else {
            valid = false;
        }
        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    ValidationMessageHelper
                            .createValidationMessage(message,messageParam, messageSource))
                    .addPropertyNode(firstField)
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
