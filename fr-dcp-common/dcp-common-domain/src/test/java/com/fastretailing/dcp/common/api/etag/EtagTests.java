/**
 * @(#)EtagTests.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.etag;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;

/**
 * test class of Etag.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        @ContextConfiguration(locations = {
                "classpath*:com/fastretailing/dcp/common/api/etag/test-context.xml"
        })
})
public class EtagTests {

    @Test
    public void onePf() {
        Etag etag = null;

        try {
            etag = new Etag("basket=2007-12-03T10:15:30.123");
        } catch (Exception e) {
            // do nothing
        }

        Assert.assertThat(etag.toString(), is("basket=2007-12-03T10:15:30.123"));
    }

    public void onePfMap() {
        Etag etag = null;

        Map<String,String> etagMap = new HashMap<>();
        etagMap.put("basket","2007-12-03T10:15:30.123");
        try {
            etag = new Etag(etagMap);
        } catch (Exception e) {
            // do nothing
        }

        Assert.assertThat(etag.toString(), is("basket=2007-12-03T10:15:30.123"));
    }

    @Test
    public void nullEtag() throws Exception {
        Etag etag = null;

        etag = new Etag();

        Assert.assertThat(etag.toString(), is(""));

        Map<String, String> etagMap = new HashMap<>();
        etagMap.put("basket", "2007-12-03T10:15:30.123|inventory=2007-12-03T10:15:30.234");

        etag.setEtagMap(etagMap);
        Assert.assertThat(etag.toString(),
                is("basket=2007-12-03T10:15:30.123|inventory=2007-12-03T10:15:30.234"));
        Assert.assertThat(etag.getEtagMap(),
                is(etagMap));

    }

    @Test
    public void twoPf() throws Exception {
        Etag etag = null;

        etag = new Etag("basket=2007-12-03T10:15:30.123|inventory=2007-12-03T10:15:30.234");

        Assert.assertThat(etag.toString(),
                is("basket=2007-12-03T10:15:30.123|inventory=2007-12-03T10:15:30.234"));
    }

    @Test(expected = MissingServletRequestParameterException.class)
    public void test2() throws Exception {
        Etag etag = null;
        String str = null;
        etag = new Etag(str);

        Assert.assertThat(etag.toString(),
                is("basket=2007-12-03T10:15:30.123|inventory=2007-12-03T10:15:30.234"));
    }

    @Test
    public void test1() throws Exception {
        Etag etag = null;

        Map<String, String> etagMap = new HashMap<>();
        etagMap.put("basket", "2007-12-03T10:15:30.123|inventory=2007-12-03T10:15:30.234");

        etag = new Etag(etagMap);

        Assert.assertThat(etag.toString(),
                is("basket=2007-12-03T10:15:30.123|inventory=2007-12-03T10:15:30.234"));

        Assert.assertThat(etag.getEtagMap(),
                is(etagMap));


    }

    @Test(expected = MissingServletRequestParameterException.class)
    public void dateParseException() throws Exception {
        new Etag("basket=2007-12-03T10:15:30.123|inventory==2007-12-03T10:15:30.234");

    }
}
