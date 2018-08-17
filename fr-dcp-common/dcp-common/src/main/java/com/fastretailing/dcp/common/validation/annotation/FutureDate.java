/**
 * @(#)FutureDate.java
 * 
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.validation.annotation;

import com.fastretailing.dcp.common.validation.validator.FutureDateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.Documented;

/**
 * <pre>
 * check whether input date is later then now date.
 * this check does not check time part, check date only.
 * input type allows: LocalDate LocalDateTime
 * This is validator class of {@link PastDate}
 *
 * </pre>
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = FutureDateValidator.class)
public @interface FutureDate {

    /**
     * Error message or message key
     * @return error message or message key
     */
    String message() default "{w.common.validation.future-date}";

    /**
     * Constraint groups
     * @return constraint groups
     */
    Class<?>[] groups() default {};

    /**
     * Payload
     * @return payload
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * <pre>
     * Allow today(system date) as future day's flag
     * true:  the day of check day OK
     * false: the day of check day NG (default)
     * </pre>
     */
    boolean today() default false;

    /**
     * definition for multiple annotations.
     */
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        FutureDate[] value();
    }
}
