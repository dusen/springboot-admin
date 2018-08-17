/**
 * @(#)CommonBaseFormTest.java
 *
 *                             Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.form;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Unit test of CommonBaseForm class.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CommonBaseFormTest {

    @InjectMocks
    private CommonBaseForm target = new CommonBaseForm();

    /**
     * The preHandle method.
     * 
     * @throws Exception Exception that occurred.
     */
    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
    }

    /**
     * <UL>
     * <LI>Target method:addDetailError.
     * <LI>Condition:ErrorCode, ErrorMessage, ErrorFields is null.
     * <LI>Verification result confirmation:The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testAddDetailError01() {
        // Prepare expect error information for test.
        DetailError expErrorList = new DetailError();

        // Method execution
        target.setDetailError(null, null, null, null, null);

        // Confirm result
        assertThat(ReflectionTestUtils.getField(target, "detailError"), is(expErrorList));
    }

    /**
     * <UL>
     * <LI>Target method:addDetailError.
     * <LI>Condition:ErrorFields is not null.
     * <LI>Verification result confirmation:The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testAddDetailError02() {
        // Prepare expect error information for test.
        DetailError expDetailError = new DetailError();
        expDetailError.setErrorCode("E001");
        expDetailError.setErrorMessageTitle("ERROR TITLE");
        expDetailError.setErrorMessage("ERROR");
        expDetailError.setMessageType("E");
        List<String> errorFiledList = new ArrayList<String>();
        errorFiledList.add("field1");
        errorFiledList.add("field2");
        expDetailError.setErrorFieldList(errorFiledList);

        // Method execution
        target.setDetailError("E001", "ERROR TITLE", "ERROR", new String[] {"field1", "field2"},
                "E");

        // Confirm result
        assertThat(ReflectionTestUtils.getField(target, "detailError"), is(expDetailError));
    }

    /**
     * Test equals.
     */
    @Test
    public void testEquals() {
        DetailError detailError = new DetailError();
        detailError.setErrorCode("E001");
        detailError.setErrorMessageTitle("ERROR TITLE");
        detailError.setErrorMessage("ERROR");
        List<String> errorFiledList = new ArrayList<String>();
        errorFiledList.add("field1");
        errorFiledList.add("field2");
        detailError.setErrorFieldList(errorFiledList);

        CommonBaseForm commonForm1 = new CommonBaseForm();
        commonForm1.setCountryCode("JP");
        commonForm1.setDefaultLocale("JP");
        commonForm1.setSpecifyLocale("JP");
        commonForm1.setBrandCode("UQ");
        commonForm1.setDetailError(detailError);
        CommonBaseForm commonForm2 = new CommonBaseForm();
        commonForm2.setCountryCode("JP");
        commonForm2.setDefaultLocale("JP");
        commonForm2.setSpecifyLocale("JP");
        commonForm2.setBrandCode("UQ");
        commonForm2.setDetailError(detailError);

        assertTrue(commonForm1.equals(commonForm2));

        CommonBaseForm commonForm3 = new CommonBaseForm();
        commonForm3.setCountryCode("EN");
        commonForm3.setDefaultLocale("EN");
        commonForm3.setSpecifyLocale("EN");
        commonForm3.setBrandCode("UQ");
        commonForm3.setDetailError(detailError);
        assertFalse(commonForm1.equals(commonForm3));
    }

    /**
     * Test hashcode.
     */
    @Test
    public void testHashcode() {
        DetailError detailError = new DetailError();
        detailError.setErrorCode("E001");
        detailError.setErrorMessageTitle("ERROR TITLE");
        detailError.setErrorMessage("ERROR");
        List<String> errorFiledList = new ArrayList<String>();
        errorFiledList.add("field1");
        errorFiledList.add("field2");
        detailError.setErrorFieldList(errorFiledList);

        CommonBaseForm commonForm1 = new CommonBaseForm();
        commonForm1.setCountryCode("JP");
        commonForm1.setDefaultLocale("JP");
        commonForm1.setSpecifyLocale("JP");
        commonForm1.setBrandCode("UQ");
        commonForm1.setDetailError(detailError);
        CommonBaseForm commonForm2 = new CommonBaseForm();
        commonForm2.setCountryCode("JP");
        commonForm2.setDefaultLocale("JP");
        commonForm2.setSpecifyLocale("JP");
        commonForm2.setBrandCode("UQ");
        commonForm2.setDetailError(detailError);

        assertTrue(commonForm1.hashCode() == commonForm2.hashCode());

        CommonBaseForm commonForm3 = new CommonBaseForm();
        commonForm3.setCountryCode("EN");
        commonForm3.setDefaultLocale("EN");
        commonForm3.setSpecifyLocale("EN");
        commonForm3.setBrandCode("UQ");
        commonForm3.setDetailError(detailError);

        assertFalse(commonForm1.hashCode() == commonForm3.hashCode());
    }

}
