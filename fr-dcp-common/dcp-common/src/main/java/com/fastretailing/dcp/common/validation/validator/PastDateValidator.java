/**
 * @(#)PastDateValidator.java
 * 
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.validation.validator;

import com.fastretailing.dcp.common.util.CommonUtility;
import com.fastretailing.dcp.common.validation.annotation.PastDate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <pre>
 * check whether input date is Sooner then now date.this check does not check time part,
 * check date only input type allows: LocalDate LocalDateTime.
 * This is validator class of {@link PastDate}
 *
 * </pre>
 * @author Fast Retailing
 * @version $Revision$
 */
public class PastDateValidator implements ConstraintValidator<PastDate, Object> {

    /** allow the day of check day flag. */
    private boolean today;

    /** commonUtility. */
    @Autowired
    private CommonUtility commonUtility;

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
    public void initialize(PastDate annotation) {
        today = annotation.today();
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
            LocalDate operationDate = commonUtility.getOperationAt().toLocalDate();

            LocalDate valuePastDate;
            if (LocalDateTime.class.equals(value.getClass())) {
                valuePastDate = ((LocalDateTime) value).toLocalDate();
            } else if (LocalDate.class.equals(value.getClass())) {
                valuePastDate = (LocalDate) value;
            } else {
                return true;
            }

            boolean valid;
            if (today) {
                valid = (valuePastDate).compareTo(operationDate) <= 0;
            } else {
                valid = (valuePastDate).compareTo(operationDate) < 0;
            }

            return valid;
        }
    }
}
