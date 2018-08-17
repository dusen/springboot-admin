/**
 * @(#)TBusinessReceiverStatus.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.businessreceiver.dto;


import java.util.Date;
import lombok.Data;

/**
 * Table of right to execute business receiver.
 */
@Data
public class TBusinessReceiverStatus {
    /** Transaction Id. */
    private String transactionId;

    /** Business receiver name. */
    private String businessReceiverName;

    /** Record created date. */
    private Date createDate;

    /** Record create user. */
    private String createUser;

    /** Retry count. */
    private int retryCount;
}
