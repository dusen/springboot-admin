package com.fastretailing.dcp.common.api.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import com.fastretailing.dcp.common.util.CommonUtility;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        @ContextConfiguration(locations = {
                "classpath*:com/fastretailing/dcp/common/api/log/test-context.xml"
        })
})
public class ServiceLogInterceptorTest {

    @InjectMocks
    private ServiceLogInterceptor serviceLogInterceptor = new ServiceLogInterceptor();

    @Mock
    private ProceedingJoinPoint joinPoint;

    @Mock
    private MethodSignature signature;

    @Mock
    private Appender<ILoggingEvent> mockAppender;

    @Mock
    private CommonUtility commonUtility;

    @Autowired
    private MessageSource messageSource;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger)LoggerFactory.getLogger(
                ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        Mockito.when(mockAppender.getName()).thenReturn("consoleLog");
        root.addAppender(mockAppender);

        ReflectionTestUtils.setField(serviceLogInterceptor, "messageSource" , messageSource);
        Mockito.when(commonUtility.getOperationAt()).thenReturn(LocalDateTime.of(2017, 1, 2, 3, 4, 5, 678000000));
        ReflectionTestUtils.setField(serviceLogInterceptor,
                "commonUtility", commonUtility);
    }

    @Test
    public void serviceLogCase1() throws  Throwable{

        Mockito.when(joinPoint.getTarget()).thenReturn(serviceLogInterceptor);
        String[] args = new String[]{
                "args1","args2","args3"
        };
        Mockito.when(joinPoint.getArgs()).thenReturn(args);
        Mockito.when(joinPoint.getSignature()).thenReturn(signature);
        Mockito.when(signature.getName()).thenReturn("MethodSignature");
        Mockito.when(joinPoint.proceed()).thenReturn("result");

        Object result = serviceLogInterceptor.serviceLog(joinPoint);
        assertThat(result, is("result"));

        Map<String, Object> startLogMap = new LinkedHashMap<>();
        int index = 1;
        for(Object object : args){
            startLogMap.put("parameter-" + index, object);
            index++;
        }
        startLogMap.put("class", "com.fastretailing.dcp.common.api.log.ServiceLogInterceptor");
        startLogMap.put("method", "MethodSignature");
        startLogMap.put("serviceStartTime", "2017-01-02T03:04:05.678Z");
        // check the start log
        checkServiceLog("service-start", startLogMap);

        Map<String, Object> endLogMap = new LinkedHashMap<>();
        endLogMap.put("class", "com.fastretailing.dcp.common.api.log.ServiceLogInterceptor");
        endLogMap.put("method", "MethodSignature");
        endLogMap.put("serviceEndTime", "2017-01-02T03:04:05.678Z");
        endLogMap.put("times", "0ms");
        // check the end log
        checkServiceLog("service-end", endLogMap);
    }

    @Test
    public void serviceLogCase2() throws  Throwable{

        Mockito.when(joinPoint.getTarget()).thenReturn(serviceLogInterceptor);
        String[] args = new String[]{};
        Mockito.when(joinPoint.getArgs()).thenReturn(args);
        Mockito.when(joinPoint.getSignature()).thenReturn(signature);
        Mockito.when(signature.getName()).thenReturn("MethodSignature");
        Mockito.when(joinPoint.proceed()).thenReturn("result");

        Object result = serviceLogInterceptor.serviceLog(joinPoint);
        assertThat(result, is("result"));
        Map<String, Object> startLogMap = new LinkedHashMap<>();
        startLogMap.put("class", "com.fastretailing.dcp.common.api.log.ServiceLogInterceptor");
        startLogMap.put("method", "MethodSignature");
        startLogMap.put("serviceStartTime", "2017-01-02T03:04:05.678Z");

        // check the start log
        checkServiceLog("service-start", startLogMap);

        Map<String, Object> endLogMap = new LinkedHashMap<>();
        endLogMap.put("class", "com.fastretailing.dcp.common.api.log.ServiceLogInterceptor");
        endLogMap.put("method", "MethodSignature");
        endLogMap.put("serviceEndTime", "2017-01-02T03:04:05.678Z");
        endLogMap.put("times", "0ms");
        // check the end log
        checkServiceLog("service-end", endLogMap);
    }

    @Test
    public void serviceLogCase3() throws  Throwable{

        Mockito.when(joinPoint.getTarget()).thenReturn(serviceLogInterceptor);
        MaskItemTestDto args1 = new MaskItemTestDto();
        MaskItemTestDto args2 = new MaskItemTestDto();

        Mockito.when(joinPoint.getArgs()).thenReturn(new Object[]{args1, args2});
        Mockito.when(joinPoint.getSignature()).thenReturn(signature);
        Mockito.when(signature.getName()).thenReturn("MethodSignature");
        Mockito.when(joinPoint.proceed()).thenReturn("result");
        Mockito.when(joinPoint.proceed()).thenReturn("result");

        Object result = serviceLogInterceptor.serviceLog(joinPoint);
        assertThat(result, is("result"));
        Map<String, Object> startLogMap = new LinkedHashMap<>();
        int index = 1;
        for(Object object : new Object[]{args1, args2}){
            startLogMap.put("parameter-" + index, object);
            index++;
        }
        startLogMap.put("class", "com.fastretailing.dcp.common.api.log.ServiceLogInterceptor");
        startLogMap.put("method", "MethodSignature");
        startLogMap.put("serviceStartTime", "2017-01-02T03:04:05.678Z");

        // check the start log
        checkServiceLog("service-start", startLogMap);

        Map<String, Object> endLogMap = new LinkedHashMap<>();
        endLogMap.put("class", "com.fastretailing.dcp.common.api.log.ServiceLogInterceptor");
        endLogMap.put("method", "MethodSignature");
        endLogMap.put("serviceEndTime", "2017-01-02T03:04:05.678Z");
        endLogMap.put("times", "0ms");
        // check the end log
        checkServiceLog("service-end", endLogMap);
    }

    @Test
    public void serviceLogCase4() throws  Throwable{

        Mockito.when(joinPoint.getTarget()).thenReturn(serviceLogInterceptor);
        HashMap<String, String> args1 = new HashMap<>();
        args1.put("args1:test1", "test1");
        args1.put("args1:test2", "test2");
        args1.put("args1:test3", "test3");

        HashMap<String, String> args2 = new HashMap<>();
        args2.put("args2:test1", "test1");
        args2.put("args2:test2", "test2");
        args2.put("args2:test3", "test3");

        Mockito.when(joinPoint.getArgs()).thenReturn(new Object[]{args1, args2});
        Mockito.when(joinPoint.getSignature()).thenReturn(signature);
        Mockito.when(signature.getName()).thenReturn("MethodSignature");
        Mockito.when(joinPoint.proceed()).thenReturn("result");
        Mockito.when(joinPoint.proceed()).thenReturn("result");

        Object result = serviceLogInterceptor.serviceLog(joinPoint);
        assertThat(result, is("result"));
        Map<String, Object> startLogMap = new LinkedHashMap<>();
        int index = 1;
        for(Object object : new Object[]{args1, args2}){
            startLogMap.put("parameter-" + index, object);
            index++;
        }
        startLogMap.put("class", "com.fastretailing.dcp.common.api.log.ServiceLogInterceptor");
        startLogMap.put("method", "MethodSignature");
        startLogMap.put("serviceStartTime", "2017-01-02T03:04:05.678Z");

        // check the start log
        checkServiceLog("service-start", startLogMap);

        Map<String, Object> endLogMap = new LinkedHashMap<>();
        endLogMap.put("class", "com.fastretailing.dcp.common.api.log.ServiceLogInterceptor");
        endLogMap.put("method", "MethodSignature");
        endLogMap.put("serviceEndTime", "2017-01-02T03:04:05.678Z");
        endLogMap.put("times", "0ms");
        // check the end log
        checkServiceLog("service-end", endLogMap);
    }

    private void checkServiceLog(String message, Map<String, Object> logMap){
        // check the log
        Mockito.verify(mockAppender).doAppend(Mockito.argThat(new ArgumentMatcher<ILoggingEvent>() {
            @Override
            public boolean matches(ILoggingEvent iLoggingEvent) {
                boolean hasSameMap = false;
                if (iLoggingEvent.getArgumentArray() != null && iLoggingEvent.getArgumentArray().length > 0) {
                    hasSameMap = iLoggingEvent.getArgumentArray()[0].equals(logMap);
                }
                return iLoggingEvent.getFormattedMessage().contains(
                        message) && hasSameMap ;
            }
        }));
    }
}
