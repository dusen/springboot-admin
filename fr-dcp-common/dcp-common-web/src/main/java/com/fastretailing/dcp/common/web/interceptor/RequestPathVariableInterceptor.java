/**
 * @(#)RequestPathVariableInterceptor.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.web.interceptor;

import com.fastretailing.dcp.common.api.threadlocal.RequestPathVariableHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * RequestPathVariableInterceptor.
 * the interceptor to save the special request's  path variable to thread local
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public class RequestPathVariableInterceptor extends HandlerInterceptorAdapter {

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

        String[] paths = request.getRequestURI()
                .substring(request.getContextPath().length() + 1).split("/");
        if (paths.length >= 2) {
            Map<String, String> pathVariableMap = new HashMap<>();
            pathVariableMap.put(RequestPathVariableHolder.KEY_BRAND, paths[0]);
            pathVariableMap.put(RequestPathVariableHolder.KEY_REGION, paths[1]);
            RequestPathVariableHolder.setRequestPathVariableMap(pathVariableMap);
        }
        return true;
    }
}
