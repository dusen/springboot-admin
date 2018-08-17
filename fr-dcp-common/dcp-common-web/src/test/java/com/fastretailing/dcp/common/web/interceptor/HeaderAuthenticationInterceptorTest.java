package com.fastretailing.dcp.common.web.interceptor;

import com.fastretailing.dcp.common.api.jvm.OmsJvmParameters;
import com.fastretailing.dcp.common.exception.NoAuthenticationException;
import com.fastretailing.dcp.common.threadlocal.UserIdAndMemberIdHolder;
import com.fastretailing.dcp.common.util.CommonUtility;
import com.fastretailing.dcp.common.util.ResultObjectUtility;
import com.fastretailing.dcp.common.web.authentication.annotation.HeaderAuthentication;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Fast Retailing
 * @version $Revision$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        @ContextConfiguration(locations = {
                "classpath*:com/fastretailing/dcp/common/web/test-context.xml"
        })
})

public class HeaderAuthenticationInterceptorTest {

    @InjectMocks
    private HeaderAuthenticationInterceptor headerAuthenticationInterceptor = new HeaderAuthenticationInterceptor();

    private OmsJvmParameters jvmParameters = new OmsJvmParameters();

    private ResultObjectUtility resultObjectUtility = new ResultObjectUtility();

    @Autowired
    MessageSource messageSource;

    @Autowired
    CommonUtility commonUtility;

    @Mock
    HeaderAuthentication headerAuthentication;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(headerAuthenticationInterceptor, "messageSource", messageSource);
        ReflectionTestUtils.setField(resultObjectUtility, "messageSource", messageSource);
        ReflectionTestUtils.setField(resultObjectUtility, "commonUtility", commonUtility);
    }

    @Test
    public void test01() throws Throwable {
        ReflectionTestUtils.setField(jvmParameters, "isAdminApi", true);
        Map<String, String> requestHeaderVariableMap = new HashMap<>();
        requestHeaderVariableMap.put(UserIdAndMemberIdHolder.HEADER_KEY_X_FR_ADMIN_USER_ID,
                "test user id");
        UserIdAndMemberIdHolder.setUserIdAndMemberIdMap(requestHeaderVariableMap);

        Mockito.when(headerAuthentication.userIdCheckFlag()).thenReturn(true);
        Mockito.when(headerAuthentication.memberIdCheckFlag()).thenReturn(true);

        headerAuthenticationInterceptor.invoke(headerAuthentication);

    }

    @Test
    public void test01_1() throws Throwable {
        ReflectionTestUtils.setField(jvmParameters, "isAdminApi", true);
        Map<String, String> requestHeaderVariableMap = new HashMap<>();
        requestHeaderVariableMap.put(UserIdAndMemberIdHolder.HEADER_KEY_X_FR_ADMIN_USER_ID,
                "");
        UserIdAndMemberIdHolder.setUserIdAndMemberIdMap(requestHeaderVariableMap);

        thrown.expect(NoAuthenticationException.class);

        Mockito.when(headerAuthentication.userIdCheckFlag()).thenReturn(true);
        Mockito.when(headerAuthentication.memberIdCheckFlag()).thenReturn(true);

        headerAuthenticationInterceptor.invoke(headerAuthentication);

    }

    @Test
    public void test02() throws Throwable {
        ReflectionTestUtils.setField(jvmParameters, "isAdminApi", false);
        Map<String, String> requestHeaderVariableMap = new HashMap<>();
        requestHeaderVariableMap.put(UserIdAndMemberIdHolder.HEADER_KEY_X_FR_FRONT_MEMBER_ID,
                "test member id");
        UserIdAndMemberIdHolder.setUserIdAndMemberIdMap(requestHeaderVariableMap);

        Mockito.when(headerAuthentication.userIdCheckFlag()).thenReturn(true);
        Mockito.when(headerAuthentication.memberIdCheckFlag()).thenReturn(true);

        headerAuthenticationInterceptor.invoke(headerAuthentication);
    }

    @Test
    public void test02_1() throws Throwable {
        ReflectionTestUtils.setField(jvmParameters, "isAdminApi", false);
        Map<String, String> requestHeaderVariableMap = new HashMap<>();
        requestHeaderVariableMap.put(UserIdAndMemberIdHolder.HEADER_KEY_X_FR_FRONT_MEMBER_ID,
                "");
        UserIdAndMemberIdHolder.setUserIdAndMemberIdMap(requestHeaderVariableMap);

        thrown.expect(NoAuthenticationException.class);

        Mockito.when(headerAuthentication.userIdCheckFlag()).thenReturn(true);
        Mockito.when(headerAuthentication.memberIdCheckFlag()).thenReturn(true);

        headerAuthenticationInterceptor.invoke(headerAuthentication);
    }


    @Test
    public void test03() throws Throwable {
        ReflectionTestUtils.setField(jvmParameters, "isAdminApi", true);
        Map<String, String> requestHeaderVariableMap = new HashMap<>();
        requestHeaderVariableMap.put(UserIdAndMemberIdHolder.HEADER_KEY_X_FR_ADMIN_USER_ID,
                "123");
        requestHeaderVariableMap.put(UserIdAndMemberIdHolder.HEADER_KEY_X_FR_FRONT_MEMBER_ID,
                "456");
        UserIdAndMemberIdHolder.setUserIdAndMemberIdMap(requestHeaderVariableMap);

        Mockito.when(headerAuthentication.userIdCheckFlag()).thenReturn(false);
        Mockito.when(headerAuthentication.memberIdCheckFlag()).thenReturn(false);

        headerAuthenticationInterceptor.invoke(headerAuthentication);
    }

    @Test
    public void test03_1() throws Throwable {
        ReflectionTestUtils.setField(jvmParameters, "isAdminApi", false);
        Map<String, String> requestHeaderVariableMap = new HashMap<>();
        requestHeaderVariableMap.put(UserIdAndMemberIdHolder.HEADER_KEY_X_FR_ADMIN_USER_ID,
                "123");
        requestHeaderVariableMap.put(UserIdAndMemberIdHolder.HEADER_KEY_X_FR_FRONT_MEMBER_ID,
                "456");
        UserIdAndMemberIdHolder.setUserIdAndMemberIdMap(requestHeaderVariableMap);

        Mockito.when(headerAuthentication.userIdCheckFlag()).thenReturn(false);
        Mockito.when(headerAuthentication.memberIdCheckFlag()).thenReturn(false);

        headerAuthenticationInterceptor.invoke(headerAuthentication);
    }


}

