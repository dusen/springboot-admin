/**
 * @(#)TransactionStatusKey.java
 *
 *                               Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.dto;

import java.io.Serializable;
import javax.validation.constraints.Size;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import lombok.Data;

/**
 * This class is DTO of DynamoDB keys. The following values are set as the initial value.
 * <UL>
 * <LI>transactionId:0.
 * <LI>transactionNo.
 * </UL>
 */
@Data
public class TransactionStatusKey implements Serializable {

    /** Generated serial version UID. */
    private static final long serialVersionUID = -1378315174663656071L;

    /**
     * Transaction ID. It is ID for identifying instance executed application.
     */
    @Size(min = 1, max = 100)
    @DynamoDBHashKey
    private String transactionId;

    /**
     * Business Status. Transaction's process status.
     */
    @Size(min = 1, max = 100)
    @DynamoDBRangeKey
    private String businessStatus;

    /**
     * Javadoc.
     */
    // @Size(min = 12, max = 12)
    @DynamoDBAttribute
    private String registrationDate;
}
