/**
 * @(#)PriceUtilsTest.java
 *
 *                         Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import java.math.BigDecimal;
import java.util.Currency;
import org.junit.Test;
import com.fastretailing.dcp.storecommon.dto.Price;

/**
 * Test class for PriceUtils.
 */
public class PriceUtilsTest {

    /**
     * Test {@link PriceUtils#getCurrencyCode(Currency)}
     */
    @Test
    public void testGetCurrencyCode() {

        assertThat(PriceUtils.getCurrencyCode(Currency.getInstance("JPY")), is("JPY"));
        Price priceNull = null;
        assertNull(PriceUtils.getCurrencyCode(priceNull));

        Price price = new Price();
        assertNull(PriceUtils.getCurrencyCode(price));
        price.setCurrencyCode(Currency.getInstance("CAD"));
        assertThat(PriceUtils.getCurrencyCode(price), is("CAD"));
    }

    /**
     * Test {@link PriceUtils#setScale(BigDecimal, int, int)}
     */
    @Test
    public void testSetScaleBigDecimal() {

        BigDecimal p1049 = new BigDecimal("10.49");
        BigDecimal p1050 = new BigDecimal("10.5");
        BigDecimal p10 = new BigDecimal("10");
        BigDecimal p11 = new BigDecimal("11");

        assertThat(PriceUtils.setScale(p1049, 0, BigDecimal.ROUND_UP), is(p11));
        assertThat(PriceUtils.setScale(p1050, 0, BigDecimal.ROUND_UP), is(p11));
        assertThat(PriceUtils.setScale(p1049, 0, BigDecimal.ROUND_DOWN), is(p10));
        assertThat(PriceUtils.setScale(p1050, 0, BigDecimal.ROUND_DOWN), is(p10));
        assertThat(PriceUtils.setScale(p1049, 0, BigDecimal.ROUND_HALF_UP), is(p10));
        assertThat(PriceUtils.setScale(p1050, 0, BigDecimal.ROUND_HALF_UP), is(p11));
    }

    /**
     * Test {@link PriceUtils#setScale(Price, int, int)}
     */
    @Test
    public void testSetScalePrice() {
        Price p10 = new Price();
        p10.setValue(new BigDecimal("10"));

        Price p11 = new Price();
        p11.setValue(new BigDecimal("11"));

        // Round up.
        {
            Price p1049 = new Price();
            p1049.setValue(new BigDecimal("10.49"));

            Price p1050 = new Price();
            p1050.setValue(new BigDecimal("10.5"));
            assertThat(PriceUtils.setScale(p1049, 0, BigDecimal.ROUND_UP), is(p11));
            assertThat(PriceUtils.setScale(p1050, 0, BigDecimal.ROUND_UP), is(p11));
        }
        // Round down.
        {
            Price p1049 = new Price();
            p1049.setValue(new BigDecimal("10.49"));

            Price p1050 = new Price();
            p1050.setValue(new BigDecimal("10.5"));
            assertThat(PriceUtils.setScale(p1049, 0, BigDecimal.ROUND_DOWN), is(p10));
            assertThat(PriceUtils.setScale(p1050, 0, BigDecimal.ROUND_DOWN), is(p10));
        }
        // Round half up.
        {
            Price p1049 = new Price();
            p1049.setValue(new BigDecimal("10.49"));

            Price p1050 = new Price();
            p1050.setValue(new BigDecimal("10.5"));
            assertThat(PriceUtils.setScale(p1049, 0, BigDecimal.ROUND_HALF_UP), is(p10));
            assertThat(PriceUtils.setScale(p1050, 0, BigDecimal.ROUND_HALF_UP), is(p11));
        }
    }
}
