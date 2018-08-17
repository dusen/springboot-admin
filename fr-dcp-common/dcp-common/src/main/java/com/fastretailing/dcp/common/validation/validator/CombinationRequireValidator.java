/**
 * @(#)CombinationRequireValidator.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.validation.validator;

import com.fastretailing.dcp.common.validation.annotation.CombinationRequire;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

/**
 * <pre>
 * check all input filed sequence required.
 * This is validator class of {@link CombinationRequire}
 *
 * </pre>>
 * @author Fast Retailing
 * @version $Revision$
 */
public class CombinationRequireValidator
        implements
        ConstraintValidator<CombinationRequire, Object> {

    /** fields to check. */
    private String[] checkFields;

    /** error message. */
    private String message;

    /** replacement parts of message. */
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
    public void initialize(CombinationRequire annotation) {
        checkFields = annotation.checkFields();
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
        if (checkFields.length == 0) {
            return true;
        } else {
            BeanWrapper beanWrapper = new BeanWrapperImpl(value);
            long emptyNum = Arrays.asList(checkFields).stream()
                    .filter(checkField ->
                            beanWrapper.getPropertyValue(checkField) instanceof String
                                    && StringUtils.isEmpty(beanWrapper.getPropertyValue(checkField))
                            || (beanWrapper.getPropertyValue(checkField) == null)
                    )
                    .count();

            if (emptyNum == 0 || emptyNum == checkFields.length) {
                return true;
            } else {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                        ValidationMessageHelper.createValidationMessage(message,
                                messageParam, messageSource))
                        .addPropertyNode(checkFields[0])
                        .addConstraintViolation();
                return false;
            }
        }
    }
}
