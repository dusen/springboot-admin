package com.fastretailing.dcp.common.api.log;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.is;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fastretailing.dcp.common.constants.LogLevel;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.LoggerContextVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({@ContextConfiguration(locations = {"classpath*:com/fastretailing/dcp/common/api/log/test-context.xml"})})
public class OmsJsonLayoutTest {

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void constructorCase1() {
        OmsJsonLayout omsJsonLayout = new OmsJsonLayout();
        assertThat(omsJsonLayout.isIncludeContextName(), is(false));
        assertThat(omsJsonLayout.isAppendLineSeparator(), is(true));
        assertThat(omsJsonLayout.isIncludeMDC(), is(false));
    }

    @Test
    public void toJsonMapCase1() {
        OmsJsonLayout omsJsonLayout = new OmsJsonLayout();
        LoggingEventMock eventMock = new LoggingEventMock();

        String start = LocalDateTime.now().atOffset(ZoneOffset.UTC).toString();
        Map result = omsJsonLayout.toJsonMap(eventMock);
        String end = LocalDateTime.now().atOffset(ZoneOffset.UTC).toString();

        assertThat(result.getClass().getSimpleName(), is("LinkedHashMap"));
        String hostname;
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            hostname = "UnknownHost";
        }
        assertThat(result.get("hostName"), is(hostname));
        assertThat(result.get("level"), is(nullValue()));
        assertThat(result.get("traceId"), is(nullValue()));
        assertThat(result.get("requestId"), is(nullValue()));
        assertThat(result.get("service"), is(nullValue()));
        assertThat(result.get("thread"), is(nullValue()));
        assertThat(result.get("logger"), is(nullValue()));
        assertThat(result.get("message"), is(nullValue()));
        assertThat(result.get("rawMessage"), is(nullValue()));
        assertThat(start.compareTo((String) result.get("time")) <= 0, is(true));
        assertThat(end.compareTo((String) result.get("time")) >= 0, is(true));
    }

    @Test
    public void toJsonMapCase2() {
        Level level = Level.INFO;
        String traceId = "test1";
        String requestId = "test2";
        String service = "testService";
        String thread = "test3";
        String logger = "test4";
        String message = "test5";
        String formattedMessage = "test6";
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("test7", "test7");
        Map<String, Object> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("test8", "test8");
        Object[] argumentArray = new Object[] {hashMap, linkedHashMap};

        OmsJsonLayout omsJsonLayout = new OmsJsonLayout();
        omsJsonLayout.setIncludeMessage(true);
        LoggingEventMock eventMock = new LoggingEventMock();
        eventMock.level = level;
        Map<String, String> mdcPropertyMap = new HashMap<>();
        mdcPropertyMap.put(MdcKeyEnum.AMAZON_TRACE_ID.getKey(), traceId);
        mdcPropertyMap.put(MdcKeyEnum.REQUEST_ID.getKey(), requestId);
        mdcPropertyMap.put(MdcKeyEnum.ATTRIBUTE_SERVICE.getKey(), service);
        eventMock.mdcPropertyMap = mdcPropertyMap;
        eventMock.threadName = thread;
        eventMock.loggerName = logger;
        eventMock.message = message;
        eventMock.formattedMessage = formattedMessage;
        eventMock.argumentArray = argumentArray;
        Map result = omsJsonLayout.toJsonMap(eventMock);
        assertThat(result.get("level"), is(String.valueOf(level)));
        assertThat(result.get("traceId"), is(traceId));
        assertThat(result.get("requestId"), is(requestId));
        assertThat(result.get("service"), is(service));
        assertThat(result.get("thread"), is(thread));
        assertThat(result.get("logger"), is(logger));
        assertThat(result.get("message"), is(formattedMessage));
        assertThat(result.get("rawMessage"), is(message));
        assertThat(result.get("test7"), is(nullValue()));
        assertThat(result.get("test8"), is("test8"));
    }

    @Test
    public void toJsonMapCase3() {
        Level level = Level.INFO;
        String traceId = "test1";
        String requestId = "test2";
        String thread = "test3";
        String logger = "test4";
        String message = "test5";
        String formattedMessage = "test6";

        OmsJsonLayout omsJsonLayout = new OmsJsonLayout();
        omsJsonLayout.setIncludeThreadName(false);
        omsJsonLayout.setIncludeLoggerName(false);
        omsJsonLayout.setIncludeFormattedMessage(false);
        LoggingEventMock eventMock = new LoggingEventMock();

        eventMock.level = level;
        Map<String, String> mdcPropertyMap = new HashMap<>();
        mdcPropertyMap.put(MdcKeyEnum.AMAZON_TRACE_ID.getKey(), traceId);
        mdcPropertyMap.put(MdcKeyEnum.REQUEST_ID.getKey(), requestId);
        eventMock.mdcPropertyMap = mdcPropertyMap;
        eventMock.threadName = thread;
        eventMock.loggerName = logger;
        eventMock.message = message;
        eventMock.formattedMessage = formattedMessage;
        Marker notice = MarkerFactory.getMarker(LogLevel.NOTICE.name());
        eventMock.marker = notice;

        Map result = omsJsonLayout.toJsonMap(eventMock);
        assertThat(result.get("level"), is(LogLevel.NOTICE.name()));
        assertThat(result.get("traceId"), is(traceId));
        assertThat(result.get("requestId"), is(requestId));
        assertThat(result.get("service"), is(nullValue()));
        assertThat(result.get("thread"), is(nullValue()));
        assertThat(result.get("logger"), is(nullValue()));
        assertThat(result.get("message"), is(nullValue()));
        assertThat(result.get("rawMessage"), is(nullValue()));
    }


    @Test
    public void toJsonMapCase4() {
        Level level = Level.INFO;
        String traceId = "test1";
        String requestId = "test2";
        String thread = "test3";
        String logger = "test4";
        String message = "test5";
        String formattedMessage = "test6";

        OmsJsonLayout omsJsonLayout = new OmsJsonLayout();
        omsJsonLayout.setIncludeThreadName(false);
        omsJsonLayout.setIncludeLoggerName(false);
        omsJsonLayout.setIncludeFormattedMessage(false);
        LoggingEventMock eventMock = new LoggingEventMock();

        eventMock.level = level;
        Map<String, String> mdcPropertyMap = new HashMap<>();
        mdcPropertyMap.put(MdcKeyEnum.AMAZON_TRACE_ID.getKey(), traceId);
        mdcPropertyMap.put(MdcKeyEnum.REQUEST_ID.getKey(), requestId);
        eventMock.mdcPropertyMap = mdcPropertyMap;
        eventMock.threadName = thread;
        eventMock.loggerName = logger;
        eventMock.message = message;
        eventMock.formattedMessage = formattedMessage;
        Marker notice = MarkerFactory.getMarker(Level.WARN.toString());
        eventMock.marker = notice;

        Map result = omsJsonLayout.toJsonMap(eventMock);
        assertThat(result.get("level"), is("WARN"));
        assertThat(result.get("traceId"), is(traceId));
        assertThat(result.get("requestId"), is(requestId));
        assertThat(result.get("service"), is(nullValue()));
        assertThat(result.get("thread"), is(nullValue()));
        assertThat(result.get("logger"), is(nullValue()));
        assertThat(result.get("message"), is(nullValue()));
        assertThat(result.get("rawMessage"), is(nullValue()));
    }


    @Test
    public void toJsonMapCase5() {
        Level level = Level.INFO;
        String traceId = "test1";
        String requestId = "test2";
        String thread = "test3";
        String logger = "test4";
        String message = "test5";
        String formattedMessage = "test6";

        OmsJsonLayout omsJsonLayout = new OmsJsonLayout();
        omsJsonLayout.setIncludeThreadName(false);
        omsJsonLayout.setIncludeLoggerName(false);
        omsJsonLayout.setIncludeFormattedMessage(false);
        LoggingEventMock eventMock = new LoggingEventMock();

        eventMock.level = level;
        Map<String, String> mdcPropertyMap = new HashMap<>();
        mdcPropertyMap.put(MdcKeyEnum.AMAZON_TRACE_ID.getKey(), traceId);
        mdcPropertyMap.put(MdcKeyEnum.REQUEST_ID.getKey(), requestId);
        eventMock.mdcPropertyMap = mdcPropertyMap;
        eventMock.threadName = thread;
        eventMock.loggerName = logger;
        eventMock.message = message;
        eventMock.formattedMessage = formattedMessage;
        Marker notice = MarkerFactory.getMarker("TEST");
        eventMock.marker = notice;

        Map result = omsJsonLayout.toJsonMap(eventMock);
        assertThat(result.get("level"), is("INFO"));
        assertThat(result.get("traceId"), is(traceId));
        assertThat(result.get("requestId"), is(requestId));
        assertThat(result.get("service"), is(nullValue()));
        assertThat(result.get("thread"), is(nullValue()));
        assertThat(result.get("logger"), is(nullValue()));
        assertThat(result.get("message"), is(nullValue()));
        assertThat(result.get("rawMessage"), is(nullValue()));
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
