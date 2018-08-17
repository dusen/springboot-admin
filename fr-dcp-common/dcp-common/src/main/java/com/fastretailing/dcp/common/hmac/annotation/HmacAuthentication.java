/**
 * @(#)HmacAuthentication.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.hmac.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * HmacAuthentication.
 * <pre>
 * check authentication
 *
 * </pre>
 * @author Fast Retailing
 * @version $Revision$
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface HmacAuthentication {

    /**
     * the value of pf name
     * @return field name
     */
    String value() default "";

}
