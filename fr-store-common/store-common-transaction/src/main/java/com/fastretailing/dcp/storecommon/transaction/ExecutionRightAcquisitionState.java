/**
 * @(#)ExecutionRightAcquisitionState.java
 *
 *                                         Copyright (c) 2018 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.storecommon.transaction;

/**
 * An execution right acquisition status of transaction.
 */
public enum ExecutionRightAcquisitionState {

    /** The state when execution right can be acquired. */
    ACQUIRED,

    /** The state when execution right can not be acquired. */
    FAILED;

}
