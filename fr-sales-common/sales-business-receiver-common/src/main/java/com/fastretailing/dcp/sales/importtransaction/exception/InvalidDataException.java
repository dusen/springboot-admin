/**
 * @(#)InvalidDataException.java
 *
 *                               Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.importtransaction.exception;

import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionErrorDetail;
import lombok.Getter;
import lombok.Setter;

/**
 * The class is used for invalid data exception.
 *
 */
public class InvalidDataException extends RuntimeException {

    /** Serial version UID. */
    private static final long serialVersionUID = 1L;

    /**
     * The information will insert into sales transaction error detail table.
     */
    @Getter
    @Setter
    private SalesTransactionErrorDetail entity;

    public InvalidDataException(SalesTransactionErrorDetail entity) {
        this.entity = entity;
    }
}
