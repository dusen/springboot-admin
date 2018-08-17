/**
 * @(#)Pagination.java
 *
 *                     Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.form;

import lombok.Data;

/**
 * pagination's information.
 *
 */
@Data
public class Pagination {

    /** Total number of item. */
    private long totalNumber;

    /** Max number of item. */
    private long maxNumber;

    /** Number of per page. */
    private long perPageNumber;

    /** Total number of page. */
    private long totalPageNumber;

    /** Current page number. */
    private long currentPageNumber;

    /**
     * Set total number of item.
     *
     * @param totalNumber The total number.
     */
    public void setTotalNumber(long totalNumber) {
        this.totalNumber = totalNumber;
        updateTotalPageNumber();
    }

    /**
     * Set max number of item.
     *
     * @param maxNumber The max number.
     */
    public void setMaxNumber(long maxNumber) {
        this.maxNumber = maxNumber;
        updateTotalPageNumber();
    }

    /**
     * Set number of per page.
     *
     * @param perPageNumber The number of per page.
     */
    public void setPerPageNumber(long perPageNumber) {
        this.perPageNumber = perPageNumber;
        updateTotalPageNumber();
    }

    /**
     * Update total page number when totalNumber,perPageNumber or maxNumber is changed.
     * 
     */
    private void updateTotalPageNumber() {
        if (totalNumber > 0 && perPageNumber > 0) {
            if (totalNumber > maxNumber && maxNumber > 0) {
                totalPageNumber = maxNumber / perPageNumber;
                if (maxNumber % perPageNumber > 0) {
                    totalPageNumber += 1;
                }
            } else {
                totalPageNumber = totalNumber / perPageNumber;
                if (totalNumber % perPageNumber > 0) {
                    totalPageNumber += 1;
                }
            }
        }
    }

}
