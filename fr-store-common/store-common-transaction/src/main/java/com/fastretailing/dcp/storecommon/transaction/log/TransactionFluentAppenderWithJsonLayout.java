/**
 * @(#)TransactionFluentAppenderWithJsonLayout.java
 *
 *                                                  Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.log;

import org.slf4j.MDC;
import com.fastretailing.dcp.common.api.log.OmsFluentAppenderWithJsonLayout;

/**
 * TransactionJsonLayout.<br>
 * All layout of log with JSON format.<br>
 * 
 */
public class TransactionFluentAppenderWithJsonLayout extends OmsFluentAppenderWithJsonLayout {

    /**
     * The attribute name of sales transaction id in log.
     */
    private static final String ATTRIBUTE_SALES_TRANSACTION_ID = "salesTransactionId";

    /**
     * Constructor.
     */
    public TransactionFluentAppenderWithJsonLayout() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        super.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        super.stop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void appendItemLog() {

        // Sales transaction id.
        getAppendItemLogMap().get().put(ATTRIBUTE_SALES_TRANSACTION_ID,
                MDC.get(TransactionMdcKey.SALES_TRANSACTION_ID));
    }
}
