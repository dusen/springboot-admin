/**
 * @(#)MagnitudeRelation.java
 * 
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.validation.annotation;


import com.fastretailing.dcp.common.validation.validator.MagnitudeRelationValidator;
import com.fastretailing.dcp.common.validation.validator.ValidationMessageHelper;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.Documented;

/**
 * <pre>
 * check whether the value of small is smaller than the value of larger
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
@Constraint(validatedBy = MagnitudeRelationValidator.class)
public @interface MagnitudeRelation {

    /**
     * Error message or message key
     * @return error message or message key
     */
    String message() default "{w.common.validation.magnitude-relation-can-equal}";

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

    /** target field for small. necessary. */
    String small();
    /** target field for large. necessary. */
    String large();
    /** can small equals large flg */
    boolean canEqualFlg() default true;

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
        MagnitudeRelation[] value();
    }
}
