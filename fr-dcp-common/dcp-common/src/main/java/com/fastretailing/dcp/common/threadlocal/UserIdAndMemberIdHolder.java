/**
 * @(#)UserIdAndMemberIdHolder.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.common.threadlocal;

import java.util.Map;

/**
 * UserIdAndMemberIdHolder.
 *
 * admin-userid and front-memberid holder class.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public class UserIdAndMemberIdHolder {

    /**
     * Header key for X-FR-front-memberid.
     */
    public static final String HEADER_KEY_X_FR_FRONT_MEMBER_ID = "X-FR-front-memberid";

    /**
     * Header key for X-FR-admin-userid.
     */
    public static final String HEADER_KEY_X_FR_ADMIN_USER_ID = "X-FR-admin-userid";

    /**
     * The Thread-Safe storage for the path variable.<br>
     */
    protected static final ThreadLocal<Map<String, String>> userIdAndMemberIdHolder
            = new ThreadLocal<>();

    /**
     * Set the userIdAndMemberId's context.<br>
     * @param userIdAndMemberIdMap userIdAndMemberId's context.
     */
    public static void setUserIdAndMemberIdMap(Map<String, String> userIdAndMemberIdMap) {
        userIdAndMemberIdHolder.set(userIdAndMemberIdMap);
    }

    /**
     * Get the memberid in thread local.<br>
     * @return the memberid .
     */
    public static String getMemberId() {
        return getUserIdOrMemberIdValue(HEADER_KEY_X_FR_FRONT_MEMBER_ID);
    }

    /**
     * Get the userid in thread local.<br>
     * @return the userid .
     */
    public static String getUserId() {
        return getUserIdOrMemberIdValue(HEADER_KEY_X_FR_ADMIN_USER_ID);
    }

    /**
     * Get the userid or memberid in thread local.<br>
     * @param key key
     * @return the value
     */
    private static String getUserIdOrMemberIdValue(String key) {
        Map<String, String> userIdAndMemberIdMap = userIdAndMemberIdHolder.get();
        if (userIdAndMemberIdMap == null) {
            return null;
        }
        return userIdAndMemberIdMap.get(key);
    }
}
