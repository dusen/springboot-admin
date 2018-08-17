/**
 * @(#)HmacAuthenticationContextHolder.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.hmac.context;

/**
 * HmacAuthentication's context holder class.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public class HmacAuthenticationContextHolder {

    /**
     * The Thread-Safe storage for the HmacAuthentication's context.<br>
     */
    private static final ThreadLocal<HmacAuthenticationContext> contextHolder = new ThreadLocal<>();

    /**
     * Set the HmacAuthentication's context.<br>
     * @param context HmacAuthentication's context.
     */
    public static void setHmacAuthenticationContext(HmacAuthenticationContext context) {
        contextHolder.set(context);
    }

    /**
     * Get the HmacAuthentication's context.<br>
     * @return HmacAuthentication's context.
     */
    public static HmacAuthenticationContext getHmacAuthenticationContext() {
        return contextHolder.get();
    }

    /**
     * Clean the HmacAuthentication's context.<br>
     */
    public static void cleanHmacAuthenticationContext() {
        contextHolder.remove();
    }
}
