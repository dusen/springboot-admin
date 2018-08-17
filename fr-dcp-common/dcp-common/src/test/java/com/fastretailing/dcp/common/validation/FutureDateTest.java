package com.fastretailing.dcp.common.validation;

import com.fastretailing.dcp.common.util.CommonUtility;
import com.fastretailing.dcp.common.validation.annotation.FutureDate;
import com.fastretailing.dcp.common.validation.validator.FutureDateValidator;
import lombok.Data;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        @ContextConfiguration(locations = {
                "classpath*:com/fastretailing/dcp/common/validation/test-context.xml"
        })
})
public class FutureDateTest {

//    private static FutureDateValidator validator;
    private static DateTimeFormatter dateFormatter;
    private static DateTimeFormatter datetimeFormatter;


    private static Validator validators;
    @BeforeClass
    public static void beforeClass() {
        validators = TestValidatorFactory.createValidator();
    }

    @Autowired
    private FutureDateValidator validator;

    @Mock
    private CommonUtility commonUtility;

    @Before
    public  void setUp() {
        MockitoAnnotations.initMocks(this);
        validator = new FutureDateValidator();
        dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        datetimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    }

    // LocalDate
    @Test
    public void validWhenLocalDateIsPastDate() throws Exception {
        LocalDate date = LocalDate.parse("1999-07-07", dateFormatter);
        ReflectionTestUtils.setField(validator, "commonUtility", commonUtility);
        Mockito.when(commonUtility.getOperationAt()).thenReturn(LocalDateTime.parse("1999-07-08T00:00:00.000"));

        assertFalse(validator.isValid(date, null));
    }
    // LocalDate
    @Test
    public void validWhenLocalDateIsFutureDate() throws Exception {
        LocalDate date = LocalDate.parse("1999-07-07", dateFormatter);
        ReflectionTestUtils.setField(validator, "commonUtility", commonUtility);
        Mockito.when(commonUtility.getOperationAt()).thenReturn(LocalDateTime.parse("1999-07-07T00:00:00.000"));

        assertFalse(validator.isValid(date, null));
    }

    // LocalDateTime
    @Test
    public void validWhenLocalDateTimeIsPastDate() throws Exception {
        LocalDateTime date = LocalDateTime.parse("1999-07-07 12:30:30", datetimeFormatter);
        ReflectionTestUtils.setField(validator, "commonUtility", commonUtility);

        Mockito.when(commonUtility.getOperationAt()).thenReturn(LocalDateTime.parse("1999-07-08T00:00:00.000"));

        assertFalse(validator.isValid(date, null));
    }


    @Test
    public void validWhenLocalDateIsNull() throws Exception {

        assertTrue(validator.isValid(null, null));
    }

    @Test
    public void validWhenLocalDateIsToday() throws Exception {
        ReflectionTestUtils.setField(validator, "commonUtility", commonUtility);
        LocalDate date = LocalDate.parse("1999-07-07", dateFormatter);
        ReflectionTestUtils.setField(validator, "today", true);
        Mockito.when(commonUtility.getOperationAt()).thenReturn(LocalDateTime.parse("1999-07-07T00:00:00.000"));

        assertTrue(validator.isValid(date, null));
    }
    @Test
    public void validWhenLocalDateIsPastDay() throws Exception {
        LocalDate date = LocalDate.parse("1999-07-07", dateFormatter);
        ReflectionTestUtils.setField(validator, "commonUtility", commonUtility);
        ReflectionTestUtils.setField(validator, "today", true);
        Mockito.when(commonUtility.getOperationAt()).thenReturn(LocalDateTime.parse("1999-07-06T00:00:00.000"));

        assertTrue(validator.isValid(date, null));
    }

    @Test
    public void validWhenLocalDateTimeIsToday() throws Exception {
        ReflectionTestUtils.setField(validator, "commonUtility", commonUtility);
        LocalDateTime date = LocalDateTime.parse("1999-07-07 12:30:30", datetimeFormatter);
        ReflectionTestUtils.setField(validator, "today", true);
        Mockito.when(commonUtility.getOperationAt()).thenReturn(LocalDateTime.parse("1999-07-07T00:00:00.000"));

        assertTrue(validator.isValid(date, null));
    }

    @Test
    public void validWhenLocalDateTimeIsPastDay() throws Exception {
        LocalDateTime date = LocalDateTime.parse("1999-07-07 12:30:30", datetimeFormatter);
        ReflectionTestUtils.setField(validator, "commonUtility", commonUtility);
        ReflectionTestUtils.setField(validator, "today", true);
        Mockito.when(commonUtility.getOperationAt()).thenReturn(LocalDateTime.parse("1999-07-06T00:00:00.000"));

        assertTrue(validator.isValid(date, null));
    }

    // invalid type
    @Test
    public void validWhenValueIsNotDateType() throws Exception {
        ReflectionTestUtils.setField(validator, "commonUtility", commonUtility);
        Mockito.when(commonUtility.getOperationAt()).thenReturn(LocalDateTime.parse("1999-07-06T00:00:00.000"));

        assertTrue(validator.isValid("2016/04/01", null));
    }

    // invalid type
    @Test
    public void validForm() throws Exception {

        DateFormatterForm dateFormatterForm = new DateFormatterForm();
//        dateFormatterForm.setDateTime(LocalDateTime.parse("1999-07-05T00:00:00.000"));

        Set<ConstraintViolation<DateFormatterForm>> actual = validators.validate(dateFormatterForm);

        // compare
        assertThat(actual.size(), is(0));
    }

    @Data
    private class DateFormatterForm {

        @FutureDate
        private LocalDateTime dateTime;

    }
}
