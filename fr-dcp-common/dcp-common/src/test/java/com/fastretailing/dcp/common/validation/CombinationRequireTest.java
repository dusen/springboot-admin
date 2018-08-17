/**
 * @(#)CombinationRequireTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.validation;

import com.fastretailing.dcp.common.validation.annotation.CombinationRequire;
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
public class CombinationRequireTest extends MessageTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        validator = TestValidatorFactory.createValidator();
    }

    /**
     * check whether the target field size is 0
     */
    @Test
    public void isValid01() {
        log.debug("CombinationRequireTest.isValid01");

        NotCheckForm form = new NotCheckForm();
        Set<ConstraintViolation<NotCheckForm>> actual = validator.validate(form);

        // compare
        assertThat(actual.size(), is(0));
    }

    /**
     * abnormal case
     */
    @Test
    public void isValid02() {
        log.debug("CombinationRequireTest.isValid02");

        CheckForm form = new CheckForm();
        form.setFieldA("first");
        Set<ConstraintViolation<CheckForm>> actual = validator.validate(form);

        // compare
        assertThat(actual.size(), is(1));
        assertThat(actual.iterator().next().getMessageTemplate(), is("{w.common.validation.combination-require}"));
    }

    /**
     * normal case
     */
    @Test
    public void isValid03() {
        log.debug("CombinationRequireTest.isValid03");

        CheckForm form = new CheckForm();
        form.setFieldA("first");
        form.setFieldB("second");
        Set<ConstraintViolation<CheckForm>> actual = validator.validate(form);

        // compare
        assertThat(actual.size(), is(0));
    }

    /**
     * abnormal case
     */
    @Test
    public void isValid03_1() {
        log.debug("CombinationRequireTest.isValid03_1");

        CheckForm form = new CheckForm();
        form.setFieldA("first");
        form.setFieldB("");
        Set<ConstraintViolation<CheckForm>> actual = validator.validate(form);

        // compare
        assertThat(actual.size(), is(1));
        assertThat(actual.iterator().next().getMessageTemplate(), is("{w.common.validation.combination-require}"));
    }

    /**
     * all null
     */
    @Test
    public void isValid04() {
        log.debug("CombinationRequireTest.isValid04");

        CheckForm form = new CheckForm();

        Set<ConstraintViolation<CheckForm>> actual = validator.validate(form);

        // compare
        assertThat(actual.size(), is(0));
    }

    @CombinationRequire(checkFields = {})
    @Data
    public class NotCheckForm {
        private String fieldA;
        private String fieldB;
    }

    @CombinationRequire(checkFields = {"fieldA", "fieldB"})
    @Data
    public class CheckForm {
        private String fieldA;
        private String fieldB;
    }
}
