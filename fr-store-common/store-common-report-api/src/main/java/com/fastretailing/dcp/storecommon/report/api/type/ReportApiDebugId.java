/**
 * @(#)ReportApiDebugId.java
 * 
 *                           Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.type;

import lombok.Getter;

/**
 * Debug id for all functions in report api.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
public enum ReportApiDebugId {

    /**
     * No such report data in status db.
     */
    GET_DATA_STATUS_DB_NO_DATA("64910001"),
    /**
     * Rrror in getting data from aws s3.
     */
    GET_DATA_AWS_S3_DOWNLOAD_ERROR("64910002"),
    /**
     * Report id and number are both empty.
     */
    GET_LIST_ID_NUMBER_BOTH_EMPTY("44910003"),
    /**
     * No bucket name is specified.
     */
    UPDATE_STATUS_S3_BUCKET_NAME_EMPTY("44910004"),
    /**
     * No s3 key is specified.
     */
    UPDATE_STATUS_S3_KEY_EMPTY("44910005"),
    /**
     * The report create status is not a valid value.
     */
    UPDATE_STATUS_CREATE_STATUS_INVALID("44910006"),
    /**
     * The auto print status is not a valid value.
     */
    UPDATE_STATUS_AUTO_PRINT_STATUS_INVALID("44910007"),
    /**
     * The report create status , auto print status both null.
     */
    UPDATE_STATUS_BOTH_STATUS_NULL("44910008");

    /**
     * Debug id.
     */
    @Getter
    private String debugId;

    /**
     * Constructor for the enum.
     * 
     * @param debug id of the enum.
     */
    private ReportApiDebugId(String debugId) {
        this.debugId = debugId;
    }
}
