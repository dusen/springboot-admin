package com.fastretailing.dcp.storecommon.i18n;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.fastretailing.dcp.storecommon.ApplicationTest;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
public class InternationalizationComponentTest {

    /** Test component. */
    @Autowired
    private InternationalizationComponent internationalizationComponent;

    /**
     * Test {@link InternationalizationComponent#formatNumber(String, BigDecimal)}
     */
    @Test
    public void testFormatNumber() {
        assertThat(internationalizationComponent.formatNumber("JP", new BigDecimal("1000000.9")),
                is("1,000,000"));
    }

    /**
     * Test {@link InternationalizationComponent#formatNumber(String, BigDecimal)}
     */
    @Test
    public void testFormatNumberIllegalCountry() {
        assertNotNull(
                internationalizationComponent.formatNumber("xx", new BigDecimal("1000000.9")));
    }

    /**
     * Test {@link InternationalizationComponent#formatDate(String, OffsetDateTime)}
     */
    @Test
    public void testFormatDate() {
        OffsetDateTime offsetDateTime =
                OffsetDateTime.of(2018, 1, 1, 12, 23, 34, 45, ZoneOffset.UTC);
        assertThat(internationalizationComponent.formatDate("JP", offsetDateTime),
                is("2018/01/01"));
    }
}
