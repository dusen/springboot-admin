/**
 * @(#)ByteLength.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.validation.annotation;

import com.fastretailing.dcp.common.validation.validator.ByteLengthValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * check byte length of charset.
 * @author Fast Retailing
 * @version $Revision$
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {ByteLengthValidator.class})
public @interface ByteLength {

    /**
     * min.
     */
    int min() default 0;

    /**
     * max.
     */
    int max() default Integer.MAX_VALUE;

    /**
     * charset.
     */
    String charset() default "UTF-8";

    /**
     * Constraint groups.
     * @return constraint groups
     */
    Class<?>[] groups() default {};

    /**
     * Error message or message key.
     * @return error message or message key
     */
    String message() default "{w.common.validation.byte-length}";

    /**
     * Payload.
     * @return payload
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * definition for multiple annotations.
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        ByteLength[] value();
    }
}