/**
 * @(#)PriceValidationTest.java
 *
 *                              Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.dto;

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
public class PriceValidationTest {

    /** Mock of Similar request to SpringMVC. */
    @Autowired
    private MockMvc mockMvc;

    /** Brand. */
    private static String BRAND = "uq";

    /** Region. */
    private static String REGION = "jp";

    /** Language. */
    public static final String LANGUAGE = "jp";

    private static final String URL =
            StringUtils.join("/", BRAND, "/", REGION, "/", LANGUAGE, "/pricetest/");

    /**
     * Test Japan.
     * 
     * @throws Exception Exception that occurred during processing.
     */
    @Test
    public void testJapan() throws Exception {

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.post(URL).contentType(MediaType.APPLICATION_JSON).content(
                        "{\"currency_code\":\"JPY\",\"value\":1}");
        ResultActions res = mockMvc.perform(builder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        assertEquals("{\"currency_code\":\"JPY\",\"value\":1}",
                res.andReturn().getResponse().getContentAsString());
    }

    /**
     * Test Canada.
     * 
     * @throws Exception Exception that occurred during processing.
     */
    @Test
    public void testCanada() throws Exception {

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.post(URL).contentType(MediaType.APPLICATION_JSON).content(
                        "{\"currency_code\":\"CAD\",\"value\":1}");
        ResultActions res = mockMvc.perform(builder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        assertEquals("{\"currency_code\":\"CAD\",\"value\":1}",
                res.andReturn().getResponse().getContentAsString());
    }

    /**
     * Test null.
     * 
     * @throws Exception Exception that occurred during processing.
     */
    @Test
    public void testNull() throws Exception {

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.post(URL).contentType(MediaType.APPLICATION_JSON).content(
                        "{}");
        ResultActions res = mockMvc.perform(builder);
        res.andExpect(MockMvcResultMatchers.status().isOk());
        assertEquals("{\"currency_code\":null,\"value\":null}",
                res.andReturn().getResponse().getContentAsString());
    }

    /**
     * Test illegal currency code.
     * 
     * @throws Exception Exception that occurred during processing.
     */
    @Test
    public void testError() throws Exception {

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.post(URL).contentType(MediaType.APPLICATION_JSON).content(
                        "{\"currency_code\":\"a\"}");
        ResultActions res = mockMvc.perform(builder);
        res.andExpect(MockMvcResultMatchers.status().is4xxClientError());
        assertEquals("", res.andReturn().getResponse().getContentAsString());
    }

}
