/**
 * @(#)ConditionalEmptyTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.validation;

import com.fastretailing.dcp.common.validation.annotation.ConditionalEmpty;
import lombok.Data;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class ConditionalEmptyTest extends MessageTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        validator = TestValidatorFactory.createValidator();
    }

    // String Target
    @Test
    public void invalidStringTargetWhenConditionIsNotNullTargetIsNotBlank() {
        StringTargetForm form = new StringTargetForm("aaa", "bbb");
        Set<ConstraintViolation<StringTargetForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.conditional-empty}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void validStringTargetWhenConditionIsNotNullTargetIsBlank() {
        StringTargetForm form = new StringTargetForm("aaa", "");
        Set<ConstraintViolation<StringTargetForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validStringTargetWhenConditionIsNotNullTargetIsNull() {
        StringTargetForm form = new StringTargetForm("aaa", null);
        Set<ConstraintViolation<StringTargetForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validStringTargetWhenConditionIsBlankTargetIsNotBlank() {
        StringTargetForm form = new StringTargetForm("", "bbb");
        Set<ConstraintViolation<StringTargetForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validStringTargetWhenConditionIsBlankTargetIsBlank() {
        StringTargetForm form = new StringTargetForm("", "");
        Set<ConstraintViolation<StringTargetForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validStringTargetWhenConditionIsBlankTargetIsNull() {
        StringTargetForm form = new StringTargetForm("", null);
        Set<ConstraintViolation<StringTargetForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validStringTargetWhenConditionIsNullTargetIsNotBlank() {
        StringTargetForm form = new StringTargetForm(null, "bbb");
        Set<ConstraintViolation<StringTargetForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validStringTargetWhenConditionIsNullTargetIsBlank() {
        StringTargetForm form = new StringTargetForm(null, "");
        Set<ConstraintViolation<StringTargetForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validStringTargetWhenConditionIsNullTargetIsNull() {
        StringTargetForm form = new StringTargetForm(null, null);
        Set<ConstraintViolation<StringTargetForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    // Integer Target
    @Test
    public void invalidIntegerTargetWhenConditionIsNotNullTargetIsNotNull() {
        IntegerTargetForm form = new IntegerTargetForm("aaa", 10);
        Set<ConstraintViolation<IntegerTargetForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.conditional-empty}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void validIntegerTargetWhenConditionIsNotNullTargetIsNull() {
        IntegerTargetForm form = new IntegerTargetForm("aaa", null);
        Set<ConstraintViolation<IntegerTargetForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validIntegerTargetWhenConditionIsBlankTargetIsNotNull() {
        IntegerTargetForm form = new IntegerTargetForm("", 10);
        Set<ConstraintViolation<IntegerTargetForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validIntegerTargetWhenConditionIsBlankTargetIsNull() {
        IntegerTargetForm form = new IntegerTargetForm("", null);
        Set<ConstraintViolation<IntegerTargetForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validIntegerTargetWhenConditionIsNullTargetIsNotNull() {
        IntegerTargetForm form = new IntegerTargetForm(null, 10);
        Set<ConstraintViolation<IntegerTargetForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validIntegerTargetWhenConditionIsNullTargetIsNull() {
        IntegerTargetForm form = new IntegerTargetForm(null, null);
        Set<ConstraintViolation<IntegerTargetForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    // blank
    @Test
    public void validBlankTargetWhenAnnotationAragsAreBlank() {
        BlankTargetForm form = new BlankTargetForm("a", "b");
        Set<ConstraintViolation<BlankTargetForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validBlankTargetWhenAnnotationAragConditionIsBlank() {
        BlankConditionTargetForm form = new BlankConditionTargetForm("a", "b");
        Set<ConstraintViolation<BlankConditionTargetForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validBlankTargetWhenAnnotationAragTargetIsBlank() {
        BlankTargetTargetForm form = new BlankTargetTargetForm("a", "b");
        Set<ConstraintViolation<BlankTargetTargetForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    /*
     * Test Form classes
     */
    @Data
    @ConditionalEmpty(condition = "condition", target = "target")
    private class StringTargetForm {
        public StringTargetForm(String condition, String target) {
            this.condition = condition;
            this.target = target;
        }
        String condition;
        String target;
    }

    @Data
    @ConditionalEmpty(condition = "condition", target = "target")
    private class IntegerTargetForm {
        public IntegerTargetForm(String condition, Integer target) {
            this.condition = condition;
            this.target = target;
        }
        String condition;
        Integer target;
    }

    @Data
    @ConditionalEmpty(condition = "", target = "")
    private class BlankTargetForm {
        public BlankTargetForm(String condition, String target) {
            this.condition = condition;
            this.target = target;
        }
        String condition;
        String target;
    }

    @Data
    @ConditionalEmpty(condition = "", target = "target")
    private class BlankConditionTargetForm {
        public BlankConditionTargetForm(String condition, String target) {
            this.condition = condition;
            this.target = target;
        }
        String condition;
        String target;
    }

    @Data
    @ConditionalEmpty(condition = "condition", target = "")
    private class BlankTargetTargetForm {
        public BlankTargetTargetForm(String condition, String target) {
            this.condition = condition;
            this.target = target;
        }
        String condition;
        String target;
    }
}
