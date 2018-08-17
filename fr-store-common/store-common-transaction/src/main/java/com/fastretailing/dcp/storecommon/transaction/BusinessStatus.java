/**
 * @(#)BusinessStatus.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction;

/**
 * Transaction receiving status.
 */
public enum BusinessStatus {

    /** Success status. */
    SUCCESS,

    /** Duplicating status of transaction. */
    DUPLICATE,

    /** Error status. */
    ERROR;

}
