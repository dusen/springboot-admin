/**
 * @(#)ConvertUtilsTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.common.util;

import lombok.extern.slf4j.Slf4j;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@Slf4j
public class CommonUtilityTest {

    @InjectMocks
    private CommonUtility utility;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void byteArrayToLong01() {
        log.debug("CommonUtilityTest.byteArrayToLong01");

        Byte[] array = new Byte[8];
        array[0] = (byte)0;
        array[1] = (byte)0;
        array[2] = (byte)0;
        array[3] = (byte)0;
        array[4] = (byte)0;
        array[5] = (byte)39;
        array[6] = (byte)10;
        array[7] = (byte)0;

        long expected = (long) Math.pow(2, 0) * (short) array[7]
                + (long) Math.pow(2, 8) * (short) array[6]
                + (long) Math.pow(2, 16) * (short) array[5]
                + (long) Math.pow(2, 24) * (short) array[4]
                + (long) Math.pow(2, 32) * (short) array[3]
                + (long) Math.pow(2, 40) * (short) array[2]
                + (long) Math.pow(2, 48) * (short) array[1]
                + (long) Math.pow(2, 56) * (short) array[0];
        long actual = CommonUtility.byteArrayToLong(array);

        // compare
        assertThat(actual, is(expected));
    }

    @Test
    public void byteArrayToLong02() {
        log.debug("CommonUtilityTest.byteArrayToLong01");

        Byte[] array = new Byte[8];
        array[0] = (byte)1;
        array[1] = (byte)1;
        array[2] = (byte)1;
        array[3] = (byte)1;
        array[4] = (byte)1;
        array[5] = (byte)39;
        array[6] = (byte)10;
        array[7] = (byte)1;

        long expected = (long) Math.pow(2, 0) * (short) array[7]
                + (long) Math.pow(2, 8) * (short) array[6]
                + (long) Math.pow(2, 16) * (short) array[5]
                + (long) Math.pow(2, 24) * (short) array[4]
                + (long) Math.pow(2, 32) * (short) array[3]
                + (long) Math.pow(2, 40) * (short) array[2]
                + (long) Math.pow(2, 48) * (short) array[1]
                + (long) Math.pow(2, 56) * (short) array[0];
        long actual = CommonUtility.byteArrayToLong(array);

        // compare
        assertThat(actual, is(expected));
    }

    /**
     * Constructor class
     */
    @Test
    public void testConstructor() {
        CommonUtility commonUtils = new CommonUtility();
        Assert.assertNotNull(commonUtils);
    }

    @Test
    public void testGetOperationAtCase1() {
        ReflectionTestUtils.setField(utility, "propertiesDateTime" , null);
        testGetSystemDate();
    }

    @Test
    public void testGetOperationAtCase2() {
        ReflectionTestUtils.setField(utility, "propertiesDateTime" , "");
        testGetSystemDate();
    }

    @Test
    public void testGetOperationAtCase3() {
        testGetOperationAtFormat("yyyy-MM-dd", "2016-11-12");
    }

    @Test
    public void testGetOperationAtCase4() {
        testGetOperationAtFormat("yyyy-MM-dd HH", "2016-11-12 13");
    }

    @Test
    public void testGetOperationAtCase5() {
        testGetOperationAtFormat("yyyy-MM-dd HH:mm", "2016-11-12 13:14");
    }

    @Test
    public void testGetOperationAtCase6() {
        testGetOperationAtFormat("yyyy-MM-dd HH:mm:ss", "2016-11-12 13:14:15");
    }

    @Test
    public void testGetOperationAtCase7() {
        testGetOperationAtFormat("yyyy-MM-dd HH:mm:ss.SSS", "2016-11-12 13:14:15.167");
    }

    @Test
    public void testGetOperationAtCase8() {

        ReflectionTestUtils.setField(utility, "platformShortName" , "testName");

        String result = utility.getDebugId("logLabel", "123");

        MatcherAssert.assertThat(result, is("logLabel_testName_123"));

    }

    private void testGetOperationAtFormat(String format, String dateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        ReflectionTestUtils.setField(utility, "propertiesDateTime" , dateTime);
        LocalDateTime systemDateTime = utility.getOperationAt();
        assertThat(dateTimeFormatter.format(systemDateTime), is(dateTime));
    }

    private void testGetSystemDate() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime systemDateTime = utility.getOperationAt();
        LocalDateTime end = LocalDateTime.now();
        assertThat(!systemDateTime.isBefore(start), is(true));
        assertThat(!systemDateTime.isAfter(end), is(true));
    }


}
