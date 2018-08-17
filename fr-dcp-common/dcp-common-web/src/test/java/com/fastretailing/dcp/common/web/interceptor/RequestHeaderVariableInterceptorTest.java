package com.fastretailing.dcp.common.web.interceptor;

import com.fastretailing.dcp.common.threadlocal.UserIdAndMemberIdHolder;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({@ContextConfiguration(locations = {"classpath*:com/fastretailing/dcp/common/web/test-context.xml"})})
public class RequestHeaderVariableInterceptorTest {

    private MockHttpServletResponse response;
    private MockHttpServletRequest request;

    @InjectMocks
    private RequestHeaderVariableInterceptor interceptor = new RequestHeaderVariableInterceptor();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        request = new MockHttpServletRequest();
    }

    @Test
    public void preHandleCase1() throws Exception {
        request.setRequestURI("http://localhost:8080/oms-sample/v1/datasource/code");
        request.setContextPath("http://localhost:8080/oms-sample/v1/datasource/code");
        boolean result = interceptor.preHandle(request,response, null);
        assertThat(result, is(true));
        assertThat(UserIdAndMemberIdHolder.getMemberId(), is(CoreMatchers.nullValue()));
        assertThat(UserIdAndMemberIdHolder.getUserId(), is(CoreMatchers.nullValue()));
    }


    @Test
    public void preHandleCase2() throws Exception {
        request.setRequestURI("http://localhost:8080/oms-sample/v1/datasource/code");
        request.setContextPath("http://localhost:8080/oms-sample/v1/datasource/code");
        request.addHeader(UserIdAndMemberIdHolder.HEADER_KEY_X_FR_FRONT_MEMBER_ID, "test member id");
        request.addHeader(UserIdAndMemberIdHolder.HEADER_KEY_X_FR_ADMIN_USER_ID, "test user id");
        boolean result = interceptor.preHandle(request,response, null);
        assertThat(result, is(true));
        assertThat(UserIdAndMemberIdHolder.getMemberId(), is("test member id"));
        assertThat(UserIdAndMemberIdHolder.getUserId(), is("test user id"));
    }

}
