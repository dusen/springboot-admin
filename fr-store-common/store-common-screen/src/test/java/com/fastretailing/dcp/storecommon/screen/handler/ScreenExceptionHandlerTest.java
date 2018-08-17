/**
 * @(#)ScreenExceptionHandlerTest.java
 *
 *                                     Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.handler;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import com.fastretailing.dcp.storecommon.screen.exception.ScreenException;
import com.fastretailing.dcp.storecommon.screen.form.CommonBaseForm;
import com.fastretailing.dcp.storecommon.screen.form.DetailError;
import com.fastretailing.dcp.storecommon.screen.message.LocaleMessageSource;

/**
 * Unit test of ScreenExceptionHandler class.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ScreenExceptionHandlerTest {

    /** Create LocaleMessageSource Mock. */
    @Mock
    private LocaleMessageSource mockMessageSource;

    /** Test target object of ScreenExceptionHandler. */
    @InjectMocks
    private ScreenExceptionHandler screenExceptionHandler = new ScreenExceptionHandler();

    /** Target url. */
    private static final String TARGET_URL = "showItem";

    /**
     * <UL>
     * <LI>Target method：screenExceptionHandler.
     * <LI>Condition：Has no errors.
     * <LI>Verification result confirmation：Add commonBaseForm attribute to model.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testScreenExceptionHandler01() throws Exception {
        // Prepare exception information for test.
        CommonBaseForm commonBaseForm = new CommonBaseForm();
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("commonBaseForm", commonBaseForm);

        ScreenException screenException =
                new ScreenException(new DetailError(), "", TARGET_URL, modelMap);

        MockHttpServletRequest request = new MockHttpServletRequest();
        // Method execution
        ModelAndView actModelAndView = (ModelAndView) screenExceptionHandler
                .screenExceptionHandler(screenException, request);

        // Confirm result
        assertThat(actModelAndView.getViewName(), is(TARGET_URL));
        assertTrue(actModelAndView.getModelMap().containsKey("commonBaseForm"));

        CommonBaseForm actCommonBaseForm =
                (CommonBaseForm) actModelAndView.getModel().get("commonBaseForm");
        DetailError expectedError = new DetailError();
        assertThat(actCommonBaseForm.getDetailError(), is(expectedError));
    }

    /**
     * <UL>
     * <LI>Target method：screenExceptionHandler.
     * <LI>Condition：Has only one item check error.
     * <LI>Verification result confirmation：Add error information to CommonBaseForm object, and add
     * commonBaseForm attribute to model.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testScreenExceptionHandler02() throws Exception {
        // Prepare exception information for test.
        DetailError expDetailError = new DetailError();
        expDetailError.setErrorCode("E001");
        expDetailError.setErrorMessageTitle("ERROR TITLE");
        expDetailError.setErrorMessage("ERROR");
        List<String> errorFiledList = new ArrayList<String>();
        errorFiledList.add("field1");
        errorFiledList.add("field2");
        expDetailError.setErrorFieldList(errorFiledList);

        CommonBaseForm commonBaseForm = new CommonBaseForm();
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("commonBaseForm", commonBaseForm);

        ScreenException screenException =
                new ScreenException(expDetailError, "", TARGET_URL, modelMap);

        MockHttpServletRequest request = new MockHttpServletRequest();

        // Method execution
        ModelAndView actModelAndView = (ModelAndView) screenExceptionHandler
                .screenExceptionHandler(screenException, request);

        // Confirm result
        assertThat(actModelAndView.getViewName(), is(TARGET_URL));
        assertTrue(actModelAndView.getModelMap().containsKey("commonBaseForm"));

        CommonBaseForm actCommonBaseForm =
                (CommonBaseForm) actModelAndView.getModel().get("commonBaseForm");
        assertThat(actCommonBaseForm.getDetailError().getErrorCode(), is("E001"));
        assertThat(actCommonBaseForm.getDetailError().getErrorMessageTitle(), is("ERROR TITLE"));
        assertThat(actCommonBaseForm.getDetailError().getErrorMessage(), is("ERROR"));
        assertThat(actCommonBaseForm.getDetailError().getErrorFieldList().size(), is(2));
    }
}
