package com.fastretailing.dcp.common.web.interceptor;

import com.fastretailing.dcp.common.api.threadlocal.RequestPathVariableHolder;
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
public class RequestPathVariableInterceptorTest {

    private MockHttpServletResponse response;
    private MockHttpServletRequest request;

    @InjectMocks
    private RequestPathVariableInterceptor interceptor = new RequestPathVariableInterceptor();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        request = new MockHttpServletRequest();
    }

    @Test
    public void preHandleCase1() throws Exception {
        request.setRequestURI("http://localhost:8080/sample/v1/testBrand");
        request.setContextPath("http://localhost:8080/sample/v1");
        boolean result = interceptor.preHandle(request,response, null);
        assertThat(result, is(true));
        assertThat(RequestPathVariableHolder.getBrand(), is(CoreMatchers.nullValue()));
        assertThat(RequestPathVariableHolder.getRegion(), is(CoreMatchers.nullValue()));
    }


    @Test
    public void preHandleCase2() throws Exception {
        request.setRequestURI("http://localhost:8080/sample/v1/testBrand/testRegion/getTestInfo");
        request.setContextPath("http://localhost:8080/sample/v1");
        boolean result = interceptor.preHandle(request,response, null);
        assertThat(result, is(true));
        assertThat(RequestPathVariableHolder.getBrand(), is("testBrand"));
        assertThat(RequestPathVariableHolder.getRegion(), is("testRegion"));
    }

}
