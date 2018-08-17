/**
 * @(#)AuditLogInterceptor.java
 *
 *                              Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.interceptor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.fastretailing.dcp.storecommon.screen.authentication.AuthenticationUtil;
import com.fastretailing.dcp.storecommon.screen.authentication.entity.UserDetails;
import com.fastretailing.dcp.storecommon.screen.config.AuditConfiguration;
import com.fastretailing.dcp.storecommon.screen.log.AuditLogger;
import lombok.extern.slf4j.Slf4j;

/**
 * Interceptor for log output when controller is called.
 */
@Slf4j
public class AuditLogInterceptor extends HandlerInterceptorAdapter {

    /** Exclusion path list. */
    private List<String> exclusionPathList;

    /**
     * Constructor.
     * 
     * @param configuration configuration for audit log. Since @Autowired fails in this class, it
     *        gets from the caller.
     */
    public AuditLogInterceptor(AuditConfiguration configuration) {
        this.exclusionPathList = configuration.getExclusionPathList();
    }

    /**
     * Before processing by the controller, audit log is output.
     * 
     * @param request HTTP request.
     * @param response HTTP response.
     * @param handler Method of controller to be processed.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) {

        if (request == null || handler == null) {
            log.debug(
                    "The audit log can not be output because the argument is abnormal. request={}/handler={}",
                    request, handler);
        } else {
            if (!isExclusionPath(request)) {
                try {
                    UserDetails userDetails = AuthenticationUtil.getUserDetails();
                    Map<String, String> informationMap = new LinkedHashMap<>();
                    AuditLogger.outputAuditLog(userDetails, AuditLogInterceptor.class,
                            handler.toString(), request, informationMap);
                } catch (Exception e) {
                    log.debug(
                            "The audit log is out of scope because it is in an unauthenticated state. URL={}",
                            request.getRequestURL());
                    log.debug("Cause exception.", e);
                }
            }
        }

        return true;
    }

    /**
     * Returns true if it is an audit log exclusion path, false otherwise.
     * 
     * @param request HTTP request.
     * @return true if it is an audit log exclusion path, false otherwise.
     */
    private boolean isExclusionPath(HttpServletRequest request) {
        String requestUrl = request.getRequestURL().toString();
        return exclusionPathList.stream().anyMatch(path -> requestUrl.contains(path));
    }

}
