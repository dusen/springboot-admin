/**
 * @(#)StoreLocalDateTimeConverterTest.java
 * 
 *                                          Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.fastretailing.dcp.common.exception.SystemException;
import com.fastretailing.dcp.storecommon.report.api.repository.GeneralStoreTimeZoneMapper;
import com.fastretailing.dcp.storecommon.util.DateUtility;

/**
 * Unit test of StoreLocalDateTimeConverter class.
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class StoreLocalDateTimeConverterTest {


    /** Test class. */
    @Autowired
    private StoreLocalDateTimeConverter storeLocalDateTimeConverter;

    /** Create a mock of access DB for report create status table. */
    @MockBean
    private GeneralStoreTimeZoneMapper mockGeneralStoreTimeZoneMapper;


    /**
     * GetStoreLocalDateTime method test.
     */
    @Test
    public void testStoreLocalDateTime() {

        // DB access mock.
        when(mockGeneralStoreTimeZoneMapper.selectGeneralItem(anyString(), anyString(), anyString(),
                anyString(), anyString())).thenReturn("Europe/Rome");
        // Expect value
        String string =
                DateUtility
                        .formatDateTime(DateUtility.getZonedDateTimeUtc().toLocalDateTime(),
                                DateUtility.DateTimeFormat.UUUUMMDDHHMMSS)
                        .substring(0, 8);
        // Actual value
        String str = storeLocalDateTimeConverter.getStoreLocalDateTime("111111");
        str = str.substring(0, 8);
        assertEquals(string, str);
    }


    /**
     * <UL>
     * <LI>Target method：select.
     * <LI>Condition：ZoneId is null.
     * <LI>Verification result confirmation：The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testStoreLocalDateTimeException() throws Exception {
        final ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);

        try {
            // Method execution
            storeLocalDateTimeConverter.getStoreLocalDateTime("111111");
            fail("expected exception was not occured.");
        } catch (Exception exception) {
            assertThat(exception).isInstanceOf(SystemException.class);
        }
        verify(mockGeneralStoreTimeZoneMapper).selectGeneralItem(argument.capture(),
                argument.capture(), argument.capture(), argument.capture(), argument.capture());
        assertThat(argument.getValue(), is("111111"));
        assertThat(argument.getAllValues().get(0), is("m_store_general_purpose"));
        assertThat(argument.getAllValues().get(1), is("code"));
        assertThat(argument.getAllValues().get(2), is("general_purpose_type"));

    }
}
