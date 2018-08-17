/**
 * @(#)StringDate.java
 * 
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.validation.annotation;

import com.fastretailing.dcp.common.constants.DateTimeFormatterEnum;
import com.fastretailing.dcp.common.validation.validator.StringDateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * check whether the input str matches specified formatter.
 *
 * </pre>
 * @author Fast Retailing
 * @version $Revision$
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = StringDateValidator.class)
public @interface StringDate {

    /**
     * Error message or message key
     * @return error message or message key
     */
    String message() default "{w.common.validation.string-date}";

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

    /** specified formatter patterns,
     * only work when dateTimeFormatter has not been set.
     * */
    String[] patterns() default {"yyyy/MM/dd","yyyy-MM-dd", "yyyy.MM.dd", "yyyyMMdd"};

    /** specified dateTimeFormatter,
     * when this been set, patterns will does not work.
     * */
    DateTimeFormatterEnum[] dateTimeFormatter() default {};

    /** 
     * <pre>
     * ambiguous model flag
     * false( rigorous), true(ambiguous )
     * if true, 2016/12/32 is ok
     * default : false
     * </pre> 
     */
    boolean lenient() default false;

    /**
     * definition for multiple annotations.
     */
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        StringDate[] value();
    }
}
