/**
 * @(#)OmsFluentAppenderWithJsonLayout.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.common.api.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.pattern.TargetLengthBasedClassNameAbbreviator;
import ch.qos.logback.classic.pattern.ThrowableProxyConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastretailing.dcp.common.api.jvm.OmsJvmParameters;
import com.fastretailing.dcp.common.constants.LogLevel;
import lombok.Data;
import lombok.Getter;
import org.apache.commons.lang3.EnumUtils;
import org.fluentd.logger.FluentLogger;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * OmsFluentAppenderWithJsonLayout.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
public class OmsFluentAppenderWithJsonLayout extends UnsynchronizedAppenderBase<ILoggingEvent> {

    /**
     * the attribute name of hostname in log.
     */
    private static final String ATTRIBUTE_HOSTNAME = "hostName";

    /**
     * the attribute name of level in log.
     */
    public static final String ATTRIBUTE_LEVEL = "level";

    /**
     * the attribute name of time in log.
     */
    private static final String ATTRIBUTE_TIME = "time";

    /**
     * the attribute name of trace id in log.
     */
    private static final String ATTRIBUTE_TRACE_ID = "traceId";

    /**
     * the attribute name of request id in log.
     */
    private static final String ATTRIBUTE_REQUEST_ID = "requestId";

    /**
     * the attribute name of service in log.
     */
    private static final String ATTRIBUTE_SERVICE = "service";

    /**
     * the attribute name of message in log.
     */
    private static final String ATTRIBUTE_MESSAGE = "message";

    /**
     * the attribute name of raw message in log.
     */
    private static final String ATTRIBUTE_RAW_MESSAGE = "rawMessage";

    /**
     * the attribute name of thread in log.
     */
    private static final String ATTRIBUTE_THREAD = "thread";

    /**
     * the attribute name of logger in log.
     */
    private static final String ATTRIBUTE_LOGGER = "logger";

    /**
     * the attribute name of exception in log.
     */
    private static final String EXCEPTION_ATTR_NAME = "exception";

    /**
     * Period.
     */
    private static final String TAG_SEPARATOR = ".";

    /**
     * Task-Seq (X-Edf-Task-Seq).
     */
    private static final String TASK_SEQ = "Task-Seq";

    /**
     * endpoint.
     */
    private static final String ENDPOINT = "endpoint";

    /**
     * member_id (X-FR-front-memberid).
     */
    private static final String MEMBER_ID = "member_id";

    /**
     * user_id (X-FR-admin-userid).
     */
    private static final String USER_ID = "user_id";

    /**
     * Tag
     */
    private String tag;

    /**
     * Label
     */
    private String label;

    /**
     * Host address
     */
    private String remoteHost;

    /**
     * Port number
     */
    private int port;

    /**
     * Fluent Logger
     */
    private FluentLogger fluentLogger;

    /**
     * Flag to include thread name in log.
     */
    private boolean includeThreadName;

    /**
     * Flag to include logger name in log.
     */
    private boolean includeLoggerName;

    /**
     * Flag to include formatted message in log.
     */
    private boolean includeFormattedMessage;

    /**
     * Flag to include message in log.
     */
    private boolean includeMessage;

    /**
     * Flag to include exception in log.
     */
    private boolean includeException;

    /**
     * the attribute name of raw message in log.
     */
    private int loggerNameLength = 40;

    /**
     * Add a stack trace in case the event contains a Throwable.
     */
    private final ThrowableProxyConverter throwableProxyConverter;

    /**
     * The ObjectMapper instance.
     */
    private final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

    /**
     * append log item map
     */
    @Getter
    private final ThreadLocal<Map<String, Object>> appendItemLogMap = new ThreadLocal<>();

    /**
     * Constructor.
     */
    public OmsFluentAppenderWithJsonLayout() {
        super();
        this.includeThreadName = true;
        this.includeLoggerName = true;
        this.includeFormattedMessage = true;
        this.includeMessage = true;
        this.includeException = true;
        this.throwableProxyConverter = new ThrowableProxyConverter();
    }

    @Override
    public void start() {
        super.start();
        this.throwableProxyConverter.start();
        this.fluentLogger = FluentLogger.getLogger(label != null ? tag : null, remoteHost, port);
    }

    @Override
    public void stop() {
        super.stop();
        this.throwableProxyConverter.stop();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    protected void append(ILoggingEvent event) {

        Map<String, Object> map = new LinkedHashMap<>();

        // hostname
        try {
            map.put(ATTRIBUTE_HOSTNAME, InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            map.put(ATTRIBUTE_HOSTNAME, "UnknownHost");
        }

        // level
        Level level = event.getLevel();
        String logLevel = null;
        if (level != null) {
            logLevel = String.valueOf(level);
        }
        // if marker is exists, level overwrite
        if (event.getMarker() != null
                && EnumUtils.isValidEnum(LogLevel.class, event.getMarker().getName())) {
            logLevel = event.getMarker().getName();
        }

        if (Objects.nonNull(logLevel)) {
            map.put(ATTRIBUTE_LEVEL, logLevel);
        }

        // time
        map.put(ATTRIBUTE_TIME, LocalDateTime.now().atOffset(ZoneOffset.UTC).toString());

        // trace id & request id & service
        Map<String, String> mdc = event.getMDCPropertyMap();
        if (mdc != null && !mdc.isEmpty()) {
            map.put(ATTRIBUTE_TRACE_ID, mdc.get(MdcKeyEnum.AMAZON_TRACE_ID.getKey()));
            map.put(ATTRIBUTE_REQUEST_ID, mdc.get(MdcKeyEnum.REQUEST_ID.getKey()));
            map.put(ATTRIBUTE_SERVICE, mdc.get(MdcKeyEnum.ATTRIBUTE_SERVICE.getKey()));
        }

        // thread
        if (this.includeThreadName) {
            String threadName = event.getThreadName();
            if (threadName != null) {
                map.put(ATTRIBUTE_THREAD, threadName);
            }
        }

        // logger
        if (this.includeLoggerName) {
            String loggerName = event.getLoggerName();
            if (loggerName != null) {
                TargetLengthBasedClassNameAbbreviator targetLengthBasedClassNameAbbreviator = new TargetLengthBasedClassNameAbbreviator(
                        this.loggerNameLength);
                map.put(ATTRIBUTE_LOGGER,
                        targetLengthBasedClassNameAbbreviator.abbreviate(loggerName));
            }
        }

        // message
        if (this.includeFormattedMessage) {
            String msg = event.getFormattedMessage();
            if (msg != null && !StringUtils.isEmpty(msg)) {
                map.put(ATTRIBUTE_MESSAGE, msg);
            }
        }
        if (this.includeMessage) {
            String msg = event.getMessage();
            if (msg != null && !StringUtils.isEmpty(msg)) {
                map.put(ATTRIBUTE_RAW_MESSAGE, msg);
            }
        }

        // stack trace
        if (this.includeException) {
            IThrowableProxy throwableProxy = event.getThrowableProxy();
            if (throwableProxy != null) {
                String ex = throwableProxyConverter.convert(event);
                if (!StringUtils.isEmpty(ex)) {
                    map.put(EXCEPTION_ATTR_NAME, ex);
                }
            }
        }

        if (mdc != null && !mdc.isEmpty()) {
            // Task-Seq (X-Edf-Task-Seq)
            if (mdc.containsKey(MdcKeyEnum.TASK_SEQ.getKey())) {
                map.put(TASK_SEQ, mdc.get(MdcKeyEnum.TASK_SEQ.getKey()));
            }

            // endpoint
            if (mdc.containsKey(MdcKeyEnum.ENDPOINT.getKey())) {
                map.put(ENDPOINT, mdc.get(MdcKeyEnum.ENDPOINT.getKey()));
            }

            // member_id (X-FR-front-memberid)
            if (!OmsJvmParameters.isAdminApi() && mdc.containsKey(MdcKeyEnum.MEMBER_ID.getKey())) {
                String memberId = mdc.get(MdcKeyEnum.MEMBER_ID.getKey());
                if (!StringUtils.isEmpty(memberId)) {
                    map.put(MEMBER_ID, memberId);
                }
            }

            // user_id (X-FR-admin-userid)
            if (OmsJvmParameters.isAdminApi() && mdc.containsKey(MdcKeyEnum.USER_ID.getKey())) {
                String userId = mdc.get(MdcKeyEnum.USER_ID.getKey());
                if (!StringUtils.isEmpty(userId)) {
                    map.put(USER_ID, userId);
                }
            }
        }

        // argument info
        Object[] argumentArray = event.getArgumentArray();
        if (argumentArray != null && argumentArray.length > 0) {
            Arrays.stream(argumentArray).forEach(argument -> {
                if (argument instanceof LinkedHashMap) {
                    ((Map) argument)
                            .forEach((key, value) -> {
                                try {
                                    map.put(key.toString(), mapper.convertValue(value, Map.class));
                                } catch (IllegalArgumentException e) {
                                    map.put(key.toString(), value);
                                }
                            });
                }
            });
        }

        // init appendItemLogMap
        if (Objects.isNull(appendItemLogMap.get())) {
            appendItemLogMap.set(new LinkedHashMap<>());
        }

        // append item to appendItemLogMap, The actual call here is the subclass method (appendItemLog)
        appendItemLog();

        // output log && clear appendItemLogMap
        if (!appendItemLogMap.get().isEmpty()) {
            map.putAll(appendItemLogMap.get());
            appendItemLogMap.get().clear();
        }

        if (label == null) {
            fluentLogger.log(tag + TAG_SEPARATOR + logLevel.toLowerCase(), map,
                    event.getTimeStamp() / 1000);
        } else {
            fluentLogger.log(label, map, event.getTimeStamp() / 1000);
        }

    }

    /**
     * <p>append item to output log</p>
     * <pre>
     * The current method is empty.
     * The appendItemLog method of the subclass is expected to be called.
     *
     * An example of override:
     *   appendItemLogMap.get().put("key", "value");
     * </pre>
     */
    public void appendItemLog() {
        // append item to [map : appendItemLogMap.get()]
    }
}
