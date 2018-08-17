/**
 * @(#)MailUtility.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.mail.util;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Mail's utility.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public class MailUtility {

    /**
     * Map key for brandCode.<br>
     */
    public static final String KEY_BRAND_CODE = "brand_code";

    /**
     * Map key for regionCode.<br>
     */
    public static final String KEY_REGION_CODE = "region_code";

    /**
     * Map key for languageCode.<br>
     */
    public static final String KEY_LANGUAGE_CODE = "language_code";

    /**
     * Map key for mailType.<br>
     */
    public static final String KEY_MAIL_TYPE = "mail_type";

    /**
     * Map key for paymentType.<br>
     */
    public static final String KEY_PAYMENT_TYPE = "payment_type";

    /**
     * Map key for deliveryType.<br>
     */
    public static final String KEY_DELIVERY_TYPE = "delivery_type";

    /**
     * Map key for receiveType.<br>
     */
    public static final String KEY_RECEIVE_TYPE = "receive_type";

    /**
     * Concat DatabaseTemplateLoader's template key
     *      with template's id and  group and locale with comma.<br>
     * @param brandCode brandCode
     * @param regionCode regionCode
     * @param languageCode languageCode
     * @param mailType mailType mailType
     * @param paymentType paymentType
     * @param deliveryType deliveryType
     * @param receiveType receiveType
     * @return key string
     */
    public static String buildTemplateKey(
            String brandCode, String regionCode, String languageCode, String mailType,
            String paymentType, String deliveryType, String receiveType) {

        return StringUtils.joinWith(",",
                String.format("#brandCode<%s>#",    brandCode),
                String.format("#regionCode<%s>#",   regionCode),
                String.format("#languageCode<%s>#", languageCode),
                String.format("#mailType<%s>#",     mailType),
                String.format("#paymentType<%s>#",  paymentType),
                String.format("#deliveryType<%s>#", deliveryType),
                String.format("#receiveType<%s>#",  receiveType)
        );

    }

    /**
     * Concat the mail address with comma.<br>
     * @param addresses E-Mail's addresses
     * @return address string
     */
    public static String concatMailAddress(String... addresses) {
        return StringUtils.join(addresses, ",");
    }

    /**
     * Analyze template's key with RegEx and put return into a map.<br>
     * @param key template key
     * @return Result-Map
     */
    public static Map<String, String> analyzeTemplateKey(String key) {

        // Matched String :
        //      #brandCode<xxx>#,#regionCode<xxx>#,#languageCode<xxx>#, ...
        final String regEx = StringUtils.joinWith(",",
                "#brandCode<(.*)>#", "#regionCode<(.*)>#", "#languageCode<(.*)>#",
                "#mailType<(.*)>#", "#paymentType<(.*)>#", "#deliveryType<(.*)>#",
                "#receiveType<(.*)>#"
        );

        Map<String, String> map = new HashMap<>();
        Matcher matcher = Pattern.compile(".*" + regEx + ".*").matcher(key);
        if (matcher.matches()) {
            map.put(KEY_BRAND_CODE, matcher.group(1));
            map.put(KEY_REGION_CODE, matcher.group(2));
            map.put(KEY_LANGUAGE_CODE, matcher.group(3));
            map.put(KEY_MAIL_TYPE, matcher.group(4));
            map.put(KEY_PAYMENT_TYPE, matcher.group(5));
            map.put(KEY_DELIVERY_TYPE, matcher.group(6));
            map.put(KEY_RECEIVE_TYPE, matcher.group(7));
        }

        return map;
    }

}
