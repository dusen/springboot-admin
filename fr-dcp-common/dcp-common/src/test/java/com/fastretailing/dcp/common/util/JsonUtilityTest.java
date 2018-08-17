/**
 * @(#)JsonUtilityTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * test class of JsonUtility.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
public class JsonUtilityTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toJsonSuccess() throws Exception{

        JsonDto testDto = new JsonDto();
        testDto.setTestId("ID01");
        testDto.setTestName("nameString");
        String expected = "{\"testId\":\"ID01\",\"testName\":\"nameString\"}";
        String actual = JsonUtility.toJson(testDto);
        assertThat(actual, is(expected));
    }

    @Test
    public void toObjectSuccess() throws IOException {

        JsonDto expected = new JsonDto();
        expected.setTestId("ID01");
        expected.setTestName("nameString");
        String jsonStr = "{\"testId\":\"ID01\",\"testName\":\"nameString\"}";
        JsonDto actual;
        actual = JsonUtility.toObject(jsonStr, JsonDto.class);
        assertThat(actual, is(expected));
    }

    @Test
    public void toObjectException() throws IOException {

        JsonDto jsonDto = new JsonDto();
        jsonDto.setTestId("ID01");
        jsonDto.setTestName("nameString");
        String jsonStr = "{\"id\":\"ID01\",\"name\":\"nameString\"}";

        thrown.expect(IOException.class);
        JsonUtility.toObject(jsonStr, JsonDto.class);

    }

    @Test
    public void toJsonException() throws JsonProcessingException {

        JsonDto jsonDto = null;
        thrown.expect(JsonProcessingException.class);
        JsonUtility.toJson(new ObjectMapper());

    }

    @Test
    public void toJson() throws JsonProcessingException {

        JsonDto jsonDto = new JsonDto();
        jsonDto.setTestId("ID01");
        jsonDto.setTestName("nameString");

        assertThat(JsonUtility.toJson(jsonDto), is("{\"testId\":\"ID01\",\"testName\":\"nameString\"}"));

    }

    @Test
    public void test1() throws JsonProcessingException {

        assertThat(Objects.isNull(new JsonUtility()), is(false));

    }
}
