package com.fastretailing.dcp.common.web.adminapi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotationMap;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import com.fastretailing.dcp.annotation.ConsumerJsonIgnore;
import com.fastretailing.dcp.common.web.adminapi.aop.ConsumerJsonIgnoreIntrospector;
import com.fastretailing.dcp.common.api.jvm.OmsJvmParameters;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
public class ConsumerJsonIgnoreIntrospectorTest {

    private OmsJvmParameters jvmParameters = new OmsJvmParameters();

    @InjectMocks
    private ConsumerJsonIgnoreIntrospector consumerJsonIgnoreIntrospector =
            new ConsumerJsonIgnoreIntrospector();


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void hasIgnoreMarkerCase1() {
        AnnotationMap annotationMap = new AnnotationMap();
        JsonIgnoreMock jsonIgnoreMock = new JsonIgnoreMock();
        annotationMap.add(jsonIgnoreMock);
       AnnotatedMemberMock memberMock = new AnnotatedMemberMock(null, annotationMap);
        boolean result = consumerJsonIgnoreIntrospector.hasIgnoreMarker(memberMock);
        assertThat(result, is(true));
    }

    @Test
    public void hasIgnoreMarkerCase2(){
        AnnotationMap annotationMap = new AnnotationMap();
        AnnotatedMemberMock memberMock = new AnnotatedMemberMock(null, annotationMap);
        boolean result = consumerJsonIgnoreIntrospector.hasIgnoreMarker(memberMock);
        ReflectionTestUtils.setField(jvmParameters, "isAdminApi", true);
        assertThat(result, is(false));
    }


    @Test
    public void hasIgnoreMarkerCase3(){
        AnnotationMap annotationMap = new AnnotationMap();
        AnnotatedMemberMock memberMock = new AnnotatedMemberMock(null, annotationMap);
        boolean result = consumerJsonIgnoreIntrospector.hasIgnoreMarker(memberMock);
        ReflectionTestUtils.setField(jvmParameters, "isAdminApi", false);
        assertThat(result, is(false));
    }

    @Test
    public void hasIgnoreMarkerCase4(){
        AnnotationMap annotationMap = new AnnotationMap();
        ConsumerJsonIgnoreMock consumerJsonIgnoreMock = new ConsumerJsonIgnoreMock();
        annotationMap.add(consumerJsonIgnoreMock);
        AnnotatedMemberMock memberMock = new AnnotatedMemberMock(null, annotationMap);
        boolean result = consumerJsonIgnoreIntrospector.hasIgnoreMarker(memberMock);
        assertThat(result, is(true));
    }

    class ConsumerJsonIgnoreMock implements Annotation, ConsumerJsonIgnore {

        public Class<? extends Annotation> annotationType() {
            return ConsumerJsonIgnore.class;
        }

        public boolean value() {
            return true;
        }
    }


    class JsonIgnoreMock implements Annotation, JsonIgnore {

        public Class<? extends Annotation> annotationType() {
            return JsonIgnore.class;
        }

        @Override
        public boolean value() {
            return true;
        }
    }

    class AnnotatedMemberMock extends AnnotatedMember {


        public AnnotatedMemberMock(TypeResolutionContext ctxt, AnnotationMap annotations) {
            super(ctxt, annotations);
        }

        @Override
        public Class<?> getRawType() {
            return null;
        }

        @Override
        public Object getValue(Object pojo) throws UnsupportedOperationException, IllegalArgumentException {
            return null;
        }

        @Override
        public boolean equals(Object o) {
            return false;
        }

        @Override
        public void setValue(Object pojo, Object value) throws UnsupportedOperationException, IllegalArgumentException {

        }

        @Override
        public AnnotatedElement getAnnotated() {
            return null;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public JavaType getType() {
            return null;
        }

        @Override
        public Class<?> getDeclaringClass() {
            return null;
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public int getModifiers() {
            return 0;
        }

        @Override
        public String toString() {
            return null;
        }

        @Override
        public Member getMember() {
            return null;
        }

        @Override
        public Annotated withAnnotations(AnnotationMap fallback) {
            return null;
        }
    }
}
