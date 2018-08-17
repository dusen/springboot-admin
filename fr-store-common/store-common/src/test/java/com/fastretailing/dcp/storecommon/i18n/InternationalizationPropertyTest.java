package com.fastretailing.dcp.storecommon.i18n;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import java.math.RoundingMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.fastretailing.dcp.storecommon.ApplicationTest;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
public class InternationalizationPropertyTest {

    /** Property. */
    @Autowired
    private InternationalizationProperty property;

    /**
     * Test {@link InternationalizationProperty#getDateFormat(String)}
     */
    @Test
    public void testGetDateFormat() {
        assertThat(property.getDateFormat("JP"), equalTo("yyyy/MM/dd"));
    }

    /**
     * Test {@link InternationalizationProperty#getDecimalFormat(String)}
     */
    @Test
    public void testGetDecimalFormat() {
        assertThat(property.getDecimalFormat("JP"), equalTo("#,###"));
        assertThat(property.getDecimalFormat("CA"), equalTo("#,###.00"));
    }

    /**
     * Test {@link InternationalizationProperty#getDecimalRoundPoint(String)}
     */
    @Test
    public void testGetDecimalRoundPoint() {
        assertThat(property.getDecimalRoundPoint("JP"), equalTo(0));
        assertThat(property.getDecimalRoundPoint("CA"), equalTo(2));
    }

    /**
     * Test {@link InternationalizationProperty#getRoundingMode(String)}
     */
    @Test
    public void testGetRoundingMode() {
        assertThat(property.getRoundingMode("JP"), equalTo(RoundingMode.DOWN));
        assertThat(property.getRoundingMode("CA"), equalTo(RoundingMode.DOWN));
    }
}
