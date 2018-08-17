/**
 * @(#)ConditionalRequireTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.common.validation;

import com.fastretailing.dcp.common.validation.annotation.ConditionalRequire;
import com.fastretailing.dcp.common.validation.validator.ConditionalRequireValidator;
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
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        @ContextConfiguration(locations = {
                "classpath*:com/fastretailing/dcp/common/validation/test-context.xml"
        })
})
@Slf4j
public class ConditionalRequireTest extends MessageTest {

    private static Validator validator;

    @Autowired
    private ConditionalRequireValidator conditionalRequire;

    @BeforeClass
    public static void setUp() {
        validator = TestValidatorFactory.createValidator();
    }

    /**
     * condition is null
     */
    @Test
    public void isValid01() {
        log.debug("ConditionalRequireTest.isValid01");

        NotCheckForm01 form = new NotCheckForm01();
        Set<ConstraintViolation<NotCheckForm01>> actual = validator.validate(form);

        Map<Class, String> map = new HashMap<>();
        map.put(boolean.class, "test");
        map.put(Boolean.class, "test");
        System.out.println(map.size());
        System.out.println(Boolean.class);

        // compare
        assertThat(actual.size(), is(0));
    }

    /**
     * conditionValue is null
     */
    @Test
    public void isValid02() {
        log.debug("ConditionalRequireTest.isValid02");

        NotCheckForm02 form = new NotCheckForm02();
        Set<ConstraintViolation<NotCheckForm02>> actual = validator.validate(form);

        // compare
        assertThat(actual.size(), is(0));
    }

    /**
     * target is null
     */
    @Test
    public void isValid03() {
        log.debug("ConditionalRequireTest.isValid03");

        NotCheckForm03 form = new NotCheckForm03();
        Set<ConstraintViolation<NotCheckForm03>> actual = validator.validate(form);

        // compare
        assertThat(actual.size(), is(0));
    }

    /**
     * conditionValue is String
     */
    @Test
    public void isValid04() {
        log.debug("ConditionalRequireTest.isValid04");

        CheckFormString form = new CheckFormString();
        form.setFiled("name");
        form.setTarget("target");
        Set<ConstraintViolation<CheckFormString>> actual = validator.validate(form);

        // compare
        assertThat(actual.size(), is(0));
    }

    /**
     * condition input is null
     */
    @Test
    public void isValidInputIsNull() {
        log.debug("ConditionalRequireTest.isValid04");

        CheckFormString form = new CheckFormString();
        form.setFiled(null);
        form.setTarget("target");
        Set<ConstraintViolation<CheckFormString>> actual = validator.validate(form);

        // compare
        assertThat(actual.size(), is(0));
    }

    /**
     * conditionValue is BigDecimal
     */
    @Test
    public void isValid05() {
        log.debug("ConditionalRequireTest.isValid05");

        CheckFormBigDecimal form = new CheckFormBigDecimal();
        form.setFiled(new BigDecimal("123"));
        form.setTarget("target");
        Set<ConstraintViolation<CheckFormBigDecimal>> actual = validator.validate(form);

        // compare
        assertThat(actual.size(), is(0));
    }

    /**
     * conditionValue is BigInteger
     */
    @Test
    public void isValid06() {
        log.debug("ConditionalRequireTest.isValid06");

        CheckFormBigInteger form = new CheckFormBigInteger();
        form.setFiled(new BigInteger("123"));
        form.setTarget("target");
        Set<ConstraintViolation<CheckFormBigInteger>> actual = validator.validate(form);

        // compare
        assertThat(actual.size(), is(0));
    }

    /**
     * conditionValue is Short
     */
    @Test
    public void isValid07() {
        log.debug("ConditionalRequireTest.isValid07");

        CheckFormShort form = new CheckFormShort();
        form.setFiled(new Short("123"));
        form.setTarget("target");
        Set<ConstraintViolation<CheckFormShort>> actual = validator.validate(form);

        // compare
        assertThat(actual.size(), is(0));
    }

    /**
     * conditionValue is Integer
     */
    @Test
    public void isValid08() {
        log.debug("ConditionalRequireTest.isValid08");

        CheckFormInteger form = new CheckFormInteger();
        form.setFiled(123);
        form.setTarget("target");
        Set<ConstraintViolation<CheckFormInteger>> actual = validator.validate(form);

        // compare
        assertThat(actual.size(), is(0));
    }

    /**
     * conditionValue is Long
     */
    @Test
    public void isValid09() {
        log.debug("ConditionalRequireTest.isValid09");

        CheckFormLong form = new CheckFormLong();
        form.setFiled(123L);
        form.setTarget("target");
        Set<ConstraintViolation<CheckFormLong>> actual = validator.validate(form);

        // compare
        assertThat(actual.size(), is(0));
    }

    /**
     * conditionValue is byte[]
     */
    @Test
    public void isValid10() {
        log.debug("ConditionalRequireTest.isValid10");

        CheckFormLowByte form = new CheckFormLowByte();
        form.setFiled(new byte[]{0,0,0,0,0,0,0,1});
        form.setTarget("target");
        Set<ConstraintViolation<CheckFormLowByte>> actual = validator.validate(form);

        // compare
        assertThat(actual.size(), is(0));
    }

    /**
     * conditionValue is Byte
     */
    @Test
    public void isValid11() {
        log.debug("ConditionalRequireTest.isValid11");

        CheckFormUppByte form = new CheckFormUppByte();
        form.setFiled(new Byte[]{0,0,0,0,0,0,0,1});
        form.setTarget("target");
        Set<ConstraintViolation<CheckFormUppByte>> actual = validator.validate(form);

        // compare
        assertThat(actual.size(), is(0));
    }

    /**
     * conditionValue is Boolean
     */
    @Test
    public void isValid12() {
        log.debug("ConditionalRequireTest.isValid12");

        CheckFormBoolean form = new CheckFormBoolean();
        form.setFiled(false);
        form.setTarget("target");
        Set<ConstraintViolation<CheckFormBoolean>> actual = validator.validate(form);

        // compare
        assertThat(actual.size(), is(0));
    }
    /**
     * conditionValue is Boolean
     */
    @Test
    public void isOk() {
        log.debug("ConditionalRequireTest.isValid12");

        CheckFormInput form = new CheckFormInput();
        String[] array = {"1","0"};
        List<String> arrayList = new ArrayList<>();
        arrayList.add("1");
        arrayList.add("0");
        form.setList(arrayList);
        form.setTarget(111);
        Set<ConstraintViolation<CheckFormInput>> actual = validator.validate(form);

        // compare
        assertThat(actual.size(), is(0));
    }


    /**
     * target is Integer
     */
    @Test
    public void isValid14() throws IOException{
        log.debug("ConditionalRequireTest.isValid11");

        CheckFormInteger1 form = new CheckFormInteger1();
        form.setFiled11(true);
        form.setTarget(123);
        Set<ConstraintViolation<CheckFormInteger1>> actual = validator.validate(form);

        // compare
        assertThat(actual.size(), is(0));
    }


    /**
     * target is Integer
     */
    @Test
    public void isValid15() throws IOException{
        log.debug("ConditionalRequireTest.isValid11");

        CheckFormInteger1 form = new CheckFormInteger1();
        form.setFiled11(false);
        form.setTarget(123);
        Set<ConstraintViolation<CheckFormInteger1>> actual = validator.validate(form);

        // compare
        assertThat(actual.size(), is(0));
    }

    /**
     * target is Integer
     */
    @Test
    public void isInValid14() throws IOException{
        log.debug("ConditionalRequireTest.isValid11");

        CheckFormInteger1 form = new CheckFormInteger1();
        form.setFiled11(true);
        Set<ConstraintViolation<CheckFormInteger1>> actual = validator.validate(form);

        // compare
        assertThat(actual.size(), is(1));
    }


    @ConditionalRequire(condition = "", conditionValue = "conditionValue", target = "target")
    private class NotCheckForm01 {

    }

    @ConditionalRequire(condition = "condition", conditionValue = "", target = "target")
    private class NotCheckForm02 {

    }

    @ConditionalRequire(condition = "condition", conditionValue = "conditionValue", target = "")
    private class NotCheckForm03 {

    }

    @ConditionalRequire(condition = "filed", conditionValue = "name", target = "target")
    @Data
    private class CheckFormString {
        private String filed;
        private String target;
    }

    @ConditionalRequire(condition = "filed", conditionValue = "123", target = "target")
    @Data
    private class CheckFormBigDecimal {
        private BigDecimal filed;
        private String target;
    }

    @ConditionalRequire(condition = "filed", conditionValue = "123", target = "target")
    @Data
    private class CheckFormBigInteger {
        private BigInteger filed;
        private String target;
    }

    @ConditionalRequire(condition = "filed", conditionValue = "123", target = "target")
    @Data
    private class CheckFormShort {
        private Short filed;
        private String target;
    }

    @ConditionalRequire(condition = "filed", conditionValue = "123", target = "target")
    @Data
    private class CheckFormInteger {
        private Integer filed;
        private String target;
    }

    @ConditionalRequire(condition = "filed", conditionValue = "123", target = "target")
    @Data
    private class CheckFormLong {
        private Long filed;
        private String target;
    }

    @ConditionalRequire(condition = "filed", conditionValue = "1", target = "target")
    @Data
    private class CheckFormLowByte {
        private byte filed[];
        private String target;
    }

    @ConditionalRequire(condition = "filed", conditionValue = "1", target = "target")
    @Data
    private class CheckFormUppByte {
        private Byte filed[];
        private String target;
    }

    @ConditionalRequire(condition = "filed", conditionValue = "false", target = "target")
    @Data
    private class CheckFormBoolean {
        private Boolean filed;
        private String target;
    }


    @ConditionalRequire(condition = "filed11", conditionValue = "true", target = "target")
    @Data
    private class CheckFormInteger1 {
        private boolean filed11;
        private Integer target;
    }


    @ConditionalRequire(condition = "list", conditionValue = "{1,0}", target = "target")
    @Data
    private class CheckFormInput {
        private List<String> list;
        private Integer target;
    }
}
