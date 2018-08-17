/**
 * @(#)HmacAuthenticationAopPointCut.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.common.hmac;

import com.fastretailing.dcp.common.hmac.annotation.HmacAuthentication;
import com.fastretailing.dcp.common.hmac.context.HmacAuthenticationContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static org.junit.Assert.assertEquals;

@Component
@Slf4j
public class HmacAuthenticationAopPointCut {

    /**
     * source==null
     */
    @HmacAuthentication(value = "test")
    public void pointCutMethod()  {
        log.info("HmacAuthenticationAopTest.Configuration01");

        assertEquals(HmacAuthenticationContextHolder.getHmacAuthenticationContext().getPlatformName(), "test");

    }

}
