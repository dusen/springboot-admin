/**
 * @(#)CommonUtility.java
 * 
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <pre>
 * CommonUtility.
 *
 * </pre>
 * @author Fast Retailing
 * @version $Revision$
 */
@Component
public class CommonUtility {

    /**
     * system datetime.
     */
    @Value("${oms.operation.datetime:#{null}}")
    private String propertiesDateTime;

    /**
     * short name of platform.
     */
    @Value("${platform.name.short:#{null}}")
    private String platformShortName;

    /**
     * under line for debug id.
     */
    private static final String DEBUG_ID_UNDERLINE = "_";

    /**
     * Convert byteArray to long.
     * @param array number byte array
     * @return long turned from array
     */
    public static long byteArrayToLong(Byte[] array) {
        int len = array.length;
        byte[] longByte = new byte[len];
        for (int i = 0; i < len; i++) {
            longByte[i] = array[i];
        }

        return ByteBuffer.wrap(longByte).getLong();
    }

    /**
     * check target is null or blank.
     *
     * @param target target
     * @return if value is null or blank, then return true
     */
    public static boolean isNullOrBlank(Object target) {

        if (target != null) {
            // If the type is String and it is blank, then return true.
            // Target is not null and blank
            return String.class.equals(target.getClass()) && StringUtils.isEmpty(target);
        } else {

            return true;
        }
    }

    /**
     * get the operation datetime.
     * @return LocalDateTime
     * @deprecated Will be removed in Wave 1.5. Use {@link com.fastretailing.dcp.common.util.SystemDateTime#now()} instead.
     */
    @Deprecated
    public LocalDateTime getOperationAt() {
        LocalDateTime now = LocalDateTime.now();
        if (StringUtils.hasText(propertiesDateTime)) {
            // property setting exists
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
            String nowString = now.format(formatter);
            String replaceDateString =
                    propertiesDateTime + nowString.substring(propertiesDateTime.length());
            now = LocalDateTime.parse(replaceDateString, formatter);
        }
        return now;
    }

    /**
     * get debug id depends on platform.
     * @param logLabel log label
     * @param serialNumber serial number
     * @return completed debug id
     */
    public String getDebugId(String logLabel, String serialNumber) {
        StringBuilder stringBuilder = new StringBuilder()
                .append(logLabel)
                .append(DEBUG_ID_UNDERLINE)
                .append(platformShortName)
                .append(DEBUG_ID_UNDERLINE)
                .append(serialNumber);
        return stringBuilder.toString();
    }
}
