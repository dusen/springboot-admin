/**
 * @(#)BrandAndRegionHolder.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.threadlocal;

import java.util.Map;

/**
 *  Brand and region holder class.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public class BrandAndRegionHolder {

    /**
     * the key of brand.
     */
    public static final String KEY_BRAND = "brand";

    /**
     * the key of region.
     */
    public static final String KEY_REGION = "region";

    /**
     * The Thread-Safe storage for the path variable.<br>
     */
    protected static final ThreadLocal<Map<String, String>> brandAndRegionHolder
            = new ThreadLocal<>();

    /**
     * Set the brandAndRegion's context.<br>
     * @param brandAndRegionMap brandAndRegion's context.
     */
    public static void setBrandAndRegionMap(Map<String, String> brandAndRegionMap) {
        brandAndRegionHolder.set(brandAndRegionMap);
    }

    /**
     * Get the brand information in thread local.<br>
     * @return the brand information .
     */
    public static String getBrand() {
        return getBrandOrRegionValue(KEY_BRAND);
    }

    /**
     * Get the brand information in thread local.<br>
     * @return the brand information .
     */
    public static String getRegion() {
        return getBrandOrRegionValue(KEY_REGION);
    }

    /**
     * Get the brand or region information in thread local.<br>
     * @param key key
     * @return the value
     */
    private static String getBrandOrRegionValue(String key) {
        Map<String, String> brandAndRegionMap = brandAndRegionHolder.get();
        if (brandAndRegionMap == null) {
            return null;
        }
        return brandAndRegionMap.get(key);
    }
}
