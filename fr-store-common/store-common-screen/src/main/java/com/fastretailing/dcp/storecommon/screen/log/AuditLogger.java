/**
 * @(#)AuditLogger.java
 *
 *                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.log;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.fastretailing.dcp.storecommon.screen.authentication.entity.UserDetails;
import lombok.extern.slf4j.Slf4j;

/**
 * Logger for audit.
 */
@Slf4j
public class AuditLogger {

    /** Login handler string. */
    public static final String LOGIN_HANDLER_STRING = "Login";

    /** Logout handler string. */
    public static final String LOGOUT_HANDLER_STRING = "Logout";

    /**
     * Outputs audit log.
     * 
     * @param userDetails login user details.
     * @param outputClass class to output audit log.
     * @param executedHandler handler to be executed.
     * @param request HTTP request.
     * @param informationMap Information to be output other than the above.
     */
    public static void outputAuditLog(UserDetails userDetails, Class<?> outputClass,
            String executedHandler, HttpServletRequest request,
            Map<String, String> informationMap) {
        if (userDetails != null && outputClass != null && executedHandler != null && request != null
                && informationMap != null) {
            Map<String, String> outputParametersMap = new LinkedHashMap<>();
            outputParametersMap.put("user details", userDetails.toString());
            outputParametersMap.put("output class", outputClass.getName());
            outputParametersMap.put("executed handler", executedHandler);
            outputParametersMap.put("request URL", request.getRequestURL().toString());
            outputParametersMap.putAll(informationMap);
            log.info("Audit log={}", outputParametersMap);
        } else {
            log.debug("The audit log can not be output because the argument is abnormal. "
                    + "userDetails={}/outputClass={}/executedHandler={}/request={}/informationMap={}",
                    userDetails, outputClass, executedHandler, request, informationMap);
        }
    }

}
