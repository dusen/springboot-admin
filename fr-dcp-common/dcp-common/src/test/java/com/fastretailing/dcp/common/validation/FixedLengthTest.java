/**
 * @(#)FixedLengthTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */


package com.fastretailing.dcp.common.validation;

import com.fastretailing.dcp.common.validation.annotation.FixedLength;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class FixedLengthTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        validator = TestValidatorFactory.createValidator();
    }

    /**
     * text line is null
     */
    @Test
    public void invalidWhenValueIsNull() {
        FixedLengthTestForm form = new FixedLengthTestForm(null);
        Set<ConstraintViolation<FixedLengthTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    /**
     * text line's length is 10
     * appoint message
     */
    @Test
    public void invalid1() throws Exception{
        Set<ConstraintViolation<FixedLengthTestForm1>> constraintViolations =
                validator.validate(new FixedLengthTest.FixedLengthTestForm1("0123456789"));
        assertEquals(1, constraintViolations.size());
        assertEquals("「JANコード」は、半角数字13文字（子JANで検索の場合は7文字も可）で入力して下さい。", constraintViolations.iterator().next().getMessage());
    }

    /**
     * text line's length is 10
     * do not appoint message
     */
    @Test
    public void invalid2() throws Exception{
        Set<ConstraintViolation<FixedLengthTestForm>> constraintViolations =
                validator.validate(new FixedLengthTest.FixedLengthTestForm("0123456789"));
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.fixed-length}", constraintViolations.iterator().next().getMessageTemplate());
    }

    /**
     * text line's length is 7
     * appoint message
     */
    @Test
    public void invalid3() throws Exception{
        Set<ConstraintViolation<FixedLengthTestForm1>> constraintViolations =
                validator.validate(new FixedLengthTest.FixedLengthTestForm1("1234567"));
        assertEquals(0, constraintViolations.size());
    }

    /**
     * text line is 13
     * appoint message
     */
    @Test
    public void invalid4() throws Exception{
        Set<ConstraintViolation<FixedLengthTestForm1>> constraintViolations =
                validator.validate(new FixedLengthTest.FixedLengthTestForm1("1234567890123"));
        assertEquals(0, constraintViolations.size());
    }

    /**
     * not appoint digit
     * appoint message
     */
    @Test
    public void invalid5() throws Exception{
        Set<ConstraintViolation<FixedLengthTestForm2>> constraintViolations =
                validator.validate(new FixedLengthTest.FixedLengthTestForm2("1234567"));
        assertEquals(1, constraintViolations.size());
    }

    private class FixedLengthTestForm {
        public FixedLengthTestForm(String str) {
            super();
            this.str = str;
        }

        @FixedLength(length = {"7", "13"})
        String str;
    }

    private class FixedLengthTestForm1 {
        public FixedLengthTestForm1(String str) {
            super();
            this.str = str;
        }
        @FixedLength(length = {"7", "13"}, message = "「JANコード」は、半角数字13文字（子JANで検索の場合は7文字も可）で入力して下さい。")
        String str;
    }

    private class FixedLengthTestForm2 {
        public FixedLengthTestForm2(String str) {
            super();
            this.str = str;
        }
        @FixedLength(length = {}, message = "「JANコード」は、半角数字13文字（子JANで検索の場合は7文字も可）で入力して下さい。")
        String str;
    }
}
