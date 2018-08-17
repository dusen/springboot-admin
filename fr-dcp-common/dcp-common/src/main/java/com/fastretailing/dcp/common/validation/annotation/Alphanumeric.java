/**
 * @(#)Alphanumeric.java
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
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * verified target field for AlphaNumber and AlphaEnglish only([a-zA-Z0-9]*).
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ReportAsSingleViolation
@Pattern(regexp = "[a-zA-Z0-9]*")
@Constraint(validatedBy = {})
public @interface Alphanumeric {

    /**
     * Error message or message key.
     * @return error message or message key.
     */
    String message() default "{w.common.validation.alphanumeric}";

    /**
     * Constraint groups.
     * @return constraint groups.
     */
    Class<?>[] groups() default {};

    /**
     * Payload.
     * @return payload.
     */
    Class<? extends Payload>[] payload() default {};


    /**
     * messageParam.
     * @return messageParam.
     */
    String[] messageParam() default {};

    /**
     * definition for multiple annotations.
     */
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @ReportAsSingleViolation
    @interface List {
        Alphanumeric[] value();
    }
}
