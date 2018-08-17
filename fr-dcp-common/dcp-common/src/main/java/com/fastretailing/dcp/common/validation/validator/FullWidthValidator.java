/**
 * @(#)FullWidthValidator.java
 * 
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.validation.validator;

import com.fastretailing.dcp.common.validation.annotation.FullWidth;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * <pre>
 * check whether check target is all full width.
 * This is validator class of {@link FullWidth}
 *
 * </pre>
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
public class FullWidthValidator implements ConstraintValidator<FullWidth, String> {

    /** Half width alphanumeric and mark. */
    private static final String REGEXP_ASCII = ".*\\p{ASCII}.*";

    /** code value include half width kata Kana. */
    private static final String CONTAINS_HALFWIDTH_KATAKANA = ".*[\\uff61-\\uff9f]+.*";

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
    public void initialize(FullWidth annotation) {
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
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return true;
        } else if (Pattern.matches(REGEXP_ASCII, value)) {
            return false;
        } else {
            return !value.matches(CONTAINS_HALFWIDTH_KATAKANA);
        }

    }

}
