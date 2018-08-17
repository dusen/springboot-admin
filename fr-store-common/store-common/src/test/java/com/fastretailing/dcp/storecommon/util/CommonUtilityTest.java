/**
 * @(#)CommonUtilityTest.java
 *
 *                            Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.util;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.fastretailing.dcp.common.constants.ErrorName;
import com.fastretailing.dcp.common.constants.LogLevel;
import com.fastretailing.dcp.common.model.Detail;
import com.fastretailing.dcp.common.model.ResultObject;
import com.fastretailing.dcp.storecommon.message.SystemMessageSource;
import com.fastretailing.dcp.storecommon.type.FunctionType;
import com.fastretailing.dcp.storecommon.type.MessageType;
import com.fastretailing.dcp.storecommon.type.PlatformShortName;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonUtilityTest {

    /** Mapper of message resource. */
    @Autowired
    private SystemMessageSource systemMessageSource;

    /**
     * Test is create message Id method.
     * 
     * @see CommonUtility#createMessageId(LogLevel, PlatformShortName, MessageType, FunctionType,
     *      String)
     */
    @Test
    public void testCreateMessageId() {
        String value = CommonUtility.createMessageId(LogLevel.ERROR, PlatformShortName.SALES,
                MessageType.SYSTEM_ERROR, FunctionType.API, "000101");
        assertEquals("E_SLS_94000101", value);
    }

    /**
     * Test is create result object method.
     * 
     * @see CommonUtility#createResultObject(SystemMessageSource, ErrorName, String, String,
     *      List<String>, List<Detail>)
     *
     */
    @Test
    public void testCreateResultObjectBasic() {
        List<String> links = new ArrayList<>();
        links.add("link1");
        List<Detail> details = new ArrayList<>();
        Detail detail =
                new Detail("E_SLS_94000101", systemMessageSource.getMessage("E_SLS_94000101"));
        detail.setField("fieldId");
        detail.setValue("value");
        details.add(detail);

        ResultObject expected = new ResultObject(ErrorName.Business.BUSINESS_CHECK_ERROR,
                "E_SLS_94000101", systemMessageSource.getMessage("E_SLS_94000101"));
        expected.setDetails(details);
        expected.setLinks(links);
        expected.setInformationLink("informationLink");

        ResultObject resultObject = CommonUtility.createResultObject(systemMessageSource,
                ErrorName.Business.BUSINESS_CHECK_ERROR, "E_SLS_94000101", "informationLink", links,
                details);

        assertEquals(expected, resultObject);
    }

    /**
     * Test is create result object method.
     * 
     * @see CommonUtility#createResultObject(SystemMessageSource, ErrorName, String, String, String,
     *      String, String[])
     *
     */
    @Test
    public void testCreateResultObjectMessages() {

        List<Detail> details = new ArrayList<>();
        Detail detail =
                new Detail("E_SLS_94000101", systemMessageSource.getMessage("E_SLS_94000101"));
        detail.setField("fieldId");
        detail.setValue("value");
        details.add(detail);
        ResultObject expected = new ResultObject(ErrorName.Business.BUSINESS_CHECK_ERROR,
                "E_SLS_94000101", systemMessageSource.getMessage("E_SLS_94000101"));
        expected.setDetails(details);

        ResultObject resultObject = CommonUtility.createResultObject(systemMessageSource,
                ErrorName.Business.BUSINESS_CHECK_ERROR, "fieldId", "value", "E_SLS_94000101",
                "E_SLS_94000101");

        assertEquals(expected, resultObject);
    }

    /**
     * Test is create result object method.
     * 
     * @see CommonUtility#createResultObject(SystemMessageSource, ErrorName, String, String, String,
     *      String, Object...)
     *
     */
    @Test
    public void testCreateResultObject() {

        List<Detail> details = new ArrayList<>();
        Detail detail = new Detail("E_SLS_94000102", systemMessageSource
                .getMessage("E_SLS_94000102", new Object[] {"param1", "param2"}));
        detail.setField("fieldId");
        detail.setValue("value");
        details.add(detail);
        ResultObject expected = new ResultObject(ErrorName.Business.BUSINESS_CHECK_ERROR,
                "E_SLS_94000101", systemMessageSource.getMessage("E_SLS_94000101"));
        expected.setDetails(details);

        ResultObject resultObject = CommonUtility.createResultObject(systemMessageSource,
                ErrorName.Business.BUSINESS_CHECK_ERROR, "fieldId", "value", "E_SLS_94000102",
                "E_SLS_94000101", "param1", "param2");

        assertEquals(expected, resultObject);
    }
}
