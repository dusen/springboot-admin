package com.fastretailing.dcp.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class ResultObjectTest {

    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        objectMapper = new ObjectMapper();

        // set json field type(snake case)
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

        // generate json without null field
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test1() throws Exception {

        ResultObject resultObject =
                objectMapper.readValue("{\"name\":\"HMAC_VALIDATION_FAILED\",\"debug_id\":\"E_COM_900000001\",\"message\":\"MD5 Validation Failed\"}", ResultObject.class);

        assertThat(resultObject.getName().toString(), is("HMAC_VALIDATION_FAILED"));
    }

}