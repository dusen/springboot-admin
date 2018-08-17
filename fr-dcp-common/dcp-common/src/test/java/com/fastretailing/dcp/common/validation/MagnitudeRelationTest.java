/**
 * @(#)MagnitudeRelationTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */


package com.fastretailing.dcp.common.validation;

import com.fastretailing.dcp.common.validation.annotation.MagnitudeRelation;
import com.fastretailing.dcp.common.validation.validator.MagnitudeRelationValidator;
import lombok.Data;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        @ContextConfiguration(locations = {
                "classpath*:com/fastretailing/dcp/common/validation/test-context.xml"
        })
})
public class MagnitudeRelationTest extends MessageTest {

    private static Validator validator;

    @Autowired
    private static MagnitudeRelationValidator magnitudeRelation;

    @BeforeClass
    public static void setUp() {
        validator = TestValidatorFactory.createValidator();
    }

    // BigDecimal
    @Test
    public void validBigDecimalWhenSmallIsLessThanLarge() {
        BigDecimalTestForm form = new BigDecimalTestForm(BigDecimal.valueOf(1), BigDecimal.valueOf(2));
        Set<ConstraintViolation<BigDecimalTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validBigDecimalWhenSmallEqualLarge() {
        BigDecimalTestForm form = new BigDecimalTestForm(BigDecimal.valueOf(2), BigDecimal.valueOf(2));
        Set<ConstraintViolation<BigDecimalTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void invalidBigDecimalWhenSmallIsGreaterThanLarge() {
        BigDecimalTestForm form = new BigDecimalTestForm(BigDecimal.valueOf(3), BigDecimal.valueOf(2));
        Set<ConstraintViolation<BigDecimalTestForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.magnitude-relation-can-equal}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void validBigDecimalWhenValuesIsNull() {
        BigDecimalTestForm form = new BigDecimalTestForm(null, null);
        Set<ConstraintViolation<BigDecimalTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void invalidBigDecimalWhenSmallIsNull() {
        BigDecimalTestForm form = new BigDecimalTestForm(null, BigDecimal.valueOf(2));
        Set<ConstraintViolation<BigDecimalTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void invalidBigDecimalWhenLargeIsNull() {
        BigDecimalTestForm form = new BigDecimalTestForm(BigDecimal.valueOf(3), null);
        Set<ConstraintViolation<BigDecimalTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    // BigInteger
    @Test
    public void validBigIntegerWhenSmallIsLessThanLarge() {
        BigIntegerTestForm form = new BigIntegerTestForm(BigInteger.valueOf(1), BigInteger.valueOf(2));
        Set<ConstraintViolation<BigIntegerTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validBigIntegerWhenSmallEqualLarge() {
        BigIntegerTestForm form = new BigIntegerTestForm(BigInteger.valueOf(2), BigInteger.valueOf(2));
        Set<ConstraintViolation<BigIntegerTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void invalidBigIntegerWhenSmallIsGreaterThanLarge() {
        BigIntegerTestForm form = new BigIntegerTestForm(BigInteger.valueOf(3), BigInteger.valueOf(2));
        Set<ConstraintViolation<BigIntegerTestForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.magnitude-relation-can-equal}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void validBigIntegerWhenValuesIsNull() {
        BigIntegerTestForm form = new BigIntegerTestForm(null, null);
        Set<ConstraintViolation<BigIntegerTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void invalidBigIntegerWhenSmallIsNull() {
        BigIntegerTestForm form = new BigIntegerTestForm(null, BigInteger.valueOf(2));
        Set<ConstraintViolation<BigIntegerTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void invalidBigIntegerWhenLargeIsNull() {
        BigIntegerTestForm form = new BigIntegerTestForm(BigInteger.valueOf(3), null);
        Set<ConstraintViolation<BigIntegerTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    // short
    @Test
    public void validShortWhenSmallIsLessThanLarge() {
        ShortTestForm form = new ShortTestForm(Short.valueOf("1"), Short.valueOf("2"));
        Set<ConstraintViolation<ShortTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validShortWhenSmallEqualLarge() {
        ShortTestForm form = new ShortTestForm(Short.valueOf("2"), Short.valueOf("2"));
        Set<ConstraintViolation<ShortTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void invalidShortWhenSmallIsGreaterThanLarge() {
        ShortTestForm form = new ShortTestForm(Short.valueOf("3"), Short.valueOf("2"));
        Set<ConstraintViolation<ShortTestForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.magnitude-relation-can-equal}", constraintViolations.iterator().next().getMessageTemplate());
    }

    // Short
    @Test
    public void validShortWrapperWhenSmallIsLessThanLarge() {
        ShortWrapperTestForm form = new ShortWrapperTestForm(Short.valueOf("1"), Short.valueOf("2"));
        Set<ConstraintViolation<ShortWrapperTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validShortWrapperWhenSmallEqualLarge() {
        ShortWrapperTestForm form = new ShortWrapperTestForm(Short.valueOf("2"), Short.valueOf("2"));
        Set<ConstraintViolation<ShortWrapperTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void invalidShortWrapperWhenSmallIsGreaterThanLarge() {
        ShortWrapperTestForm form = new ShortWrapperTestForm(Short.valueOf("3"), Short.valueOf("2"));
        Set<ConstraintViolation<ShortWrapperTestForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.magnitude-relation-can-equal}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void validShortWrapperWhenValuesIsNull() {
        ShortWrapperTestForm form = new ShortWrapperTestForm(null, null);
        Set<ConstraintViolation<ShortWrapperTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void invalidShortWrapperWhenSmallIsNull() {
        ShortWrapperTestForm form = new ShortWrapperTestForm(null, Short.valueOf("2"));
        Set<ConstraintViolation<ShortWrapperTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void invalidShortWrapperWhenLargeIsNull() {
        ShortWrapperTestForm form = new ShortWrapperTestForm(Short.valueOf("3"), null);
        Set<ConstraintViolation<ShortWrapperTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    // int
    @Test
    public void validIntWhenSmallIsLessThanLarge() {
        IntTestForm form = new IntTestForm(1, 2);
        Set<ConstraintViolation<IntTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validIntWhenSmallEqualLarge() {
        IntTestForm form = new IntTestForm(2, 2);
        Set<ConstraintViolation<IntTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void invalidIntWhenSmallIsGreaterThanLarge() {
        IntTestForm form = new IntTestForm(3, 2);
        Set<ConstraintViolation<IntTestForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.magnitude-relation-can-equal}", constraintViolations.iterator().next().getMessageTemplate());
    }

    // Integer
    @Test
    public void validIntegerWhenSmallIsLessThanLarge() {
        IntegerTestForm form = new IntegerTestForm(Integer.valueOf(1), Integer.valueOf(2));
        Set<ConstraintViolation<IntegerTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void validIntegerWhenSmallEqualLarge() {
        IntegerTestForm form = new IntegerTestForm(Integer.valueOf(2), Integer.valueOf(2));
        Set<ConstraintViolation<IntegerTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void invalidIntegerWhenSmallIsGreaterThanLarge() {
        IntegerTestForm form = new IntegerTestForm(Integer.valueOf(3), Integer.valueOf(2));
        Set<ConstraintViolation<IntegerTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(1, ConstraintViolations.size());
        assertEquals("{w.common.validation.magnitude-relation-can-equal}", ConstraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void validIntegerWhenValuesIsNull() {
        IntegerTestForm form = new IntegerTestForm(null, null);
        Set<ConstraintViolation<IntegerTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void invalidIntegerWhenSmallIsNull() {
        IntegerTestForm form = new IntegerTestForm(null, Integer.valueOf(2));
        Set<ConstraintViolation<IntegerTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void invalidIntegerWhenLargeIsNull() {
        IntegerTestForm form = new IntegerTestForm(Integer.valueOf(3), null);
        Set<ConstraintViolation<IntegerTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    // long
    @Test
    public void validLongWhenSmallIsLessThanLarge() {
        LongTestForm form = new LongTestForm(1, 2);
        Set<ConstraintViolation<LongTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void validLongWhenSmallEqualLarge() {
        LongTestForm form = new LongTestForm(2, 2);
        Set<ConstraintViolation<LongTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void invalidLongWhenSmallIsGreaterThanLarge() {
        LongTestForm form = new LongTestForm(3, 2);
        Set<ConstraintViolation<LongTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(1, ConstraintViolations.size());
        assertEquals("{w.common.validation.magnitude-relation-can-equal}", ConstraintViolations.iterator().next().getMessageTemplate());
    }

    // Long
    @Test
    public void validLongWrapperWhenSmallIsLessThanLarge() {
        LongWrapperTestForm form = new LongWrapperTestForm(Long.valueOf(1), Long.valueOf(2));
        Set<ConstraintViolation<LongWrapperTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void validLongWrapperWhenSmallEqualLarge() {
        LongWrapperTestForm form = new LongWrapperTestForm(Long.valueOf(2), Long.valueOf(2));
        Set<ConstraintViolation<LongWrapperTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void invalidLongWrapperWhenSmallIsGreaterThanLarge() {
        LongWrapperTestForm form = new LongWrapperTestForm(Long.valueOf(3), Long.valueOf(2));
        Set<ConstraintViolation<LongWrapperTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(1, ConstraintViolations.size());
        assertEquals("{w.common.validation.magnitude-relation-can-equal}", ConstraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void validLongWrapperWhenValuesIsNull() {
        LongWrapperTestForm form = new LongWrapperTestForm(null, null);
        Set<ConstraintViolation<LongWrapperTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void invalidLongWrapperWhenSmallIsNull() {
        LongWrapperTestForm form = new LongWrapperTestForm(null, Long.valueOf(2));
        Set<ConstraintViolation<LongWrapperTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void invalidLongWrapperWhenLargeIsNull() {
        LongWrapperTestForm form = new LongWrapperTestForm(Long.valueOf(3), null);
        Set<ConstraintViolation<LongWrapperTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    // byte
    @Test
    public void validByteWhenSmallIsLessThanLarge() {
        ByteTestForm form = new ByteTestForm(intToByte(1), intToByte(2));
        Set<ConstraintViolation<ByteTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void validByteWhenSmallEqualLarge() {
        ByteTestForm form = new ByteTestForm(intToByte(2), intToByte(2));
        Set<ConstraintViolation<ByteTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void invalidByteWhenSmallIsGreaterThanLarge() {
        ByteTestForm form = new ByteTestForm(intToByte(3), intToByte(2));
        Set<ConstraintViolation<ByteTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(1, ConstraintViolations.size());
        assertEquals("{w.common.validation.magnitude-relation-can-equal}", ConstraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void validByteWhenValuesIsNull() {
        ByteTestForm form = new ByteTestForm(null, null);
        Set<ConstraintViolation<ByteTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void invalidByteWhenSmallIsNull() {
        ByteTestForm form = new ByteTestForm(null, intToByte(2));
        Set<ConstraintViolation<ByteTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void invalidByteWhenLargeIsNull() {
        ByteTestForm form = new ByteTestForm(intToByte(3), null);
        Set<ConstraintViolation<ByteTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    // Byte
    @Test
    public void validByteWrapperWhenSmallIsLessThanLarge() {
        ByteWrapperTestForm form = new ByteWrapperTestForm(intToByteWrapper(1), intToByteWrapper(2));
        Set<ConstraintViolation<ByteWrapperTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void validByteWrapperWhenSmallEqualLarge() {
        ByteWrapperTestForm form = new ByteWrapperTestForm(intToByteWrapper(2), intToByteWrapper(2));
        Set<ConstraintViolation<ByteWrapperTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void invalidByteWrapperWhenSmallIsGreaterThanLarge() {
        ByteWrapperTestForm form = new ByteWrapperTestForm(intToByteWrapper(3), intToByteWrapper(2));
        Set<ConstraintViolation<ByteWrapperTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(1, ConstraintViolations.size());
        assertEquals("{w.common.validation.magnitude-relation-can-equal}", ConstraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void validByteWrapperWhenValuesIsNull() {
        ByteWrapperTestForm form = new ByteWrapperTestForm(null, null);
        Set<ConstraintViolation<ByteWrapperTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void invalidByteWrapperWhenSmallIsNull() {
        ByteWrapperTestForm form = new ByteWrapperTestForm(null, intToByteWrapper(2));
        Set<ConstraintViolation<ByteWrapperTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void invalidByteWrapperWhenLargeIsNull() {
        ByteWrapperTestForm form = new ByteWrapperTestForm(intToByteWrapper(3), null);
        Set<ConstraintViolation<ByteWrapperTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    // LocalDateTime
    @Test
    public void validLocalDateTimeWhenSmallIsLessThanLarge() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTimeTestForm form = new LocalDateTimeTestForm(LocalDateTime.parse("2016-05-23 13:40:00", formatter), LocalDateTime.parse("2016-05-23 13:40:30", formatter));
        Set<ConstraintViolation<LocalDateTimeTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void validLocalDateTimeWhenSmallEqualLarge() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTimeTestForm form = new LocalDateTimeTestForm(LocalDateTime.parse("2016-05-23 13:40:00", formatter), LocalDateTime.parse("2016-05-23 13:40:00", formatter));
        Set<ConstraintViolation<LocalDateTimeTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void invalidLocalDateTimeWhenSmallIsGreaterThanLarge() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTimeTestForm form = new LocalDateTimeTestForm(LocalDateTime.parse("2016-05-23 13:41:00", formatter), LocalDateTime.parse("2016-05-23 13:40:00", formatter));
        Set<ConstraintViolation<LocalDateTimeTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(1, ConstraintViolations.size());
        assertEquals("{w.common.validation.magnitude-relation-can-equal}", ConstraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void validLocalDateTimeWhenValuesIsNull() {
        LocalDateTimeTestForm form = new LocalDateTimeTestForm(null, null);
        Set<ConstraintViolation<LocalDateTimeTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void invalidLocalDateTimeWhenSmallIsNull() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTimeTestForm form = new LocalDateTimeTestForm(null, LocalDateTime.parse("2016-05-23 13:40:00", formatter));
        Set<ConstraintViolation<LocalDateTimeTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void invalidLocalDateTimeWhenLargeIsNull() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTimeTestForm form = new LocalDateTimeTestForm(LocalDateTime.parse("2016-05-23 13:40:00", formatter), null);
        Set<ConstraintViolation<LocalDateTimeTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    // LocalDate
    @Test
    public void validLocalDateWhenSmallIsLessThanLarge() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTestForm form = new LocalDateTestForm(LocalDate.parse("2016-05-23", formatter), LocalDate.parse("2016-05-24", formatter));
        Set<ConstraintViolation<LocalDateTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void validLocalDateWhenSmallEqualLarge() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTestForm form = new LocalDateTestForm(LocalDate.parse("2016-05-23", formatter), LocalDate.parse("2016-05-23", formatter));
        Set<ConstraintViolation<LocalDateTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void invalidLocalDateWhenSmallIsGreaterThanLarge() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTestForm form = new LocalDateTestForm(LocalDate.parse("2016-05-24", formatter), LocalDate.parse("2016-05-23", formatter));
        Set<ConstraintViolation<LocalDateTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(1, ConstraintViolations.size());
        assertEquals("{w.common.validation.magnitude-relation-can-equal}", ConstraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void validLocalDateWhenValuesIsNull() {
        LocalDateTestForm form = new LocalDateTestForm(null, null);
        Set<ConstraintViolation<LocalDateTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void invalidLocalDateWhenSmallIsNull() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTestForm form = new LocalDateTestForm(null, LocalDate.parse("2016-05-23", formatter));
        Set<ConstraintViolation<LocalDateTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void invalidLocalDateWhenLargeIsNull() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTestForm form = new LocalDateTestForm(LocalDate.parse("2016-05-23", formatter), null);
        Set<ConstraintViolation<LocalDateTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    // LocalTime
    @Test
    public void validLocalTimeWhenSmallIsLessThanLarge() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTimeTestForm form = new LocalTimeTestForm(LocalTime.parse("13:40:00", formatter), LocalTime.parse("13:40:30", formatter));
        Set<ConstraintViolation<LocalTimeTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void validLocalTimeWhenSmallEqualLarge() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTimeTestForm form = new LocalTimeTestForm(LocalTime.parse("13:40:00", formatter), LocalTime.parse("13:40:00", formatter));
        Set<ConstraintViolation<LocalTimeTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void invalidLocalTimeWhenSmallIsGreaterThanLarge() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTimeTestForm form = new LocalTimeTestForm(LocalTime.parse("13:41:00", formatter), LocalTime.parse("13:40:00", formatter));
        Set<ConstraintViolation<LocalTimeTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(1, ConstraintViolations.size());
        assertEquals("{w.common.validation.magnitude-relation-can-equal}", ConstraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void validLocalTimeWhenValuesIsNull() {
        LocalTimeTestForm form = new LocalTimeTestForm(null, null);
        Set<ConstraintViolation<LocalTimeTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void invalidLocalTimeWhenSmallIsNull() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTimeTestForm form = new LocalTimeTestForm(null, LocalTime.parse("13:40:00", formatter));
        Set<ConstraintViolation<LocalTimeTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void invalidLocalTimeWhenLargeIsNull() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTimeTestForm form = new LocalTimeTestForm(LocalTime.parse("13:40:00", formatter), null);
        Set<ConstraintViolation<LocalTimeTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    // String
    @Test
    public void validStringWhenSmallIsLessThanLarge() {
        StringTestForm form = new StringTestForm("1", "2");
        Set<ConstraintViolation<StringTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void validStringWhenSmallEqualLarge() {
        StringTestForm form = new StringTestForm("2", "2");
        Set<ConstraintViolation<StringTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void invalidStringWhenSmallIsGreaterThanLarge() {
        StringTestForm form = new StringTestForm("3", "2");
        Set<ConstraintViolation<StringTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(1, ConstraintViolations.size());
        assertEquals("{w.common.validation.magnitude-relation-can-equal}", ConstraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void validStringWhenSmallIsNotNumeric() {
        StringTestForm form = new StringTestForm("a", "2");
        Set<ConstraintViolation<StringTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    @Test
    public void validStringWhenLargeIsNotNumeric() {
        StringTestForm form = new StringTestForm("2", "a");
        Set<ConstraintViolation<StringTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    // blank target
    @Test
    public void validWhenSmallTargetIsBlank() {
        SmallBlankTestForm form = new SmallBlankTestForm(1l, 2l);
        Set<ConstraintViolation<SmallBlankTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validWhenLargeTargetIsBlank() {
        LargeBlankTestForm form = new LargeBlankTestForm(1l, 2l);
        Set<ConstraintViolation<LargeBlankTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validWhenNotEqual() {
        NotEqualForm form = new NotEqualForm(1l, 1l);
        Set<ConstraintViolation<NotEqualForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
    }
    @Test
    public void validWhenEqual() {
        EqualForm form = new EqualForm(1l, 1l);
        Set<ConstraintViolation<EqualForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    // wrong type field: Integer & Long
    @Test
    public void invalidWhenTargetFieldTypeWrong() {
        WrongFieldTestForm form = new WrongFieldTestForm(3, 2l);
        Set<ConstraintViolation<WrongFieldTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }
    // wrong type field: Integer & Long
    @Test
    public void invalidWhenTargetTypeWrong() {
        BooleanTestForm form = new BooleanTestForm(true, true);
        Set<ConstraintViolation<BooleanTestForm>> ConstraintViolations = validator.validate(form);
        assertEquals(0, ConstraintViolations.size());
    }

    /*
     * Test Form classes
     */
    @Data
    @MagnitudeRelation(small = "small", large = "large")
    private class BooleanTestForm {
        public BooleanTestForm(Boolean small, Boolean large) {
            this.small = small;
            this.large = large;
        }
        Boolean small;
        Boolean large;
    }
    /*
     * Test Form classes
     */
    @Data
    @MagnitudeRelation(small = "small", large = "large")
    private class BigDecimalTestForm {
        public BigDecimalTestForm(BigDecimal small, BigDecimal large) {
            this.small = small;
            this.large = large;
        }
        BigDecimal small;
        BigDecimal large;
    }

    @Data
    @MagnitudeRelation(small = "small", large = "large")
    private class BigIntegerTestForm {
        public BigIntegerTestForm(BigInteger small, BigInteger large) {
            this.small = small;
            this.large = large;
        }
        BigInteger small;
        BigInteger large;
    }

    @Data
    @MagnitudeRelation(small = "small", large = "large")
    private class ByteTestForm {
        public ByteTestForm(byte[] small, byte[] large) {
            this.small = small;
            this.large = large;
        }
        byte[] small;
        byte[] large;
    }

    @Data
    @MagnitudeRelation(small = "small", large = "large")
    private class ByteWrapperTestForm {
        public ByteWrapperTestForm(Byte[] small, Byte[] large) {
            this.small = small;
            this.large = large;
        }
        Byte[] small;
        Byte[] large;
    }

    @Data
    @MagnitudeRelation(small = "small", large = "large")
    private class IntTestForm {
        public IntTestForm(int small, int large) {
            this.small = small;
            this.large = large;
        }
        int small;
        int large;
    }

    @Data
    @MagnitudeRelation(small = "small", large = "large")
    private class IntegerTestForm {
        public IntegerTestForm(Integer small, Integer large) {
            this.small = small;
            this.large = large;
        }
        Integer small;
        Integer large;
    }

    @Data
    @MagnitudeRelation(small = "small", large = "large")
    private class ShortTestForm {
        public ShortTestForm(short small, short large) {
            this.small = small;
            this.large = large;
        }
        short small;
        short large;
    }

    @Data
    @MagnitudeRelation(small = "small", large = "large")
    private class ShortWrapperTestForm {
        public ShortWrapperTestForm(Short small, Short large) {
            this.small = small;
            this.large = large;
        }
        Short small;
        Short large;
    }

    @Data
    @MagnitudeRelation(small = "small", large = "large")
    private class LongTestForm {
        public LongTestForm(long small, long large) {
            this.small = small;
            this.large = large;
        }
        long small;
        long large;
    }

    @Data
    @MagnitudeRelation(small = "small", large = "large")
    private class LongWrapperTestForm {
        public LongWrapperTestForm(Long small, Long large) {
            this.small = small;
            this.large = large;
        }
        Long small;
        Long large;
    }

    @Data
    @MagnitudeRelation(small = "small", large = "large")
    private class LocalDateTimeTestForm {
        public LocalDateTimeTestForm(LocalDateTime small, LocalDateTime large) {
            this.small = small;
            this.large = large;
        }
        LocalDateTime small;
        LocalDateTime large;
    }

    @Data
    @MagnitudeRelation(small = "small", large = "large")
    private class LocalDateTestForm {
        public LocalDateTestForm(LocalDate small, LocalDate large) {
            this.small = small;
            this.large = large;
        }
        LocalDate small;
        LocalDate large;
    }

    @Data
    @MagnitudeRelation(small = "small", large = "large")
    private class LocalTimeTestForm {
        public LocalTimeTestForm(LocalTime small, LocalTime large) {
            this.small = small;
            this.large = large;
        }
        LocalTime small;
        LocalTime large;
    }

    @Data
    @MagnitudeRelation(small = "", large = "large")
    private class SmallBlankTestForm {
        public SmallBlankTestForm(Long small, Long large) {
            this.small = small;
            this.large = large;
        }
        Long small;
        Long large;
    }

    @Data
    @MagnitudeRelation(small = "small", large = "")
    private class LargeBlankTestForm {
        public LargeBlankTestForm(Long small, Long large) {
            this.small = small;
            this.large = large;
        }
        Long small;
        Long large;
    }
    @Data
    @MagnitudeRelation(small = "small", large = "large", canEqualFlg = false)
    private class NotEqualForm {
        public NotEqualForm(Long small, Long large) {
            this.small = small;
            this.large = large;
        }
        Long small;
        Long large;
    }
    @Data
    @MagnitudeRelation(small = "small", large = "large")
    private class EqualForm {
        public EqualForm(Long small, Long large) {
            this.small = small;
            this.large = large;
        }
        Long small;
        Long large;
    }

    @Data
    @MagnitudeRelation(small = "small", large = "large")
    private class WrongFieldTestForm {
        public WrongFieldTestForm(Integer small, Long large) {
            this.small = small;
            this.large = large;
        }
        Integer small;
        Long large;
    }

    @Data
    @MagnitudeRelation(small = "small", large = "large")
    private class StringTestForm {
        public StringTestForm(String small, String large) {
            super();
            this.small = small;
            this.large = large;
        }
        String small;
        String large;
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
}
