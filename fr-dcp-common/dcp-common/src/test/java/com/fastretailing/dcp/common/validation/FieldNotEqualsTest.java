/**
 * @(#)FieldEualsTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.validation;

import com.fastretailing.dcp.common.validation.annotation.FieldNotEquals;
import lombok.Data;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class FieldNotEqualsTest extends MessageTest {

    private static Validator validator;
    private static SimpleDateFormat sdf;

    @BeforeClass
    public static void setUp() {
        validator = TestValidatorFactory.createValidator();
        sdf = new SimpleDateFormat("yyyyMMdd");
    }

    // String
    @Test
    public void invalidWhenStringEquals() {
        StringTestForm form = new StringTestForm("xxx", "xxx");
        Set<ConstraintViolation<StringTestForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.field-not-equals}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void validWhenStringNotEquals() {
        StringTestForm form = new StringTestForm("xxx", "yyy");
        Set<ConstraintViolation<StringTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validWhenStringFirstIsNull() {
        StringTestForm form = new StringTestForm(null, "yyy");
        Set<ConstraintViolation<StringTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void nvalidWhenStringSecondIsNull() {
        StringTestForm form = new StringTestForm("xxx", null);
        Set<ConstraintViolation<StringTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void invalidWhenStringValuesAreNull() {
        StringTestForm form = new StringTestForm(null, null);
        Set<ConstraintViolation<StringTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    // BigDecimal
    @Test
    public void invalidWhenBigDecimalEquals() {
        BigDecimalTestForm form = new BigDecimalTestForm(BigDecimal.valueOf(1), BigDecimal.valueOf(1));
        Set<ConstraintViolation<BigDecimalTestForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.field-not-equals}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void validWhenBigDecimalNotEquals() {
        BigDecimalTestForm form = new BigDecimalTestForm(BigDecimal.valueOf(2), BigDecimal.valueOf(1));
        Set<ConstraintViolation<BigDecimalTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validWhenBigDecimalFirstIsNull() {
        BigDecimalTestForm form = new BigDecimalTestForm(null, BigDecimal.valueOf(1));
        Set<ConstraintViolation<BigDecimalTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validWhenBigDecimalSecondIsNull() {
        BigDecimalTestForm form = new BigDecimalTestForm(BigDecimal.valueOf(1), null);
        Set<ConstraintViolation<BigDecimalTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validWhenBigDecimalValuesAreNull() {
        BigDecimalTestForm form = new BigDecimalTestForm(null, null);
        Set<ConstraintViolation<BigDecimalTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    // int
    @Test
    public void invalidWhenIntEquals() {
        IntTestForm form = new IntTestForm(1, 1);
        Set<ConstraintViolation<IntTestForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.field-not-equals}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void validWhenIntNotEquals() {
        IntTestForm form = new IntTestForm(2, 1);
        Set<ConstraintViolation<IntTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    // Date
    @Test
    public void invalidWhenDateEquals() throws ParseException {
        Date d1 = sdf.parse("20160315");
        Date d2 = sdf.parse("20160315");
        DateTestForm form = new DateTestForm(d1, d2);
        Set<ConstraintViolation<DateTestForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.field-not-equals}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void validWhenDateNotEquals() throws ParseException {
        Date d1 = sdf.parse("20160315");
        Date d2 = sdf.parse("20160310");
        DateTestForm form = new DateTestForm(d1, d2);
        Set<ConstraintViolation<DateTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validWhenDateFirstIsNull() throws ParseException {
        Date d1 = null;
        Date d2 = sdf.parse("20160315");
        DateTestForm form = new DateTestForm(d1, d2);
        Set<ConstraintViolation<DateTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validWhenDateSecondIsNull() throws ParseException {
        Date d1 = sdf.parse("20160315");
        Date d2 = null;
        DateTestForm form = new DateTestForm(d1, d2);
        Set<ConstraintViolation<DateTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validWhenDateValuesAreNull() {
        DateTestForm form = new DateTestForm(null, null);
        Set<ConstraintViolation<DateTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    // Different Type
    @Test
    public void validWhenValuesAreStringAndInteger() {
        Different1TestForm form = new Different1TestForm("10", 10);
        Set<ConstraintViolation<Different1TestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validWhenValuesAreIntegerAndBigDecimal() {
        Different2TestForm form = new Different2TestForm(10, BigDecimal.valueOf(10));
        Set<ConstraintViolation<Different2TestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validWhenValuesAreDateAndCalendar() {
        Different3TestForm form = new Different3TestForm(new Date(), Calendar.getInstance());
        Set<ConstraintViolation<Different3TestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    /*
     * Test Form classes
     */
    @Data
    @FieldNotEquals(firstField = "first", secondField = "second")
    private class StringTestForm {
        public StringTestForm(String first, String second) {
            this.first = first;
            this.second = second;
        }
        String first;
        String second;
    }

    @Data
    @FieldNotEquals(firstField = "first", secondField = "second")
    private class BigDecimalTestForm {
        public BigDecimalTestForm(BigDecimal first, BigDecimal second) {
            this.first = first;
            this.second = second;
        }
        BigDecimal first;
        BigDecimal second;
    }

    @Data
    @FieldNotEquals(firstField = "first", secondField = "second")
    private class IntTestForm {
        public IntTestForm(int first, int second) {
            this.first = first;
            this.second = second;
        }
        int first;
        int second;
    }

    @Data
    @FieldNotEquals(firstField = "first", secondField = "second")
    private class DateTestForm {
        public DateTestForm(Date first, Date second) {
            this.first = first;
            this.second = second;
        }
        Date first;
        Date second;
    }

    @Data
    @FieldNotEquals(firstField = "first", secondField = "second")
    private class Different1TestForm {
        public Different1TestForm(String first, Integer second) {
            this.first = first;
            this.second = second;
        }
        String first;
        Integer second;
    }

    @Data
    @FieldNotEquals(firstField = "first", secondField = "second")
    private class Different2TestForm {
        public Different2TestForm(Integer first, BigDecimal second) {
            this.first = first;
            this.second = second;
        }
        Integer first;
        BigDecimal second;
    }

    @Data
    @FieldNotEquals(firstField = "first", secondField = "second")
    private class Different3TestForm {
        public Different3TestForm(Date first, Calendar second) {
            this.first = first;
            this.second = second;
        }
        Date first;
        Calendar second;
    }
}
