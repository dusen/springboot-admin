/**
 * @(#)BrandAndRegionHolderTest.java Copyright (c) 2017 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.common.threadlocal;

import com.fastretailing.dcp.common.util.CommonUtility;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.is;

@Slf4j
public class BrandAndRegionHolderTest {

    @InjectMocks
    private CommonUtility utility;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * getBrand
     */
    @Test
    public void test1() {

        Map<String, String> brandAndRegionMap = new HashMap<>();
        brandAndRegionMap.put("brand", "test brand");
        brandAndRegionMap.put("region", "test region");
        BrandAndRegionHolder.setBrandAndRegionMap(brandAndRegionMap);
        MatcherAssert.assertThat(BrandAndRegionHolder.getBrand(), is("test brand"));

    }

    /**
     * getRegion
     */
    @Test
    public void test2() {

        Map<String, String> brandAndRegionMap = new HashMap<>();
        brandAndRegionMap.put("brand", "test brand");
        brandAndRegionMap.put("region", "test region");
        BrandAndRegionHolder.setBrandAndRegionMap(brandAndRegionMap);
        MatcherAssert.assertThat(BrandAndRegionHolder.getRegion(), is("test region"));

    }

    /**
     * brandAndRegionMap is null
     */
    @Test
    public void test3() {

        BrandAndRegionHolder.setBrandAndRegionMap(null);
        MatcherAssert.assertThat(Objects.isNull(BrandAndRegionHolder.getRegion()), is(true));

    }

    @Test
    public void test4() {

        MatcherAssert.assertThat(Objects.isNull(new BrandAndRegionHolder()), is(false));

    }

}
