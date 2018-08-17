package com.fastretailing.dcp.common.api.jvm;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({@ContextConfiguration(locations = {"classpath*:com/fastretailing/dcp/common/api/jvm/test-context.xml"})})
public class OmsConfigurationInitializerTest {

    @Inject
    private ConfigurableApplicationContext applicationContext1;

    @Mock
    private ConfigurableApplicationContext applicationContext;

    @Mock
    private ConfigurableEnvironment configurableEnvironment;

    @Mock
    private MutablePropertySources mps;

    @Mock
    private   Resource resource;

    private OmsConfigurationInitializer omsConfigurationInitializer = new OmsConfigurationInitializer();

    /**
     * the class path of oms property file.
     */
    private static final String OMS_VERSION_PROPERTIES
            = "classpath*:/*-version.properties";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void test1() {
        omsConfigurationInitializer.initialize(applicationContext1);
    }

    @Test
    public void test2() {

        Map<String, Object> testMap = new HashMap<>();
        testMap.put("oms.api.type", "admin");
        testMap.put("country", "ca");
        Mockito.when(applicationContext.getEnvironment()).thenReturn(configurableEnvironment);
        Mockito.when(applicationContext.getEnvironment().getSystemProperties()).thenReturn(testMap);
        Mockito.when(applicationContext.getEnvironment().getSystemEnvironment()).thenReturn(testMap);

        omsConfigurationInitializer.initialize(applicationContext);
    }

    @Test(expected = RuntimeException.class)
    public void test3() throws IOException {

//        Resource [] resources = {resource};
        Resource [] resources = applicationContext1.getResources("fsdf");
        Mockito.when(applicationContext.getResources(OMS_VERSION_PROPERTIES)).thenReturn(resources);
        Map<String, Object> testMap = new HashMap<>();
        testMap.put("oms.api.type", "admin");
        testMap.put("country", "ca");
        Mockito.when(applicationContext.getEnvironment()).thenReturn(configurableEnvironment);
        Mockito.when(applicationContext.getEnvironment().getSystemProperties()).thenReturn(testMap);
        Mockito.when(applicationContext.getEnvironment().getSystemEnvironment()).thenReturn(testMap);
        Mockito.when(applicationContext.getEnvironment().getPropertySources()).thenReturn(mps);

        omsConfigurationInitializer.initialize(applicationContext);
    }

    @Test(expected = RuntimeException.class)
    public void test4() throws IOException {

        Mockito.doThrow(new IOException()).when(applicationContext).getResources(OMS_VERSION_PROPERTIES);
        Map<String, Object> testMap = new HashMap<>();
        testMap.put("oms.api.type", "admin");
        testMap.put("country", "ca");
        Mockito.when(applicationContext.getEnvironment()).thenReturn(configurableEnvironment);
        Mockito.when(applicationContext.getEnvironment().getSystemProperties()).thenReturn(testMap);
        Mockito.when(applicationContext.getEnvironment().getSystemEnvironment()).thenReturn(testMap);

        omsConfigurationInitializer.initialize(applicationContext);
    }

}
