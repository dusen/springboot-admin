/**
 * @(#)TransactionStatusIndex.java
 *
 *                                 Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.dto;

import org.springframework.data.annotation.Id;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;

/**
 * Transaction status history.
 */
@DynamoDBTable(tableName = "transaction-dispatch.dynamodb.transaction-status-table")
@Data
public class TransactionStatusIndex {

    /** Class that contains hashKey and transactionId. */
    @Id
    private TransactionStatusIndexKey transactionStatusIndexKey = new TransactionStatusIndexKey();

    /**
     * Gets registration date.
     * 
     * @return Registration date.
     */
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "RegistrationDate-index",
            attributeName = "RegistrationDate")
    public String getRegistrationDate() {
        return transactionStatusIndexKey.getRegistrationDate();
    }

    /**
     * Sets registration date.
     * 
     * @return Registration date.
     */
    public void setRegistrationDate(String registrationDate) {
        transactionStatusIndexKey.setRegistrationDate(registrationDate);
    }

    /**
     * Get transaction Id from dynamoDBKeysInfo.
     * 
     * @return Transaction Id.
     */
    @DynamoDBHashKey(attributeName = "TransactionId")
    public String getTransactionId() {
        return transactionStatusIndexKey.getTransactionId();
    }

    /**
     * Set transaction Id in dynamoDBKeysInfo.
     * 
     * @param transactionId Transaction ID.
     */
    public void setTransactionId(String transactionId) {
        transactionStatusIndexKey.setTransactionId(transactionId);
    }

}
