/**
 * @(#)ProcessingFlag.java
 * 
 *                         Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.type;

import lombok.Getter;

/**
 * This class is the enumerable class of the processing flag.
 *
 */
public enum ProcessingFlag {
    /** Value of unprocessed. */
    UNPROCESSED(0),

    /** Value of processed. */
    PROCESSED(1);

    /**
     * String representation of the processing flag.
     */
    @Getter
    private Integer processingFlag;

    /**
     * Set the string representation of the processing flag.
     * 
     * @param Processing flag.
     */
    private ProcessingFlag(Integer value) {
        this.processingFlag = value;
    }
}
