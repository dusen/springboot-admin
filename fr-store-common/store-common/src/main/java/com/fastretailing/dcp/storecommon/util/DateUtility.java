/**
 * @(#)DateUtility.java
 *
 *                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;
import java.util.TimeZone;
import org.apache.commons.lang3.StringUtils;
import lombok.Getter;

/**
 * Common component <br>
 * date utility class.
 *
 * @deprecated Will be removed in Wave 1.5. Use
 *             {@link com.fastretailing.dcp.common.util.SystemDateTime#now()} instead of
 *             {@link #getZonedDateTimeUtc()}.
 */
@Deprecated
public class DateUtility {

    /** Zone id is utc. */
    public static final String ZONE_ID_UTC = "UTC";

    /** Offset Date time is date time format . */
    public static final String OFFSET_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssX";

    private DateUtility() {}

    /**
     * Convert the specified text to a LocalDate with the specified format pattern.
     * 
     * @param dateString specified date string.
     * @param pattern specified format pattern.<BR>
     *        see {@link DateFormat} to use the predefined patterns <BR>
     *        see {@link DateTimeFormatter} to use more supported patterns <BR>
     *        use "uuuu-MM-dd" as default format when empty
     * @return {@link LocalDate} when success, null otherwise
     */
    public static LocalDate parseDate(String dateString, DateFormat pattern) {

        if (StringUtils.isEmpty(dateString)) {
            IllegalArgumentException illegalArugumentException =
                    new IllegalArgumentException("date string must be non-empty.");
            throw illegalArugumentException;
        }

        String formatPattern =
                Optional.ofNullable(pattern).orElse(DateFormat.UUUUHMMHDD).getFormat();

        DateTimeFormatter dateTimeFormatter =
                DateTimeFormatter.ofPattern(formatPattern).withResolverStyle(ResolverStyle.STRICT);
        return LocalDate.parse(dateString, dateTimeFormatter);
    }

    /**
     * Convert the specified text to a LocalDateTime with the specified format pattern.
     * 
     * @param dateTimeString Specified dateTime string.
     * @param pattern Specified format pattern.<BR>
     *        see {@link DateTimeFormat} to use the predefined pattern. <BR>
     *        see {@link DateTimeFormatter} to use more patterns. <BR>
     *        use "uuuu-MM-dd HH:mm:ss.SSS" as default format when empty
     * @return {@link LocalDateTime} when success, null otherwise
     */
    public static LocalDateTime parseDateTime(String dateTimeString, DateTimeFormat pattern) {

        return parseDateTime(dateTimeString,
                Optional.ofNullable(pattern)
                        .orElse(DateTimeFormat.UUUUHMMHDDHHQMIQSS_SSS)
                        .getFormat());
    }

    /**
     * Convert the specified text to a LocalDateTime with the specified format pattern.
     * 
     * @param dateTimeString Specified dateTime string.
     * @param pattern Specified format pattern.<BR>
     *        see {@link DateTimeFormat} to use the predefined pattern. <BR>
     *        see {@link DateTimeFormatter} to use more patterns. <BR>
     *        use "uuuu-MM-dd HH:mm:ss.SSS" as default format when empty
     * @return {@link LocalDateTime} when success, null otherwise
     */
    public static LocalDateTime parseDateTime(String dateTimeString, String pattern) {

        if (StringUtils.isEmpty(dateTimeString)) {
            IllegalArgumentException illegalArugumentException =
                    new IllegalArgumentException("date string, pattern must be non-empty.");
            throw illegalArugumentException;
        }

        DateTimeFormatter dateTimeFormatter =
                DateTimeFormatter.ofPattern(pattern).withResolverStyle(ResolverStyle.STRICT);
        return LocalDateTime.parse(dateTimeString, dateTimeFormatter);

    }

    /**
     * String formatted date to OffsetDateTime.
     * 
     * @param dateTimeString Specified date time string.
     * @param pattern Specified format pattern.<BR>
     *        see {@link DateFormat} to use the predefined pattern. <BR>
     *        see {@link DateTimeFormatter} to use more patterns. <BR>
     *        use "uuuu-MM-dd HH:mm:ss.SSS" as default format when empty.
     * @return {@link OffsetDateTime} when success, null otherwise
     */
    public static OffsetDateTime parseOffsetDateTime(String dateTimeString, DateFormat pattern) {

        return OffsetDateTime.of(parseDate(dateTimeString, pattern), LocalTime.MIN, ZoneOffset.UTC);
    }

    /**
     * String formatted date to OffsetDateTime.
     * 
     * @param dateTimeString Specified date time string.
     * @param pattern Specified format pattern.<BR>
     *        see {@link DateFormat} to use the predefined pattern. <BR>
     *        see {@link DateTimeFormatter} to use more patterns. <BR>
     *        use "uuuu-MM-dd HH:mm:ss.SSS" as default format when empty.
     * @return {@link OffsetDateTime} when success, null otherwise
     */
    public static OffsetDateTime parseOffsetDateTime(String dateTimeString,
            DateTimeFormat pattern) {
        return OffsetDateTime.of(parseDateTime(dateTimeString, pattern), ZoneOffset.UTC);
    }

    /**
     * String formatted date to OffsetDateTime.
     * 
     * @param dateTimeString Specified date time string.
     * @param pattern Specified format pattern.
     * @return {@link OffsetDateTime} when success, null otherwise
     */
    public static OffsetDateTime parseOffsetDateTime(String dateTimeString, String pattern) {
        return OffsetDateTime.of(parseDateTime(dateTimeString, pattern), ZoneOffset.UTC);
    }

    /**
     * String formatted date to OffsetDateTime.
     * 
     * @param dateTimeString Specified date time string.
     * @param pattern Specified format pattern.
     * @param offsetId Offset id.
     * @return {@link OffsetDateTime} when success, null otherwise
     */
    public static OffsetDateTime parseOffsetDateTime(String dateTimeString, String pattern,
            String offsetId) {
        return OffsetDateTime.of(parseDateTime(dateTimeString, pattern), ZoneOffset.of(offsetId));
    }

    /**
     * String formatted date to OffsetDateTime.
     * 
     * @param dateTimeString Specified date time string.
     * @param pattern Specified format pattern.
     * @param timeZoneId Time zone id.
     * @return {@link OffsetDateTime} when success, null otherwise
     */
    public static OffsetDateTime parseOffsetDateTimeWithTimeZone(String dateTimeString,
            String pattern, String timeZoneId) {
        return parseDateTime(dateTimeString, pattern)
                .atZone(TimeZone.getTimeZone(timeZoneId).toZoneId())
                .toOffsetDateTime();
    }

    /**
     * Convert the specified text to a ZonedDateTime with the specified format pattern.
     * 
     * @param dateTimeString specified dateTime string.
     * @param pattern specified format pattern.<BR>
     *        see {@link ZonedDateTimeFormat} to use the predefined pattern. <BR>
     *        see {@link DateTimeFormatter} to use more patterns. <BR>
     *        use "uuuu-MM-dd'T'HH:mm:ssX" as default format when empty
     * @return {@link ZonedDateTime} when success, null otherwise
     */
    public static ZonedDateTime parseZonedDateTime(String dateTimeString,
            ZonedDateTimeFormat pattern) {

        if (StringUtils.isEmpty(dateTimeString)) {
            IllegalArgumentException illegalArugumentException =
                    new IllegalArgumentException("date string, pattern must be non-empty.");
            throw illegalArugumentException;
        }

        String formatPattern = Optional.ofNullable(pattern)
                .orElse(ZonedDateTimeFormat.UUUUHMMHDTHHQMISSTZD)
                .getFormat();

        DateTimeFormatter dateTimeFormatter =
                DateTimeFormatter.ofPattern(formatPattern).withResolverStyle(ResolverStyle.STRICT);
        return ZonedDateTime.parse(dateTimeString, dateTimeFormatter);
    }

    /**
     * Convert the specified localDate to a date string with the specified format pattern.
     * 
     * @param localDate the specified localDate.
     * @param pattern the specified format pattern. {@link DateFormat}<BR>
     *        use "uuuu-MM-dd" as default.
     * @return a formatted date string when success, null otherwise.
     */
    public static String formatDate(LocalDate localDate, DateFormat pattern) {

        if (Objects.isNull(localDate)) {
            IllegalArgumentException illegalArugumentException =
                    new IllegalArgumentException("date must be non-empty.");
            throw illegalArugumentException;
        }

        String formatPattern =
                Optional.ofNullable(pattern).orElse(DateFormat.UUUUHMMHDD).getFormat();

        DateTimeFormatter dateTimeFormatter =
                DateTimeFormatter.ofPattern(formatPattern).withResolverStyle(ResolverStyle.STRICT);
        return localDate.format(dateTimeFormatter);
    }

    /**
     * Convert the specified localDateTime to a date time text with the specified format pattern.
     * 
     * @param localDateTime the specified localDateTime.
     * @param pattern the specified format pattern. <BR>
     *        see {@link DateTimeFormat} to use the predefined pattern. <BR>
     *        see {@link DateTimeFormatter} to use more pattern. <BR>
     *        use "uuuu-MM-dd HH:mm:ss.SSS" as default format when empty.
     * @return a date time text when success, null otherwise.
     */
    public static String formatDateTime(LocalDateTime localDateTime, DateTimeFormat pattern) {

        if (Objects.isNull(localDateTime)) {
            IllegalArgumentException illegalArugumentException =
                    new IllegalArgumentException("datetime must be non-empty.");
            throw illegalArugumentException;
        }

        String formatPattern = Optional.ofNullable(pattern)
                .orElse(DateTimeFormat.UUUUHMMHDDHHQMIQSS_SSS)
                .getFormat();

        DateTimeFormatter dateTimeFormatter =
                DateTimeFormatter.ofPattern(formatPattern).withResolverStyle(ResolverStyle.STRICT);
        return localDateTime.format(dateTimeFormatter);
    }

    /**
     * Convert the specified ZonedDateTime to a date time text with the specified format pattern.
     * 
     * @param zonedDateTime the specified localDateTime.
     * @param pattern the specified format pattern. <BR>
     *        see {@link ZonedDateTimeFormat} to use the predefined pattern. <BR>
     *        see {@link DateTimeFormatter} to use more pattern. <BR>
     *        use "uuuu-MM-ddTHH:mm:ssTZD" as default format when empty.
     * @return a date time text when success, null otherwise.
     */
    public static String formatZonedDateTime(ZonedDateTime zonedDateTime,
            ZonedDateTimeFormat pattern) {

        if (Objects.isNull(zonedDateTime)) {
            IllegalArgumentException illegalArugumentException =
                    new IllegalArgumentException("datetime must be non-empty.");
            throw illegalArugumentException;
        }

        String formatPattern = Optional.ofNullable(pattern)
                .orElse(ZonedDateTimeFormat.UUUUHMMHDTHHQMISSTZD)
                .getFormat();

        DateTimeFormatter dateTimeFormatter =
                DateTimeFormatter.ofPattern(formatPattern).withResolverStyle(ResolverStyle.STRICT);
        return zonedDateTime.format(dateTimeFormatter);
    }

    /**
     * Based on the specified date and time, the conversion source time zone ID, and the converted
     * time zone ID, the date and time are converted from the conversion source with the converted
     * time zone ID.
     * 
     * @param localDateTime a specified localDateTime from.
     * @param fromTimeZone a specified time zone from.
     * @param toTimeZone a specified time zone to.
     * @return {@link LocalDateTime} when success, null otherwise.
     */
    public static LocalDateTime changeTimeZone(LocalDateTime localDateTime, TimeZone fromTimeZone,
            TimeZone toTimeZone) {

        if (Objects.isNull(localDateTime) || Objects.isNull(fromTimeZone)
                || Objects.isNull(toTimeZone)) {
            IllegalArgumentException illegalArugumentException = new IllegalArgumentException(
                    "localDateTime, fromTimeZone, toTimeZone must be non-empty.");
            throw illegalArugumentException;
        }

        ZonedDateTime orginZonedDateTime = localDateTime.atZone(fromTimeZone.toZoneId());
        return orginZonedDateTime.toInstant().atZone(toTimeZone.toZoneId()).toLocalDateTime();
    }

    /**
     * Calculate the amount of days between the specified two LocalDate.
     * 
     * @param stringFromDate from LocalDate
     * @param stringToDate to LocalDate
     * @param pattern the specified format pattern<BR>
     *        see {@link DateFormat} to use the predefined pattern <BR>
     *        see {@link DateTimeFormatter} to use the more pattern <BR>
     *        use "uuuu-MM-dd" as default format when empty
     * @return the amount of days between two LocalDate.
     */
    public static Long getDateDifference(String stringFromDate, String stringToDate,
            DateFormat pattern) {

        LocalDate fromDate = parseDate(stringFromDate, pattern);
        LocalDate toDate = parseDate(stringToDate, pattern);

        return ChronoUnit.DAYS.between(fromDate, toDate);
    }

    /**
     * Calculate the amount of time between the specified two LocalDateTimes.
     * 
     * @param stringFromDateTime from LocalDateTime
     * @param stringToDateTime to LocalDateTime
     * @param pattern the specified format pattern<BR>
     *        see {@link DateTimeFormat} to use the predefined pattern <BR>
     *        see {@link DateTimeFormatter} to use more pattern <BR>
     *        use {@link DateTimeFormat#UUUUHMMHDDHHQMIQSS_SSS} as default format when empty
     * @return the amount of time between two LocalDateTime, null when failed
     */
    public static Long getDateTimeDifference(String stringFromDateTime, String stringToDateTime,
            DateTimeFormat pattern) {

        LocalDateTime fromDateTime = parseDateTime(stringFromDateTime, pattern);
        LocalDateTime toDateTime = parseDateTime(stringToDateTime, pattern);

        return ChronoUnit.MILLIS.between(fromDateTime, toDateTime);
    }

    /**
     * Converts the date and time with the time zone ID from the specified date and time and the
     * character string of the time zone ID and then converts it in UTC time zone.
     * 
     * @param stringDateTime a specified string date time from.
     * @param stringZoneId a specified time zone to.
     * @return {@link OffsetDateTime} when success, null otherwise.
     */
    public static OffsetDateTime changeUtcTimeZoneDatetimeByZoneId(String stringDateTime,
            String stringZoneId) {
        if (Objects.isNull(stringDateTime) || Objects.isNull(stringZoneId)) {
            IllegalArgumentException illegalArugumentException = new IllegalArgumentException(
                    "stringDateTime, stringZoneId pattern must be non-empty.");
            throw illegalArugumentException;
        }
        ZoneId zoneId = ZoneId.of(stringZoneId);
        ZonedDateTime orginZonedDateTime = DateUtility.parseZonedDateTime(stringDateTime,
                DateUtility.ZonedDateTimeFormat.UUUUHMMHDTHHQMISSTZD);
        return orginZonedDateTime.withZoneSameLocal(zoneId)
                .toInstant()
                .atZone(ZoneId.of(ZONE_ID_UTC))
                .toOffsetDateTime();
    }

    /**
     * Converts the date and time with the time zone ID from the character string of the specified
     * date and time zone ID, and then converts it in UTC time zone.
     * 
     * @param stringDate a specified string date time from.
     * @param stringZoneId a specified string time zone to.
     * @return {@link OffsetDateTime} when success, null otherwise.
     */
    public static OffsetDateTime changeUtcTimeZoneDateByZoneId(String stringDate,
            String stringZoneId) {
        if (Objects.isNull(stringDate) || Objects.isNull(stringZoneId)) {
            IllegalArgumentException illegalArugumentException = new IllegalArgumentException(
                    "stringDate, stringZoneId pattern must be non-empty.");
            throw illegalArugumentException;
        }
        ZoneId zoneId = ZoneId.of(stringZoneId);
        LocalDate localDate = DateUtility.parseDate(stringDate, DateFormat.UUUUMMDD);
        LocalDateTime localDateTime = localDate.atTime(LocalTime.MIDNIGHT);
        return localDateTime.atZone(zoneId)
                .toInstant()
                .atZone(ZoneId.of(ZONE_ID_UTC))
                .toOffsetDateTime();
    }

    /**
     * Convert OffsetDateTime Date is now for UTC.
     * 
     * @return OffsetDateTime Offset Date Time is now from UTC.
     */
    public static OffsetDateTime getZonedDateTimeUtc() {
        return ZonedDateTime.now(ZoneId.of(ZONE_ID_UTC)).toOffsetDateTime();
    }

    /**
     * Generate string date time from the specified offset date time and zoned date time format.
     * 
     * @param offsetDateTime Date Time is offset data time.
     * @param stringZoneId a specified string time zone to.
     * @param format Zoned date time is format string.
     * @return DateTimeSting Offset Date Time is string from zoned date time format string.
     */
    public static String formatDateTime(OffsetDateTime offsetDateTime, String stringZoneId,
            String format) {
        if (Objects.isNull(offsetDateTime) || Objects.isNull(stringZoneId)
                || Objects.isNull(format)) {
            IllegalArgumentException illegalArugumentException = new IllegalArgumentException(
                    "offsetDateTime, stringZoneId, format pattern must be non-empty.");
            throw illegalArugumentException;
        }
        ZoneId zoneId = ZoneId.of(stringZoneId);
        offsetDateTime.atZoneSameInstant(zoneId);
        return offsetDateTime.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * default date format pattern enum.
     *
     */
    public enum DateFormat {

        /**
         * date format context<br>
         * (uuuuMM).
         */
        UUUUMM("uuuuMM"),
        /**
         * date format context<br>
         * (uuuuMMdd).
         */
        UUUUMMDD("uuuuMMdd"),
        /**
         * date format context<br>
         * (uuuu/MM/dd).
         */
        UUUUSMMSDD("uuuu/MM/dd"),
        /**
         * date format context<br>
         * (uuuu-MM-dd).
         */
        UUUUHMMHDD("uuuu-MM-dd"),
        /**
         * date format context<br>
         * (uuuu.MM.dd).
         */
        UUUUDMMDDD("uuuu.MM.dd");

        /** format pattern. */
        @Getter
        private String format;

        private DateFormat(String format) {
            this.format = format;
        }
    }

    /**
     * default dateTime format pattern enum.
     */
    public enum DateTimeFormat {

        /**
         * date time format context<br>
         * (HHmmss).
         */
        HHMMSS("HHmmss"),
        /**
         * date time format context<br>
         * (HH:mm:ss).
         */
        HHQMMQSS("HH:mm:ss"),
        /**
         * date time format context<br>
         * (uuuuMMddHHmmss).
         */
        UUUUMMDDHHMMSS("uuuuMMddHHmmss"),
        /**
         * date time format <br>
         * (uuuu-MM-ddTHH:mm:ss).
         */
        UUUUHMMHDDTHHQMIQSS("uuuu-MM-dd'T'HH:mm:ss"),
        /**
         * date time format <br>
         * (uuuu/MM/dd HH:mm:ss).
         */
        UUUUSMMSDDHHQMIQSS("uuuu/MM/dd HH:mm:ss"),
        /**
         * date time format <br>
         * (uuuu/MM/ddTHH:mm:ss).
         */
        UUUUSMMSDDTHHQMIQSS("uuuu/MM/dd'T'HH:mm:ss"),
        /**
         * date time format <br>
         * (uuuu-MM-dd HH:mm:ss).
         */
        UUUUHMMHDDHHQMIQSS("uuuu-MM-dd HH:mm:ss"),
        /**
         * date time format <br>
         * (uuuu-MM-ddTHH:mm:ss).
         */
        UUUUHMMHDDTHHQMIQSS_SSS("uuuu-MM-dd'T'HH:mm:ss.SSS"),
        /**
         * date time format <br>
         * (uuuu/MM/dd HH:mm:ss.SSS).
         */
        UUUUSMMSDDHHQMIQSS_SSS("uuuu/MM/dd HH:mm:ss.SSS"),
        /**
         * date time format <br>
         * (uuuu/MM/ddTHH:mm:ss.SSS).
         */
        UUUUSMMSDDTHHQMIQSS_SSS("uuuu/MM/dd'T'HH:mm:ss.SSS"),
        /**
         * date time format <br>
         * (uuuu-MM-dd HH:mm:ss.SSS).
         */
        UUUUHMMHDDHHQMIQSS_SSS("uuuu-MM-dd HH:mm:ss.SSS"),

        /**
         * date time format <br>
         * (YYYY-MM-ddTHH:mm).
         */
        UUUUHMMHDTHHQMI("uuuu-MM-dd'T'HH:mm");

        /** format pattern. */
        @Getter
        private String format;

        private DateTimeFormat(String format) {
            this.format = format;
        }
    }

    /**
     * default zonedDateTime format pattern enum.
     */
    public enum ZonedDateTimeFormat {

        /**
         * date time format <br>
         * (YYYY-MM-ddTHH:mm:ssTZD).
         */
        UUUUHMMHDTHHQMISSTZD("uuuu-MM-dd'T'HH:mm:ssVV"),
        /**
         * date time format <br>
         * (YYYY-MM-ddTHH:mmTZD).
         */
        UUUUHMMHDTHHQMITZD("uuuu-MM-dd'T'HH:mmVV");


        /** format pattern. */
        @Getter
        private String format;

        private ZonedDateTimeFormat(String format) {
            this.format = format;
        }
    }

}
