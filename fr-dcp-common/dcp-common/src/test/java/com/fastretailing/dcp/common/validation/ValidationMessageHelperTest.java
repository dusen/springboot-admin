package com.fastretailing.dcp.common.validation;

import com.fastretailing.dcp.common.validation.validator.ValidationMessageHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        @ContextConfiguration(locations = {
                "classpath*:com/fastretailing/dcp/common/validation/test-context.xml"
        })
})
public class ValidationMessageHelperTest {

    private static ValidationMessageHelper validationMessageHelper = new ValidationMessageHelper();

    @Autowired
    private MessageSource messageSource;

    @Before
    public  void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    // LocalDate
    @Test
    public void validWhenWithOutParam() throws Exception {

        assertThat(validationMessageHelper.createValidationMessage("{w.common.validation.full-width}",null , messageSource),
                is("Please enter full width characters."));

    }


    // LocalDate
    @Test
    public void validWhenWithParam() throws Exception {

        String[] messageParam = {"platform.name.short"};

        assertThat(validationMessageHelper.createValidationMessage("{w.common.validation.combination-require}",messageParam , messageSource),
                is("The following items must be entered : BSK"));

    }


    // LocalDate
    @Test
    public void validMessageId() throws Exception {

        String[] messageParam = {"1"};

        assertThat(validationMessageHelper.createValidationMessage("{x.ex.fw.5005}",messageParam , messageSource),
                is("{x.ex.fw.5005}"));

    }
}
