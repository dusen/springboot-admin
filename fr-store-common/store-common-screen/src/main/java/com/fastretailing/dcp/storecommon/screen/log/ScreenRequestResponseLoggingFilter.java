/**
 * @(#)ScreenRequestResponseLoggingFilter.java
 *
 *                                             Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fastretailing.dcp.common.util.CommonUtility;
import com.fastretailing.dcp.common.util.JsonUtility;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * ScreenRequestResponseLoggingFilter<br>
 *     All requests will walk through this filter.<br>
 *     Request and response information will be logged.
 *
 * </pre>
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
@Slf4j
public class ScreenRequestResponseLoggingFilter extends CommonsRequestLoggingFilter {

    /**
     * Key of token in request header.
     */
    private static final String KEY_TOKEN = "Postman-Token";

    /**
     * The attribute name of request id in log.
     */
    public static final String LOG_ATTRIBUTE_REQUEST_ID = "requestId";

    /**
     * The attribute name of request in log.
     */
    public static final String LOG_ATTRIBUTE_REQ = "req";

    /**
     * The attribute name of response in log.
     */
    public static final String LOG_ATTRIBUTE_RES = "res";

    /**
     * Message.
     */
    @Autowired
    private MessageSource messageSource;
    /**
     * Message id (start).
     */
    private static final String MESSAGE_REQUEST_START = "i.common.log.request.start";

    /**
     * Message id (end).
     */
    private static final String MESSAGE_REQUEST_END = "i.common.log.request.end";

    /**
     * Separator of trace id.
     */
    private static final String TRACE_ID_SEPARATOR = "-";

    /**
     * CommonUtility.
     */
    @Autowired
    private CommonUtility commonUtility;

    /**
     * The name of api service.
     */
    @Value("${platform.name.full}")
    private String apiServiceName;

    /**
     * The version of aws.
     */
    @Value("${version.aws}")
    private String versionAws;

    /**
     * Do request's filter.<br>
     * This method was used to log response.And request will be logged in super class.
     *
     * @param request Request.
     * @param response Response.
     * @param filterChain Filter chain.
     * @throws ServletException Servlet exception.
     * @throws IOException IO exception.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String hostName = InetAddress.getLocalHost().getHostName();
        MDC.put(MdcKey.HOST_NAME.getKey(), hostName);
        String[] traceIds = getTraceIdFromRequest(request);
        MDC.put(MdcKey.AMAZON_TRACE_ID.getKey(), traceIds[1]);
        MDC.put(MdcKey.REQUEST_ID.getKey(), StringUtils.join(hostName, "/", traceIds[0]));
        MDC.put(MdcKey.ATTRIBUTE_SERVICE.getKey(), apiServiceName);
        boolean isFirstRequest = !isAsyncDispatch(request);
        HttpServletRequest requestToUse = request;
        if (isIncludePayload() && isFirstRequest
                && !(request instanceof ContentCachingRequestWrapper)) {
            requestToUse = new ContentCachingRequestWrapper(request);
        }
        HttpServletResponse responseToUse = response;
        if (isIncludePayload() && !isAsyncDispatch(request)
                && !(response instanceof ContentCachingResponseWrapper)) {
            responseToUse = new ContentCachingResponseWrapper(response);
        }
        // Response message.
        // Start instant.
        Instant startInstant = commonUtility.getOperationAt().toInstant(ZoneOffset.UTC);
        ResponseLogInfo responseLogInfo = null;
        try {
            super.doFilterInternal(requestToUse, responseToUse, filterChain);
            // Calculate the execute times.
            Instant endInstant = commonUtility.getOperationAt().toInstant(ZoneOffset.UTC);
            long responseTime = ChronoUnit.MILLIS.between(startInstant, endInstant);
            responseLogInfo = getResponseMessage(responseToUse, responseTime);
        } finally {
            if (!isAsyncStarted(request)) {
                Map<String, Object> logMap = new LinkedHashMap<>();
                // Set response body context blank when log level is info.
                logMap.put(LOG_ATTRIBUTE_RES, responseLogInfo);
                log.info("", logMap);
                log.debug("body:", responseLogInfo.getResponse());
            }
        }
    }

    /**
     * Writes a log message before the request is processed.
     *
     * @param request Request.
     * @param message Message content.
     */
    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        outputRequestMessage(message, MESSAGE_REQUEST_START);
    }

    /**
     * Writes a log message after the request is processed.
     *
     * @param request Request.
     * @param message Message content.
     */
    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        outputRequestMessage(message, MESSAGE_REQUEST_END);
    }

    /**
     * Output the message of request.
     * 
     * @param message Message.
     * @param messageCode The message code.
     */
    private void outputRequestMessage(String message, String messageCode) {
        try {
            RequestLogInfo requestLogInfo = JsonUtility.toObject(message, RequestLogInfo.class);
            Map<String, Object> logMap = new LinkedHashMap<>();
            logMap.put(LOG_ATTRIBUTE_REQUEST_ID, requestLogInfo.getRequestId());
            logMap.put(LOG_ATTRIBUTE_REQ, requestLogInfo.getRequestInfo());
            log.info(messageSource.getMessage(messageCode, new Object[] {}, Locale.getDefault()),
                    logMap);
        } catch (IOException e) {
            log.error("error in outputRequestMessage.", e);
        }
    }

    /**
     * To extract information from response.
     * 
     * @param response Response.
     * @param responseTime Response time.
     * @return The response's information for logger.
     * @throws IOException IO exception.
     */
    private ResponseLogInfo getResponseMessage(HttpServletResponse response, long responseTime)
            throws IOException {
        ResponseLogInfo responseLogInfo = new ResponseLogInfo();

        responseLogInfo.setResponseTime(responseTime);
        responseLogInfo
                .setTime(commonUtility.getOperationAt().toInstant(ZoneOffset.UTC).toString());
        responseLogInfo.setStatusCode(response.getStatus());

        String contentType = response.getContentType();
        // Payload output flag.
        boolean isOutputPayLoad = true;
        // Disable the payload output when response is a picture or javascript or html or css.
        if (contentType != null && (contentType.contains("image")
                || contentType.equalsIgnoreCase("application/javascript")
                || contentType.equalsIgnoreCase("text/html")
                || contentType.equalsIgnoreCase("text/css"))) {
            isOutputPayLoad = false;
        }

        String payload = null;
        if (isIncludePayload()) {
            ContentCachingResponseWrapper wrapper =
                    WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
            if (wrapper != null) {
                byte[] buf = wrapper.getContentAsByteArray();
                // Payload is exists and payload output is enable.
                if (buf.length > 0 && isOutputPayLoad) {
                    try {
                        payload = new String(buf, wrapper.getCharacterEncoding());
                    } catch (UnsupportedEncodingException ex) {
                        payload = "\"[unknown]\"";
                    }
                }
                wrapper.copyBodyToResponse();
            }
        }
        if (payload != null) {
            responseLogInfo.setResponse(payload);
        }
        return responseLogInfo;
    }

    /**
     * Create log message.
     * 
     * @param request Request.
     * @param prefix Prefix.
     * @param suffix Suffix.
     * @return Message.
     */
    @Override
    protected final String createMessage(HttpServletRequest request, String prefix, String suffix) {
        RequestLogInfo requestLogInfo = new RequestLogInfo();

        // Request id.
        requestLogInfo.setRequestId(MDC.get(MdcKey.REQUEST_ID.getKey()));

        RequestHeaderObject requestInfo = new RequestHeaderObject();

        requestLogInfo.setRequestInfo(requestInfo);
        // Method.
        requestInfo.setMethod(request.getMethod());
        // Header.
        if (this.isIncludeHeaders()) {
            HttpHeaders httpHeaders = new ServletServerHttpRequest(request).getHeaders();
            // Remove token info from header.
            if (httpHeaders.containsKey(KEY_TOKEN)) {
                httpHeaders.remove(KEY_TOKEN);
            }
            // Remove trace id from header.
            if (httpHeaders.containsKey(MdcKey.AMAZON_TRACE_ID.getKey())) {
                httpHeaders.remove(MdcKey.AMAZON_TRACE_ID.getKey());
            }
            // Remove hostname info from header.
            if (httpHeaders.containsKey(HttpHeaders.HOST)) {
                httpHeaders.remove(HttpHeaders.HOST);
            }
            requestInfo.setHttpHeaders(httpHeaders);
        }
        // Token.
        requestInfo.setToken(request.getHeader(KEY_TOKEN));

        // Query.
        if (this.isIncludeQueryString()) {
            requestInfo.setQuery(request.getQueryString());
        }
        // Trace id.
        requestInfo.setTraceId(MDC.get(MdcKey.AMAZON_TRACE_ID.getKey()));
        String result = null;
        try {
            result = JsonUtility.toJson(requestLogInfo);
        } catch (JsonProcessingException e) {
            log.error("Error in createMessage.", e);
        }
        return result;
    }

    /**
     * Get trace id from request.
     * 
     * @param request Request.
     * @return The trace id array [0:Self; 1:Root].
     */
    private String[] getTraceIdFromRequest(HttpServletRequest request) {
        String requestTraceId = request.getHeader(MdcKey.AMAZON_TRACE_ID.getKey());
        String[] traceIds = new String[] {null, null};

        if (StringUtils.isNotEmpty(requestTraceId)) {
            Arrays.stream(requestTraceId.split(";")).forEach(traceInfo -> {
                if (traceInfo.contains("Root=")) {
                    traceIds[1] = getValueFromKeyEqualValueString(traceInfo);
                } else if (traceInfo.contains("Self=")) {
                    traceIds[0] = getValueFromKeyEqualValueString(traceInfo);
                }
            });
        }
        if (StringUtils.isEmpty(traceIds[1])) {
            // When root trace id is not exists, generate the trace id.
            traceIds[1] = StringUtils.join(versionAws, TRACE_ID_SEPARATOR,
                    commonUtility.getOperationAt().toEpochSecond(ZoneOffset.UTC),
                    TRACE_ID_SEPARATOR, UUID.randomUUID().toString());
        }
        if (StringUtils.isEmpty(traceIds[0])) {
            // When self trace is not exists , set the value of root trace.
            traceIds[0] = traceIds[1];
        }
        return traceIds;
    }

    /**
     * Get the value part from the string of format "key=value".
     * 
     * @param keyAndValueString The string of format "key=value".
     * @return The value part.
     */
    private String getValueFromKeyEqualValueString(String keyAndValueString) {
        return keyAndValueString.substring(keyAndValueString.indexOf("=") + 1);
    }

    /**
     * Check the log output enable flag.
     * 
     * @param request Request.
     * @return check result.
     */
    @Override
    protected boolean shouldLog(HttpServletRequest request) {
        return logger.isInfoEnabled();
    }
}
