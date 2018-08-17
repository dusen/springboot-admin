/**
 * @(#)AuthenticationUtil.java
 *
 *                             Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import com.fastretailing.dcp.storecommon.screen.authentication.entity.UserDetails;
import com.fastretailing.dcp.storecommon.screen.exception.NoAuthenticationException;
import lombok.extern.slf4j.Slf4j;

/**
 * Utility class for authentication.
 */
@Slf4j
public class AuthenticationUtil {

    /**
     * Return authenticated user information. User information is managed by Spring Security. If
     * authentication by the framework is not done, an unauthenticated exception will be thrown.
     * 
     * @return Authenticated user information.
     */
    public static UserDetails getUserDetails() {

        Object principalObj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (UserDetails.class.isInstance(principalObj)) {
            UserDetails userDetails = UserDetails.class.cast(principalObj);
            log.debug("userDetails={}", userDetails);
            return userDetails;
        } else {
            log.debug("principalObj={}", principalObj);
            throw new NoAuthenticationException(
                    "store-common-screen framework authentication is not being done by SAML authentication.");
        }

    }

}
