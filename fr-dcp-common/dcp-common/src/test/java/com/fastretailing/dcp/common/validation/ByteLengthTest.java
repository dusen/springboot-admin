/**
 * @(#)ByteLengthTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.validation;

import com.fastretailing.dcp.common.validation.annotation.ByteLength;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@Slf4j
public class ByteLengthTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        validator = TestValidatorFactory.createValidator();
    }

    /**
     * value == null
     */
    @Test
    public void isValid01() {
        Form form1 = new Form();
        Set<ConstraintViolation<Form>> actual = validator.validate(form1);

        // compare
        assertThat(actual.size(), is(0));
    }

    /**
     * normal case
     */
    @Test
    public void isValid02() {
        log.debug("ByteLengthTest.isValid02");

        Form form1 = new Form();
        form1.setFiled("hello");
        Set<ConstraintViolation<Form>> actual = validator.validate(form1);

        // compare
        assertThat(actual.size(), is(0));
    }

    /**
     * normal case
     */
    @Test
    public void isValid03() {
        log.debug("ByteLengthTest.isValid03");

        Form form1 = new Form();
        form1.setFiled("hel");
        Set<ConstraintViolation<Form>> actual = validator.validate(form1);

        // compare
        assertThat(actual.size(), is(0));
    }

    /**
     * abnormal case
     */
    @Test
    public void isValid04() {
        log.debug("ByteLengthTest.isValid04");

        Form form1 = new Form();
        form1.setFiled("hello world");
        Set<ConstraintViolation<Form>> actual = validator.validate(form1);

        // compare
        assertThat(actual.size(), is(1));
        assertThat(actual.iterator().next().getMessageTemplate(), is("{w.common.validation.byte-length}"));
    }

    /**
     * abnormal case
     */
    @Test
    public void isValid05() {
        log.debug("ByteLengthTest.isValid05");

        Form form1 = new Form();
        form1.setFiled("he");
        Set<ConstraintViolation<Form>> actual = validator.validate(form1);

        // compare
        assertThat(actual.size(), is(1));
        assertThat(actual.iterator().next().getMessageTemplate(), is("{w.common.validation.byte-length}"));
    }

    /**
     * abnormal case
     */
    @Test
    public void isValid06() {
        log.debug("ByteLengthTest.isValid05");

        InvalidCharsetForm form1 = new InvalidCharsetForm();
        form1.setFiled("he");
        Set<ConstraintViolation<InvalidCharsetForm>> actual = validator.validate(form1);

        // compare
        assertThat(actual.size(), is(1));
        assertThat(actual.iterator().next().getMessageTemplate(), is("{w.common.validation.byte-length}"));
    }

    @Data
    public class Form {
        @ByteLength(min = 3, max = 5)
        private String filed;
    }

    @Data
    public class InvalidCharsetForm {
        @ByteLength(min = 3, max = 5, charset = "test")
        private String filed;
    }
}
