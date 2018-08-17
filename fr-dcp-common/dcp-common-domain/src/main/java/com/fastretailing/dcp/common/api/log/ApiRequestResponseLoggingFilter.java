/**
 * @(#)APIRequestResponseLoggingFilter.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.common.api.log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fastretailing.dcp.common.threadlocal.UserIdAndMemberIdHolder;
import com.fastretailing.dcp.common.util.CommonUtility;
import com.fastretailing.dcp.common.util.JsonUtility;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * <pre>
 * APIRequestResponseLoggingFilter<br>
 *     All requests will walk through this filter.<br>
 *     Request and response information will be logged.
 *
 * </pre>
 * @author Fast Retailing
 * @version $Revision$
 */
@Slf4j
@ConfigurationProperties(prefix = "oms.logging")
public class ApiRequestResponseLoggingFilter extends CommonsRequestLoggingFilter {

    /**
     * key of token in request header.
     */
    private static final String KEY_TOKEN = "Postman-Token";

    /**
     * the attribute name of request id in log.
     */
    private static final String LOG_ATTRIBUTE_REQUEST_ID = "requestId";

    /**
     * the attribute name of response time in log.
     */
    private static final String ATTRIBUTE_RESPONSE_TIME= "responseTime";

    /**
     * the attribute name of request in log.
     */
    private static final String LOG_ATTRIBUTE_REQ = "req";

    /**
     * the attribute name of response in log.
     */
    private static final String LOG_ATTRIBUTE_RES = "res";

    /**
     * message source.
     */
    @Autowired
    private MessageSource messageSource;
    /**
     * message id (start).
     */
    private static final String MSG_REQUEST_START = "i.common.log.request.start";

    /**
     * message id (end).
     */
    private static final String MSG_REQUEST_END = "i.common.log.request.end";

    /**
     * CommonUtility.
     */
    @Autowired
    private CommonUtility commonUtility;

    /**
     * the name of api service.
     */
    @Value("${platform.name.full}")
    private String apiServiceName;

    /**
     * the version of aws.
     */
    @Value("${version.aws}")
    private String versionAws;

    /**
     * masking uri map.
     */
    @Setter
    @Getter
    private Map<String, String> maskingUris;

    /**
     * set logging masking items information.
     */
    @Setter
    @Getter
    private Map<String, String> maskingItems;

    /**
     * mask mark is *.
     */
    private static final String MASK_MARK = "*";

    /**
     * Do request's filter.<br>
     *     This method was used to log response.And request will be logged in super class.
     *
     * @param request request
     * @param response response
     * @param filterChain filter chain
     * @throws ServletException ServletException
     * @throws IOException IOException
     */
    @Override
    protected final void doFilterInternal(HttpServletRequest request,
                                          HttpServletResponse response,
                                          FilterChain filterChain)
            throws ServletException, IOException {
        String hostName = InetAddress.getLocalHost().getHostName();
        MDC.put(MdcKeyEnum.HOST_NAME.getKey(), hostName);
        String[] traceIds = getTraceIdFromRequest(request);
        MDC.put(MdcKeyEnum.AMAZON_TRACE_ID.getKey(), traceIds[1]);
        MDC.put(
                MdcKeyEnum.REQUEST_ID.getKey(),
                StringUtils.join(hostName, "/", traceIds[0])
        );

        // service
        if (StringUtils.isNotEmpty(apiServiceName)) {
            MDC.put(MdcKeyEnum.ATTRIBUTE_SERVICE.getKey(), apiServiceName);
        }

        // member_id (X-FR-front-memberid)
        MDC.put(MdcKeyEnum.MEMBER_ID.getKey(),
                request.getHeader(UserIdAndMemberIdHolder.HEADER_KEY_X_FR_FRONT_MEMBER_ID));
        // user_id (X-FR-admin-userid)
        MDC.put(MdcKeyEnum.USER_ID.getKey(),
                request.getHeader(UserIdAndMemberIdHolder.HEADER_KEY_X_FR_ADMIN_USER_ID));

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
        // response message
        ResponseLogInfo responseLogInfo = null;
        boolean shouldLog = shouldLog(requestToUse);
        BodyReaderHttpServletRequestWrapper bodyReaderHttpServletRequestWrapper =
                new BodyReaderHttpServletRequestWrapper(requestToUse);

        Instant startInstant = commonUtility.getOperationAt().toInstant(ZoneOffset.UTC);

        try {
            if (shouldLog && isFirstRequest) {
                beforeRequest(bodyReaderHttpServletRequestWrapper,
                        createMessage(bodyReaderHttpServletRequestWrapper, DEFAULT_BEFORE_MESSAGE_PREFIX,
                        DEFAULT_BEFORE_MESSAGE_SUFFIX));
            }

            filterChain.doFilter(bodyReaderHttpServletRequestWrapper, responseToUse);

            // get response information
            responseLogInfo = getResponseMessage(request, responseToUse);
        } finally {
            if (shouldLog && !isAsyncStarted(bodyReaderHttpServletRequestWrapper)) {
                afterRequest(bodyReaderHttpServletRequestWrapper,
                        createMessage(bodyReaderHttpServletRequestWrapper, DEFAULT_AFTER_MESSAGE_PREFIX,
                        DEFAULT_AFTER_MESSAGE_SUFFIX));
            }
            if (!isAsyncStarted(request)) {
                Map<String, Object> logMap = new LinkedHashMap<>();
                // response time
                Instant endInstant = commonUtility.getOperationAt().toInstant(ZoneOffset.UTC);
                logMap.put(ATTRIBUTE_RESPONSE_TIME,  ChronoUnit.MILLIS.between(startInstant, endInstant));
                logMap.put(LOG_ATTRIBUTE_RES, responseLogInfo);
                log.info("", logMap);
            }
        }
    }

    /**
     * Writes a log message before the request is processed.
     *
     * @param request request
     * @param message message content
     */
    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        outputRequestMessage(message, MSG_REQUEST_START);
    }

    /**
     * Writes a log message after the request is processed.
     *
     * @param request request
     * @param message message content
     */
    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        outputRequestMessage(message, MSG_REQUEST_END);
    }

    /**
     * Output the message of request.
     * @param message message
     * @param messageCode the message code
     */
    private void outputRequestMessage(String message,
                                      String messageCode) {
        try {

            RequestLogInfo requestLogInfo = JsonUtility.toObject(message,
                    RequestLogInfo.class);
            Map<String, Object> logMap = new LinkedHashMap<>();
            logMap.put(LOG_ATTRIBUTE_REQUEST_ID, requestLogInfo.getRequestId());
            logMap.put(LOG_ATTRIBUTE_REQ, requestLogInfo.getReq());
            log.info(messageSource.getMessage(
                    messageCode, new Object[]{}, Locale.getDefault()), logMap);
        } catch (IOException e) {
            // shouldn't really happen
        }
    }

    /**
     * To extract information from response.
     * @param response response
     * @return the response's information for logger.
     * @throws IOException IOException
     */
    private ResponseLogInfo getResponseMessage(HttpServletRequest request,
                                               HttpServletResponse response) throws IOException {
        ResponseLogInfo responseLogInfo = new ResponseLogInfo();

        responseLogInfo.setStatusCode(response.getStatus());

        // response header
        HttpHeaders headers = new HttpHeaders();
        List<String> mskItems = getMaskItems(request);
        for (String headerName :
                response.getHeaderNames()) {
            if (mskItems.contains(headerName)) {
                headers.add(headerName, MASK_MARK);
            } else {
                headers.put(headerName, new ArrayList<>(response.getHeaders(headerName)));
            }
        }
        try {
            responseLogInfo.setHeaders(JsonUtility.toJson(headers));
        } catch (JsonProcessingException e) {
            log.error("An error occurred.", e);
        }

        String contentType = response.getContentType();
        // payload output flag
        boolean isOutputPayLoad = true;
        // disable the  payload output when response is a picture or javascript or html or css
        if (contentType != null
                && (contentType.contains("image")
                    || contentType.equalsIgnoreCase("application/javascript")
                    || contentType.equalsIgnoreCase("text/html")
                    || contentType.equalsIgnoreCase("text/css"))) {
            isOutputPayLoad = false;
        }
        String payload = null;
        // UnsupportedEncodingException occurs flag
        boolean hasError = false;
        if (isIncludePayload()) {
            ContentCachingResponseWrapper wrapper =
                    WebUtils.getNativeResponse(
                            response, ContentCachingResponseWrapper.class);
            if (wrapper != null) {
                byte[] buf = wrapper.getContentAsByteArray();
                // payload is exists and payload output is enable
                if (buf.length > 0 && isOutputPayLoad) {
                    try {
                        payload = new String(buf, wrapper.getCharacterEncoding());
                    } catch (UnsupportedEncodingException ex) {
                        hasError = true;
                        payload = "\"[unknown]\"";
                    }
                }
                wrapper.copyBodyToResponse();
            }
        }
        if (payload != null) {
            if (hasError) {
                responseLogInfo.setBody(payload);
            } else {
                try {
                    responseLogInfo.setBody(JsonUtility.toJson(maskLog(JsonUtility.toObject(payload, Object.class),
                            getMaskItems(request))));
                } catch (IOException ex) {
                    // Other than unknown, there will be no exceptions due to system limitations
                    responseLogInfo.setBody(payload);
                }
            }
        }
        return responseLogInfo;
    }

    /**
     *  Create log message.
     * @param request request
     * @param prefix prefix
     * @param suffix  suffix
     * @return message
     */
    @Override
    protected final String createMessage(HttpServletRequest request,
                                         String prefix, String suffix) {
        RequestLogInfo requestLogInfo = new RequestLogInfo();

        // request id
        requestLogInfo.setRequestId(MDC.get(MdcKeyEnum.REQUEST_ID.getKey()));

        RequestInfo requestInfo = requestLogInfo.getReq();
        requestLogInfo.setReq(requestInfo);
        // method
        requestInfo.setMethod(request.getMethod());
        // body
        if (request instanceof BodyReaderHttpServletRequestWrapper) {
            BodyReaderHttpServletRequestWrapper bodyReaderHttpServletRequestWrapper =
                    (BodyReaderHttpServletRequestWrapper) request;
            try {
                requestInfo.setBody(JsonUtility.toJson(maskLog(bodyReaderHttpServletRequestWrapper.getBodyObject(),
                        getMaskItems(request))));
            } catch (JsonProcessingException e) {
                log.error("An error occurred.", e);
            }
        }
        // url
        requestInfo.setUrl(request.getRequestURL().toString());
        // originalUrl
        requestInfo.setOriginalUrl(request.getRequestURI());
        // httpVersion
        requestInfo.setHttpVersion(request.getProtocol());
        // header
        if (this.isIncludeHeaders()) {
            HttpHeaders httpHeaders =
                    new ServletServerHttpRequest(request).getHeaders();
            // remove token info from header
            if (httpHeaders.containsKey(KEY_TOKEN)) {
                httpHeaders.remove(KEY_TOKEN);
            }
            // remove trace id from header
            if (httpHeaders.containsKey(MdcKeyEnum.AMAZON_TRACE_ID.getKey())) {
                httpHeaders.remove(MdcKeyEnum.AMAZON_TRACE_ID.getKey());
            }
            // remove hostname info from header
            if (httpHeaders.containsKey(HttpHeaders.HOST)) {
                httpHeaders.remove(HttpHeaders.HOST);
            }

            List<String> mskItems = getMaskItems(request);
            List<String> headerKeyList = new ArrayList<>(httpHeaders.keySet());
            if (CollectionUtils.isNotEmpty(headerKeyList)) {
                headerKeyList.forEach(
                        headerKey -> {
                            if (mskItems.contains(headerKey)) {
                                httpHeaders.remove(headerKey);
                                httpHeaders.add(headerKey, MASK_MARK);
                            }
                        }
                );
            }
            try {
                requestInfo.setHeaders(JsonUtility.toJson(httpHeaders));
            } catch (JsonProcessingException e) {
                log.error("An error occurred.", e);
            }
        }

        // query
        if (this.isIncludeQueryString()) {
            requestInfo.setQuery(request.getQueryString());
        }
        // trace id
        requestInfo.setTraceId(MDC.get(MdcKeyEnum.AMAZON_TRACE_ID.getKey()));

        String result = null;
        try {
            result = JsonUtility.toJson(requestLogInfo);
        } catch (JsonProcessingException e) {
            log.error("An error occurred.", e);
        }
        return result;
    }

    /**
     * get trace id from request.
     * @param request request
     * @return the trace id array [0:Self; 1:Root]
     */
    private String[] getTraceIdFromRequest(HttpServletRequest request) {
        String requestTraceId = request.getHeader(MdcKeyEnum.AMAZON_TRACE_ID.getKey());
        String[] traceIds = new String[]{null, null};

        if (StringUtils.isNotEmpty(requestTraceId)) {
            Arrays.stream(requestTraceId.split(";"))
                    .forEach(traceInfo -> {
                        if (traceInfo.contains("Root=")) {
                            traceIds[1] = getValueFromKeyEqualValueString(traceInfo);
                        } else if (traceInfo.contains("Self=")) {
                            traceIds[0] = getValueFromKeyEqualValueString(traceInfo);
                        }
                    });
        }
        if (StringUtils.isEmpty(traceIds[1])) {
            // when root trace id is not exists, generate the trace id
            traceIds[1] = StringUtils.join(versionAws,
                    "-",
                    commonUtility.getOperationAt().toEpochSecond(ZoneOffset.UTC),
                    "-",
                    UUID.randomUUID().toString());
        }
        if (StringUtils.isEmpty(traceIds[0])) {
            // when self trace is not exists , set the value of root trace
            traceIds[0] = traceIds[1];
        }
        return traceIds;
    }

    /**
     * get the value from the string of format key=value.
     * @param keyAndValueString the string of format key=value
     * @return value
     */
    private String getValueFromKeyEqualValueString(String keyAndValueString) {
        return keyAndValueString
                .substring(keyAndValueString.indexOf("=") + 1);
    }

    /**
     * check the log output enable flag.
     * @param request request
     * @return check result
     */
    @Override
    protected boolean shouldLog(HttpServletRequest request) {
        return logger.isInfoEnabled();
    }

    /**
     * get mask item's list
     * @param request HttpServletRequest
     * @return mskItems
     */
    private List<String> getMaskItems(HttpServletRequest request) {
        String maskItems = getMaskItemsKey(request);

        if (Objects.nonNull(maskItems)) {
            return Arrays.asList(
                    maskItems.replaceAll("(^[(]+)|([)]+$)", StringUtils.EMPTY).split("[|]+"));
        }

        return new ArrayList<>();
    }

    /**
     * <p>
     * mask log according setting.
     * For example:If you mask the name field of
     * {age : 28, name : "jack"}
     * you will get the result below.
     * {age : 28, name : "*"}
     * </p>
     *
     * @param maskTargetObject mask input for Object
     * @param maskItems     mask target key
     * @return mask after map
     */
    private Object maskLog(Object maskTargetObject, List<String> maskItems) {

        // if setting is not enough, pass without masking.
        if (CollectionUtils.isEmpty(maskItems)) {
            return maskTargetObject;
        }

        if (Objects.isNull(maskTargetObject)) {
            return null;
        }

        if (maskTargetObject instanceof Map) {
            Map<String, Object> maskTargetMap = (Map)maskTargetObject;

            // replace maskItem to '*'
            maskItems.forEach(
                    maskItem -> {
                        if (maskTargetMap.keySet().contains(maskItem)) {
                            if (Objects.nonNull(maskTargetMap.get(maskItem))) {
                                maskTargetMap.put(maskItem, MASK_MARK);
                            }
                        }
                    }

            );

            // recursively json all nodes
            maskTargetMap.keySet().forEach(
                    markContentKey -> {
                        Object markContentVal = maskTargetMap.get(markContentKey);
                        if (Objects.nonNull(markContentVal)) {
                            if (markContentVal instanceof Map) {
                                maskLog((Map<String, Object>) markContentVal, maskItems);
                            } else if (markContentVal instanceof List) {
                                analyzeList((List<?>) markContentVal, maskItems);
                            }
                        }
                    }
            );

        } else if (maskTargetObject instanceof List) {
            analyzeList((List<?>) maskTargetObject, maskItems);
        }

        return maskTargetObject;

    }

    /**
     * <p>
     * Analyzing the composition of the list
     * If the list object is a map, call the maskLog function
     * If the list object is a list, recursively call the analyzeList function
     * </p>
     *
     * @param list target list
     * @param maskItems mask target key
     */
    private void analyzeList(List<?> list, List<String> maskItems) {

        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        list.forEach(
                object -> {
                    if (Objects.nonNull(object)) {
                        if (object instanceof List) {
                            analyzeList((List<?>) object, maskItems);
                        } else if (object instanceof Map) {
                            maskLog((Map<String, Object>) object, maskItems);
                        }
                    }
                }
        );
    }

    /**
     * <p>
     * get mask items according to request uri.
     *
     * mask uri and mask items are stored in application-xxx.yml.
     * mask uri and mask items setting sample:
     * oms:
     *   logging:
     *       maskingUris:
     *           basket_basketNo_shipments: /v1/uq/ca/cache_sample/(.*)
     *       maskingItems:
     *           basket_basketNo_shipments: (cd_id|cd_nm)
     *
     * get current uri from request, then match with mask target uris,
     * if matched, then get mask target uri key(eg.basket_basketNo_shipments)
     * in application-xxx.yml file,
     * use uri key(eg.basket_basketNo_shipments) to get mask items.
     *  </p>
     *
     * @param request HttpServletRequest
     * @return mark items
     */
    private String getMaskItemsKey(HttpServletRequest request) {
        // if masking uris or masking items can not be find, return null
        if (maskingUris == null || maskingUris.isEmpty() ||
                maskingItems == null || maskingItems.isEmpty()){
            return null;
        }
        String uri = request.getRequestURI();
        Optional<Map.Entry<String, String>> maskingUri = maskingUris.entrySet()
                .stream().filter(maskingUriList ->
                        uri.matches(maskingUriList.getValue())).findFirst();

        String maskItems = null;
        if (maskingUri.isPresent()) {
            maskItems = maskingItems.get(maskingUri.get().getKey());
        }

        return maskItems;
    }
}
