/**
 * @(#)TransactionJsonLayoutTest.java
 *
 *                                    Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.log;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.slf4j.Marker;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.LoggerContextVO;

/**
 * TransactionJsonLayout test class.
 */
public class TransactionJsonLayoutTest {

    /**
     * Constructor test.
     */
    @Test
    public void constructorCase1() {
        TransactionJsonLayout transactionJsonLayout = new TransactionJsonLayout();
        assertThat(transactionJsonLayout.isIncludeContextName(), is(false));
        assertThat(transactionJsonLayout.isAppendLineSeparator(), is(true));
        assertThat(transactionJsonLayout.isIncludeMDC(), is(false));
    }

    /**
     * The value of "salesTransactionId" is set to the map.
     */
    @Test
    public void testToJsonMapNormal() {

        Map<String, String> mdcPropertyMap = new HashMap<>();
        mdcPropertyMap.put(TransactionMdcKey.SALES_TRANSACTION_ID, "testid");
        LoggingEventMock eventMock = new LoggingEventMock();

        eventMock.mdcPropertyMap = mdcPropertyMap;

        TransactionJsonLayout transactionJsonLayout = new TransactionJsonLayout();
        Map<String, Object> result = transactionJsonLayout.toJsonMap(eventMock);

        assertThat(result.getClass().getSimpleName(), is("LinkedHashMap"));
        String hostname;
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            hostname = "UnknownHost";
        }
        assertThat(result.get("hostName"), is(hostname));

        assertThat(result.get("salesTransactionId"), is("testid"));
    }

    /**
     * MDC map is null.
     */
    @Test
    public void testToJsonMapMdcMapNull() {

        LoggingEventMock eventMock = new LoggingEventMock();

        TransactionJsonLayout transactionJsonLayout = new TransactionJsonLayout();
        Map<String, Object> result = transactionJsonLayout.toJsonMap(eventMock);

        assertThat(result.getClass().getSimpleName(), is("LinkedHashMap"));
        String hostname;
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            hostname = "UnknownHost";
        }
        assertThat(result.get("hostName"), is(hostname));

        assertThat(result.get("salesTransactionId"), is(nullValue()));
    }


    class LoggingEventMock implements ILoggingEvent {

        public String threadName = null;
        public Level level = null;
        public String message = null;
        public Object[] argumentArray = null;
        public String formattedMessage = null;
        public String loggerName = null;
        public Map<String, String> mdcPropertyMap = null;
        public Marker marker = null;

        public String getThreadName() {
            return this.threadName;
        }

        public Level getLevel() {
            return this.level;
        }

        public String getMessage() {
            return this.message;
        }

        public Object[] getArgumentArray() {
            return this.argumentArray;
        }

        public String getFormattedMessage() {
            return this.formattedMessage;
        }

        public String getLoggerName() {
            return this.loggerName;
        }

        public LoggerContextVO getLoggerContextVO() {
            return null;
        }

        public IThrowableProxy getThrowableProxy() {
            return null;
        }

        public StackTraceElement[] getCallerData() {
            return null;
        }

        public boolean hasCallerData() {
            return false;
        }

        public Marker getMarker() {
            return marker;
        }

        public Map<String, String> getMDCPropertyMap() {
            return this.mdcPropertyMap;
        }

        public Map<String, String> getMdc() {
            return null;
        }

        public long getTimeStamp() {
            return 0;
        }

        public void prepareForDeferredProcessing() {}
    }

}
