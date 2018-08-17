/**
 * @(#)DateUtilityTest.java
 *
 *                          Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.zone.ZoneRulesException;
import java.util.TimeZone;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import com.fastretailing.dcp.storecommon.util.DateUtility.DateFormat;
import com.fastretailing.dcp.storecommon.util.DateUtility.DateTimeFormat;
import com.fastretailing.dcp.storecommon.util.DateUtility.ZonedDateTimeFormat;

public class DateUtilityTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    /**
     * test parseDate method.
     * 
     * @see DateUtility#parseDate(String, String)
     */
    @Test
    public void testParseDate() {
        // compare LocalDate object
        LocalDate orginLocalDate = LocalDate.of(2017, 11, 22);

        // pattern uuuuMMdd(20171122) --> LocalDate
        LocalDate targetLocalDate = DateUtility.parseDate("20171122", DateFormat.UUUUMMDD);
        assertEquals(orginLocalDate.compareTo(targetLocalDate), 0);

        // pattern uuuu-MM-dd(2017-11-22) --> LocalDate
        targetLocalDate = DateUtility.parseDate("2017-11-22", DateFormat.UUUUHMMHDD);
        assertEquals(orginLocalDate.compareTo(targetLocalDate), 0);

        // pattern uuuu/MM/dd(2017/11/22) --> LocalDate
        targetLocalDate = DateUtility.parseDate("2017/11/22", DateFormat.UUUUSMMSDD);
        assertEquals(orginLocalDate.compareTo(targetLocalDate), 0);

        // pattern uuuu.MM.dd(2017.11.22) --> LocalDate
        targetLocalDate = DateUtility.parseDate("2017.11.22", DateFormat.UUUUDMMDDD);

        assertEquals(orginLocalDate.compareTo(targetLocalDate), 0);
        targetLocalDate = DateUtility.parseDate("2017-11-22", null);
        assertEquals(orginLocalDate.compareTo(targetLocalDate), 0);
    }

    @Test
    public void testParseDate_IllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        DateUtility.parseDate(null, DateFormat.UUUUMMDD);
    }

    @Test
    public void testParseOffsetDateTime() {
        assertThat(
                DateUtility.parseOffsetDateTime("20180102", DateFormat.UUUUMMDD)
                        .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")),
                equalTo("20180102000000000"));
    }

    @Test
    public void testParseOffsetDateTimeTime() {
        assertThat(
                DateUtility.parseOffsetDateTime("20180102123456", DateTimeFormat.UUUUMMDDHHMMSS)
                        .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")),
                equalTo("20180102123456000"));
        assertThat(
                DateUtility
                        .parseOffsetDateTime("20180102123456",
                                DateTimeFormat.valueOf("UUUUMMDDHHMMSS"))
                        .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")),
                equalTo("20180102123456000"));
    }

    @Test
    public void testParseOffsetDateTimeWithTimeZone() {
        OffsetDateTime d1 = DateUtility.parseOffsetDateTimeWithTimeZone("20180102123456",
                DateTimeFormat.UUUUMMDDHHMMSS.getFormat(), "Asia/Tokyo");

        assertThat(d1.toString(), equalTo("2018-01-02T12:34:56+09:00"));

        OffsetDateTime d2 = DateUtility.parseOffsetDateTimeWithTimeZone("20180102123456",
                DateTimeFormat.UUUUMMDDHHMMSS.getFormat(), "America/New_York");

        assertThat(d2.toString(), equalTo("2018-01-02T12:34:56-05:00"));
    }

    /**
     * test parseDate method when use wrong date string.
     * 
     * @see DateUtility#parseDate(String, String)
     */
    @Test
    public void testParseDateWrongDateString() {

        try {
            // month not exist(20172211) --> null
            DateUtility.parseDate("20172211", DateFormat.UUUUMMDD);
            fail();
        } catch (DateTimeParseException e) {
            // success
        }

        try {
            // day not exist(20170332) --> null
            DateUtility.parseDate("20170332", DateFormat.UUUUMMDD);
            fail();
        } catch (DateTimeParseException e) {
            // success
        }

        try {
            // wrong leap year, day not exist(20170229) --> null
            DateUtility.parseDate("20170229", DateFormat.UUUUMMDD);
            fail();
        } catch (DateTimeParseException e) {
            // success
        }

        try {
            // wrong date(2017A029) --> null
            DateUtility.parseDate("2017A029", DateFormat.UUUUMMDD);
            fail();
        } catch (DateTimeParseException e) {
            // success
        }
    }

    /**
     * test parseDateTime method.
     * 
     * @see DateUtility#parseDateTime(String, String)
     */
    @Test
    public void testParseDateTime() {
        // compare LocalDateTime object
        LocalDateTime orginLocalDateTime = LocalDateTime.of(2017, 11, 22, 12, 20, 30);

        // pattern uuuu-MM-dd HH:mm:ss(2017-11-22 12:20:30) --> LocalDateTime
        LocalDateTime targetLocalDateTime =
                DateUtility.parseDateTime("2017-11-22 12:20:30", DateTimeFormat.UUUUHMMHDDHHQMIQSS);
        assertEquals(orginLocalDateTime.compareTo(targetLocalDateTime), 0);

        // pattern uuuu/MM/dd HH:mm:ss(2017/11/22 12:20:30) --> LocalDateTime
        targetLocalDateTime =
                DateUtility.parseDateTime("2017/11/22 12:20:30", DateTimeFormat.UUUUSMMSDDHHQMIQSS);
        assertEquals(orginLocalDateTime.compareTo(targetLocalDateTime), 0);

        // pattern uuuu-MM-ddTHH:mm:ss(2017-11-22T12:20:30) --> LocalDateTime
        targetLocalDateTime = DateUtility.parseDateTime("2017-11-22T12:20:30",
                DateTimeFormat.UUUUHMMHDDTHHQMIQSS);
        assertEquals(orginLocalDateTime.compareTo(targetLocalDateTime), 0);

        // pattern uuuu/MM/ddTHH:mm:ss(2017/11/22T12:20:30) --> LocalDateTime
        targetLocalDateTime = DateUtility.parseDateTime("2017/11/22T12:20:30",
                DateTimeFormat.UUUUSMMSDDTHHQMIQSS);
        assertEquals(orginLocalDateTime.compareTo(targetLocalDateTime), 0);


        orginLocalDateTime = LocalDateTime.of(2017, 11, 22, 12, 20, 30, 567000000);

        // pattern uuuu-MM-dd HH:mm:ss.SSS(2017-11-22 12:20:30.567) --> LocalDateTime
        targetLocalDateTime = DateUtility.parseDateTime("2017-11-22 12:20:30.567",
                DateTimeFormat.UUUUHMMHDDHHQMIQSS_SSS);
        assertEquals(orginLocalDateTime.compareTo(targetLocalDateTime), 0);

        // pattern uuuu/MM/dd HH:mm:ss.SSS(2017/11/22 12:20:30.567) --> LocalDateTime
        targetLocalDateTime = DateUtility.parseDateTime("2017/11/22 12:20:30.567",
                DateTimeFormat.UUUUSMMSDDHHQMIQSS_SSS);
        assertEquals(orginLocalDateTime.compareTo(targetLocalDateTime), 0);

        // pattern uuuu-MM-ddTHH:mm:ss(2017-11-22T12:20:30.567) --> LocalDateTime
        targetLocalDateTime = DateUtility.parseDateTime("2017-11-22T12:20:30.567",
                DateTimeFormat.UUUUHMMHDDTHHQMIQSS_SSS);
        assertEquals(orginLocalDateTime.compareTo(targetLocalDateTime), 0);

        // pattern uuuu/MM/ddTHH:mm:ss.SSS(2017/11/22T12:20:30.567) --> LocalDateTime
        targetLocalDateTime = DateUtility.parseDateTime("2017/11/22T12:20:30.567",
                DateTimeFormat.UUUUSMMSDDTHHQMIQSS_SSS);
        assertEquals(orginLocalDateTime.compareTo(targetLocalDateTime), 0);
        // //pattern empty (2017-11-22 12:20:30.567) --> LocalDate
        // targetLocalDateTime =
        // DateUtility.parseDateTime("2017-11-22 12:20:30.567", "");
        assertEquals(orginLocalDateTime.compareTo(targetLocalDateTime), 0);
        DateTimeFormat dateTimeFormat = null;
        targetLocalDateTime = DateUtility.parseDateTime("2017-11-22 12:20:30.567", dateTimeFormat);
        assertEquals(orginLocalDateTime.compareTo(targetLocalDateTime), 0);

        targetLocalDateTime =
                DateUtility.parseDateTime("2017/11/22T12:20:30.567", "uuuu/MM/dd'T'HH:mm:ss.SSS");
        assertEquals(orginLocalDateTime.compareTo(targetLocalDateTime), 0);
    }

    @Test
    public void testParseDateTime_IllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        DateUtility.parseDateTime(null, DateTimeFormat.UUUUSMMSDDHHQMIQSS_SSS);
    }

    @Test
    public void testParseDateTime_DateTimeParseException() {
        thrown.expect(DateTimeParseException.class);
        DateUtility.parseDateTime("abd", DateTimeFormat.UUUUSMMSDDHHQMIQSS_SSS);
    }

    @Test
    public void testParseZonedDateTime() {

        ZonedDateTime targetLocalDateTime =
                DateUtility.parseZonedDateTime("2017-11-22T12:20:30-08:00", null);
        assertEquals(0, targetLocalDateTime
                .compareTo(ZonedDateTime.of(2017, 11, 22, 12, 20, 30, 0, ZoneId.of("-08:00"))));

        targetLocalDateTime = DateUtility.parseZonedDateTime("2017-11-22T12:20:30Z", null);
        assertEquals(0, targetLocalDateTime
                .compareTo(ZonedDateTime.of(2017, 11, 22, 12, 20, 30, 0, ZoneId.of("Z"))));

        targetLocalDateTime = DateUtility.parseZonedDateTime("2017-11-22T12:20Z",
                DateUtility.ZonedDateTimeFormat.UUUUHMMHDTHHQMITZD);
        assertEquals(0, targetLocalDateTime
                .compareTo(ZonedDateTime.of(2017, 11, 22, 12, 20, 0, 0, ZoneId.of("Z"))));
    }

    @Test
    public void testParseZonedDateTime_IllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        DateUtility.parseZonedDateTime(null, null);
    }

    @Test
    public void testParseZonedDateTime_DateTimeParseException() {
        thrown.expect(DateTimeParseException.class);
        DateUtility.parseZonedDateTime("abcd", null);
    }

    /**
     * test parseDateTime method when use wrong date string.
     * 
     * @see DateUtility#parseDateTime(String, String)
     */
    @Test
    public void testParseDateTimeWrongDateString() {

        try {
            // wrong minutes (2017/11/22T12:70:30.567) --> null
            DateUtility.parseDateTime("2017/11/22T12:70:30.567",
                    DateTimeFormat.UUUUSMMSDDHHQMIQSS_SSS);
            fail();
        } catch (DateTimeParseException e) {
            // success
        }

        try {
            // day not exist (2017/01/32T12:20:30.567) --> null
            DateUtility.parseDateTime("2017/01/32T12:20:30.567",
                    DateTimeFormat.UUUUSMMSDDHHQMIQSS_SSS);
            fail();
        } catch (DateTimeParseException e) {
            // success
        }

        try {
            // wrong leap year, day not exist (2017/02/29T12:20:30.567) --> null
            DateUtility.parseDateTime("2017/02/29T12:20:30.567",
                    DateTimeFormat.UUUUSMMSDDHHQMIQSS_SSS);
            fail();
        } catch (DateTimeParseException e) {
            // success
        }

        try {
            // month not exist (2017/22/20T12:20:30.567) --> null
            DateUtility.parseDateTime("2017/22/20T12:20:30.567",
                    DateTimeFormat.UUUUSMMSDDHHQMIQSS_SSS);
            fail();
        } catch (DateTimeParseException e) {
            // success
        }

        try {
            // wrong date (2017/22/A3T12:20:30.567) --> null
            DateUtility.parseDateTime("2017/22/A3T12:20:30.567",
                    DateTimeFormat.UUUUSMMSDDHHQMIQSS_SSS);
            fail();
        } catch (DateTimeParseException e) {
            // success
        }
    }

    @Test
    public void testFormatDate_IllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        DateUtility.formatDate(null, DateFormat.UUUUMMDD);
    }

    /**
     * test formatDate method.
     * 
     * @see DateUtility#formatDate(LocalDate, String)
     */
    @Test
    public void testFormatDate() {
        // compare LocalDate object
        LocalDate orginLocalDate = LocalDate.of(2017, 11, 22);

        // LocalDate --> 20171122
        String orginDateString = "20171122";
        String targetDateString = DateUtility.formatDate(orginLocalDate, DateFormat.UUUUMMDD);
        assertEquals(orginDateString.equals(targetDateString), true);

        // LocalDate --> 2017-11-22
        orginDateString = "2017-11-22";
        targetDateString = DateUtility.formatDate(orginLocalDate, DateFormat.UUUUHMMHDD);
        assertEquals(orginDateString.equals(targetDateString), true);

        // LocalDate --> 2017/11/22
        orginDateString = "2017/11/22";
        targetDateString = DateUtility.formatDate(orginLocalDate, DateFormat.UUUUSMMSDD);
        assertEquals(orginDateString.equals(targetDateString), true);

        // LocalDate --> 2017.11.22
        orginDateString = "2017.11.22";
        targetDateString = DateUtility.formatDate(orginLocalDate, DateFormat.UUUUDMMDDD);
        assertEquals(orginDateString.equals(targetDateString), true);

        // LocalDate --> 201711
        orginDateString = "201711";
        targetDateString = DateUtility.formatDate(orginLocalDate, DateFormat.UUUUMM);
        assertEquals(orginDateString.equals(targetDateString), true);

    }

    @Test
    public void testFormatDateTime_IllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        DateUtility.formatDateTime(null, DateTimeFormat.UUUUHMMHDDHHQMIQSS);
    }

    /**
     * test formatDateTime method.
     * 
     * @see DateUtility#formatDateTime(LocalDateTime, String)
     */
    @Test
    public void testFormatDateTime() {

        LocalDateTime orginLocalDateTime = LocalDateTime.of(2017, 11, 22, 12, 20, 30);

        // LocalDateTime --> 2017-11-22 12:20:30
        String orginDateTimeString = "20171122122030";
        String targetDateTimeString =
                DateUtility.formatDateTime(orginLocalDateTime, DateTimeFormat.UUUUMMDDHHMMSS);
        assertEquals(orginDateTimeString.equals(targetDateTimeString), true);

        // LocalDateTime --> 2017-11-22 12:20:30
        orginDateTimeString = "2017-11-22 12:20:30";
        targetDateTimeString =
                DateUtility.formatDateTime(orginLocalDateTime, DateTimeFormat.UUUUHMMHDDHHQMIQSS);
        assertEquals(orginDateTimeString.equals(targetDateTimeString), true);

        // LocalDateTime --> 2017/11/22 12:20:30
        orginDateTimeString = "2017/11/22 12:20:30";
        targetDateTimeString =
                DateUtility.formatDateTime(orginLocalDateTime, DateTimeFormat.UUUUSMMSDDHHQMIQSS);
        assertEquals(orginDateTimeString.equals(targetDateTimeString), true);

        // LocalDateTime --> 2017-11-22T12:20:30
        orginDateTimeString = "2017-11-22T12:20:30";
        targetDateTimeString =
                DateUtility.formatDateTime(orginLocalDateTime, DateTimeFormat.UUUUHMMHDDTHHQMIQSS);
        assertEquals(orginDateTimeString.equals(targetDateTimeString), true);

        // LocalDateTime --> 2017/11/22T12:20:30
        orginDateTimeString = "2017/11/22T12:20:30";
        targetDateTimeString =
                DateUtility.formatDateTime(orginLocalDateTime, DateTimeFormat.UUUUSMMSDDTHHQMIQSS);
        assertEquals(orginDateTimeString.equals(targetDateTimeString), true);

        orginLocalDateTime = LocalDateTime.of(2017, 11, 22, 12, 20, 30, 567000000);

        // LocalDateTime --> 2017-11-22 12:20:30.567
        orginDateTimeString = "2017-11-22 12:20:30.567";
        targetDateTimeString = DateUtility.formatDateTime(orginLocalDateTime,
                DateTimeFormat.UUUUHMMHDDHHQMIQSS_SSS);
        assertEquals(orginDateTimeString.equals(targetDateTimeString), true);

        // LocalDateTime --> 2017/11/22 12:20:30.567
        orginDateTimeString = "2017/11/22 12:20:30.567";
        targetDateTimeString = DateUtility.formatDateTime(orginLocalDateTime,
                DateTimeFormat.UUUUSMMSDDHHQMIQSS_SSS);
        assertEquals(orginDateTimeString.equals(targetDateTimeString), true);

        // LocalDateTime --> 2017-11-22T12:20:30.567
        orginDateTimeString = "2017-11-22T12:20:30.567";
        targetDateTimeString = DateUtility.formatDateTime(orginLocalDateTime,
                DateTimeFormat.UUUUHMMHDDTHHQMIQSS_SSS);
        assertEquals(orginDateTimeString.equals(targetDateTimeString), true);

        // LocalDateTime --> 2017/11/22T12:20:30.567
        orginDateTimeString = "2017/11/22T12:20:30.567";
        targetDateTimeString = DateUtility.formatDateTime(orginLocalDateTime,
                DateTimeFormat.UUUUSMMSDDTHHQMIQSS_SSS);
        assertEquals(orginDateTimeString.equals(targetDateTimeString), true);

        // LocalDateTime --> 2017/11/22T12:20:30.567
        orginDateTimeString = "2017-11-22T12:20";
        targetDateTimeString =
                DateUtility.formatDateTime(orginLocalDateTime, DateTimeFormat.UUUUHMMHDTHHQMI);
        assertEquals(orginDateTimeString.equals(targetDateTimeString), true);

        // LocalDateTime --> 2017-11-22 12:20:30
        orginDateTimeString = "122030";
        targetDateTimeString =
                DateUtility.formatDateTime(orginLocalDateTime, DateTimeFormat.HHMMSS);
        assertEquals(orginDateTimeString.equals(targetDateTimeString), true);
    }

    @Test
    public void testFormatZonedDateTime_IllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        DateUtility.formatZonedDateTime(null, ZonedDateTimeFormat.UUUUHMMHDTHHQMISSTZD);
    }

    /**
     * test formatDateTime method.
     * 
     * @see DateUtility#formatDateTime(LocalDateTime, String)
     */
    @Test
    public void testFormatZonedDateTime() {

        ZonedDateTime orginLocalDateTime =
                ZonedDateTime.of(2017, 11, 22, 12, 20, 30, 0, ZoneId.of("Z"));

        String orginDateTimeString = "2017-11-22T12:20:30Z";
        String targetDateTimeString = DateUtility.formatZonedDateTime(orginLocalDateTime,
                ZonedDateTimeFormat.UUUUHMMHDTHHQMISSTZD);
        assertEquals(orginDateTimeString.equals(targetDateTimeString), true);

        orginLocalDateTime = ZonedDateTime.of(2017, 11, 22, 12, 20, 30, 0, ZoneId.of("+08:00"));

        orginDateTimeString = "2017-11-22T12:20:30+08:00";
        targetDateTimeString = DateUtility.formatZonedDateTime(orginLocalDateTime,
                ZonedDateTimeFormat.UUUUHMMHDTHHQMISSTZD);
        assertEquals(orginDateTimeString.equals(targetDateTimeString), true);

        orginLocalDateTime = ZonedDateTime.of(2017, 11, 22, 12, 20, 0, 0, ZoneId.of("Z"));

        orginDateTimeString = "2017-11-22T12:20Z";
        targetDateTimeString = DateUtility.formatZonedDateTime(orginLocalDateTime,
                ZonedDateTimeFormat.UUUUHMMHDTHHQMITZD);
        assertEquals(orginDateTimeString.equals(targetDateTimeString), true);

        orginLocalDateTime = ZonedDateTime.of(2017, 11, 22, 12, 20, 0, 0, ZoneId.of("-08:00"));

        orginDateTimeString = "2017-11-22T12:20-08:00";
        targetDateTimeString = DateUtility.formatZonedDateTime(orginLocalDateTime,
                ZonedDateTimeFormat.UUUUHMMHDTHHQMITZD);
        assertEquals(orginDateTimeString.equals(targetDateTimeString), true);
    }

    @Test
    public void testChangeTimeZone_IllegalArgumentException() {
        TimeZone toTimeZone = TimeZone.getTimeZone("Asia/Tokyo");
        LocalDateTime.of(2017, 11, 22, 14, 20, 30);

        TimeZone fromTimeZone = TimeZone.getTimeZone("Asia/Shanghai");
        LocalDateTime localDateTime = LocalDateTime.of(2017, 11, 22, 13, 20, 30);

        try {
            localDateTime = null;
            DateUtility.changeTimeZone(localDateTime, fromTimeZone, toTimeZone);
            fail();
        } catch (IllegalArgumentException e) {
            // success
        }

        try {
            localDateTime = LocalDateTime.of(2017, 11, 22, 13, 20, 30);
            fromTimeZone = null;
            DateUtility.changeTimeZone(localDateTime, fromTimeZone, toTimeZone);
            fail();
        } catch (IllegalArgumentException e) {
            // success
        }

        try {
            localDateTime = LocalDateTime.of(2017, 11, 22, 13, 20, 30);
            fromTimeZone = TimeZone.getTimeZone("Asia/Shanghai");
            toTimeZone = null;
            DateUtility.changeTimeZone(localDateTime, fromTimeZone, toTimeZone);
            fail();
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * test convertTimeZone method.
     * 
     * @see DateUtility#changeTimeZone(LocalDateTime, TimeZone, TimeZone)
     */
    @Test
    public void testChangeTimeZone() {
        TimeZone tokyoZone = TimeZone.getTimeZone("Asia/Tokyo");
        LocalDateTime tokyoLocalDateTime = LocalDateTime.of(2017, 11, 22, 14, 20, 30);

        TimeZone shanghaiZone = TimeZone.getTimeZone("Asia/Shanghai");
        LocalDateTime shanghaiLocalDateTime = LocalDateTime.of(2017, 11, 22, 13, 20, 30);

        LocalDateTime targetLocalDateTime =
                DateUtility.changeTimeZone(tokyoLocalDateTime, tokyoZone, shanghaiZone);

        // 1 hour Difference between Tokyo time and Shanghai time
        assertEquals(shanghaiLocalDateTime.compareTo(targetLocalDateTime), 0);
    }

    /**
     * test getDateDifference method.
     * 
     * @see DateUtility#getDateDifference(String, String, String)
     */
    @Test
    public void testGetDateDifference() {
        // get 3 between 2017-11-22 and 2017-11-25
        String stringFromDate = "2017-11-22";
        String stringToDate = "2017-11-25";
        Long dateDifference =
                DateUtility.getDateDifference(stringFromDate, stringToDate, DateFormat.UUUUHMMHDD);
        assertEquals(dateDifference.longValue(), 3);

        // get -2 between 2017-11-22 and 2017-11-25
        stringFromDate = "2017-11-22";
        stringToDate = "2017-11-20";
        dateDifference =
                DateUtility.getDateDifference(stringFromDate, stringToDate, DateFormat.UUUUHMMHDD);
        assertEquals(dateDifference.longValue(), -2);

        // get 0 between 2017-11-22 and 2017-11-22
        stringFromDate = "2017-11-22";
        stringToDate = "2017-11-22";
        dateDifference =
                DateUtility.getDateDifference(stringFromDate, stringToDate, DateFormat.UUUUHMMHDD);
        assertEquals(dateDifference.longValue(), 0);
    }

    /**
     * test getDateDifference method when use wrong date string.
     * 
     * @see DateUtility#getDateDifference(String, String, String)
     */
    @Test
    public void testGetDateDifferenceWrongDate() {

        try {
            // get null
            String stringFromDate = "2017-11-A0";
            String stringToDate = "2017-11-25";
            DateUtility.getDateDifference(stringFromDate, stringToDate, DateFormat.UUUUHMMHDD);
            fail();
        } catch (DateTimeParseException e) {
            // success
        }

        try {
            // get null
            String stringFromDate = "2017-11-22";
            String stringToDate = "2017-A0-25";
            DateUtility.getDateDifference(stringFromDate, stringToDate, DateFormat.UUUUHMMHDD);
        } catch (Exception e) {
            // success
        }

    }


    /**
     * test getDateTimeDifference method.
     * 
     * @see DateUtility#getDateTimeDifference(String, String, String)
     */
    @Test
    public void testGetDateTimeDifference() {
        // get 10000 between 2017-11-22 12:00:00 and 2017-11-22 12:00:10
        String stringFromDate = "2017-11-22 12:00:00";
        String stringToDate = "2017-11-22 12:00:10";
        Long dateDifference = DateUtility.getDateTimeDifference(stringFromDate, stringToDate,
                DateTimeFormat.UUUUHMMHDDHHQMIQSS);
        assertEquals(dateDifference.longValue(), 10000);

        // get -10000 between 2017-11-22T12:00:10 and 2017-11-22T12:00:00
        stringFromDate = "2017-11-22T12:00:10";
        stringToDate = "2017-11-22T12:00:00";
        dateDifference = DateUtility.getDateTimeDifference(stringFromDate, stringToDate,
                DateTimeFormat.UUUUHMMHDDTHHQMIQSS);
        assertEquals(dateDifference.longValue(), -10000);

        // get 0 between 2017-11-22 12:00:10.567 and 2017-11-22 12:00:10.567
        stringFromDate = "2017-11-22 12:00:10.567";
        stringToDate = "2017-11-22 12:00:10.567";
        dateDifference = DateUtility.getDateTimeDifference(stringFromDate, stringToDate,
                DateTimeFormat.UUUUHMMHDDHHQMIQSS_SSS);
        assertEquals(dateDifference.longValue(), 0);
    }

    /**
     * test getDateTimeDifference method when use wrong date.
     * 
     * @see DateUtility#getDateTimeDifference(String, String, String)
     */
    @Test
    public void testGetDateTimeDifferenceWrongDate() {
        try {
            // get null
            String stringFromDate = "2017-11-A0 12:00:10.567";
            String stringToDate = "2017-11-25 12:00:10.567";

            DateUtility.getDateTimeDifference(stringFromDate, stringToDate,
                    DateTimeFormat.UUUUHMMHDDHHQMIQSS_SSS);
            fail();
        } catch (DateTimeParseException e) {
            // success
        }

        try {
            // get null
            String stringFromDate = "2017-11-22T12:00:10.567";
            String stringToDate = "2017-A0-25T12:00:10.567";

            DateUtility.getDateTimeDifference(stringFromDate, stringToDate,
                    DateTimeFormat.UUUUHMMHDDTHHQMIQSS_SSS);
            fail();
        } catch (DateTimeParseException e) {
            // success
        }
    }

    /**
     * test getDateTimeDifference method when use wrong format pattern.
     * 
     * @see DateUtility#getDateTimeDifference(String, String, String)
     */
    @Test
    public void testGetDateTimeDifferenceWrongFormat() {
        // get null
        String stringFromDate = "2017-11-22T12:00:10.567";
        String stringToDate = "2017-11-25T12:00:10.567";
        thrown.expect(DateTimeParseException.class);
        DateUtility.getDateTimeDifference(stringFromDate, stringToDate,
                DateTimeFormat.UUUUHMMHDDHHQMIQSS_SSS);
    }

    /**
     * test changeUtcTimeZoneDatetimeByZoneId method.
     * 
     * @see DateUtility#changeUtcTimeZoneDatetimeByZoneId(String, String)
     */
    @Test
    public void testChangeUtcTimeZoneDatetimeByZoneId() {

        String dateTimeString = "2017-11-22T12:20:30Z";
        String timeZone = "Asia/Shanghai";
        OffsetDateTime assertOffsetDateTime =
                OffsetDateTime.parse("2017-11-22T04:20:30Z", DateTimeFormatter.ISO_ZONED_DATE_TIME);

        OffsetDateTime offsetDateTime =
                DateUtility.changeUtcTimeZoneDatetimeByZoneId(dateTimeString, timeZone);

        assertEquals(assertOffsetDateTime.compareTo(offsetDateTime), 0);
    }


    /**
     * test changeUtcTimeZoneDatetimeByZoneId method Exception.
     * 
     * @see DateUtility#changeUtcTimeZoneDatetimeByZoneId(String, String)
     */
    @Test
    public void testChangeUtcTimeZoneDatetimeByZoneId_Exception() {

        String dateTimeString = "2017-11-22T12:20:30Z";
        String timeZone = null;

        try {
            DateUtility.changeUtcTimeZoneDatetimeByZoneId(dateTimeString, timeZone);
            fail();
        } catch (IllegalArgumentException e) {
            // success
        }

        dateTimeString = null;
        timeZone = "Asia/Shanghai";
        try {
            DateUtility.changeUtcTimeZoneDatetimeByZoneId(dateTimeString, timeZone);
            fail();
        } catch (IllegalArgumentException e) {
            // success
        }

        dateTimeString = "2017-11-xxT12:20:30Z";
        timeZone = "Asia/Shanghai";
        try {
            DateUtility.changeUtcTimeZoneDatetimeByZoneId(dateTimeString, timeZone);
            fail();
        } catch (DateTimeParseException e) {
            // success
        }

        dateTimeString = "2017-11-22T12:20:30Z";
        timeZone = "Asia/Shanghaixxxx";
        try {
            DateUtility.changeUtcTimeZoneDatetimeByZoneId(dateTimeString, timeZone);
            fail();
        } catch (ZoneRulesException e) {
            // success
        }
    }

    /**
     * test changeUtcTimeZoneDatetimeByZoneId method.
     * 
     * @see DateUtility#changeUtcTimeZoneDateByZoneId(String, String)
     */
    @Test
    public void testChangeUtcTimeZoneDateByZoneId() {
        String dateString = "20171122";
        String timeZone = "Asia/Shanghai";

        OffsetDateTime assertOffsetDateTime =
                OffsetDateTime.parse("2017-11-21T16:00Z", DateTimeFormatter.ISO_ZONED_DATE_TIME);

        OffsetDateTime offsetDateTime =
                DateUtility.changeUtcTimeZoneDateByZoneId(dateString, timeZone);

        assertEquals(assertOffsetDateTime.compareTo(offsetDateTime), 0);
    }

    /**
     * test changeUtcTimeZoneDatetimeByZoneId method Exception.
     * 
     * @see DateUtility#changeUtcTimeZoneDateByZoneId(String, String)
     */
    @Test
    public void testChangeUtcTimeZoneDateByZoneId_Exception() {

        String dateString = "20171122";
        String timeZone = null;

        try {
            DateUtility.changeUtcTimeZoneDateByZoneId(dateString, timeZone);
            fail();
        } catch (IllegalArgumentException e) {
            // success
        }

        dateString = null;
        timeZone = "Asia/Shanghai";
        try {
            DateUtility.changeUtcTimeZoneDateByZoneId(dateString, timeZone);
            fail();
        } catch (IllegalArgumentException e) {
            // success
        }

        dateString = "201711xx";
        timeZone = "Asia/Shanghai";
        try {
            DateUtility.changeUtcTimeZoneDateByZoneId(dateString, timeZone);
            fail();
        } catch (DateTimeParseException e) {
            // success
        }

        dateString = "20171122";
        timeZone = "Asia/Shanghaixxxx";
        try {
            DateUtility.changeUtcTimeZoneDateByZoneId(dateString, timeZone);
            fail();
        } catch (ZoneRulesException e) {
            // success
        }
    }

    /**
     * test getZonedDateTimeUtc method.
     * 
     * @see DateUtility#getZonedDateTimeUtc()
     */
    @Test
    public void testGetZonedDateTimeUtc() {
        OffsetDateTime offsetDateTime = DateUtility.getZonedDateTimeUtc();

        assertTrue("Z".equals(offsetDateTime.getOffset().toString()));
    }

    /**
     * test formatDateTime method.
     * 
     * @see DateUtility#formatDateTime(OffsetDateTime, String, String)
     */
    @Test
    public void testFormatDateTimeByOffsetDateTime() {
        OffsetDateTime offsetDateTime =
                OffsetDateTime.of(2018, 1, 1, 12, 10, 20, 0, ZoneOffset.UTC);
        assertTrue("2018-01-01T12:10:20Z".equals(DateUtility.formatDateTime(offsetDateTime, "UTC",
                DateUtility.OFFSET_DATE_TIME_FORMAT)));
    }

    /**
     * test formatDateTime method Exception.
     * 
     * @see DateUtility#testFormatDateTimeByOffsetDateTime_Exception(OffsetDateTime, String, String)
     */
    @Test
    public void testFormatDateTimeByOffsetDateTime_Exception() {
        OffsetDateTime offsetDateTime = null;
        String timeZone = "UTC";
        String format = DateUtility.OFFSET_DATE_TIME_FORMAT;
        try {
            DateUtility.formatDateTime(offsetDateTime, timeZone, format);
            fail();
        } catch (IllegalArgumentException e) {
            // success
        }
        offsetDateTime = OffsetDateTime.of(2018, 1, 1, 12, 10, 20, 0, ZoneOffset.UTC);
        timeZone = null;
        format = DateUtility.OFFSET_DATE_TIME_FORMAT;
        try {
            DateUtility.formatDateTime(offsetDateTime, timeZone, format);
            fail();
        } catch (IllegalArgumentException e) {
            // success
        }

        offsetDateTime = OffsetDateTime.of(2018, 1, 1, 12, 10, 20, 0, ZoneOffset.UTC);
        timeZone = "UTC";
        format = null;
        try {
            DateUtility.formatDateTime(offsetDateTime, timeZone, format);
            fail();
        } catch (IllegalArgumentException e) {
            // success
        }

        offsetDateTime = OffsetDateTime.of(2018, 1, 1, 12, 10, 20, 0, ZoneOffset.UTC);
        timeZone = "xxxx";
        format = DateUtility.OFFSET_DATE_TIME_FORMAT;
        try {
            DateUtility.formatDateTime(offsetDateTime, timeZone, format);
            fail();
        } catch (ZoneRulesException e) {
            // success
        }

        offsetDateTime = OffsetDateTime.of(2018, 1, 1, 12, 10, 20, 0, ZoneOffset.UTC);
        timeZone = "UTC";
        format = "UUUUU";
        try {
            DateUtility.formatDateTime(offsetDateTime, timeZone, format);
            fail();
        } catch (IllegalArgumentException e) {
            // success
        }
    }

}
