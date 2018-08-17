/**
 * @(#)CommonColumnsName.java
 *
 *                            Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.masterupdate.component;

/**
 * Common columns name of table class.
 */
public class ColumnName {

    /** Create user id. */
    public static final String CREATE_USER_ID = "create_user_id";

    /** Create date. */
    public static final String CREATE_DATE_TIME = "create_datetime";

    /** Create program id. */
    public static final String CREATE_PROGRAM_ID = "create_program_id";

    /** Update user id. */
    public static final String UPDATE_USER_ID = "update_user_id";

    /** Update date. */
    public static final String UPDATE_DATE_TIME = "update_datetime";

    /** Update program id. */
    public static final String UPDATE_PROGRAM_ID = "update_program_id";

    /**
     * Check if item is common item.
     * 
     * @param itemName Common item name.
     * @return Parameter does common item result.
     */
    public static boolean isCommonItem(String itemName) {
        switch (itemName) {
            case CREATE_USER_ID:
                return true;
            case CREATE_DATE_TIME:
                return true;
            case CREATE_PROGRAM_ID:
                return true;
            case UPDATE_USER_ID:
                return true;
            case UPDATE_DATE_TIME:
                return true;
            case UPDATE_PROGRAM_ID:
                return true;
            default:
                return false;
        }
    }
}
