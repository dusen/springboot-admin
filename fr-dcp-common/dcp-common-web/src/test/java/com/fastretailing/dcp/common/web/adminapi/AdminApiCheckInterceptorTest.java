package com.fastretailing.dcp.common.web.adminapi;

import com.fastretailing.dcp.common.web.adminapi.annotation.AdminApi;
import com.fastretailing.dcp.common.web.adminapi.aop.AdminApiCheckInterceptor;
import com.fastretailing.dcp.common.api.jvm.OmsJvmParameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;
import java.util.logging.Handler;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

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

public class AdminApiCheckInterceptorTest {

    @InjectMocks
    private AdminApiCheckInterceptor adminAPIAop = new AdminApiCheckInterceptor();

    private OmsJvmParameters jvmParameters = new OmsJvmParameters();

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void isHandlerMethod() throws Exception{
        ReflectionTestUtils.setField(jvmParameters, "isAdminApi", false);

        assertThat(adminAPIAop.preHandle(null, null, new Object()), is(true));
    }

    @Test
    public void beforeCase1() {
        ReflectionTestUtils.setField(jvmParameters, "isAdminApi", false);
        try {
            HandlerMethod handlerMethod = new HandlerMethod(new AdminApiTestClass(), "withAnnotation");
            adminAPIAop.preHandle(null, null, handlerMethod);
        } catch (Exception e) {
            assertThat(e.getClass().getSimpleName(), is("ForbiddenException"));
            assertThat(e.getMessage(), is("e.access.deny"));
        }
    }

    @Test
    public void beforeCase3() {
        ReflectionTestUtils.setField(jvmParameters, "isAdminApi", true);
        try {
            HandlerMethod handlerMethod = new HandlerMethod(new AdminApiTestClass(), "withoutAnnotation");
            adminAPIAop.preHandle(null, null, handlerMethod);
        } catch (Exception e) {
            assertFalse(true);
        }
        assertFalse(false);
    }

    public class AdminApiTestClass{
        @AdminApi
        public void withAnnotation(){
            return;
        }

        public void withoutAnnotation(){
            return;
        }
    }
}

