/**
 * @(#)StringDateTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */


package com.fastretailing.dcp.common.validation;

import com.fastretailing.dcp.common.constants.DateTimeFormatterEnum;
import com.fastretailing.dcp.common.validation.annotation.StringDate;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class StringDateTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        validator = TestValidatorFactory.createValidator();
    }

    // pattern = yyyy/MM/dd
    @Test
    public void validYMDPatternWhenValueFollowFormat() {
        YMDTestForm form = new YMDTestForm("2016/04/01");
        Set<ConstraintViolation<YMDTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void invalidYMDPatternWhenValueNotFollowFormat() {
        YMDTestForm form = new YMDTestForm("2016-04-01");
        Set<ConstraintViolation<YMDTestForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.string-date}", constraintViolations.iterator().next().getMessageTemplate());
    }

    // pattern = yyyy/MM/dd hh:mm:ss
    @Test
    public void validYMDHMSPatternWhenValueFollowFormat() {
        YMDHMSTestForm form = new YMDHMSTestForm("2016/04/01 13:00:40");
        Set<ConstraintViolation<YMDHMSTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void invalidYMDHMSPatternWhenValueNotFollowFormat() {
        YMDHMSTestForm form = new YMDHMSTestForm("2016-04-01 13:00:40");
        Set<ConstraintViolation<YMDHMSTestForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.string-date}", constraintViolations.iterator().next().getMessageTemplate());
    }

    // pattern = ""
    @Test
    public void validWhenPatternIsBlank() {
        YMDTestForm form = new YMDTestForm("2016/04/01");
        Set<ConstraintViolation<YMDTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    // pattern = 年月日
    @Test
    public void invalidWhenPatternIsNotDateFormat() {
        InvalidPatternTestForm form = new InvalidPatternTestForm("2016-04-01");
        Set<ConstraintViolation<InvalidPatternTestForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.string-date}", constraintViolations.iterator().next().getMessageTemplate());
    }

    // lenient = true, value = 2016/03/32
    @Test
    public void validWhenLenientIsTrueAndNotExistDate() {
        LenientTrueTestForm form = new LenientTrueTestForm("2016/03/32");
        Set<ConstraintViolation<LenientTrueTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    // value = ""
    @Test
    public void validWhenValueIsBlank() {
        YMDTestForm form = new YMDTestForm("");
        Set<ConstraintViolation<YMDTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    // default patterns
    @Test
    public void validWhenPatternsIsDefaultAndValueIsDefaultPattern() {
        DefaultValueTestForm form = new DefaultValueTestForm("2016/04/19");
        Set<ConstraintViolation<DefaultValueTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());

        form = new DefaultValueTestForm("2016-04-19");
        constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());

        form = new DefaultValueTestForm("2016.04.19");
        constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());

        form = new DefaultValueTestForm("20160419");
        constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void invalidWhenPatternsIsDefaultAndValueIsNotDefaultPattern() {
        DefaultValueTestForm form = new DefaultValueTestForm("2016/04/19 12:00:00");
        Set<ConstraintViolation<DefaultValueTestForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.string-date}", constraintViolations.iterator().next().getMessageTemplate());
    }

    // specify multiple pattern
    @Test
    public void validMultiPatternWhenValueFollowFormat() {
        MultiplePatternTestForm form = new MultiplePatternTestForm("2016-04-19");
        Set<ConstraintViolation<MultiplePatternTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());

        form = new MultiplePatternTestForm("2016-04-19 12:00:00");
        constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void invalidMultiPatternWhenValueNotFollowFormat() {
        MultiplePatternTestForm form = new MultiplePatternTestForm("2016/04/19");
        Set<ConstraintViolation<MultiplePatternTestForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.string-date}", constraintViolations.iterator().next().getMessageTemplate());

        form = new MultiplePatternTestForm("2016/04/19 12:00:00");
        constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.string-date}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void invalidWhenNotTimeZone() {
        NotTimeZoneForm form = new NotTimeZoneForm("2016-04-19T12:00:00:00");
        Set<ConstraintViolation<NotTimeZoneForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.string-date}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void invalidMultiWhenNotTimeZone() {
        NotTimeZoneForm form = new NotTimeZoneForm("2016-04-19 12:00:00:00+10:00");
        Set<ConstraintViolation<NotTimeZoneForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.string-date}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void validMultiWhenNotTimeZone() {
        MultiNotTimeZoneForm form = new MultiNotTimeZoneForm("2018-01-01T00:00:00+09:00");
        Set<ConstraintViolation<MultiNotTimeZoneForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    /*
     * test form classes
     */
    private class YMDTestForm {
        public YMDTestForm(String date) {
            super();
            this.date = date;
        }
        @StringDate(patterns = "yyyy/MM/dd")
        String date;
    }

    private class YMDHMSTestForm {
        public YMDHMSTestForm(String date) {
            super();
            this.date = date;
        }
        @StringDate(patterns = "yyyy/MM/dd HH:mm:ss")
        String date;
    }

    private class InvalidPatternTestForm {
        public InvalidPatternTestForm(String date) {
            super();
            this.date = date;
        }
        @StringDate(patterns = "年月日")
        String date;
    }

    private class LenientTrueTestForm {
        public LenientTrueTestForm(String date) {
            super();
            this.date = date;
        }
        @StringDate(patterns = "yyyy/MM/dd", lenient = true)
        String date;
    }

    private class DefaultValueTestForm {
        public DefaultValueTestForm(String date) {
            super();
            this.date = date;
        }
        @StringDate
        String date;
    }

    private class MultiplePatternTestForm {
        public MultiplePatternTestForm(String date) {
            super();
            this.date = date;
        }
        @StringDate(patterns = {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"})
        String date;
    }

    private class NotTimeZoneForm {
        public NotTimeZoneForm(String date) {
            super();
            this.date = date;
        }
        @StringDate(dateTimeFormatter = {DateTimeFormatterEnum.ISO_OFFSET_DATE_TIME})
        String date;
    }

    private class MultiNotTimeZoneForm {
        public MultiNotTimeZoneForm(String date) {
            super();
            this.date = date;
        }
        @StringDate(dateTimeFormatter = {DateTimeFormatterEnum.ISO_OFFSET_DATE_TIME, DateTimeFormatterEnum.ISO_ZONED_DATE_TIME, DateTimeFormatterEnum.ISO_DATE_TIME, DateTimeFormatterEnum.ISO_LOCAL_DATE_TIME})
        String date;
    }
}
