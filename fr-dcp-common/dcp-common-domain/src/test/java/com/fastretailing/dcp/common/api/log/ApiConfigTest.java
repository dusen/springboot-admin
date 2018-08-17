package com.fastretailing.dcp.common.api.log;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ApiConfigTest {

    private ApiConfig apiConfig = new ApiConfig();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void test1() {
        assertThat(Objects.isNull(apiConfig.commonsRequestLoggingFilter()), is(false));
    }

}
