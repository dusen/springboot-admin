/**
 * @(#)TransactionConsistencyErrorType.java
 * 
 *                                          Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.type;

import com.fastretailing.dcp.common.constants.LogLevel;
import com.fastretailing.dcp.storecommon.FunctionType;
import com.fastretailing.dcp.storecommon.MessageType;
import com.fastretailing.dcp.storecommon.PlatformShortName;

/**
 * This class is the enumerable class of the transaction consistency error type.
 *
 */
public enum TransactionConsistencyErrorType {

    /** Error type of validation error. */
    VALIDATION_ERROR(ErrorType.VALIDATION_ERROR.getErrorType(), "000181"),
    /** Error type of update business date error. */
    UPDATE_BUSINESS_DATE_ERROR(ErrorType.UPDATE_BUSINESS_DATE_ERROR.getErrorType(), "000182"),
    /** Error type of relation error. */
    RELATION_ERROR(ErrorType.RELATION_ERROR.getErrorType(), "000183"),
    /** Value of tender error. */
    TENDER_ERROR(ErrorType.TENDER_ERROR.getErrorType(), "000184"),
    /** Value of amount balance error. */
    AMOUNT_BALANCE_ERROR(ErrorType.AMOUNT_BALANCE_ERROR.getErrorType(), "000185"),
    /** Value of other exceptions. */
    OTHER_EXCEPTIONS("", "000179");

    /**
     * Under line for message id.
     */
    private static final String UNDERLINE = "_";

    /** Error type id. */
    private String id;

    /** Error type message value. */
    private String value;

    /**
     * Set of the transaction consistency error type.
     * 
     * @param id Error type id.
     * @param value Error type value.
     */
    private TransactionConsistencyErrorType(String id, String value) {
        this.id = id;
        this.value = value;
    }

    /**
     * Get error type id.
     * 
     * @return Error type Id.
     */
    private String getId() {
        return this.id;
    }

    /**
     * Get error type value.
     * 
     * @return Error type value.
     */
    private String getValue() {
        return this.value;
    }

    /**
     * Get error type message value.
     * 
     * @param id Error type id.
     * @return Error type message from error type value.
     */
    public static String getMessageId(String id) {
        TransactionConsistencyErrorType[] types = TransactionConsistencyErrorType.values();
        for (TransactionConsistencyErrorType type : types) {
            if (type.getId() == id) {
                return new StringBuilder().append(LogLevel.ERROR.toString())
                        .append(UNDERLINE)
                        .append(PlatformShortName.SALES.toString())
                        .append(UNDERLINE)
                        .append(MessageType.BUSINESS_ERROR.toString())
                        .append(FunctionType.SCREEN.toString())
                        .append(type.getValue())
                        .toString();
            }
        }
        return null;
    }
}

