/**
 * @(#)TransactionData.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.dto;

import java.util.HashMap;
import java.util.Map;
import javax.validation.constraints.NotNull;
import com.fastretailing.dcp.storecommon.transaction.TransactionResource;
import lombok.Data;

/**
 * DTO class that stores information on transaction data.
 */
@Data
public class TransactionData {

    /**
     * Request header information.
     */
    @NotNull
    private Map<String, String> requestHeaderMap = new HashMap<>();

    /**
     * Parameter value obtained from the URL. The value of the key is defined as
     * "{@link TransactionResource}".
     */
    @NotNull
    private Map<TransactionResource, String> resourceMap = new HashMap<>();

    /**
     * Transaction data used for business processing.
     */
    @NotNull
    private String businessTransactionData = null;

    /**
     * Information for controlling the transaction dispatch function.
     * 
     * @see TransactionDispatchControlInformation
     */
    @NotNull
    private TransactionDispatchControlInformation transactionDispatchControlInformation =
            new TransactionDispatchControlInformation();

}
