/**
 * @(#)StringUtility.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import lombok.Getter;

/**
 * Common component <br>
 * string utility class.
 *
 */
public class StringUtility {

    private StringUtility() {}

    /** default charset UTF-8. */
    private static final String DEFAULT_CHARSET = "UTF-8";
    /** default currency scale (2). */
    private static final int DEFAULT_CURRENCY_SCALE = 2;
    /** True string number. */
    private static final String TRUE_NUMBER = "1";
    /** True string char. */
    private static final String TRUE_CHAR = "T";
    /** True string char. */
    private static final String TRUE_YES_CHAR = "Y";

    /**
     * parse the specified text to the specified number class type.
     * 
     * @param text the specified text
     * @param numberClass the specified number class type
     * @return {@link Number} when the specified text can be parsed, null otherwise
     * @throws NumberFormatException if the string does not contain a parsable number.
     */
    public static Number parseNumber(String text, NumberClass numberClass) {

        if (StringUtils.isEmpty(text)) {
            return null;
        }

        Number parsed = null;
        if (NumberClass.INTEGER.equals(numberClass)) {
            parsed = Integer.valueOf(text);
        } else if (NumberClass.LONG.equals(numberClass)) {
            parsed = Long.valueOf(text);
        } else if (NumberClass.DOUBLE.equals(numberClass)) {
            parsed = Double.valueOf(text);
        } else if (NumberClass.FLOAT.equals(numberClass)) {
            parsed = Float.valueOf(text);
        }
        return parsed;
    }

    /**
     * Get the specified text's bytes length by the default charset UTF-8.
     * 
     * @param text the specified text
     * @return length of the specified text
     */
    public static int getByteLength(String text) {
        int byteLength = 0;
        try {
            byteLength = getByteLength(text, DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            // do nothing since this will never happen.
        }
        return byteLength;
    }

    /**
     * Get the specified text's length by the specified charset.
     * 
     * @param text the specified text
     * @param charsetName The name of a supported {@linkplain java.nio.charset.Charset charset}
     * @return size of the string in bytes
     * @throws UnsupportedEncodingException throw an exception when the specified encoding type is
     *         unsupported
     */
    public static int getByteLength(String text, String charsetName)
            throws UnsupportedEncodingException {

        if (StringUtils.isEmpty(charsetName)) {
            UnsupportedEncodingException unsupportedEncodingException =
                    new UnsupportedEncodingException("charsetName must be non-empty.");
            throw unsupportedEncodingException;
        }

        int length = 0;
        if (StringUtils.isEmpty(text)) {
            return length;
        } else {
            return text.getBytes(charsetName).length;
        }
    }

    /**
     * convert the specified long value to a text number with the specified locale format<BR>
     * example:<BR>
     * StringUtility.formatNumber(1234567, Locale.JAPAN) --> 1,234,567.
     * 
     * @param value the specified long value
     * @param locale the specified Locale. Uses Locale.getDefault() if null.
     * @return formatted text.
     */
    public static String formatNumber(long value, Locale locale) {
        NumberFormat numberFormat = NumberFormat
                .getNumberInstance(Optional.ofNullable(locale).orElse(Locale.getDefault()));
        return numberFormat.format(value);
    }

    /**
     * convert the specified double value to a text number with the specified locale format and the
     * specified scale and round type <BR>
     * example:<BR>
     * StringUtility.formatNumber(1234567.891234, 3, RoundMode.ROUND, Locale.JAPAN) -->
     * 1,234,567.891<BR>
     * StringUtility.formatNumber(0.2355, 3, RoundMode.ROUND, Locale.JAPAN) --> 0.236
     * 
     * @param value the specified double value
     * @param scale the specified scale
     * @param roundMode the specified RoundMode. Uses RoundMOde.ROUND if null.
     * @param locale the specified Locale. Uses Locale.getDefault() if null.
     * @return formatted text.
     */
    public static String formatNumber(double value, int scale, RoundMode roundMode, Locale locale) {
        BigDecimal bigDecimal = new BigDecimal(Double.toString(value));
        BigDecimal scaledBigDecimal = bigDecimal.setScale(scale,
                Optional.ofNullable(roundMode).orElse(RoundMode.ROUND).getMode());
        NumberFormat numberFormat = NumberFormat
                .getNumberInstance(Optional.ofNullable(locale).orElse(Locale.getDefault()));
        return numberFormat.format(scaledBigDecimal.doubleValue());
    }

    /**
     * convert the specified long value to a string with the specified locale format<BR>
     * example:<BR>
     * StringUtility.formatCurrency(1234567, Locale.JAPAN)<BR>
     * StringUtility.formatCurrency(12345, Locale.FRANCE).
     * 
     * @param value the specified long value
     * @param locale the specified Locale. Uses Locale.getDefault() if null.
     * @return formatted text.
     */
    public static String formatCurrency(long value, Locale locale) {
        NumberFormat numberFormat = NumberFormat
                .getCurrencyInstance(Optional.ofNullable(locale).orElse(Locale.getDefault()));
        return numberFormat.format(value);
    }

    /**
     * convert the specified double value to a text currency with the specified locale format and
     * the specified scale and round type <BR>
     * example:<BR>
     * StringUtility.formatCurrency(1234567.891234, RoundMode.ROUND, Locale.JAPAN)<BR>
     * StringUtility.formatCurrency(0.2355, RoundMode.ROUND, Locale.FRANCE)
     * 
     * @param value the specified double value
     * @param roundMode the specified RoundMode
     * @param locale the specified Locale
     * @return formatted text
     */
    public static String formatCurrency(double value, RoundMode roundMode, Locale locale) {
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(value));
        BigDecimal resultBigDecimal = bigDecimal.setScale(DEFAULT_CURRENCY_SCALE,
                Optional.ofNullable(roundMode).orElse(RoundMode.ROUND).getMode());
        NumberFormat numberFormat = NumberFormat
                .getCurrencyInstance(Optional.ofNullable(locale).orElse(Locale.getDefault()));
        return numberFormat.format(resultBigDecimal.doubleValue());
    }

    /**
     * convert the specified double value to a text percent with the specified locale format and the
     * specified scale and round type <BR>
     * example:<BR>
     * StringUtility.formatPercent(0.891234, 3, RoundMode.ROUND, Locale.JAPAN) -->89.1%<BR>
     * StringUtility.formatPercent(0.2355, 1, RoundMode.ROUND, Locale.FRANCE) -->20 %
     * 
     * @param value the specified double value
     * @param scale the specified scale
     * @param roundMode the specified RoundMode
     * @param locale the specified Locale
     * @return formatted text.
     */
    public static String formatPercent(double value, int scale, RoundMode roundMode,
            Locale locale) {
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(value));
        BigDecimal resultBigDecimal = bigDecimal.setScale(scale,
                Optional.ofNullable(roundMode).orElse(RoundMode.ROUND).getMode());
        NumberFormat numberFormat = NumberFormat
                .getPercentInstance(Optional.ofNullable(locale).orElse(Locale.getDefault()));
        return numberFormat.format(resultBigDecimal.doubleValue());
    }

    /**
     * Substring specific mix string.
     * 
     * @param inputValue Input string value.
     * @param lenght Substring length.
     * @return Result.
     */
    public static String substringByUtf8(String inputValue, int lenght) {

        if (inputValue == null) {
            return null;
        }

        byte[] buf;
        String convertResult = inputValue;
        try {
            buf = inputValue.getBytes(DEFAULT_CHARSET);

            if (buf.length > lenght) {
                int count = 0;
                for (int i = lenght - 1; i >= 0; i--) {
                    if (buf[i] < 0) {
                        count++;
                    } else {
                        break;
                    }
                }

                // Utf-8 string is three bytes.
                convertResult = new String(buf, 0, lenght - count % 3, DEFAULT_CHARSET);
            }
        } catch (UnsupportedEncodingException e) {
            // Auto-generated catch block
            e.printStackTrace();
        }
        return convertResult;
    }

    /**
     * Change string to boolean.
     * 
     * @param fromString From string.
     * @return Boolean.
     */
    public static boolean changeStringToBoolean(String fromString) {
        return StringUtils.equalsAny(fromString, TRUE_NUMBER, TRUE_CHAR, TRUE_YES_CHAR);
    }


    /**
     * The java number class type enum.
     *
     */
    public enum NumberClass {
        /**
         * {@link Integer} .
         */
        INTEGER(Integer.class),
        /**
         * {@link Long} .
         */
        LONG(Long.class),
        /**
         * {@link Float} .
         */
        FLOAT(Float.class),
        /**
         * {@link Double} .
         */
        DOUBLE(Double.class);

        /** class type. */
        @Getter
        private Class<? extends Number> classType;

        private NumberClass(Class<? extends Number> classType) {
            this.classType = classType;
        }
    }

    /**
     * The round mode type class enum.
     *
     */
    public enum RoundMode {
        /**
         * ceil mode.
         */
        CEIL(BigDecimal.ROUND_UP),
        /**
         * round mode.
         */
        ROUND(BigDecimal.ROUND_HALF_UP),
        /**
         * floor mode.
         */
        FLOOR(BigDecimal.ROUND_DOWN);

        /** mode. */
        @Getter
        private int mode;

        private RoundMode(int mode) {
            this.mode = mode;
        }
    }
}
