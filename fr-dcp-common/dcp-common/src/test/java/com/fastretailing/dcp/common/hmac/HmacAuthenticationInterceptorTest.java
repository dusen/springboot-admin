/**
 * @(#)HmacAuthenticationJoinPointTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.common.hmac;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.slf4j.Slf4j;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        @ContextConfiguration("classpath*:com/fastretailing/dcp/common/hmac/test-context.xml")
})
@Slf4j
public class HmacAuthenticationInterceptorTest {

    @Autowired
    private HmacAuthenticationAopPointCut pointCut;

    /**
     * source==null
     */
    @Test
    public void configuration01()  {
        log.info("HmacAuthenticationAopTest.Configuration01");

        pointCut.pointCutMethod();

    }

}
