/**
 * @(#)InternationalizationComponent.java
 *
 *                                        Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.i18n;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Internationalization utility class.
 */
@Component
public class InternationalizationComponent {

    /**
     * Internationalization property.
     */
    @Autowired
    private InternationalizationProperty internationalizationProperty;

    /**
     * Format number.
     * 
     * @param countryCode Country code.
     * @param value Format value.
     * @return Formatted value.
     */
    public String formatNumber(String countryCode, BigDecimal value) {

        RoundingMode roundingMode = internationalizationProperty.getRoundingMode(countryCode);

        int scale = internationalizationProperty.getDecimalRoundPoint(countryCode);

        BigDecimal scaledBigDecimal = value.setScale(scale,
                Optional.ofNullable(roundingMode).orElse(RoundingMode.HALF_UP));

        String propertyDecimalFormat = internationalizationProperty.getDecimalFormat(countryCode);
        if (StringUtils.isEmpty(propertyDecimalFormat)) {
            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
            return numberFormat.format(scaledBigDecimal.doubleValue());
        }

        DecimalFormat decimalFormat = new DecimalFormat(propertyDecimalFormat);
        return decimalFormat.format(scaledBigDecimal);
    }

    /**
     * Format date.
     * 
     * @param countryCode Country code.
     * @param date Offset date time.
     * @return Formatted date.
     */
    public String formatDate(String countryCode, OffsetDateTime date) {
        String dateFormat = internationalizationProperty.getDateFormat(countryCode);
        if (StringUtils.isEmpty(dateFormat)) {
            return date.toString();
        }
        return date.format(DateTimeFormatter.ofPattern(dateFormat));
    }
}
