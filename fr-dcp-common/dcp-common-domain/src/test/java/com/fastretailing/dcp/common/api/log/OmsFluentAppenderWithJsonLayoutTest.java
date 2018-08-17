package com.fastretailing.dcp.common.api.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.LoggerContextVO;
import com.fastretailing.dcp.common.api.jvm.OmsJvmParameters;
import com.fastretailing.dcp.common.constants.LogLevel;
import org.fluentd.logger.FluentLogger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({@ContextConfiguration(locations = {"classpath*:com/fastretailing/dcp/common/api/log/test-context.xml"})})
public class OmsFluentAppenderWithJsonLayoutTest {

    @Mock
    private FluentLogger fluentLogger;

    @InjectMocks
    private OmsFluentAppenderWithJsonLayout omsFluentAppenderWithJsonLayout = new OmsFluentAppenderWithJsonLayout();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        omsFluentAppenderWithJsonLayout.start();
    }

    @Test
    public void appendCase1() {

        LoggingEventMock eventMock = new LoggingEventMock();

        eventMock.level = Level.INFO;
        eventMock.marker = MarkerFactory.getMarker(LogLevel.NOTICE.name());
        Map<String, String> mdcPropertyMap = new HashMap<>();
        mdcPropertyMap.put(MdcKeyEnum.AMAZON_TRACE_ID.getKey(), "test traceId");
        mdcPropertyMap.put(MdcKeyEnum.REQUEST_ID.getKey(), "test requestId");
        mdcPropertyMap.put(MdcKeyEnum.TASK_SEQ.getKey(), "test task seq");
        mdcPropertyMap.put(MdcKeyEnum.ENDPOINT.getKey(), "test endpoint");
        mdcPropertyMap.put(MdcKeyEnum.MEMBER_ID.getKey(), "test memberId");
        mdcPropertyMap.put(MdcKeyEnum.USER_ID.getKey(), "test userId");
        eventMock.mdcPropertyMap = mdcPropertyMap;
        eventMock.threadName = "test thread01";
        eventMock.loggerName = "test loggerName";
        eventMock.formattedMessage= "test formattedMessage";
        eventMock.message = "test message";

        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("test7", "test7");
        Map<String, Object> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("test8", "test8");
        Object[] argumentArray = new Object[] {hashMap, linkedHashMap};
        eventMock.argumentArray = argumentArray;

        ReflectionTestUtils.setField(omsFluentAppenderWithJsonLayout, "fluentLogger", fluentLogger);

        Mockito.when(fluentLogger.log(Matchers.anyString(),Matchers.anyMap(),Matchers.anyLong())).thenReturn(false);

        omsFluentAppenderWithJsonLayout.append(eventMock);
        omsFluentAppenderWithJsonLayout.stop();

    }

    @Test
    public void appendCase2() throws UnknownHostException {

        LoggingEventMock eventMock = new LoggingEventMock();

        eventMock.level = Level.INFO;
        eventMock.marker = MarkerFactory.getMarker(LogLevel.NOTICE.name());
        Map<String, String> mdcPropertyMap = new HashMap<>();
        mdcPropertyMap.put(MdcKeyEnum.AMAZON_TRACE_ID.getKey(), "test traceId");
        mdcPropertyMap.put(MdcKeyEnum.REQUEST_ID.getKey(), "test requestId");
        mdcPropertyMap.put(MdcKeyEnum.TASK_SEQ.getKey(), "test task seq");
        mdcPropertyMap.put(MdcKeyEnum.ENDPOINT.getKey(), "test endpoint");
        mdcPropertyMap.put(MdcKeyEnum.MEMBER_ID.getKey(), "test memberId");
        mdcPropertyMap.put(MdcKeyEnum.USER_ID.getKey(), "test userId");
        eventMock.mdcPropertyMap = mdcPropertyMap;
        eventMock.threadName = "test thread01";
        eventMock.loggerName = "test loggerName";
        eventMock.formattedMessage= "test formattedMessage";
        eventMock.message = "test message";

        OmsJvmParameters omsJvmParameters = new OmsJvmParameters();
        ReflectionTestUtils.setField(omsJvmParameters, "isAdminApi", true);

        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("test7", "test7");
        Map<String, Object> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("test8", "test8");
        Object[] argumentArray = new Object[] {hashMap, linkedHashMap};
        eventMock.argumentArray = argumentArray;

        ReflectionTestUtils.setField(omsFluentAppenderWithJsonLayout, "fluentLogger", fluentLogger);
        ReflectionTestUtils.setField(omsFluentAppenderWithJsonLayout, "label", "test label");

        Mockito.when(fluentLogger.log(Matchers.anyString(),Matchers.anyMap(),Matchers.anyLong())).thenReturn(false);

        omsFluentAppenderWithJsonLayout.append(eventMock);
        omsFluentAppenderWithJsonLayout.stop();

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
