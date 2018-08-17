package com.fastretailing.dcp.annotation;

import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        @ContextConfiguration(locations = {
                "classpath*:com/fastretailing/dcp/common/validation/test-context.xml"
        })
})
@Slf4j
public class CountryTest {

    private CountryProfileCondition countryProfileCondition = new CountryProfileCondition();

    /*@Autowired
    private ConfigurableApplicationContext context;*/

    @BeforeClass
    public static void setUp() {
    }

    /**
     * environment is null
     */
    @Test
    public void valid1() {

        ConditionContextImpl conditionContext = new ConditionContextImpl();
        AnnotatedTypeMetadataImpl annotatedTypeMetadata = new AnnotatedTypeMetadataImpl();
        countryProfileCondition.matches(conditionContext, annotatedTypeMetadata);

    }

    /**
     * environment is not null
     * attrs is null
     */
    @Test
    public void valid2() {

        ConditionContextImpl1 conditionContext = new ConditionContextImpl1();
        AnnotatedTypeMetadataImpl annotatedTypeMetadata = new AnnotatedTypeMetadataImpl();
        countryProfileCondition.matches(conditionContext, annotatedTypeMetadata);

    }

    /**
     * environment is not null
     * attrs is not null
     */
    @Test
    public void valid3() {

        ConditionContextImpl1 conditionContext = new ConditionContextImpl1();
        AnnotatedTypeMetadataImpl1 annotatedTypeMetadata = new AnnotatedTypeMetadataImpl1();
        countryProfileCondition.matches(conditionContext, annotatedTypeMetadata);

    }

    /**
     * environment is not null
     * attrs is not null
     */
    @Test
    public void valid4() {

        ConditionContextImpl1 conditionContext = new ConditionContextImpl1();
        AnnotatedTypeMetadataImpl2 annotatedTypeMetadata = new AnnotatedTypeMetadataImpl2();
        countryProfileCondition.matches(conditionContext, annotatedTypeMetadata);

    }

    private class ConditionContextImpl implements ConditionContext {

        @Override
        public BeanDefinitionRegistry getRegistry() {
            return null;
        }

        @Override
        public ConfigurableListableBeanFactory getBeanFactory() {
            return null;
        }

        @Override
        public Environment getEnvironment() {
            return null;
        }

        @Override
        public ResourceLoader getResourceLoader() {
            return null;
        }

        @Override
        public ClassLoader getClassLoader() {
            return null;
        }
    }

    private class ConditionContextImpl1 implements ConditionContext {

        @Override
        public BeanDefinitionRegistry getRegistry() {
            return null;
        }

        @Override
        public ConfigurableListableBeanFactory getBeanFactory() {
            return null;
        }

        @Override
        public Environment getEnvironment() {
            return new EnvironmentImpl1();
        }

        @Override
        public ResourceLoader getResourceLoader() {
            return null;
        }

        @Override
        public ClassLoader getClassLoader() {
            return null;
        }
    }

    private class EnvironmentImpl1 implements Environment {

        @Override
        public String[] getActiveProfiles() {
            return new String[0];
        }

        @Override
        public String[] getDefaultProfiles() {
            return new String[0];
        }

        @Override
        public boolean acceptsProfiles(String... strings) {
            return false;
        }

        @Override
        public boolean containsProperty(String s) {
            return false;
        }

        @Override
        public String getProperty(String s) {

            return "canada";
        }

        @Override
        public String getProperty(String s, String s1) {
            return null;
        }

        @Override
        public <T> T getProperty(String s, Class<T> aClass) {
            return null;
        }

        @Override
        public <T> T getProperty(String s, Class<T> aClass, T t) {
            return null;
        }

        /**
         * @param s
         * @param aClass
         * @deprecated
         */
        @Override
        public <T> Class<T> getPropertyAsClass(String s, Class<T> aClass) {
            return null;
        }

        @Override
        public String getRequiredProperty(String s) throws IllegalStateException {
            return null;
        }

        @Override
        public <T> T getRequiredProperty(String s, Class<T> aClass) throws IllegalStateException {
            return null;
        }

        @Override
        public String resolvePlaceholders(String s) {
            return null;
        }

        @Override
        public String resolveRequiredPlaceholders(String s) throws IllegalArgumentException {
            return null;
        }
    }

    private class AnnotatedTypeMetadataImpl implements AnnotatedTypeMetadata {

        @Override
        public boolean isAnnotated(String s) {
            return false;
        }

        @Override
        public Map<String, Object> getAnnotationAttributes(String s) {
            return null;
        }

        @Override
        public Map<String, Object> getAnnotationAttributes(String s, boolean b) {
            return null;
        }

        @Override
        public MultiValueMap<String, Object> getAllAnnotationAttributes(String s) {
            return null;
        }

        @Override
        public MultiValueMap<String, Object> getAllAnnotationAttributes(String s, boolean b) {
            return null;
        }
    }

    private class AnnotatedTypeMetadataImpl1 implements AnnotatedTypeMetadata {

        @Override
        public boolean isAnnotated(String s) {
            return false;
        }

        @Override
        public Map<String, Object> getAnnotationAttributes(String s) {
            return null;
        }

        @Override
        public Map<String, Object> getAnnotationAttributes(String s, boolean b) {

            return null;
        }

        @Override
        public MultiValueMap<String, Object> getAllAnnotationAttributes(String s) {

            MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();

            multiValueMap.put("value", Arrays.asList(Arrays.asList(Countries.CANADA, Countries.JAPAN).toArray(), Arrays.asList(Countries.CANADA, Countries.JAPAN).toArray()));

            return multiValueMap;
        }

        @Override
        public MultiValueMap<String, Object> getAllAnnotationAttributes(String s, boolean b) {
            return null;
        }
    }

    private class AnnotatedTypeMetadataImpl2 implements AnnotatedTypeMetadata {

        @Override
        public boolean isAnnotated(String s) {
            return false;
        }

        @Override
        public Map<String, Object> getAnnotationAttributes(String s) {
            return null;
        }

        @Override
        public Map<String, Object> getAnnotationAttributes(String s, boolean b) {

            return null;
        }

        @Override
        public MultiValueMap<String, Object> getAllAnnotationAttributes(String s) {

            MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();

            multiValueMap.put("value", Arrays.asList(Arrays.asList( Countries.JAPAN).toArray(), Arrays.asList(Countries.JAPAN).toArray()));

            return multiValueMap;
        }

        @Override
        public MultiValueMap<String, Object> getAllAnnotationAttributes(String s, boolean b) {
            return null;
        }
    }

}
