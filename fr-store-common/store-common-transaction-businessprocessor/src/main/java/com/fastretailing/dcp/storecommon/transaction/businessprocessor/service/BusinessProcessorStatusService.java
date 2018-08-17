/**
 * @(#)BusinessProcessorStatusService.java
 *
 *                                         Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.businessprocessor.service;

import com.fastretailing.dcp.storecommon.transaction.ExecutionRightAcquisitionState;
import com.fastretailing.dcp.storecommon.transaction.dto.ReceiveMessage;

/**
 * This interface that manipulates pointer information.
 */
public interface BusinessProcessorStatusService {

    /**
     * Acquires a execution right of the transaction. If the partition number of the acquired data
     * is the partition number to be processed, execution right acquisition processing is performed.
     * The partition number to be processed is managed with the setting file. If it is a partition
     * number not subject to processing, or if acquisition of execution right fails, FAILED is
     * returned, and processing is terminated at that point.
     * 
     * @param receiveMessage Target transaction message.
     * @return Acquisition status of execution right.
     */
    ExecutionRightAcquisitionState acquireExecutionRight(ReceiveMessage receiveMessage);

}
