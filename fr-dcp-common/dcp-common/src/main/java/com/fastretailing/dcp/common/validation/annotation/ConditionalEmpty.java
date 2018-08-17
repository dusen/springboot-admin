/**
 * @(#)ConditionalEmpty.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.validation.annotation;

import com.fastretailing.dcp.common.validation.validator.ConditionalEmptyValidator;
import com.fastretailing.dcp.common.validation.validator.ValidationMessageHelper;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * if check condition has inputted, check that whether check target is null or blank
 * Please specifies property key of the label name when output message needs label name.
 * Detail:{@link ValidationMessageHelper}
 * 
 * </pre>
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ConditionalEmptyValidator.class)
public @interface ConditionalEmpty {

    /**
     * Error message or message key
     * @return error message or message key
     */
    String message() default "{w.common.validation.conditional-empty}";

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

    /** check condition filed name. necessary */
    String condition();
    /** check target filed name.necessary */
    String target();

    /** 
     * <pre>
     * replacement part words of message.
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
        ConditionalEmpty[] value();
    }
}
