/**
 * @(#)RequestHeaderVariableInterceptor.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.web.interceptor;

import com.fastretailing.dcp.common.api.client.FilteredHttpHeaderHolder;
import com.fastretailing.dcp.common.api.client.RequestHeaderBlackListConfigurationProperties;
import com.fastretailing.dcp.common.threadlocal.UserIdAndMemberIdHolder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RequestHeaderVariableInterceptor.
 *
 * the interceptor to save the special request's header variable to thread local.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public class RequestHeaderVariableInterceptor extends HandlerInterceptorAdapter {

    /**
     * Enabled transfer api request header.
     */
    private static final String ENABLED_TRANSFER_API_REQUEST_HEADER = "ON";

    /**
     * HttpHeader : X-Forwarded-For
     */
    private static final String HEADER_KEY_X_FORWARDED_FOR = "X-Forwarded-For";

    /**
     * The deal before controller execute.
     * @param request request
     * @param response response
     * @param handler handler
     * @return the result
     * @throws Exception Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        Map<String, String> requestHeaderVariableMap = new HashMap<>();
        requestHeaderVariableMap.put(UserIdAndMemberIdHolder.HEADER_KEY_X_FR_FRONT_MEMBER_ID,
                request.getHeader(UserIdAndMemberIdHolder.HEADER_KEY_X_FR_FRONT_MEMBER_ID));
        requestHeaderVariableMap.put(UserIdAndMemberIdHolder.HEADER_KEY_X_FR_ADMIN_USER_ID,
                request.getHeader(UserIdAndMemberIdHolder.HEADER_KEY_X_FR_ADMIN_USER_ID));
        UserIdAndMemberIdHolder.setUserIdAndMemberIdMap(requestHeaderVariableMap);

        // filtered headers
        Map<String, List<String>> filteredMap = new HashMap<>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            if (StringUtils.equalsIgnoreCase(key, HEADER_KEY_X_FORWARDED_FOR)) {
                filteredMap.put(key, Collections.singletonList(request.getHeader(key)));
            }

            if (StringUtils.equalsIgnoreCase(
                    RequestHeaderBlackListConfigurationProperties.staticTransferEnabled,
                    ENABLED_TRANSFER_API_REQUEST_HEADER
            )) {
                if (CollectionUtils.isNotEmpty(RequestHeaderBlackListConfigurationProperties.staticBlackList)
                        && !RequestHeaderBlackListConfigurationProperties.staticBlackList.contains(key)) {
                    filteredMap.put(key, Collections.singletonList(request.getHeader(key)));
                }
            }

        }

        FilteredHttpHeaderHolder.replace(filteredMap);

        return true;
    }

}
