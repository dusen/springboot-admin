/**
 * @(#)AlphaNumberMajuscule.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.validation.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;

/**
 * verified target field for AlphaNumber and UpperCase English only([0-9A-Z]*).
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ReportAsSingleViolation
@Pattern(regexp = "[0-9A-Z]*")
@Constraint(validatedBy = {})
public @interface AlphaNumberMajuscule {

    /**
     * Error message or message key
     * @return error message or message key
     */
    String message() default "{w.common.validation.alpha-number-majuscule}";

    /**
     * Constraint groups
     * @return constraint groups
     */
    Class<?>[] groups() default {};

    /**
     * payload
     * @return payload
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * definition for multiple annotations.
     */
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @ReportAsSingleViolation
    @interface List {
        AlphaNumberMajuscule[] value();
    }
}
