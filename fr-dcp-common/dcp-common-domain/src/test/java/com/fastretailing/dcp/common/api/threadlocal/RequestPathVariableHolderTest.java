package com.fastretailing.dcp.common.api.threadlocal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.hamcrest.core.Is.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({@ContextConfiguration(locations = {"classpath*:com/fastretailing/dcp/common/api/jvm/test-context.xml"})})
public class RequestPathVariableHolderTest {

    private RequestPathVariableHolder requestPathVariableHolder = new RequestPathVariableHolder();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void test1() {
        Map<String, String> pathVariableMap = new HashMap<>();
        pathVariableMap.put("testKey", "testVal");
        RequestPathVariableHolder.setRequestPathVariableMap(pathVariableMap);
    }

    @Test
    public void test2() {
        Assert.assertThat(Objects.isNull(new RequestPathVariableHolder()), is(false));
    }

}
