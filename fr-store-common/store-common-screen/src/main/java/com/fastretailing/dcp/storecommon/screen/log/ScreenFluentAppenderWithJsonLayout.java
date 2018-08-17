/**
 * @(#)ScreenFluentAppenderWithJsonLayout.java
 *
 *                                             Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.log;

import org.slf4j.MDC;
import com.fastretailing.dcp.common.api.log.OmsFluentAppenderWithJsonLayout;

/**
 * Screen log layout.<br>
 * All layout of log with JSON format.<br>
 * 
 */
public class ScreenFluentAppenderWithJsonLayout extends OmsFluentAppenderWithJsonLayout {

    /**
     * The attribute name of user id in log.
     */
    private static final String ATTRIBUTE_USER_ID = "user_id";

    /**
     * Constructor.
     */
    public ScreenFluentAppenderWithJsonLayout() {
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

        // User id.
        getAppendItemLogMap().get().put(ATTRIBUTE_USER_ID, MDC.get(MdcKey.USER_ID.getKey()));
    }
}
