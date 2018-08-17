/**
 * @(#)RequireCorrelation.java
 * 
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.validation.annotation;

import com.fastretailing.dcp.common.validation.validator.RequireCorrelationValidator;
import com.fastretailing.dcp.common.validation.validator.ValidationMessageHelper;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Documented;

/**
 * <pre>
 * if one of the input targets is not null or blank, the other one must be inputted
 * Please specifies property key of the label name when output message needs label name.
 * Detail:{@link ValidationMessageHelper}
 *
 * </pre>
 * @author Fast Retailing
 * @version $Revision$
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = RequireCorrelationValidator.class)
public @interface RequireCorrelation {

    /**
     * Error message or message key
     * @return error message or message key
     */
    String message() default "{w.common.validation.require-correlation}";

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

    /** target filed1. necessary. */
    String firstField();

    /** target filed2. necessary.  */
    String secondField();

    /** 
     * <pre>
     * definition for multiple annotations.
     * Detail:{@link ValidationMessageHelper}
     * </pre>
     */
    String[] messageParam() default {};

    /**
     * definition for multiple annotations.
     */
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        RequireCorrelation[] value();
    }
}
