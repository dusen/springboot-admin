/**
 * @(#)PriceUtils.java
 *
 *                     Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.util;

import java.math.BigDecimal;
import java.util.Currency;
import com.fastretailing.dcp.storecommon.dto.Price;

/**
 * Price utility class.
 */
public class PriceUtils {

    /**
     * Private constructor.
     */
    private PriceUtils() {}

    /**
     * Get currency code.
     * 
     * @param price Price.
     * @return Currency code or null.
     */
    public static String getCurrencyCode(Price price) {
        if (price == null) {
            return null;
        }
        return getCurrencyCode(price.getCurrencyCode());
    }

    /**
     * Get currency code.
     * 
     * @param currency Currency.
     * @return Currency code or null.
     */
    public static String getCurrencyCode(Currency currency) {
        if (currency == null) {
            return null;
        }
        return currency.getCurrencyCode();
    }

    /**
     * Set scale to price value.
     * 
     * @param price Price.
     * @param newScale Scale.
     * @param roundingMode Big decimal rounding mode.
     */
    public static Price setScale(Price price, int newScale, int roundingMode) {
        if (price.getValue() == null) {
            return price;
        }

        price.setValue(setScale(price.getValue(), newScale, roundingMode));
        return price;
    }

    /**
     * Set scale to price value.
     * 
     * @param value Big decimal value.
     * @param newScale Scale.
     * @param roundingMode Big decimal rounding mode.
     */
    public static BigDecimal setScale(BigDecimal value, int newScale, int roundingMode) {
        if (value == null) {
            return null;
        }

        return value.setScale(newScale, roundingMode);
    }
}
