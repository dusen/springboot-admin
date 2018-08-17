/**
 * @(#)ResultObjectUtilityTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.common.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.metadata.ConstraintDescriptor;

import org.apache.commons.collections4.CollectionUtils;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.FieldError;

import com.fastretailing.dcp.common.constants.ErrorName;
import com.fastretailing.dcp.common.constants.LogLevel;
import com.fastretailing.dcp.common.model.Detail;
import com.fastretailing.dcp.common.model.ResultObject;
import com.fastretailing.dcp.common.validation.annotation.MagnitudeRelation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResultObjectUtilityTest {
    /**
     * MessageSource.
     */
    @Mock
    private MessageSource messageSource;

    /**
     * CommonUtility.
     */
    @Mock
    private CommonUtility commonUtility;

    @Mock
    private   ConstraintViolation constraintViolation;

    @Mock
    private Path path;

    @Mock
    private ConstraintDescriptor<Annotation> constraintDescriptor;

    @Mock
    private Annotation annotation;

    @InjectMocks
    private ResultObjectUtility resultObjectUtility = new ResultObjectUtility();

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(resultObjectUtility, "autowiredMessageSource", messageSource);
        ReflectionTestUtils.setField(resultObjectUtility, "autowiredCommonUtility", commonUtility);
        resultObjectUtility.initialize();
    }

    @Test
    public void testResultObjectBuilderCase01() {

        ResultObjectUtility.ResultObjectBuilder resultObjectBuilder = ResultObjectUtility.ResultObjectBuilder.getBuilder();
        ResultObject resultObject = new ResultObject();

        assertThat(resultObjectBuilder, CoreMatchers.notNullValue());

        resultObjectBuilder.withName(ErrorName.Basis.ASYNC_TIMEOUT);
        resultObject.setName(ErrorName.Basis.ASYNC_TIMEOUT);
        Mockito.when(commonUtility.getDebugId(Matchers.anyString(), Matchers.anyString())).thenReturn("test Debug Id");
        resultObjectBuilder.withDebugId(LogLevel.DEBUG, "test Debug Id");
        resultObject.setDebugId("test Debug Id");

        resultObjectBuilder.withMessageText("test msg txt");
        resultObject.setMessage("test msg txt");

        resultObjectBuilder.withInformationLink("test info link");
        resultObject.setInformationLink("test info link");

        resultObjectBuilder.withLinks(Arrays.asList("link1", "link2", "link3"));
        resultObject.setLinks(Arrays.asList("link1", "link2", "link3"));

        resultObjectBuilder.withDetails(Arrays.asList(new Detail("issueCode1", "issue1"), new Detail("issueCode2", "issue2")));
        resultObject.setDetails(Arrays.asList(new Detail("issueCode1", "issue1"), new Detail("issueCode2", "issue2")));

        Assert.assertEquals(resultObjectBuilder.build().getMessage(), resultObject.getMessage());
        Assert.assertEquals(resultObjectBuilder.build().getDebugId(), resultObject.getDebugId());
        Assert.assertEquals(resultObjectBuilder.build().getDetails().toString(), resultObject.getDetails().toString());
        Assert.assertEquals(resultObjectBuilder.build().getInformationLink(), resultObject.getInformationLink());
        Assert.assertEquals(resultObjectBuilder.build().getLinks(), resultObject.getLinks());
        Assert.assertEquals(resultObjectBuilder.build().getName(), resultObject.getName());

        Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(), Matchers.any())).thenReturn("test msg");
        resultObjectBuilder.withMessage("test msg code");
        resultObject.setMessage("test msg");

        resultObjectBuilder.withLinks("link4", "link5", "link6");
        resultObject.setLinks(Arrays.asList("link4", "link5", "link6"));

        resultObjectBuilder.withDetails(new Detail("issueCode3", "issue3"), new Detail("issueCode4", "issue4"));
        resultObject.setDetails(Arrays.asList(new Detail("issueCode3", "issue3"), new Detail("issueCode4", "issue4")));

        Assert.assertEquals(resultObjectBuilder.build().getMessage(), resultObject.getMessage());
        Assert.assertEquals(resultObjectBuilder.build().getDetails().toString(), resultObject.getDetails().toString());
        Assert.assertEquals(resultObjectBuilder.build().getLinks(), resultObject.getLinks());

    }

    @Test
    public void testDetailBuilderCase01() {

        Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(), Matchers.any())).thenReturn("test regex");

        ResultObjectUtility.DetailBuilder detailBuilder = ResultObjectUtility.DetailBuilder.getBuilder();
        Detail detial = new Detail();

        assertThat(detailBuilder, CoreMatchers.notNullValue());

        detailBuilder.withField("test field");
        detial.setField("test field");

        detailBuilder.withValue("test val");
        detial.setValue("test val");

        Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(), Matchers.any())).thenReturn("test issue msg");
        detailBuilder.withIssue("test issueCode");
        detial.setIssueCode("test issueCode");
        detial.setIssue("test issue msg");

        Assert.assertEquals(detailBuilder.build().getField(), detial.getField());
        Assert.assertEquals(detailBuilder.build().getIssue(), detial.getIssue());
        Assert.assertEquals(detailBuilder.build().getIssueCode(), detial.getIssueCode());
        Assert.assertEquals(detailBuilder.build().getValue(), detial.getValue());

        detailBuilder.withIssue("test issueCode", "test issueText");
        detial.setIssue("test issueText");

        Assert.assertEquals(detailBuilder.build().getIssue(), detial.getIssue());

    }

    @Test
    public void testDetailBuilderCase02() {

        Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(), Matchers.any())).thenReturn("test regex");

        FieldError fieldError1 = new FieldError("1", "2", "3");
        FieldError fieldError2 = new FieldError("4", "5", "test rejectedValue", false, null, null, "6");
        List<Detail> detailList = ResultObjectUtility.DetailBuilder.build(Arrays.asList(fieldError1, fieldError2));

        Detail detail1 = new Detail();
        detail1.setField("2");
        detail1.setIssue("3");
        Detail detail2 = new Detail();
        detail2.setValue("test rejectedValue");
        detail2.setField("5");
        detail2.setIssue("6");
        assertEquals(detailList.toString(), Arrays.asList(detail1, detail2).toString());

        List<FieldError> fieldErrorList = null;
        List<Detail> detailList1 = ResultObjectUtility.DetailBuilder.build(fieldErrorList);
        assertEquals(CollectionUtils.isEmpty(detailList1), true);

    }

    @Test
    public void testDetailBuilderCase03() {

        Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(), Matchers.any())).thenReturn("test regex");
        Set<ConstraintViolation> constraintViolationSet = new HashSet<>();
        constraintViolationSet.add(constraintViolation);
        Mockito.when(constraintViolation.getPropertyPath()).thenReturn(path);
        Mockito.when(path.toString()).thenReturn("test path");
        Mockito.when(constraintViolation.getInvalidValue()).thenReturn("test InvalidValue");
        Mockito.when(constraintViolation.getConstraintDescriptor()).thenReturn(constraintDescriptor);
        Mockito.when(constraintDescriptor.getAnnotation()).thenReturn(annotation);
        Mockito.doReturn(Annotation.class).when(annotation).annotationType();

        List<Detail> detailList = ResultObjectUtility.DetailBuilder.build(constraintViolationSet);

        Detail detail1 = new Detail();
        detail1.setField("test path");
        detail1.setValue("test InvalidValue");
        assertEquals(detailList.toString(), Arrays.asList(detail1).toString());

        Set<ConstraintViolation> constraintViolationSet1 = new HashSet<>();
        List<Detail> detailList1 = ResultObjectUtility.DetailBuilder.build(constraintViolationSet1);
        assertEquals(CollectionUtils.isEmpty(detailList1), true);

    }

    @Test
    public void testDetailBuilderCase04() {

        Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(), Matchers.any())).thenReturn("test regex");
        Set<ConstraintViolation> constraintViolationSet = new HashSet<>();
        constraintViolationSet.add(constraintViolation);
        Mockito.when(constraintViolation.getPropertyPath()).thenReturn(path);
        Mockito.when(constraintViolation.getMessage()).thenReturn("tset msg123");
        Mockito.when(path.toString()).thenReturn("test path");
        Mockito.when(constraintViolation.getInvalidValue()).thenReturn("test InvalidValue");
        Mockito.when(constraintViolation.getConstraintDescriptor()).thenReturn(constraintDescriptor);
        Mockito.when(constraintDescriptor.getAnnotation()).thenReturn(annotation);
        Mockito.doReturn(MagnitudeRelation.class).when(annotation).annotationType();

        List<Detail> detailList = ResultObjectUtility.DetailBuilder.build(constraintViolationSet);

        Detail detail1 = new Detail();
        detail1.setField("test path");
        detail1.setValue("test InvalidValue");
        detail1.setIssue("tset msg123");
        detail1.setIssueCode("400-032");
        assertEquals(detailList.toString(), Arrays.asList(detail1).toString());

        Set<ConstraintViolation> constraintViolationSet1 = new HashSet<>();
        List<Detail> detailList1 = ResultObjectUtility.DetailBuilder.build(constraintViolationSet1);
        assertEquals(CollectionUtils.isEmpty(detailList1), true);

    }

    @Test
    public void testDetailBuilderCase05() {

        Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(), Matchers.any())).thenReturn("test regex");
        Set<ConstraintViolation> constraintViolationSet = new HashSet<>();
        constraintViolationSet.add(constraintViolation);
        Mockito.when(constraintViolation.getPropertyPath()).thenReturn(path);
        Mockito.when(constraintViolation.getMessage()).thenReturn("test regex");
        Mockito.when(path.toString()).thenReturn("test path");
        Mockito.when(constraintViolation.getInvalidValue()).thenReturn("test InvalidValue");
        Mockito.when(constraintViolation.getConstraintDescriptor()).thenReturn(constraintDescriptor);
        Mockito.when(constraintDescriptor.getAnnotation()).thenReturn(annotation);
        Mockito.doReturn(MagnitudeRelation.class).when(annotation).annotationType();

        List<Detail> detailList = ResultObjectUtility.DetailBuilder.build(constraintViolationSet);

        Detail detail1 = new Detail();
        detail1.setField("test path");
        detail1.setValue("test InvalidValue");
        detail1.setIssue("test regex");
        detail1.setIssueCode("400-033");
        assertEquals(detailList.toString(), Arrays.asList(detail1).toString());

        Set<ConstraintViolation> constraintViolationSet1 = new HashSet<>();
        List<Detail> detailList1 = ResultObjectUtility.DetailBuilder.build(constraintViolationSet1);
        assertEquals(CollectionUtils.isEmpty(detailList1), true);

    }

}
