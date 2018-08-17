/**
 * @(#)HmacAuthenticationInterceptor.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.hmac.interceptor;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.fastretailing.dcp.common.hmac.annotation.HmacAuthentication;
import com.fastretailing.dcp.common.hmac.context.HmacAuthenticationContext;
import com.fastretailing.dcp.common.hmac.context.HmacAuthenticationContextHolder;

/**
 * the interceptor of hmac authentication.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Order(1)
@Aspect
@Component
public class HmacAuthenticationInterceptor {

    /**
     * set the client id of hmac authentication into thread safe hmacAuthenticationContextHolder.
     * @param proceedingJoinPoint ProceedingJoinPoint
     * @param hmacAuthentication HmacAuthentication
     * @return ProceedingJoinPoint ProceedingJoinPoint
     * @throws Throwable Throwable
     */
    @Around("@annotation(hmacAuthentication)")
    public Object invoke(ProceedingJoinPoint proceedingJoinPoint,
                         HmacAuthentication hmacAuthentication)
            throws Throwable {

        try {
            // set hmac context : client id
            HmacAuthenticationContext hmacAuthenticationContext = new HmacAuthenticationContext();
            hmacAuthenticationContext.setPlatformName(hmacAuthentication.value());
            HmacAuthenticationContextHolder.setHmacAuthenticationContext(hmacAuthenticationContext);
            return proceedingJoinPoint.proceed();
        } finally {
            HmacAuthenticationContextHolder.cleanHmacAuthenticationContext();
        }

    }
}
