/**
 * @(#)ReadOnlyTransactional.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.datasource.annotation;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ReadOnlyTransactional.<br>
 * @author Fast Retailing
 * @version $Revision$
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
public @interface ReadOnlyTransactional {

    /**
     * Datasource's name<br>
     */
    String name() default "";
}
