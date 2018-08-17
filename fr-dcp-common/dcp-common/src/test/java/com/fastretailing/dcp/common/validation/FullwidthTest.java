/**
 * @(#)FullwidthTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */


package com.fastretailing.dcp.common.validation;

import com.fastretailing.dcp.common.validation.annotation.FullWidth;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class FullwidthTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        validator = TestValidatorFactory.createValidator();
    }

    @Test
    public void validaWhenValueIsFullwidthKana() {
        FullwidthTestForm form = new FullwidthTestForm("あいうえおかきくけこさしすせそたちつてとなにぬねのはひふへほまみむめもやゐゆゑよらりるれろわをんがぎぐげござじずぜぞだぢづでどばびぶべぼぱぴぷぺぽぁぃぅぇぉっゃゅょゎ");
        Set<ConstraintViolation<FullwidthTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validaWhenValueIsFullwidthKatakana() {
        FullwidthTestForm form = new FullwidthTestForm("アイウエオカキクケコサシスセソタチツテトナニヌネノハヒフヘホマミムメモヤユヨラリルレロワヲンガギグゲゴザジズゼゾダヂヅデドバビブベボパピプペポァィゥェォッャュョヮ");
        Set<ConstraintViolation<FullwidthTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validaWhenValueIsFullwidthChaineseCharactor() {
        FullwidthTestForm form = new FullwidthTestForm("阿伊宇江於");
        Set<ConstraintViolation<FullwidthTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validaWhenValueIsFullwidthNumber() {
        FullwidthTestForm form = new FullwidthTestForm("０１２３４５６７８９");
        Set<ConstraintViolation<FullwidthTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validaWhenValueIsFullwidthSign() {
        FullwidthTestForm form = new FullwidthTestForm("”＠％＆＋￥＜＞！＃＄＾＊（）＿－＝｛｝「」：；？／・");
        Set<ConstraintViolation<FullwidthTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validaWhenValueIsFullwidthAlphabet() {
        FullwidthTestForm form = new FullwidthTestForm("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ");
        Set<ConstraintViolation<FullwidthTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validaWhenValueIsFullwidthCharactor() {
        FullwidthTestForm form = new FullwidthTestForm("あいうえおカキクケコ左氏須世素　０１２３４５６７８９ＡＢＣＤＥ＠％＆＋￥");
        Set<ConstraintViolation<FullwidthTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void invalidaWhenValueHaveHalfwidthAlphabet() {
        FullwidthTestForm form = new FullwidthTestForm("abcdefghijklmnopqrstuvwxyzあいうえおカキクケコ左氏須世素　０１２３４５６７８９ＡＢＣＤＥ＠％＆＋￥ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        Set<ConstraintViolation<FullwidthTestForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.full-width}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void invalidaWhenValueHaveHalfwidthSpace() {
        FullwidthTestForm form = new FullwidthTestForm("あいうえお カキクケコ左氏須世素　０１２３４５６７８９ＡＢＣＤＥ＠％＆＋￥");
        Set<ConstraintViolation<FullwidthTestForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.full-width}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void invalidaWhenValueHaveHalfwidthNumber() {
        FullwidthTestForm form = new FullwidthTestForm("0123456789あいうえおカキクケコ左氏須世素０１２３４５６７８９ＡＢＣＤＥ＠％＆＋￥");
        Set<ConstraintViolation<FullwidthTestForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.full-width}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void invalidaWhenValueHaveHalfwidthSign() {
        FullwidthTestForm form = new FullwidthTestForm("あいうえおカキクケコ左氏須世素０１２３４５６７８９ＡＢＣＤＥ＠％＆＋￥\"@%&+\\<>!#$^*()_-={}｢｣:;?/･");
        Set<ConstraintViolation<FullwidthTestForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.full-width}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void invalidaWhenValueHaveHalfwidthKatakana() {
        FullwidthTestForm form = new FullwidthTestForm("あいうえおカキクケコｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓﾔﾕﾖﾗﾘﾙﾚﾛﾜｦﾝｶﾞｷﾞｸﾞｹﾞｺﾞｻﾞｼﾞｽﾞｾﾞｿﾞﾀﾞﾁﾞﾂﾞﾃﾞﾄﾞﾊﾞﾋﾞﾌﾞﾍﾞﾎﾞﾊﾟﾋﾟﾌﾟﾍﾟﾎﾟｧｨｩｪｫｯｬｭｮﾜ左氏須世素０１２３４５６７８９ＡＢＣＤＥ＠％＆＋￥");
        Set<ConstraintViolation<FullwidthTestForm>> constraintViolations = validator.validate(form);
        assertEquals(1, constraintViolations.size());
        assertEquals("{w.common.validation.full-width}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    public void validaWhenValueIsBlank() {
        FullwidthTestForm form = new FullwidthTestForm("");
        Set<ConstraintViolation<FullwidthTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validaWhenValueIsNull() {
        FullwidthTestForm form = new FullwidthTestForm(null);
        Set<ConstraintViolation<FullwidthTestForm>> constraintViolations = validator.validate(form);
        assertEquals(0, constraintViolations.size());
    }

    private class FullwidthTestForm {
        public FullwidthTestForm(String str) {
            this.str = str;
        }
        @FullWidth
        String str;
    }
}
