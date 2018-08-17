/**
 * @(#)MessageTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.common.validation;

import com.fastretailing.dcp.common.validation.validator.ValidationMessageHelper;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.MessageSource;

@RunWith(JMockit.class)
public class MessageTest {

    @Before
    public void init() {
        new MockUp<ValidationMessageHelper>() {
            @mockit.Mock
            public String createValidationMessage(String messageTemplate,
                                                         String[] messageParam, MessageSource messageSource) {
                return messageTemplate;
            }
        };
    }

    @Test
    public void tset() {
    }

}
