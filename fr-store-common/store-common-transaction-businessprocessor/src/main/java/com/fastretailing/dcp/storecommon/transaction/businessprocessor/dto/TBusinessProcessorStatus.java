/**
 * @(#)TBusinessProcessorStatus.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.businessprocessor.dto;

import java.util.Date;
import lombok.Data;

/**
 * Table of right to execute business process.
 */
@Data
public class TBusinessProcessorStatus {
    
    /** Partitioning process number. */
    private long partitioningNo;
    
    /** Transaction Id. */
    private String transactionId;
    
    /** Business process name. */
    private String businessProcessName;

    /** Record created date. */
    private Date createDate;

    /** Record create user. */
    private String createUser;
    
    /** Retry count. */
    private int retryCount;

}
