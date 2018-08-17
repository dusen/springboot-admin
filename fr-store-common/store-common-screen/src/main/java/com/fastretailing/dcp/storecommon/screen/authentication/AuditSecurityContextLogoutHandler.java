/**
 * @(#)AuditSecurityContextLogoutHandler.java
 *
 *                                            Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.authentication;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import com.fastretailing.dcp.storecommon.screen.authentication.entity.UserDetails;
import com.fastretailing.dcp.storecommon.screen.log.AuditLogger;

/**
 * Security context logout handler with audit log output.
 */
public class AuditSecurityContextLogoutHandler extends SecurityContextLogoutHandler {

    /**
     * Outputs the audit log before logging out.
     *
     * @param request From which to obtain a HTTP session. (can not be null)
     * @param response Not used. (can be <code>null</code>)
     * @param authentication Not used. (can be <code>null</code>)
     */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) {
        UserDetails userDetails = AuthenticationUtil.getUserDetails();
        Map<String, String> informationMap = new LinkedHashMap<>();
        AuditLogger.outputAuditLog(userDetails, AuditSecurityContextLogoutHandler.class,
                AuditLogger.LOGOUT_HANDLER_STRING, request, informationMap);
        super.logout(request, response, authentication);
    }

}
