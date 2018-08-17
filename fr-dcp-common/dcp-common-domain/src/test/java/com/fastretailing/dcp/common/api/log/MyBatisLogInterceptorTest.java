package com.fastretailing.dcp.common.api.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import com.fastretailing.dcp.common.util.CommonUtility;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Invocation;
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

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        @ContextConfiguration(locations = {
                "classpath*:com/fastretailing/dcp/common/api/log/test-context.xml"
        })
})
public class MyBatisLogInterceptorTest {
    @InjectMocks
    private  MyBatisLogInterceptor myBatisLogInterceptor = new MyBatisLogInterceptor();

    @Mock
    private StatementHandler statementHandler;

    @Mock
    private MethodInvocation executor;

    @Mock
    private Invocation invocation;

    @Mock
    private CommonUtility commonUtility;

    @Mock
    private BoundSql boundSql;

    @Mock
    private Appender<ILoggingEvent> mockAppender;

    private Map<String , Object> params;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.when(boundSql.getSql()).thenReturn("testSql");
        params = new HashMap<>();
        params.put("paramName1", "paramValue1");
        params.put("paramName2", 1);
        Mockito.when(boundSql.getParameterObject()).thenReturn(params);
        Mockito.when(statementHandler.getBoundSql()).thenReturn(boundSql);
        Mockito.when(invocation.getTarget()).thenReturn(statementHandler);

        Mockito.when(commonUtility.getOperationAt()).thenReturn(LocalDateTime.of(2017, 1, 2, 3, 4, 5, 678000000));
        ReflectionTestUtils.setField(myBatisLogInterceptor,
                "commonUtility", commonUtility);

        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(
                ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        Mockito.when(mockAppender.getName()).thenReturn("consoleLog");
        root.addAppender(mockAppender);
    }

    @Test
    public void interceptCase1() throws  Throwable{
        Mockito.when(invocation.proceed()).thenReturn("result");
        Object result = myBatisLogInterceptor.intercept(invocation);
        assertThat(result, is("result"));
    }

    @Test
    public void interceptCase2() {
        try {
            Mockito.when(invocation.proceed()).thenThrow(new InvocationTargetException(new NullPointerException()));
            myBatisLogInterceptor.intercept(invocation);
        } catch (Throwable e) {
            assertThat(e.getClass().getSimpleName(), is("InvocationTargetException"));
        }
    }

    @Test
    public void interceptCase3() {
        try {
            Mockito.when(invocation.proceed()).thenThrow(new InvocationTargetException(
                    new SQLException("" , "23505", new NullPointerException())));
            myBatisLogInterceptor.intercept(invocation);
        } catch (Throwable e) {
            Map<String, Object> logMap = new LinkedHashMap<>();
            logMap.put("sql", "testSql");
            logMap.put("parameter", params.toString());
            logMap.put("state", "23505");
            logMap.put("times", "0ms");
            assertThat(e.getClass().getSimpleName(), is("InvocationTargetException"));
            // check the sql log
            Mockito.verify(mockAppender).doAppend(Mockito.argThat(new ArgumentMatcher<ILoggingEvent>() {
                @Override
                public boolean matches(ILoggingEvent iLoggingEvent) {
                    if (iLoggingEvent.getArgumentArray() != null && iLoggingEvent.getArgumentArray().length > 0) {
                        return iLoggingEvent.getArgumentArray()[0].equals(logMap) ;
                    } else {
                        return false;
                    }
                }
            }));
        }
    }

    @Test
    public void interceptCase4() throws  Throwable{
        Mockito.when(invocation.getTarget()).thenReturn(executor);
        Mockito.when(invocation.proceed()).thenReturn("result");
        Object result = myBatisLogInterceptor.intercept(invocation);
        assertThat(result, is("result"));
    }

    @Test
    public void pluginCase1() {
        Object result = myBatisLogInterceptor.plugin(statementHandler);
        assertThat(result, is(statementHandler));
    }

    @Test
    public void setPropertiesCase1() {
        Properties properties = new Properties();
        properties.setProperty("test" , "testValue");
        myBatisLogInterceptor.setProperties(properties);
    }
}
