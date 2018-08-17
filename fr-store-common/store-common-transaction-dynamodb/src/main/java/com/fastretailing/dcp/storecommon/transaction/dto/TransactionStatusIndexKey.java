/**
 * @(#)TransactionStatusIndexKey.java
 *
 *                                    Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.dto;

import java.io.Serializable;
import javax.validation.constraints.Size;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import lombok.Data;

/**
 * This class is DTO of DynamoDB keys for the global secondary index of transaction status. The
 * following values are set as the initial value.
 * <UL>
 * <LI>registrationDate
 * <LI>transactionId.
 * </UL>
 */
@Data
public class TransactionStatusIndexKey implements Serializable {

    /** Generated serial version UID. */
    private static final long serialVersionUID = -316068747725840140L;

    /**
     * Javadoc.
     */
    @DynamoDBIndexHashKey
    private String registrationDate;

    /**
     * Transaction ID. It is ID for identifying instance executed application.
     */
    @Size(min = 1, max = 100)
    @DynamoDBHashKey
    private String transactionId;

}
