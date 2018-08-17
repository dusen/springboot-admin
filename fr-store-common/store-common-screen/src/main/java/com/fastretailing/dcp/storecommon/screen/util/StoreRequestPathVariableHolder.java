/**
 * @(#)StoreRequestPathVariableHolder.java
 *
 *                                         Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.util;

import java.util.Map;
import com.fastretailing.dcp.common.api.threadlocal.RequestPathVariableHolder;

/**
 * Path variable holder class for store.
 */
public class StoreRequestPathVariableHolder extends RequestPathVariableHolder {

    /**
     * the key of store code.
     */
    public static final String KEY_STORECODE = "storeCode";

    /**
     * Get the store code information in thread local.<br>
     * 
     * @return the store code information .
     */
    public static String getStoreCode() {
        return getRequestPathValue(KEY_STORECODE);
    }

    /**
     * Get the request path variable information in thread local.
     * 
     * @param key key.
     * @return the value.
     */
    protected static String getRequestPathValue(String key) {
        Map<String, String> brandAndRegionMap = brandAndRegionHolder.get();
        if (brandAndRegionMap == null) {
            return null;
        }
        return brandAndRegionMap.get(key);
    }

}
