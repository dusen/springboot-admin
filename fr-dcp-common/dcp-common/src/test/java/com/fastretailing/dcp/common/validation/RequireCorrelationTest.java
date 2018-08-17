/**
 * @(#)RequireCorrelationTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.common.validation;

import com.fastretailing.dcp.common.validation.annotation.RequireCorrelation;
import com.fastretailing.dcp.common.validation.validator.RequireCorrelationValidator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

@Slf4j
public class RequireCorrelationTest extends MessageTest {

    private static Validator validator;
    private static RequireCorrelationValidator requireCorrelationValidator;

    @BeforeClass
    public static void setUp() {
        validator = TestValidatorFactory.createValidator();
        requireCorrelationValidator = new RequireCorrelationValidator();
    }

    @Test
    public void validWhenStringValueIsNotNull() {
        RequireCorrelationStringForm form = new RequireCorrelationStringForm("a", "b");
        Set<ConstraintViolation<RequireCorrelationStringForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validWhenStringValueIsNull() {
        RequireCorrelationStringForm form = new RequireCorrelationStringForm(null, null);
        Set<ConstraintViolation<RequireCorrelationStringForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

//    @Test
//    public void invalidWhenStringValueIsBlank() {
//        RequireCorrelationStringForm form = new RequireCorrelationStringForm("", "");
//        Set<ConstraintViolation<RequireCorrelationStringForm>> constraintViolations = validator.validate(form);
//        assertEquals(1, constraintViolations.size());
//        assertEquals("{w.common.validation.require-correlation}", constraintViolations.iterator().next().getMessageTemplate());
//    }

    @Test
    public void invalidWhenStringValueFirstIsNull() {
        RequireCorrelationStringForm form = new RequireCorrelationStringForm(null, "b");
        Set<ConstraintViolation<RequireCorrelationStringForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.require-correlation}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void invalidWhenStringValueFirstIsBlank() {
        RequireCorrelationStringForm form = new RequireCorrelationStringForm("", "b");
        Set<ConstraintViolation<RequireCorrelationStringForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.require-correlation}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void invalidWhenStringValueSecondIsNull() {
        RequireCorrelationStringForm form = new RequireCorrelationStringForm("a", null);
        Set<ConstraintViolation<RequireCorrelationStringForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.require-correlation}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void invalidWhenStringValueSecondIsBlank() {
        RequireCorrelationStringForm form = new RequireCorrelationStringForm("a", "");
        Set<ConstraintViolation<RequireCorrelationStringForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.require-correlation}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void validWhenIntegerValueIsNotNull() {
        RequireCorrelationIntegerForm form = new RequireCorrelationIntegerForm(1, 2);
        Set<ConstraintViolation<RequireCorrelationIntegerForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validWhenIntegerValueIsNull() {
        RequireCorrelationIntegerForm form = new RequireCorrelationIntegerForm(null, null);
        Set<ConstraintViolation<RequireCorrelationIntegerForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void invalidWhenIntegerValueFirstIsNull() {
        RequireCorrelationIntegerForm form = new RequireCorrelationIntegerForm(null, 2);
        Set<ConstraintViolation<RequireCorrelationIntegerForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.require-correlation}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void invalidWhenIntegerValueSecondIsNotNull() {
        RequireCorrelationIntegerForm form = new RequireCorrelationIntegerForm(1, null);
        Set<ConstraintViolation<RequireCorrelationIntegerForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.require-correlation}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void validWhenIntValueIsNotNull() {
        RequireCorrelationIntForm form = new RequireCorrelationIntForm(1, 2);
        Set<ConstraintViolation<RequireCorrelationIntForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validWhenBigDecimalValueIsNotNull() {
        RequireCorrelationBigDecimalForm form = new RequireCorrelationBigDecimalForm(BigDecimal.valueOf(1), BigDecimal.valueOf(2));
        Set<ConstraintViolation<RequireCorrelationBigDecimalForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validWhenBigDecimalValueIsNull() {
        RequireCorrelationBigDecimalForm form = new RequireCorrelationBigDecimalForm(null, null);
        Set<ConstraintViolation<RequireCorrelationBigDecimalForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void invalidWhenBigDecimalValueFirstIsNull() {
        RequireCorrelationBigDecimalForm form = new RequireCorrelationBigDecimalForm(null, BigDecimal.valueOf(2));
        Set<ConstraintViolation<RequireCorrelationBigDecimalForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.require-correlation}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void invalidWhenBigDecimalValueSecondIsNotNull() {
        RequireCorrelationBigDecimalForm form = new RequireCorrelationBigDecimalForm(BigDecimal.valueOf(1), null);
        Set<ConstraintViolation<RequireCorrelationBigDecimalForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.require-correlation}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void validWhenDateValueIsNotNull() {
        RequireCorrelationDateForm form = new RequireCorrelationDateForm(new Date(), new Date());
        Set<ConstraintViolation<RequireCorrelationDateForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validWhenDateValueIsNull() {
        RequireCorrelationDateForm form = new RequireCorrelationDateForm(null, null);
        Set<ConstraintViolation<RequireCorrelationDateForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void invalidWhenDateValueFirstIsNull() {
        RequireCorrelationDateForm form = new RequireCorrelationDateForm(null, new Date());
        Set<ConstraintViolation<RequireCorrelationDateForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.require-correlation}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void invalidWhenDateValueSecondIsNotNull() {
        RequireCorrelationDateForm form = new RequireCorrelationDateForm(new Date(), null);
        Set<ConstraintViolation<RequireCorrelationDateForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.require-correlation}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void validWhenListValueIsNotNull() {
        List<String> l = new ArrayList<>();
        RequireCorrelationListForm form = new RequireCorrelationListForm(l, l);
        Set<ConstraintViolation<RequireCorrelationListForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validWhenListValueIsNull() {
        RequireCorrelationListForm form = new RequireCorrelationListForm(null, null);
        Set<ConstraintViolation<RequireCorrelationListForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void invalidWhenListValueFirstIsNull() {
        List<String> l = new ArrayList<>();
        RequireCorrelationListForm form = new RequireCorrelationListForm(null, l);
        Set<ConstraintViolation<RequireCorrelationListForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.require-correlation}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void invalidWhenListValueSecondIsNotNull() {
        List<String> l = new ArrayList<>();
        RequireCorrelationListForm form = new RequireCorrelationListForm(l, null);
        Set<ConstraintViolation<RequireCorrelationListForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.require-correlation}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void validWhenArrayValueIsNotNull() {
        String[] array = new String[0];
        RequireCorrelationArrayForm form = new RequireCorrelationArrayForm(array, array);
        Set<ConstraintViolation<RequireCorrelationArrayForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validWhenArrayValueIsNull() {
        RequireCorrelationArrayForm form = new RequireCorrelationArrayForm(null, null);
        Set<ConstraintViolation<RequireCorrelationArrayForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void invalidWhenArrayValueFirstIsNull() {
        String[] array = new String[0];
        RequireCorrelationArrayForm form = new RequireCorrelationArrayForm(null, array);
        Set<ConstraintViolation<RequireCorrelationArrayForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.require-correlation}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void invalidWhenArrayValueSecondIsNotNull() {
        String[] array = new String[0];
        RequireCorrelationArrayForm form = new RequireCorrelationArrayForm(array, null);
        Set<ConstraintViolation<RequireCorrelationArrayForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.require-correlation}", constraintViolations.iterator().next().getMessageTemplate());
    }

    // blank
    @Test
    public void validWhenFirstParamIsBlank() {
        FirstBlankForm form = new FirstBlankForm("a", "b");
        Set<ConstraintViolation<FirstBlankForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validWhenSecondParamIsBlank() {
        SecondBlankForm form = new SecondBlankForm("a", "b");
        Set<ConstraintViolation<SecondBlankForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    /*
     * Test Form classes
     */
    @Data
    @RequireCorrelation(firstField = "str1", secondField = "str2")
    private class RequireCorrelationStringForm {
        public RequireCorrelationStringForm(String str1, String str2) {
            this.str1 = str1;
            this.str2 = str2;
        }
        String str1;
        String str2;
    }

    @Data
    @RequireCorrelation(firstField = "num1", secondField = "num2")
    private class RequireCorrelationIntegerForm {
        public RequireCorrelationIntegerForm(Integer num1, Integer num2) {
            this.num1 = num1;
            this.num2 = num2;
        }
        Integer num1;
        Integer num2;
    }

    @Data
    @RequireCorrelation(firstField = "num1", secondField = "num2")
    private class RequireCorrelationIntForm {
        public RequireCorrelationIntForm(int num1, int num2) {
            this.num1 = num1;
            this.num2 = num2;
        }
        int num1;
        int num2;
    }

    @Data
    @RequireCorrelation(firstField = "num1", secondField = "num2")
    private class RequireCorrelationBigDecimalForm {
        public RequireCorrelationBigDecimalForm(BigDecimal num1, BigDecimal num2) {
            this.num1 = num1;
            this.num2 = num2;
        }
        BigDecimal num1;
        BigDecimal num2;
    }

    @Data
    @RequireCorrelation(firstField = "date1", secondField = "date2")
    private class RequireCorrelationDateForm {
        public RequireCorrelationDateForm(Date date1, Date date2) {
            this.date1 = date1;
            this.date2 = date2;
        }
        Date date1;
        Date date2;
    }

    @Data
    @RequireCorrelation(firstField = "list1", secondField = "list2")
    private class RequireCorrelationListForm {
        public RequireCorrelationListForm(List<String> list1, List<String> list2) {
            this.list1 = list1;
            this.list2 = list2;
        }
        List<String> list1;
        List<String> list2;
    }

    @Data
    @RequireCorrelation(firstField = "str1", secondField = "str2")
    private class RequireCorrelationArrayForm {
        public RequireCorrelationArrayForm(String[] str1, String[] str2) {
            this.str1 = str1;
            this.str2 = str2;
        }
        String[] str1;
        String[] str2;
    }

    @Data
    @RequireCorrelation(firstField = "", secondField = "str2")
    private class FirstBlankForm {
        public FirstBlankForm(String str1, String str2) {
            this.str1 = str1;
            this.str2 = str2;
        }
        String str1;
        String str2;
    }

    @Data
    @RequireCorrelation(firstField = "str1", secondField = "")
    private class SecondBlankForm {
        public SecondBlankForm(String str1, String str2) {
            this.str1 = str1;
            this.str2 = str2;
        }
        String str1;
        String str2;
    }

    /**
     * firstField is empty
     */
    @Test
    public void isValid01() {
        log.debug("RequireCorrelationTest.isValid01");

        // initialize
        RequireCorrelation annotation = mock(RequireCorrelation.class);
        Mockito.when(annotation.firstField()).thenReturn("");
        Mockito.when(annotation.secondField()).thenReturn("filedB");
        requireCorrelationValidator.initialize(annotation);

        // validate
        boolean actual = requireCorrelationValidator.isValid("value", null);

        // compare
        assertThat(actual, is(true));
    }

    /**
     * secondField is empty
     */
    @Test
    public void isValid02() {
        log.debug("RequireCorrelationTest.isValid02");

        // initialize
        RequireCorrelation annotation = mock(RequireCorrelation.class);
        Mockito.when(annotation.firstField()).thenReturn("filedA");
        Mockito.when(annotation.secondField()).thenReturn("");
        requireCorrelationValidator.initialize(annotation);

        // validate
        boolean actual = requireCorrelationValidator.isValid("value", null);

        // compare
        assertThat(actual, is(true));
    }

    /**
     * firstValue == null && secondValue == null
     */
    @Test
    public void isValid03() {
        log.debug("RequireCorrelationTest.isValid03");

        // initialize
        RequireCorrelation annotation = mock(RequireCorrelation.class);
        Mockito.when(annotation.firstField()).thenReturn("fieldA");
        Mockito.when(annotation.secondField()).thenReturn("fieldB");
        requireCorrelationValidator.initialize(annotation);

        // validate
        ClassA classA = new ClassA();
        boolean actual = requireCorrelationValidator.isValid(classA, null);

        // compare
        assertThat(actual, is(true));
    }

    /**
     * firstValue == null && secondValue != null
     */
    @Test
    public void isValid04() {
        log.debug("RequireCorrelationTest.isValid04");

        // initialize
        RequireCorrelation annotation = mock(RequireCorrelation.class);
        Mockito.when(annotation.firstField()).thenReturn("fieldA");
        Mockito.when(annotation.secondField()).thenReturn("fieldB");
        Mockito.when(annotation.message()).thenReturn("");
        Mockito.when(annotation.messageParam()).thenReturn(new String[0]);
        requireCorrelationValidator.initialize(annotation);

        // validate
        ClassA classA = new ClassA();
        classA.setFieldB("bbb");
        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        PathImpl path = PathImpl.createRootPath();
        ConstraintValidatorContextImpl contextImpl
                = new ConstraintValidatorContextImpl(null,null,path,null);
        Mockito.when(context.buildConstraintViolationWithTemplate(Mockito.anyString()))
                .thenReturn(contextImpl.buildConstraintViolationWithTemplate(null));
        boolean actual = requireCorrelationValidator.isValid(classA, context);

        // compare
        assertThat(actual, is(false));
    }

    /**
     * firstValue != null && secondValue == null
     */
    @Test
    public void isValid05() {
        log.debug("RequireCorrelationTest.isValid05");

        // initialize
        RequireCorrelation annotation = mock(RequireCorrelation.class);
        Mockito.when(annotation.firstField()).thenReturn("fieldA");
        Mockito.when(annotation.secondField()).thenReturn("fieldB");
        Mockito.when(annotation.message()).thenReturn("");
        Mockito.when(annotation.messageParam()).thenReturn(new String[0]);
        requireCorrelationValidator.initialize(annotation);

        // validate
        ClassA classA = new ClassA();
        classA.setFieldA("aaa");
        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        PathImpl path = PathImpl.createRootPath();
        ConstraintValidatorContextImpl contextImpl
                = new ConstraintValidatorContextImpl(null,null,path,null);
        Mockito.when(context.buildConstraintViolationWithTemplate(Mockito.anyString()))
                .thenReturn(contextImpl.buildConstraintViolationWithTemplate(null));
        boolean actual = requireCorrelationValidator.isValid(classA, context);

        // compare
        assertThat(actual, is(false));
    }

    /**
     * firstValueが文字列 && secondValueが文字列
     */
    @Test
    public void isValid06() {
        log.debug("RequireCorrelationTest.isValid06");

        // initialize
        RequireCorrelation annotation = mock(RequireCorrelation.class);
        Mockito.when(annotation.firstField()).thenReturn("fieldA");
        Mockito.when(annotation.secondField()).thenReturn("fieldB");
        requireCorrelationValidator.initialize(annotation);

        // validate
        ClassA classA = new ClassA();
        classA.setFieldA("aaa");
        classA.setFieldB("bbb");
        boolean actual = requireCorrelationValidator.isValid(classA, null);

        // compare
        assertThat(actual, is(true));
    }

    /**
     * firstValueが文字列 && secondValueがObject
     */
    @Test
    public void isValid07() {
        log.debug("RequireCorrelationTest.isValid07");

        // initialize
        RequireCorrelation annotation = mock(RequireCorrelation.class);
        Mockito.when(annotation.firstField()).thenReturn("fieldA");
        Mockito.when(annotation.secondField()).thenReturn("fieldC");
        requireCorrelationValidator.initialize(annotation);

        // validate
        ClassA classA = new ClassA();
        classA.setFieldA("aaa");
        ClassB classB = new ClassB();
        classA.setFieldC(classB);
        boolean actual = requireCorrelationValidator.isValid(classA, null);

        // compare
        assertThat(actual, is(true));
    }

    /**
     * firstValueがObject && secondValueがObject
     */
    @Test
    public void isValid08() {
        log.debug("RequireCorrelationTest.isValid08");

        // initialize
        RequireCorrelation annotation = mock(RequireCorrelation.class);
        Mockito.when(annotation.firstField()).thenReturn("fieldC");
        Mockito.when(annotation.secondField()).thenReturn("fieldD");
        requireCorrelationValidator.initialize(annotation);

        // validate
        ClassA classA = new ClassA();
        ClassB fieldC = new ClassB();
        ClassB fieldD = new ClassB();
        classA.setFieldC(fieldC);
        classA.setFieldD(fieldD);
        boolean actual = requireCorrelationValidator.isValid(classA, null);

        // compare
        assertThat(actual, is(true));
    }

    /**
     * firstValueがObject && secondValueが文字列
     */
    @Test
    public void isValid09() {
        log.debug("RequireCorrelationTest.isValid09");

        // initialize
        RequireCorrelation annotation = mock(RequireCorrelation.class);
        Mockito.when(annotation.firstField()).thenReturn("fieldC");
        Mockito.when(annotation.secondField()).thenReturn("fieldB");
        requireCorrelationValidator.initialize(annotation);

        // validate
        ClassA classA = new ClassA();
        ClassB fieldC = new ClassB();
        classA.setFieldC(fieldC);
        classA.setFieldB("bbb");
        boolean actual = requireCorrelationValidator.isValid(classA, null);

        // compare
        assertThat(actual, is(true));
    }

    @Data
    public class ClassA {
        private String fieldA;

        private String fieldB;

        private ClassB fieldC;

        private ClassB fieldD;
    }

    @Data
    public class ClassB {
        private String fieldC;
    }
}
