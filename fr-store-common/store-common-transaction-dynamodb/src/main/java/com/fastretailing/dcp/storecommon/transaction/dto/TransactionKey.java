/**
 * @(#)TransactionKey.java
 *
 *                         Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.dto;

import java.io.Serializable;
import javax.validation.constraints.Size;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import lombok.Data;

/**
 * This class is DTO of DynamoDB keys. The following values are set as the initial value.
 * <UL>
 * <LI>transactionId:0.
 * </UL>
 */
@Data
public class TransactionKey implements Serializable {

    /** Generated serial version UID. */
    private static final long serialVersionUID = 6908535066470631L;

    /**
     * Transaction ID. It is ID for identifying instance executed application.
     */
    @Size(min = 1, max = 100)
    @DynamoDBHashKey
    private String transactionId;

}
