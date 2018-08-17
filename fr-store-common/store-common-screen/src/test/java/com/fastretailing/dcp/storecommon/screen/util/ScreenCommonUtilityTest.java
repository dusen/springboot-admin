/**
 * @(#)ScreenCommonUtilityTest.java
 *
 *                                  Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.util;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.fastretailing.dcp.common.constants.ErrorName;
import com.fastretailing.dcp.common.model.ResultObject;
import com.fastretailing.dcp.storecommon.message.SystemMessageSource;
import com.fastretailing.dcp.storecommon.screen.ApplicationTest;
import com.fastretailing.dcp.storecommon.screen.message.LocaleMessageSource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
public class ScreenCommonUtilityTest {

    /** Mapper of message resource. */
    @Autowired
    private LocaleMessageSource localeMessageSource;

    /**
     * Test is create result object method.
     *
     * @see ScreenCommonUtility#createResultObject(SystemMessageSource, ErrorName, String, String,
     *      String)
     */
    @Test
    public void testBaseCreateResultObject() {
        ResultObject expected = new ResultObject(ErrorName.Business.BUSINESS_CHECK_ERROR,
                "msg.noparam", localeMessageSource.getMessage("msg.noparam"));

        ResultObject resultObject = ScreenCommonUtility.createResultObject(localeMessageSource,
                ErrorName.Business.BUSINESS_CHECK_ERROR, "msg.noparam", "msg.noparam");
        assertEquals(expected, resultObject);
    }

    /**
     * Test is create result object method.
     *
     * @see ScreenCommonUtility#createResultObject(SystemMessageSource, ErrorName, String, String,
     *      String, Object...)
     */
    @Test
    public void testCreateResultObjectByMessageList() {
        ResultObject expected = new ResultObject(ErrorName.Business.BUSINESS_CHECK_ERROR,
                "msg.withparam", localeMessageSource.getMessage("msg.withparam",
                        new Object[] {"111", "222", "333"}));

        ResultObject resultObject = ScreenCommonUtility.createResultObject(localeMessageSource,
                ErrorName.Business.BUSINESS_CHECK_ERROR, "msg.withparam", "msg.withparam",
                new Object[] {"111", "222", "333"});

        assertEquals(expected, resultObject);
    }
}
