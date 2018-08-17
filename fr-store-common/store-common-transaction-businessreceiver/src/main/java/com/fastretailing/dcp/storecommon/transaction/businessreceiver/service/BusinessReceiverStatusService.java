/**
 * @(#)BusinessReceiverStatusService.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.businessreceiver.service;

import com.fastretailing.dcp.storecommon.transaction.ExecutionRightAcquisitionState;
import com.fastretailing.dcp.storecommon.transaction.dto.ReceiveMessage;

/**
 * This interface that business receiver status table service.
 */
public interface BusinessReceiverStatusService {

    /**
     * Acquires a execution right of the transaction.
     * 
     * @param receiveMessage Target transaction message.
     * @return Acquisition status of execution right.
     */
    ExecutionRightAcquisitionState acquireExecutionRight(ReceiveMessage receiveMessage);

}
