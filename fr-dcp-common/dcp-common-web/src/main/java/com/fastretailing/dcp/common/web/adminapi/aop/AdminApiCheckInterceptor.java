/**
 * @(#)AdminApiCheckInterceptor.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.web.adminapi.aop;

import com.fastretailing.dcp.common.api.jvm.OmsJvmParameters;
import com.fastretailing.dcp.common.exception.ForbiddenException;
import com.fastretailing.dcp.common.web.adminapi.annotation.AdminApi;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The annotation AdminApi check interceptor.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public class AdminApiCheckInterceptor extends HandlerInterceptorAdapter {

    /**
     * check the AdminApi setting when controller has the annotation AdminApi.
     * @param request request
     * @param response response
     * @param handler handler
     * @return check result
     * @throws Exception Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (HandlerMethod.class.isAssignableFrom(handler.getClass())) {
            haveAccessPermission((HandlerMethod)handler);
        }
        return super.preHandle(request, response, handler);
    }

    /**
     * If controller's method have @AdminApi annotation, check the jvm setting.<br>
     * If the setting is not admin api, then throw exception to stop this request.
     * @param handlerMethod handlerMethod
     */
    private void haveAccessPermission(HandlerMethod handlerMethod) {
        if (handlerMethod.getMethodAnnotation(AdminApi.class) != null) {
            if (!OmsJvmParameters.isAdminApi()) {
                throw new ForbiddenException("e.access.deny");
            }
        }
    }
}
