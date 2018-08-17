/**
 * @(#)PositiveNumberTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.common.validation;

import com.fastretailing.dcp.common.validation.annotation.PositiveNumber;
import com.fastretailing.dcp.common.validation.validator.PositiveNumberValidator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        @ContextConfiguration(locations = {
                "classpath*:com/fastretailing/dcp/common/validation/test-context.xml"
        })
})
@Slf4j
public class PositiveNumberTest {

    private static Validator validator;
    private static PositiveNumberValidator positiveNumberValidator;

    @Autowired
    private static PositiveNumberValidator positiveNumberValidatorBean;

    @BeforeClass
    public static void setUp() {
        validator = TestValidatorFactory.createValidator();
        positiveNumberValidator = new PositiveNumberValidator();
    }

    // BigDecimal
    @Test
    public void validWhenValueIsBigDecimal5() {
        BigDecimalTestForm form = new BigDecimalTestForm(BigDecimal.valueOf(5));
        Set<ConstraintViolation<BigDecimalTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validWhenValueIsBigDecimal1() {
        BigDecimalTestForm form = new BigDecimalTestForm(BigDecimal.valueOf(1));
        Set<ConstraintViolation<BigDecimalTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validWhenValueIsBigDecimal0() {
        BigDecimalTestForm form = new BigDecimalTestForm(BigDecimal.valueOf(0));
        Set<ConstraintViolation<BigDecimalTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void invalidWhenValueIsBigDecimalM1() {
        BigDecimalTestForm form = new BigDecimalTestForm(BigDecimal.valueOf(-1));
        Set<ConstraintViolation<BigDecimalTestForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.positive-number}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void invalidWhenValueIsBigDecimalM5() {
        BigDecimalTestForm form = new BigDecimalTestForm(BigDecimal.valueOf(-5));
        Set<ConstraintViolation<BigDecimalTestForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.positive-number}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void validWhenValueIsBigDecimal1point1() {
        BigDecimalTestForm form = new BigDecimalTestForm(BigDecimal.valueOf(1.1));
        Set<ConstraintViolation<BigDecimalTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validWhenValueIsBigDecimal0point0() {
        BigDecimalTestForm form = new BigDecimalTestForm(BigDecimal.valueOf(0.0));
        Set<ConstraintViolation<BigDecimalTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    // BigInteger
    @Test
    public void validWhenValueIsBigInteger5() {
        BigIntegerTestForm form = new BigIntegerTestForm(BigInteger.valueOf(5));
        Set<ConstraintViolation<BigIntegerTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validWhenValueIsBigInteger1() {
        BigIntegerTestForm form = new BigIntegerTestForm(BigInteger.valueOf(1));
        Set<ConstraintViolation<BigIntegerTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validWhenValueIsBigInteger0() {
        BigIntegerTestForm form = new BigIntegerTestForm(BigInteger.valueOf(0));
        Set<ConstraintViolation<BigIntegerTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void invalidWhenValueIsBigIntegerM1() {
        BigIntegerTestForm form = new BigIntegerTestForm(BigInteger.valueOf(-1));
        Set<ConstraintViolation<BigIntegerTestForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.positive-number}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void invalidWhenValueIsBigIntegerM5() {
        BigIntegerTestForm form = new BigIntegerTestForm(BigInteger.valueOf(-5));
        Set<ConstraintViolation<BigIntegerTestForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.positive-number}", constraintViolations.iterator().next().getMessageTemplate());
    }

    // byte
    @Test
    public void validWhenValueIsByte5() {
        ByteTestForm form = new ByteTestForm(intToByte(5));
        Set<ConstraintViolation<ByteTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validWhenValueIsByte1() {
        ByteTestForm form = new ByteTestForm(intToByte(1));
        Set<ConstraintViolation<ByteTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validWhenValueIsByte0() {
        ByteTestForm form = new ByteTestForm(intToByte(0));
        Set<ConstraintViolation<ByteTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void invalidWhenValueIsByteM1() {
        ByteTestForm form = new ByteTestForm(intToByte(-1));
        Set<ConstraintViolation<ByteTestForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.positive-number}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void invalidWhenValueIsByteM5() {
        ByteTestForm form = new ByteTestForm(intToByte(-5));
        Set<ConstraintViolation<ByteTestForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.positive-number}", constraintViolations.iterator().next().getMessageTemplate());
    }

    // Byte
    @Test
    public void validWhenValueIsByteWrapper5() {
        ByteWrapperTestForm form = new ByteWrapperTestForm(intToByteWrapper(5));
        Set<ConstraintViolation<ByteWrapperTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validWhenValueIsByteWrapper1() {
        ByteWrapperTestForm form = new ByteWrapperTestForm(intToByteWrapper(1));
        Set<ConstraintViolation<ByteWrapperTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validWhenValueIsByteWrapper0() {
        ByteWrapperTestForm form = new ByteWrapperTestForm(intToByteWrapper(0));
        Set<ConstraintViolation<ByteWrapperTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void invalidWhenValueIsByteWrapperM1() {
        ByteWrapperTestForm form = new ByteWrapperTestForm(intToByteWrapper(-1));
        Set<ConstraintViolation<ByteWrapperTestForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.positive-number}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void invalidWhenValueIsByteWrapperM5() {
        ByteWrapperTestForm form = new ByteWrapperTestForm(intToByteWrapper(-5));
        Set<ConstraintViolation<ByteWrapperTestForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.positive-number}", constraintViolations.iterator().next().getMessageTemplate());
    }

    // int
    @Test
    public void validWhenValueIsInt5() {
        IntTestForm form = new IntTestForm(5);
        Set<ConstraintViolation<IntTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validWhenValueIsInt1() {
        IntTestForm form = new IntTestForm(1);
        Set<ConstraintViolation<IntTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validWhenValueIsInt0() {
        IntTestForm form = new IntTestForm(0);
        Set<ConstraintViolation<IntTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void invalidWhenValueIsIntM1() {
        IntTestForm form = new IntTestForm(-1);
        Set<ConstraintViolation<IntTestForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.positive-number}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void invalidWhenValueIsIntM5() {
        IntTestForm form = new IntTestForm(-5);
        Set<ConstraintViolation<IntTestForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.positive-number}", constraintViolations.iterator().next().getMessageTemplate());
    }

    // Integer
    @Test
    public void validWhenValueIsInteger5() {
        IntegerTestForm form = new IntegerTestForm(Integer.valueOf(5));
        Set<ConstraintViolation<IntegerTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void validWhenValueIsInteger1() {
        IntegerTestForm form = new IntegerTestForm(Integer.valueOf(1));
        Set<ConstraintViolation<IntegerTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void validWhenValueIsInteger0() {
        IntegerTestForm form = new IntegerTestForm(Integer.valueOf(0));
        Set<ConstraintViolation<IntegerTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void invalidWhenValueIsIntegerM1() {
        IntegerTestForm form = new IntegerTestForm(Integer.valueOf(-1));
        Set<ConstraintViolation<IntegerTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(1, ConstraintViolations.size());
        assertEquals("{w.common.validation.positive-number}", ConstraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void invalidWhenValueIsIntegerM5() {
        IntegerTestForm form = new IntegerTestForm(Integer.valueOf(-5));
        Set<ConstraintViolation<IntegerTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(1, ConstraintViolations.size());
        assertEquals("{w.common.validation.positive-number}", ConstraintViolations.iterator().next().getMessageTemplate());
    }

    // short
    @Test
    public void validWhenValueIsShort5() {
        ShortTestForm form = new ShortTestForm(Short.valueOf("5"));
        Set<ConstraintViolation<ShortTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void validWhenValueIsShort1() {
        ShortTestForm form = new ShortTestForm(Short.valueOf("1"));
        Set<ConstraintViolation<ShortTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void validWhenValueIsShort0() {
        ShortTestForm form = new ShortTestForm(Short.valueOf("0"));
        Set<ConstraintViolation<ShortTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void invalidWhenValueIsShortM1() {
        ShortTestForm form = new ShortTestForm(Short.valueOf("-1"));
        Set<ConstraintViolation<ShortTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(1, ConstraintViolations.size());
        assertEquals("{w.common.validation.positive-number}", ConstraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void invalidWhenValueIsShortM5() {
        ShortTestForm form = new ShortTestForm(Short.valueOf("-5"));
        Set<ConstraintViolation<ShortTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(1, ConstraintViolations.size());
        assertEquals("{w.common.validation.positive-number}", ConstraintViolations.iterator().next().getMessageTemplate());
    }

    // Short
    @Test
    public void validWhenValueIsShortWrapper5() {
        ShortWrapperTestForm form = new ShortWrapperTestForm(Short.valueOf("5"));
        Set<ConstraintViolation<ShortWrapperTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void validWhenValueIsShortWrapper1() {
        ShortWrapperTestForm form = new ShortWrapperTestForm(Short.valueOf("1"));
        Set<ConstraintViolation<ShortWrapperTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void validWhenValueIsShortWrapper0() {
        ShortWrapperTestForm form = new ShortWrapperTestForm(Short.valueOf("0"));
        Set<ConstraintViolation<ShortWrapperTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void invalidWhenValueIsShortWrapperM1() {
        ShortWrapperTestForm form = new ShortWrapperTestForm(Short.valueOf("-1"));
        Set<ConstraintViolation<ShortWrapperTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(1, ConstraintViolations.size());
        assertEquals("{w.common.validation.positive-number}", ConstraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void invalidWhenValueIsShortWrapperM5() {
        ShortWrapperTestForm form = new ShortWrapperTestForm(Short.valueOf("-5"));
        Set<ConstraintViolation<ShortWrapperTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(1, ConstraintViolations.size());
        assertEquals("{w.common.validation.positive-number}", ConstraintViolations.iterator().next().getMessageTemplate());
    }

    // long
    @Test
    public void validWhenValueIsLong5() {
        LongTestForm form = new LongTestForm(Long.valueOf(5));
        Set<ConstraintViolation<LongTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void validWhenValueIsLong1() {
        LongTestForm form = new LongTestForm(Long.valueOf(1));
        Set<ConstraintViolation<LongTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void validWhenValueIsLong0() {
        LongTestForm form = new LongTestForm(Long.valueOf(0));
        Set<ConstraintViolation<LongTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void invalidWhenValueIsLongM1() {
        LongTestForm form = new LongTestForm(Long.valueOf(-1));
        Set<ConstraintViolation<LongTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(1, ConstraintViolations.size());
        assertEquals("{w.common.validation.positive-number}", ConstraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void invalidWhenValueIsLongM5() {
        LongTestForm form = new LongTestForm(Long.valueOf(-5));
        Set<ConstraintViolation<LongTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(1, ConstraintViolations.size());
        assertEquals("{w.common.validation.positive-number}", ConstraintViolations.iterator().next().getMessageTemplate());
    }

    // Long
    @Test
    public void validWhenValueIsLongWrapper5() {
        LongWrapperTestForm form = new LongWrapperTestForm(Long.valueOf(5));
        Set<ConstraintViolation<LongWrapperTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void validWhenValueIsLongWrapper1() {
        LongWrapperTestForm form = new LongWrapperTestForm(Long.valueOf(1));
        Set<ConstraintViolation<LongWrapperTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void validWhenValueIsLongWrapper0() {
        LongWrapperTestForm form = new LongWrapperTestForm(Long.valueOf(0));
        Set<ConstraintViolation<LongWrapperTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void invalidWhenValueIsLongWrapperM1() {
        LongWrapperTestForm form = new LongWrapperTestForm(Long.valueOf(-1));
        Set<ConstraintViolation<LongWrapperTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(1, ConstraintViolations.size());
        assertEquals("{w.common.validation.positive-number}", ConstraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void invalidWhenValueIsLongWrapperM5() {
        LongWrapperTestForm form = new LongWrapperTestForm(Long.valueOf(-5));
        Set<ConstraintViolation<LongWrapperTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(1, ConstraintViolations.size());
        assertEquals("{w.common.validation.positive-number}", ConstraintViolations.iterator().next().getMessageTemplate());
    }

    // not number type
    @Test
    public void validWhenValueIsNotNumberType() {
        StringTestForm form = new StringTestForm("1");
        Set<ConstraintViolation<StringTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    // null
    @Test
    public void validWhenValueIsNull() {
        StringTestForm form = new StringTestForm(null);
        Set<ConstraintViolation<StringTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    /*
     * Test Form classes
     */
    private class BigDecimalTestForm {
        public BigDecimalTestForm(BigDecimal num) {
            this.num = num;
        }
        @PositiveNumber
        BigDecimal num;
    }

    private class BigIntegerTestForm {
        public BigIntegerTestForm(BigInteger num) {
            this.num = num;
        }
        @PositiveNumber
        BigInteger num;
    }

    private class ByteTestForm {
        public ByteTestForm(byte[] num) {
            this.num = num;
        }
        @PositiveNumber
        byte[] num;
    }

    private class ByteWrapperTestForm {
        public ByteWrapperTestForm(Byte[] num) {
            this.num = num;
        }
        @PositiveNumber
        Byte[] num;
    }

    private class IntTestForm {
        public IntTestForm(int num) {
            this.num = num;
        }
        @PositiveNumber
        int num;
    }

    private class IntegerTestForm {
        public IntegerTestForm(Integer num) {
            this.num = num;
        }
        @PositiveNumber
        Integer num;
    }

    private class ShortTestForm {
        public ShortTestForm(short num) {
            this.num = num;
        }
        @PositiveNumber
        short num;
    }

    private class ShortWrapperTestForm {
        public ShortWrapperTestForm(Short num) {
            this.num = num;
        }
        @PositiveNumber
        Short num;
    }

    private class LongTestForm {
        public LongTestForm(long num) {
            this.num = num;
        }
        @PositiveNumber
        long num;
    }

    private class LongWrapperTestForm {
        public LongWrapperTestForm(Long num) {
            this.num = num;
        }
        @PositiveNumber
        Long num;
    }

    private class StringTestForm {
        public StringTestForm(String num) {
            this.num = num;
        }
        @PositiveNumber
        String num;
    }

    /*
     * test util
     */
    private byte[] intToByte(int num) {
        ByteBuffer buf = ByteBuffer.allocate(Long.SIZE / Byte.SIZE);
        buf.putInt(num);
        return buf.array();
    }

    private Byte[] intToByteWrapper(int num) {
        ByteBuffer buf = ByteBuffer.allocate(Long.SIZE / Byte.SIZE);
        buf.putInt(num);
        byte[] tmparray = buf.array();
        Byte[] array = new Byte[tmparray.length];
        for (int i = 0; i < tmparray.length; i++) {
            array[i] = tmparray[i];
        }
        return array;
    }

    /**
     * valueがnull
     */
    @Test
    public void isValid01() {
        log.debug("PositiveNumberTest.isValid01");

        // validate
        boolean actual = positiveNumberValidator.isValid(null, null);

        // 比較する
        assertThat(actual, is(true));
    }

    /**
     * valueがBigDecimal(>0)
     */
    @Test
    public void isValid02() {
        log.debug("PositiveNumberTest.isValid02");

        BigDecimal value = new BigDecimal("20");

        // validate
        boolean actual = positiveNumberValidator.isValid(value, null);

        // 比較する
        assertThat(actual, is(true));
    }

    /**
     * valueがBigDecimal(=0)
     */
    @Test
    public void isValid03() {
        log.debug("PositiveNumberTest.isValid03");

        BigDecimal value = new BigDecimal("0");

        // validate
        boolean actual = positiveNumberValidator.isValid(value, null);

        // 比較する
        assertThat(actual, is(true));
    }

    /**
     * valueがBigDecimal(<0)
     */
    @Test
    public void isValid04() {
        log.debug("PositiveNumberTest.isValid04");

        BigDecimal value = new BigDecimal("-10");

        // validate
        boolean actual = positiveNumberValidator.isValid(value, null);

        // 比較する
        assertThat(actual, is(false));
    }

    /**
     * valueがBigInteger(>0)
     */
    @Test
    public void isValid05() {
        log.debug("PositiveNumberTest.isValid05");

        BigInteger value = new BigInteger("20");

        // validate
        boolean actual = positiveNumberValidator.isValid(value, null);

        // 比較する
        assertThat(actual, is(true));
    }

    /**
     * valueがBigInteger(=0)
     */
    @Test
    public void isValid06() {
        log.debug("PositiveNumberTest.isValid06");

        BigInteger value = new BigInteger("0");

        // validate
        boolean actual = positiveNumberValidator.isValid(value, null);

        // 比較する
        assertThat(actual, is(true));
    }

    /**
     * valueがBigInteger(<0)
     */
    @Test
    public void isValid07() {
        log.debug("PositiveNumberTest.isValid07");

        BigInteger value = new BigInteger("-10");

        // validate
        boolean actual = positiveNumberValidator.isValid(value, null);

        // 比較する
        assertThat(actual, is(false));
    }

    /**
     * valueがLong(>0)
     */
    @Test
    public void isValid08() {
        log.debug("PositiveNumberTest.isValid08");

        Long value = new Long("20");

        // validate
        boolean actual = positiveNumberValidator.isValid(value, null);

        // 比較する
        assertThat(actual, is(true));
    }

    /**
     * valueがLong(=0)
     */
    @Test
    public void isValid09() {
        log.debug("PositiveNumberTest.isValid09");

        Long value = new Long("0");

        // validate
        boolean actual = positiveNumberValidator.isValid(value, null);

        // 比較する
        assertThat(actual, is(true));
    }

    /**
     * valueがLong(<0)
     */
    @Test
    public void isValid10() {
        log.debug("PositiveNumberTest.isValid10");

        Long value = new Long("-10");

        // validate
        boolean actual = positiveNumberValidator.isValid(value, null);

        // 比較する
        assertThat(actual, is(false));
    }

    /**
     * valueがInteger(>0)
     */
    @Test
    public void isValid11() {
        log.debug("PositiveNumberTest.isValid11");

        Integer value = new Integer("20");

        // validate
        boolean actual = positiveNumberValidator.isValid(value, null);

        // 比較する
        assertThat(actual, is(true));
    }

    /**
     * valueがInteger(=0)
     */
    @Test
    public void isValid12() {
        log.debug("PositiveNumberTest.isValid12");

        Integer value = new Integer("0");

        // validate
        boolean actual = positiveNumberValidator.isValid(value, null);

        // 比較する
        assertThat(actual, is(true));
    }

    /**
     * valueがInteger(<0)
     */
    @Test
    public void isValid13() {
        log.debug("PositiveNumberTest.isValid13");

        Integer value = new Integer("-10");

        // validate
        boolean actual = positiveNumberValidator.isValid(value, null);

        // 比較する
        assertThat(actual, is(false));
    }

    /**
     * valueがShort(>0)
     */
    @Test
    public void isValid14() {
        log.debug("PositiveNumberTest.isValid14");

        Short value = new Short("20");

        // validate
        boolean actual = positiveNumberValidator.isValid(value, null);

        // 比較する
        assertThat(actual, is(true));
    }

    /**
     * valueがShort(=0)
     */
    @Test
    public void isValid15() {
        log.debug("PositiveNumberTest.isValid15");

        Short value = new Short("0");

        // validate
        boolean actual = positiveNumberValidator.isValid(value, null);

        // 比較する
        assertThat(actual, is(true));
    }

    /**
     * valueがShort(<0)
     */
    @Test
    public void isValid16() {
        log.debug("PositiveNumberTest.isValid16");

        Short value = new Short("-10");

        // validate
        boolean actual = positiveNumberValidator.isValid(value, null);

        // 比較する
        assertThat(actual, is(false));
    }

    /**
     * valueがbyte(>0)
     */
    @Test
    public void isValid17() {
        log.debug("PositiveNumberTest.isValid17");

        Byte[] value = {0, 0, 0, 0, 0, 0, 0, 1};

        // validate
        boolean actual = positiveNumberValidator.isValid(value, null);

        // 比較する
        assertThat(actual, is(true));
    }

    /**
     * valueがbyte(=0)
     */
    @Test
    public void isValid18() {
        log.debug("PositiveNumberTest.isValid18");

        Byte[] value = {0, 0, 0, 0, 0, 0, 0, 0};

        // validate
        boolean actual = positiveNumberValidator.isValid(value, null);

        // 比較する
        assertThat(actual, is(true));
    }

    /**
     * valueがbyte(<0)
     */
    @Test
    public void isValid19() {
        log.debug("PositiveNumberTest.isValid19");

        byte[] value = {-1, -1, -1, -1, -1, -1, -1, -1};

        // validate
        boolean actual = positiveNumberValidator.isValid(value, null);

        // 比較する
        assertThat(actual, is(false));
    }

    /**
     * valueがByte(>0)
     */
    @Test
    public void isValid20() {
        log.debug("PositiveNumberTest.isValid20");

        Byte[] value = {0, 0, 0, 0, 0, 0, 0, 1};

        // validate
        boolean actual = positiveNumberValidator.isValid(value, null);

        // 比較する
        assertThat(actual, is(true));
    }

    /**
     * valueがByte(=0)
     */
    @Test
    public void isValid21() {
        log.debug("PositiveNumberTest.isValid21");

        Byte[] value = {0, 0, 0, 0, 0, 0, 0, 0};

        // validate
        boolean actual = positiveNumberValidator.isValid(value, null);

        // 比較する
        assertThat(actual, is(true));
    }

    /**
     * valueがByte(<0)
     */
    @Test
    public void isValid22() {
        log.debug("PositiveNumberTest.isValid22");

        Byte[] value = {-1, -1, -1, -1, -1, -1, -1, -1};

        // validate
        boolean actual = positiveNumberValidator.isValid(value, null);

        // 比較する
        assertThat(actual, is(false));
    }

    /**
     * valueがObject
     */
    @Test
    public void isValid23() {
        log.debug("PositiveNumberTest.isValid23");

        ClassA classA = new ClassA();

        // validate
        boolean actual = positiveNumberValidator.isValid(classA, null);

        // 比較する
        assertThat(actual, is(true));
    }

    @Data
    private class ClassA {
        private String fieldA;
    }
}
