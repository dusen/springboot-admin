/**
 * @(#)TransactionConsistencyErrorException.java
 *
 *                            Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.importtransaction.exception;

import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionErrorDetail;
import lombok.Getter;
import lombok.Setter;

/**
 * The class is used for transactional consistency error.
 *
 */
public class TransactionConsistencyErrorException extends RuntimeException {

    /** Serial version UID. */
    private static final long serialVersionUID = 7434740146898631351L;

    /**
     * The information will insert into sales transaction error detail table.
     */
    @Getter
    @Setter
    private SalesTransactionErrorDetail entity;

    /**
     * Constructor by sales transaction error detail.
     * 
     * @param entity sales transaction error detail.
     */
    public TransactionConsistencyErrorException(SalesTransactionErrorDetail entity) {
        this.entity = entity;
    }
}
