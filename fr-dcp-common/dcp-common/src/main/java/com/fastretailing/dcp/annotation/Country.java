/**
 * @(#)Country.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.annotation;

import org.springframework.context.annotation.Conditional;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Documented;

/**
 * Use this annotation to mark the country's logic.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Conditional(CountryProfileCondition.class)
public @interface Country {

    /**
     * The set of profiles for which the annotated component should be registered.
     */
    Countries[] value();

}