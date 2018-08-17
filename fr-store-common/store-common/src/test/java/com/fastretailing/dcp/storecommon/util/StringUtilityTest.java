/**
 * @(#)StringUtilityTest.java
 *
 *                            Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import org.junit.Test;
import com.fastretailing.dcp.storecommon.util.StringUtility;
import com.fastretailing.dcp.storecommon.util.StringUtility.NumberClass;
import com.fastretailing.dcp.storecommon.util.StringUtility.RoundMode;

public class StringUtilityTest {

    @Test
    public void testParseNumber_emptystring() {
        assertNull(StringUtility.parseNumber(null, NumberClass.INTEGER));
        assertNull(StringUtility.parseNumber("", NumberClass.INTEGER));
    }

    /**
     * Test parseNumber method.
     * 
     * @see StringUtility#parseNumber(String, NumberClass)
     */
    @Test
    public void testParseNumber() {

        assertEquals(StringUtility.parseNumber("1", NumberClass.INTEGER), 1);
        assertEquals(StringUtility.parseNumber("１", NumberClass.INTEGER), 1);

        String[] notANubmers = new String[] {"A", "あ", "ｱ1", "ａ1"};
        for (String nan : notANubmers) {
            try {
                StringUtility.parseNumber(nan, NumberClass.INTEGER);
                fail(nan);
            } catch (Exception e) {
                // do nothing.
            }
        }

        assertEquals(StringUtility.parseNumber("1", NumberClass.LONG), 1L);
        assertEquals(StringUtility.parseNumber("１", NumberClass.LONG), 1L);
        for (String nan : notANubmers) {
            try {
                StringUtility.parseNumber(nan, NumberClass.LONG);
                fail(nan);
            } catch (Exception e) {
                // do nothing.
            }
        }

        assertEquals(StringUtility.parseNumber("1", NumberClass.FLOAT), 1.0F);
        assertEquals(StringUtility.parseNumber("1.0", NumberClass.FLOAT), 1.0F);

        notANubmers = new String[] {"１", "１．０", "A", "あ", "あ1.0", "ｱ1.0", "ａ1.0"};
        for (String nan : notANubmers) {
            try {
                StringUtility.parseNumber(nan, NumberClass.FLOAT);
                fail(nan);
            } catch (Exception e) {
                // do nothing.
            }
        }

        assertEquals(StringUtility.parseNumber("1", NumberClass.DOUBLE), 1.0D);
        assertEquals(StringUtility.parseNumber("1.0", NumberClass.DOUBLE), 1.0D);
        for (String nan : notANubmers) {
            try {
                StringUtility.parseNumber(nan, NumberClass.DOUBLE);
                fail(nan);
            } catch (Exception e) {
                // do nothing.
            }
        }
    }

    @Test
    public void testGetByteLengthWithCharset_UnsupportedCharsetName() {
        try {
            StringUtility.getByteLength("abcde", null);
            fail();
        } catch (UnsupportedEncodingException e) {
        }

        try {
            StringUtility.getByteLength("abcde", "abcd");
            fail();
        } catch (UnsupportedEncodingException e) {
        }
    }

    @Test
    public void testGetByteLengthWithCharset_EmptText() {
        try {
            assertThat(StringUtility.getByteLength(null, "UTF-8"), equalTo(0));
        } catch (UnsupportedEncodingException e) {
            fail();
            e.printStackTrace();
        }
    }

    /**
     * test getByteLength method.
     * 
     * @see StringUtility#getByteLength(String, String)
     */
    @Test
    public void testGetByteLengthWithCharset() {
        try {
            assertEquals(StringUtility.getByteLength("abcde", "UTF-8"), 5);
            assertEquals(StringUtility.getByteLength("あいうえお", "UTF-8"), 15);
            assertEquals(StringUtility.getByteLength("あいうえお", "Shift-JIS"), 10);
            assertEquals(StringUtility.getByteLength("abcdeあいうえお", "UTF-8"), 20);
            assertEquals(StringUtility.getByteLength("abcdeあいうえお", "Shift-JIS"), 15);
        } catch (UnsupportedEncodingException e) {
            fail();
            e.printStackTrace();
        }
    }

    /**
     * test getByteLength method.
     * 
     * @see StringUtility#getByteLength(String)
     */
    @Test
    public void testGetByteLength() {
        assertEquals(StringUtility.getByteLength("abcde"), 5);
        assertEquals(StringUtility.getByteLength("あいうえお"), 15);
        assertEquals(StringUtility.getByteLength("abcdeあいうえお"), 20);
    }

    /**
     * test getByteLength method when throw exception.
     * 
     * @see StringUtility#getByteLength(String, String)
     * @throws UnsupportedEncodingException when the specified Character Encoding is not supported.
     */
    @Test(expected = UnsupportedEncodingException.class)
    public void testGetByteLengthThrowException() throws UnsupportedEncodingException {
        StringUtility.getByteLength("abcdeあいうえお", "adfg");
    }

    /**
     * test formatNumber method.
     * 
     * @see StringUtility#formatNumber(long, Locale)
     */
    @Test
    public void testFormatNumberLong() {
        assertEquals(StringUtility.formatNumber(12345L, Locale.FRANCE), "12 345");
        assertEquals(StringUtility.formatNumber(12L, Locale.GERMAN), "12");
        assertEquals(StringUtility.formatNumber(-12345L, Locale.FRANCE), "-12 345");
        assertEquals(StringUtility.formatNumber(-12L, Locale.FRANCE), "-12");

    }

    /**
     * test formatNumber method.
     * 
     * @see StringUtility#formatNumber(double, int, RoundMode, Locale)
     */
    @Test
    public void testFormatNumberDouble() {

        assertEquals(StringUtility.formatNumber(12345.567, 2, RoundMode.ROUND, Locale.FRANCE)
                .equals("12 345,57"), true);
        assertEquals(StringUtility.formatNumber(345.55, 1, RoundMode.ROUND, Locale.JAPAN)
                .equals("345.6"), true);
        assertEquals(StringUtility.formatNumber(1.2345, 3, RoundMode.ROUND, Locale.CHINESE)
                .equals("1.235"), true);
        assertEquals(StringUtility.formatNumber(0.2355, 3, RoundMode.ROUND, Locale.CHINESE)
                .equals("0.236"), true);
        assertEquals(StringUtility.formatNumber(12345.563, 2, RoundMode.CEIL, Locale.CHINESE)
                .equals("12,345.57"), true);
        assertEquals(StringUtility.formatNumber(345.53, 1, RoundMode.CEIL, Locale.CHINESE)
                .equals("345.6"), true);
        assertEquals(StringUtility.formatNumber(1.2344, 3, RoundMode.CEIL, Locale.CHINESE)
                .equals("1.235"), true);
        assertEquals(StringUtility.formatNumber(0.2353, 3, RoundMode.CEIL, Locale.CHINESE)
                .equals("0.236"), true);
        assertEquals(StringUtility.formatNumber(12345.567, 2, RoundMode.FLOOR, Locale.CHINESE)
                .equals("12,345.56"), true);
        assertEquals(StringUtility.formatNumber(345.55, 1, RoundMode.FLOOR, Locale.CHINESE)
                .equals("345.5"), true);
        assertEquals(StringUtility.formatNumber(1.2345, 3, RoundMode.FLOOR, Locale.CHINESE)
                .equals("1.234"), true);
        assertEquals(StringUtility.formatNumber(0.2355, 3, RoundMode.FLOOR, Locale.CHINESE)
                .equals("0.235"), true);
        assertEquals(StringUtility.formatNumber(1234567.891234, 3, RoundMode.ROUND, Locale.CHINESE)
                .equals("1,234,567.891"), true);
        assertEquals(StringUtility.formatNumber(-22345.5678, 3, RoundMode.ROUND, Locale.CHINESE)
                .equals("-22,345.568"), true);
        assertEquals(StringUtility.formatNumber(-345.55, 1, RoundMode.ROUND, Locale.CHINESE)
                .equals("-345.6"), true);
        assertEquals(StringUtility.formatNumber(-1.2345, 3, RoundMode.ROUND, Locale.CHINESE)
                .equals("-1.235"), true);
        assertEquals(StringUtility.formatNumber(-0.2355, 3, RoundMode.ROUND, Locale.CHINESE)
                .equals("-0.236"), true);
        assertEquals(StringUtility.formatNumber(-1234567.891234, 3, RoundMode.ROUND, Locale.CHINESE)
                .equals("-1,234,567.891"), true);
    }

    /**
     * test formatCurrency method.
     * 
     * @see StringUtility#formatCurrency(long, Locale)
     */
    @Test
    public void testFormatCurrencyLong() {
        assertEquals(StringUtility.formatCurrency(12345L, Locale.FRANCE).equals("12 345,00 €"),
                true);
        assertEquals(StringUtility.formatCurrency(12L, Locale.CANADA).equals("$12.00"), true);
    }

    /**
     * test formatCurrency method.
     * 
     * @see StringUtility#formatCurrency(double, RoundMode, Locale)
     */
    @Test
    public void testFormatCurrencyDouble() {
        assertEquals(StringUtility.formatCurrency(12345.567, RoundMode.ROUND, Locale.CANADA)
                .equals("$12,345.57"), true);
        assertEquals(
                StringUtility.formatCurrency(345.55, RoundMode.ROUND, Locale.JAPAN).equals("￥346"),
                true);
        assertEquals(StringUtility.formatCurrency(1.2345, RoundMode.ROUND, Locale.CANADA)
                .equals("$1.23"), true);
        assertEquals(StringUtility.formatCurrency(0.2355, RoundMode.ROUND, Locale.FRANCE)
                .equals("0,24 €"), true);
        assertEquals(StringUtility.formatCurrency(12345.563, RoundMode.CEIL, Locale.CANADA)
                .equals("$12,345.57"), true);
        assertEquals(StringUtility.formatCurrency(345.53, RoundMode.CEIL, Locale.CANADA)
                .equals("$345.53"), true);
    }

    /**
     * test formatPercent method.
     * 
     * @see StringUtility#formatPercent(double, int, RoundMode, Locale)
     */
    @Test
    public void testFormatPercent() {
        assertEquals(StringUtility.formatPercent(12345.567, 2, RoundMode.ROUND, Locale.FRANCE)
                .equals("1 234 557 %"), true);
        assertEquals(StringUtility.formatPercent(345.55, 0, RoundMode.ROUND, Locale.JAPAN)
                .equals("34,600%"), true);
        assertEquals(StringUtility.formatPercent(1.2345, 3, RoundMode.ROUND, Locale.CHINESE)
                .equals("124%"), true);
        assertEquals(StringUtility.formatPercent(0.2355, 1, RoundMode.ROUND, Locale.FRANCE)
                .equals("20 %"), true);
        assertEquals(StringUtility.formatPercent(12345.563, 2, RoundMode.CEIL, Locale.CHINESE)
                .equals("1,234,557%"), true);
        assertEquals(
                StringUtility.formatPercent(1.2344, 1, RoundMode.CEIL, Locale.JAPAN).equals("130%"),
                true);
        assertEquals(StringUtility.formatPercent(0.2355, 1, RoundMode.FLOOR, Locale.CHINESE)
                .equals("20%"), true);
    }

    @Test
    public void testChangeStringToBoolean() {

        assertTrue(StringUtility.changeStringToBoolean("1"));
        assertTrue(StringUtility.changeStringToBoolean("T"));
        assertTrue(StringUtility.changeStringToBoolean("Y"));

        assertFalse(StringUtility.changeStringToBoolean("0"));
        assertFalse(StringUtility.changeStringToBoolean("F"));
        assertFalse(StringUtility.changeStringToBoolean("N"));

    }

}
