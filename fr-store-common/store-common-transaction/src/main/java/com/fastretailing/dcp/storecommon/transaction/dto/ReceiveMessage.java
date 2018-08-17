/**
 * @(#)ReceiveMessage.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import lombok.Data;

/**
 * SQS message.
 */
@Data
public class ReceiveMessage {

    /** Transaction id. */
    @NotEmpty
    private String transactionId;

    /** Transaction data object. */
    @NotNull
    @Valid
    private TransactionData transactionData;
}
