/**
 * @(#)RequestPathVariableHolder.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.threadlocal;

import com.fastretailing.dcp.common.threadlocal.BrandAndRegionHolder;

import java.util.Map;

/**
 *  Path Variable holder class.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public class RequestPathVariableHolder extends BrandAndRegionHolder {

    /**
     * Set the request's context.<br>
     * @param pathVariableMap pathVariableMap's context.
     */
    public static void setRequestPathVariableMap(Map<String, String> pathVariableMap) {
        brandAndRegionHolder.set(pathVariableMap);
    }
}
