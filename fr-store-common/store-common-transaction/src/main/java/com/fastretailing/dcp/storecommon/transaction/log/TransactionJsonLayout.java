/**
 * @(#)TransactionJsonLayout.java
 *
 *                                Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.log;

import java.util.Map;
import com.fastretailing.dcp.common.api.log.OmsJsonLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * TransactionJsonLayout.<br>
 * All layout of log with JSON format.<br>
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
public class TransactionJsonLayout extends OmsJsonLayout {

    /**
     * The attribute name of sales transaction id in log.
     */
    private static final String ATTRIBUTE_SALES_TRANSACTION_ID = "salesTransactionId";

    /**
     * Constructor.
     */
    public TransactionJsonLayout() {
        super();
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
    }

    /**
     * Generate the log output map.
     * 
     * @param event Log event.
     * @return JSON map.
     */
    @Override
    protected Map<String, Object> toJsonMap(ILoggingEvent event) {
        Map<String, Object> map = super.toJsonMap(event);

        // Sales transaction id.
        Map<String, String> mdc = event.getMDCPropertyMap();
        if (mdc != null && !mdc.isEmpty()) {
            map.put(ATTRIBUTE_SALES_TRANSACTION_ID,
                    mdc.get(TransactionMdcKey.SALES_TRANSACTION_ID));
        }

        return map;
    }
}
