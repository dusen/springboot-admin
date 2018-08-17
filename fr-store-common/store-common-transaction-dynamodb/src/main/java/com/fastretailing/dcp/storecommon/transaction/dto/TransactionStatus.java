/**
 * @(#)TransactionStatus.java
 *
 *                            Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.dto;

import org.springframework.data.annotation.Id;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * Transaction status history.
 */
@DynamoDBTable(tableName = "transaction-dispatch.dynamodb.transaction-status-table")
public class TransactionStatus {

    /** Value separator. */
    public static final String BUSINESS_STATUS_SEPARATOR = ":";

    /** Class that contains hashKey and transactionId. */
    @Id
    private TransactionStatusKey transactionStatusKey = new TransactionStatusKey();

    /**
     * Get transaction Id from dynamoDB key object.
     * 
     * @return Transaction Id.
     */
    @DynamoDBHashKey(attributeName = "TransactionId")
    public String getTransactionId() {
        return transactionStatusKey.getTransactionId();
    }

    /**
     * Set transaction Id in dynamoDB key object.
     * 
     * @param transactionId Transaction ID.
     */
    public void setTransactionId(String transactionId) {
        transactionStatusKey.setTransactionId(transactionId);
    }

    /**
     * Get business status from dynamoDB key object.
     * 
     * @return Business status.
     */
    @DynamoDBRangeKey(attributeName = "BusinessStatus")
    public String getBusinessStatus() {
        return transactionStatusKey.getBusinessStatus();
    }

    /**
     * Set business status in dynamoDB key object.
     * 
     * @param businessStatus Business status.
     */
    public void setBusinessStatus(String businessStatus) {
        transactionStatusKey.setBusinessStatus(businessStatus);
    }

    /**
     * Gets registration date.
     * 
     * @return Registration date.
     */
    @DynamoDBAttribute(attributeName = "RegistrationDate")
    public String getRegistrationDate() {
        return transactionStatusKey.getRegistrationDate();
    }

    /**
     * Sets registration date..
     * 
     * @param registrationDate Registration date.
     */
    public void setRegistrationDate(String registrationDate) {
        transactionStatusKey.setRegistrationDate(registrationDate);
    }

    /** The date of transaction data expired. */
    private long expirationTime;

    /**
     * Get expiration time.
     * 
     * @return Expiration time.
     */
    @DynamoDBAttribute(attributeName = "ExpirationTime")
    public long getExpirationTime() {
        return expirationTime;
    }

    /**
     * Set expiration time.
     * 
     * @param expirationTime Expiration time.
     */
    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }
}
