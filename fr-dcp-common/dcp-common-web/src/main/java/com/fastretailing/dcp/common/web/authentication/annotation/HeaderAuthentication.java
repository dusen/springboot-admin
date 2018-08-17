/**
 * @(#)HeaderAuthentication.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.web.authentication.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * HeaderAuthentication.
 *
 * When non-empty check of admin-userid or front-memberid in HTTP Request Header is required, Use this annotation.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface HeaderAuthentication {

    /**
     * Check flag of admin-userid
     * @return
     *          true do check<br/>
     *          false do not check
     */
    boolean userIdCheckFlag() default true;

    /**
     * Check flag of front-memberid
     * @return
     *          true do check<br/>
     *          false do not check
     */
    boolean memberIdCheckFlag() default true;

}
