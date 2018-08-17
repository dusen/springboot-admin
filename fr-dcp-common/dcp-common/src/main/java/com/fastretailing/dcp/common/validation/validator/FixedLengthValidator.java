/**
 * @(#)FixedLengthValidator.java
 * 
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.validation.validator;

import com.fastretailing.dcp.common.validation.annotation.FixedLength;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 * check whether the length of check target equals with specified numbers.
 * This is validator class of {@link FixedLength}
 *
 * </pre>
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
public class FixedLengthValidator implements
        ConstraintValidator<FixedLength, String> {

    /** specified number.necessary. */
    private String[] length;

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
    public void initialize(FixedLength annotation) {
        length = annotation.length();
    }

    /**
     * Implements the validation logic.
     * The state of {@code value} must not be altered.
     * <p/>
     * This method can be accessed concurrently, thread-safety must be ensured
     * by the implementation.
     *
     * @param str object to validate
     * @param context context in which the constraint is evaluated
     *
     * @return {@code false} if {@code value} does not pass the constraint
     */
    @Override
    public boolean isValid(String str, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(str)) {
            return true;
        } else {
            List lenList = Arrays.asList(length);

            if (!CollectionUtils.isEmpty(lenList)) {
                return lenList.contains(String.valueOf(str.codePointCount(0, str.length())));
            } else {
                return false;
            }
        }
    }
}
