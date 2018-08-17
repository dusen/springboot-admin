/**
 * @(#)InternationalizationProperty.java
 *
 *                                       Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.i18n;

import java.math.RoundingMode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * Internationalization property utility.
 */
@Configuration
@PropertySource("classpath:/i18n/i18n.properties")
public class InternationalizationProperty {

    /** Property key sepalator. */
    private static final String KEY_SEPALATOR = ".";
    /** Date format key. */
    private static final String KEY_DATE_FORMAT = "date.format";
    /** Decimal format key. */
    private static final String KEY_DECIMAL_FORMAT = "decimal.format";
    /** Decimal round point. */
    private static final String KEY_DECIMAL_ROUND_POINT = "decimal.round.point";
    /** Decimal round mode. */
    private static final String KEY_DECIMAL_ROUND_MODE = "decimal.rounding.mode";

    /** Environment. */
    @Autowired
    private Environment env;

    /**
     * Get date format.
     * 
     * @param countryCode Country code.
     * @return Date format.
     */
    public String getDateFormat(String countryCode) {
        return get(countryCode + KEY_SEPALATOR + KEY_DATE_FORMAT);
    }

    /**
     * Get decimal format.
     * 
     * @param countryCode Country code.
     * @return Decimal format.
     */
    public String getDecimalFormat(String countryCode) {
        return get(countryCode + KEY_SEPALATOR + KEY_DECIMAL_FORMAT);
    }

    /**
     * Get decimal round point.
     * 
     * @param countryCode Country code.
     * @return Decimal round point.
     */
    public int getDecimalRoundPoint(String countryCode) {
        String value = get(countryCode + KEY_SEPALATOR + KEY_DECIMAL_ROUND_POINT);
        if (StringUtils.isEmpty(value)) {
            return 0;
        }
        return Integer.parseInt(value);
    }

    /**
     * Get decimal round mode.
     * 
     * @param countryCode Country code.
     * @return Decimal round mode.
     */
    private String getDecimalRoundMode(String countryCode) {
        return get(countryCode + KEY_SEPALATOR + KEY_DECIMAL_ROUND_MODE);
    }

    /**
     * Get rounding mode.
     * 
     * @param coutnryCode Country code.
     * @return Rounding mode.
     */
    public RoundingMode getRoundingMode(String countryCode) {
        String propertyRoundMode = getDecimalRoundMode(countryCode);
        if (StringUtils.isEmpty(propertyRoundMode)) {
            return RoundingMode.HALF_UP;
        }
        return RoundingMode.valueOf(propertyRoundMode);
    }

    /**
     * Get property by key.
     * 
     * @param key Property key.
     * @return Property value.
     */
    private String get(String key) {
        return env.getProperty(key);
    }
}
