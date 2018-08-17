/**
 * @(#)FilteredHttpHeaderHolder.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.client;

import java.util.List;
import java.util.Map;

/**
 * FilteredHttpHeaderHolder.
 *
 * the interceptor to save the filtered with black list request's header variable to thread local.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public class FilteredHttpHeaderHolder {

    /**
     * The Thread-Safe storage for the filtered request's header map.<br>
     */
    private static final ThreadLocal<Map<String, List<String>>> header = new ThreadLocal<>();

    /**
     * Get filtered request's header map in thread local.
     * @return request's header map
     */
    public static Map<String, List<String>> get() {
        return header.get();
    }

    /**
     * Replace filtered request's header map with target.
     * @param map request's header map
     */
    public static void replace(Map<String, List<String>> map) {
        header.remove();
        header.set(map);
    }

}
