/**
 * @(#)ConditionalEmptyValidator.java
 * 
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.validation.validator;

import com.fastretailing.dcp.common.util.CommonUtility;
import com.fastretailing.dcp.common.validation.annotation.ConditionalEmpty;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <pre>
 * if check condition has been inputted, check that whether check target is null or blank.
 * This is validator class of {@link ConditionalEmpty}
 *
 * </pre>
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
public class ConditionalEmptyValidator implements ConstraintValidator<ConditionalEmpty, Object> {

    /** field name of check condition. */
    private String condition;

    /** check target. */
    private String target;

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
    public void initialize(ConditionalEmpty annotation) {
        condition = annotation.condition();
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
        if (StringUtils.isEmpty(condition) || StringUtils.isEmpty(target)) {
            return true;
        } else {
            BeanWrapper beanWrapper = new BeanWrapperImpl(value);
            Object conditionValue = beanWrapper.getPropertyValue(condition);
            Object targetValue = beanWrapper.getPropertyValue(target);
            // two fields were inputted is forbidden
            if (!CommonUtility.isNullOrBlank(conditionValue)
                    && !CommonUtility.isNullOrBlank(targetValue)) {
                // Build the message.
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                        ValidationMessageHelper.createValidationMessage(message,
                                messageParam, messageSource))
                        .addPropertyNode(condition)
                        .addConstraintViolation();
                return false;
            } else {
                return true;
            }
        }
    }
}
