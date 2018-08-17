/**
 * @(#)CustomizeDeserializerBlackBoxTest.java
 *
 *                                            Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.deserialize;

import static org.junit.Assert.assertEquals;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fastretailing.dcp.storecommon.ApplicationTest;

/**
 * Test class for converting character strings to Boolean.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
@AutoConfigureMockMvc
public class CustomizeDeserializerBlackBoxTest {

    /** Mock of Similar request to SpringMVC. */
    @Autowired
    private MockMvc mockMvc;

    /** Brand. */
    private static final String BRAND = "uq";

    /** Region. */
    private static final String REGION = "jp";

    /** Language. */
    private static final String LANGUAGE = "jp";

    /**
     * When sending a request to the specified path, specific string data is converted to Boolean.
     * Request data is as follows.
     * 
     * <ul>
     * <li>oneZero:"1".
     * <li>trueFalse:"T".
     * <li>yesNo:"N".
     * </ul>
     * 
     * @throws Exception Exception that occurred during processing.
     */
    @Test
    public void setPostBoolean_1() throws Exception {

        // Prepare request url for test.
        String requestUrl =
                StringUtils.join("/", BRAND, "/", REGION, "/", LANGUAGE, "/transactions/");

        // oneZero:"1", trueFalse:"T", yesNo:"N".
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(requestUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"oneZero\":\"1\",\"trueFalse\":\"T\",\"yesNo\":\"N\"}");
        ResultActions res = mockMvc.perform(builder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        assertEquals("{\"oneZero\":true,\"trueFalse\":true,\"yesNo\":false}",
                res.andReturn().getResponse().getContentAsString());

    }

    /**
     * When sending a request to the specified path, specific string data is converted to Boolean.
     * Request data is as follows.
     * 
     * <ul>
     * <li>oneZero:"0".
     * <li>trueFalse:"F".
     * <li>yesNo:"Y".
     * </ul>
     * 
     * @throws Exception Exception that occurred during processing.
     */
    @Test
    public void setPostBoolean_2() throws Exception {

        // Prepare request url for test.
        String requestUrl =
                StringUtils.join("/", BRAND, "/", REGION, "/", LANGUAGE, "/transactions/");

        // oneZero:"0", trueFalse:"F", yesNo:"Y".
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(requestUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"oneZero\":\"0\",\"trueFalse\":\"F\",\"yesNo\":\"Y\"}");
        ResultActions res = mockMvc.perform(builder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        assertEquals("{\"oneZero\":false,\"trueFalse\":false,\"yesNo\":true}",
                res.andReturn().getResponse().getContentAsString());

    }

    /**
     * When sending the request to the specified path, an error response is returned. An illegal
     * value is set for a character string that requires Boolean conversion of request data.
     * 
     * <ul>
     * <li>oneZero:"hoge".
     * <li>trueFalse:"F".
     * <li>yesNo:"Y".
     * </ul>
     * 
     * @throws Exception Exception that occurred during processing.
     */
    @Test
    public void setPostBoolean_3() throws Exception {

        // Prepare request url for test.
        String requestUrl =
                StringUtils.join("/", BRAND, "/", REGION, "/", LANGUAGE, "/transactions/");

        // oneZero:"hoge", trueFalse:"F", yesNo:"Y".
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(requestUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"oneZero\":\"hoge\",\"trueFalse\":\"F\",\"yesNo\":\"Y\"}");
        ResultActions res = mockMvc.perform(builder);
        res.andExpect(MockMvcResultMatchers.status().isBadRequest());

        String expectBody =
                "{\"name\":\"VALIDATION_ERROR\",\"debugId\":\"I_SLS_40000001\",\"message\":\"Input parameters include invalid values.\",\"informationLink\":null,\"links\":null,\"details\":[{\"field\":\"oneZero\",\"value\":\"hoge\",\"issue\":\"oneZero must be 1 or 0.\",\"issueCode\":null}]}";
        assertEquals(expectBody, res.andReturn().getResponse().getContentAsString());

    }

    /**
     * When sending the request to the specified path, an error response is returned. An illegal
     * value is set for a character string that requires Boolean conversion of request data.
     * 
     * <ul>
     * <li>oneZero: null.
     * <li>trueFalse: null.
     * <li>yesNo: null.
     * </ul>
     * 
     * @throws Exception Exception that occurred during processing.
     */
    @Test
    public void setPostBoolean_4() throws Exception {

        // Prepare request url for test.
        String requestUrl =
                StringUtils.join("/", BRAND, "/", REGION, "/", LANGUAGE, "/transactions/");

        // oneZero: null, trueFalse: null, yesNo: null
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(requestUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"oneZero\":null,\"trueFalse\":null,\"yesNo\":null}");
        ResultActions res = mockMvc.perform(builder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        assertEquals("{\"oneZero\":null,\"trueFalse\":null,\"yesNo\":null}",
                res.andReturn().getResponse().getContentAsString());

    }

    /**
     * When sending the request to the specified path, an error response is returned. An illegal
     * value is set for a character string that requires Boolean conversion of request data.
     * 
     * <ul>
     * <li>empty
     * </ul>
     * 
     * @throws Exception Exception that occurred during processing.
     */
    @Test
    public void setPostBoolean_5() throws Exception {

        // Prepare request url for test.
        String requestUrl =
                StringUtils.join("/", BRAND, "/", REGION, "/", LANGUAGE, "/transactions/");

        // oneZero:"0", trueFalse:"F", yesNo:"Y".
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(requestUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}");
        ResultActions res = mockMvc.perform(builder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        assertEquals("{\"oneZero\":null,\"trueFalse\":null,\"yesNo\":null}",
                res.andReturn().getResponse().getContentAsString());

    }

}
