/**
 * @(#)PaginationTest.java
 *
 *                         Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.form;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Unit test of Pagination class.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class PaginationTest {

    @InjectMocks
    private Pagination target = new Pagination();

    /**
     * The preHandle method.
     * 
     * @throws Exception Exception that occurred.
     */
    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
    }

    /**
     * <UL>
     * <LI>Target method:calculatePageNumber.
     * <LI>Condition:TotalNumber was set, MaxNumber and PerPageNumber was not set.
     * <LI>Verification result confirmation:The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testCalculatePageNumber01() {
        // Prepare expect data for test.
        long expTotalNumber = 100;
        long expMaxNumber = 0;
        long expPerPageNumber = 0;
        long expTotalPageNumber = 0;

        // Method execution
        target.setTotalNumber(100);

        // Confirm result
        assertThat(ReflectionTestUtils.getField(target, "totalNumber"), is(expTotalNumber));
        assertThat(ReflectionTestUtils.getField(target, "maxNumber"), is(expMaxNumber));
        assertThat(ReflectionTestUtils.getField(target, "perPageNumber"), is(expPerPageNumber));
        assertThat(ReflectionTestUtils.getField(target, "totalPageNumber"), is(expTotalPageNumber));
    }

    /**
     * <UL>
     * <LI>Target method:calculatePageNumber.
     * <LI>Condition:TotalNumber and MaxNumber was set, PerPageNumber was not set.
     * <LI>Verification result confirmation:The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testCalculatePageNumber02() {
        // Prepare expect data for test.
        long expTotalNumber = 100;
        long expMaxNumber = 50;
        long expPerPageNumber = 0;
        long expTotalPageNumber = 0;

        // Method execution
        target.setTotalNumber(100);
        target.setMaxNumber(50);

        // Confirm result
        assertThat(ReflectionTestUtils.getField(target, "totalNumber"), is(expTotalNumber));
        assertThat(ReflectionTestUtils.getField(target, "maxNumber"), is(expMaxNumber));
        assertThat(ReflectionTestUtils.getField(target, "perPageNumber"), is(expPerPageNumber));
        assertThat(ReflectionTestUtils.getField(target, "totalPageNumber"), is(expTotalPageNumber));
    }

    /**
     * <UL>
     * <LI>Target method:calculatePageNumber.
     * <LI>Condition:TotalNumber, MaxNumber, PerPageNumber was set, totalNumber is bigger than
     * MaxNumber.
     * <LI>Verification result confirmation:The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testCalculatePageNumber03() {
        // Prepare expect data for test.
        long expTotalNumber = 100;
        long expMaxNumber = 51;
        long expPerPageNumber = 10;
        long expTotalPageNumber = 6;

        // Method execution
        target.setTotalNumber(100);
        target.setMaxNumber(51);
        target.setPerPageNumber(10);

        // Confirm result
        assertThat(ReflectionTestUtils.getField(target, "totalNumber"), is(expTotalNumber));
        assertThat(ReflectionTestUtils.getField(target, "maxNumber"), is(expMaxNumber));
        assertThat(ReflectionTestUtils.getField(target, "perPageNumber"), is(expPerPageNumber));
        assertThat(ReflectionTestUtils.getField(target, "totalPageNumber"), is(expTotalPageNumber));
    }

    /**
     * <UL>
     * <LI>Target method:calculatePageNumber.
     * <LI>Condition:TotalNumber, MaxNumber, PerPageNumber was set, totalNumber is smaller than
     * MaxNumber.
     * <LI>Verification result confirmation:The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testCalculatePageNumber04() {
        // Prepare expect data for test.
        long expTotalNumber = 100;
        long expMaxNumber = 150;
        long expPerPageNumber = 10;
        long expTotalPageNumber = 10;

        // Method execution
        target.setTotalNumber(100);
        target.setMaxNumber(150);
        target.setPerPageNumber(10);

        // Confirm result
        assertThat(ReflectionTestUtils.getField(target, "totalNumber"), is(expTotalNumber));
        assertThat(ReflectionTestUtils.getField(target, "maxNumber"), is(expMaxNumber));
        assertThat(ReflectionTestUtils.getField(target, "perPageNumber"), is(expPerPageNumber));
        assertThat(ReflectionTestUtils.getField(target, "totalPageNumber"), is(expTotalPageNumber));
    }

    /**
     * <UL>
     * <LI>Target method:calculatePageNumber.
     * <LI>Condition:TotalNumber, MaxNumber, PerPageNumber was set, MaxNumber is 0.
     * <LI>Verification result confirmation:The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testCalculatePageNumber05() {
        // Prepare expect data for test.
        long expTotalNumber = 105;
        long expMaxNumber = 0;
        long expPerPageNumber = 6;
        long expTotalPageNumber = 18;

        // Method execution
        target.setTotalNumber(105);
        target.setMaxNumber(0);
        target.setPerPageNumber(6);

        // Confirm result
        assertThat(ReflectionTestUtils.getField(target, "totalNumber"), is(expTotalNumber));
        assertThat(ReflectionTestUtils.getField(target, "maxNumber"), is(expMaxNumber));
        assertThat(ReflectionTestUtils.getField(target, "perPageNumber"), is(expPerPageNumber));
        assertThat(ReflectionTestUtils.getField(target, "totalPageNumber"), is(expTotalPageNumber));
    }

    /**
     * <UL>
     * <LI>Target method:calculatePageNumber.
     * <LI>Condition:TotalNumber, MaxNumber, PerPageNumber was set, TotalNumber is 0.
     * <LI>Verification result confirmation:The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testCalculatePageNumber06() {
        // Prepare expect data for test.
        long expTotalNumber = 0;
        long expMaxNumber = 105;
        long expPerPageNumber = 10;
        long expTotalPageNumber = 0;

        // Method execution
        target.setTotalNumber(0);
        target.setMaxNumber(105);
        target.setPerPageNumber(10);

        // Confirm result
        assertThat(ReflectionTestUtils.getField(target, "totalNumber"), is(expTotalNumber));
        assertThat(ReflectionTestUtils.getField(target, "maxNumber"), is(expMaxNumber));
        assertThat(ReflectionTestUtils.getField(target, "perPageNumber"), is(expPerPageNumber));
        assertThat(ReflectionTestUtils.getField(target, "totalPageNumber"), is(expTotalPageNumber));
    }

    /**
     * <UL>
     * <LI>Target method:calculatePageNumber.
     * <LI>Condition:TotalNumber, MaxNumber, PerPageNumber was set, PerPageNumber is 0.
     * <LI>Verification result confirmation:The acquired data matches the expected value.
     * </UL>
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testCalculatePageNumber07() {
        // Prepare expect data for test.
        long expTotalNumber = 155;
        long expMaxNumber = 100;
        long expPerPageNumber = 0;
        long expTotalPageNumber = 0;

        // Method execution
        target.setTotalNumber(155);
        target.setMaxNumber(100);
        target.setPerPageNumber(0);

        // Confirm result
        assertThat(ReflectionTestUtils.getField(target, "totalNumber"), is(expTotalNumber));
        assertThat(ReflectionTestUtils.getField(target, "maxNumber"), is(expMaxNumber));
        assertThat(ReflectionTestUtils.getField(target, "perPageNumber"), is(expPerPageNumber));
        assertThat(ReflectionTestUtils.getField(target, "totalPageNumber"), is(expTotalPageNumber));
    }

    /**
     * Test equals.
     */
    @Test
    public void testEquals() {
        Pagination pagenation1 = new Pagination();
        pagenation1.setTotalNumber(150);
        pagenation1.setMaxNumber(100);
        pagenation1.setPerPageNumber(10);
        pagenation1.setTotalPageNumber(10);
        pagenation1.setCurrentPageNumber(2);
        Pagination pagenation2 = new Pagination();
        pagenation2.setTotalNumber(150);
        pagenation2.setMaxNumber(100);
        pagenation2.setPerPageNumber(10);
        pagenation2.setTotalPageNumber(10);
        pagenation2.setCurrentPageNumber(2);

        assertTrue(pagenation1.equals(pagenation2));

        Pagination pagenation3 = new Pagination();
        pagenation3.setTotalNumber(150);
        pagenation3.setMaxNumber(100);
        pagenation3.setPerPageNumber(10);
        pagenation3.setTotalPageNumber(10);
        pagenation3.setCurrentPageNumber(3);

        assertFalse(pagenation1.equals(pagenation3));
    }

    /**
     * Test hashcode.
     */
    @Test
    public void testHashcode() {
        Pagination pagenation1 = new Pagination();
        pagenation1.setTotalNumber(150);
        pagenation1.setMaxNumber(100);
        pagenation1.setPerPageNumber(10);
        pagenation1.setTotalPageNumber(10);
        pagenation1.setCurrentPageNumber(2);
        Pagination pagenation2 = new Pagination();
        pagenation2.setTotalNumber(150);
        pagenation2.setMaxNumber(100);
        pagenation2.setPerPageNumber(10);
        pagenation2.setTotalPageNumber(10);
        pagenation2.setCurrentPageNumber(2);

        assertTrue(pagenation1.hashCode() == pagenation2.hashCode());

        Pagination pagenation3 = new Pagination();
        pagenation3.setTotalNumber(150);
        pagenation3.setMaxNumber(100);
        pagenation3.setPerPageNumber(10);
        pagenation3.setTotalPageNumber(10);
        pagenation3.setCurrentPageNumber(3);

        assertFalse(pagenation1.hashCode() == pagenation3.hashCode());
    }
}
