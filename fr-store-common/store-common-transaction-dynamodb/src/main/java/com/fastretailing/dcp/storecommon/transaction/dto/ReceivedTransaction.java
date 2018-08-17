/**
 * @(#)ReceivedTransaction.java
 *
 *                              Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.dto;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * This class is DTO of POS transaction.
 */
@DynamoDBTable(tableName = "transaction-dispatch.dynamodb.transaction-table")
public class ReceivedTransaction {

    /** Class that contains hashKey and transactionId. */
    @Id
    private TransactionKey transactionKey = new TransactionKey();

    /**
     * Get transaction Id from dynamoDBKeysInfo.
     * 
     * @return Transaction No.
     */
    @DynamoDBHashKey(attributeName = "TransactionId")
    public String getTransactionId() {
        return transactionKey.getTransactionId();
    }

    /**
     * Set transaction No in dynamoDBKeysInfo.
     * 
     * @param transactionId Transaction Number.
     */
    public void setTransactionId(String transactionId) {
        transactionKey.setTransactionId(transactionId);
    }

    /** Transaction data. It contains data of business processing. */
    @NotBlank
    @Size(min = 1, max = 1000000000)
    private String transactionData;

    /**
     * Get transaction data.
     * 
     * @return Transaction Data.
     */
    @DynamoDBAttribute(attributeName = "TransactionData")
    public String getTransactionData() {
        return transactionData;
    }

    /**
     * Set transaction Data.
     * 
     * @param transactionData Transaction Data.
     */
    public void setTransactionData(String transactionData) {
        this.transactionData = transactionData;
    }

    /** Registration date. The date of transaction data registered. */
    @NotBlank
    @Size(min = 16, max = 16)
    private String registrationDate;

    /**
     * Get Registration date.
     * 
     * @return Registration date.
     */
    @DynamoDBAttribute(attributeName = "RegistrationDate")
    public String getRegistrationDate() {
        return registrationDate;
    }

    /**
     * Set Registration date.
     * 
     * @param registrationDate Registration date.
     */
    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
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
