/**
 * @(#)GetReportListServiceTest.java
 * 
 *                                   Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.fastretailing.dcp.common.constants.ErrorName;
import com.fastretailing.dcp.common.exception.BusinessException;
import com.fastretailing.dcp.common.model.ResultObject;
import com.fastretailing.dcp.storecommon.report.api.entity.ReportCreateStatus;
import com.fastretailing.dcp.storecommon.report.api.repository.ReportCreateStatusMapper;
import com.fastretailing.dcp.storecommon.report.dto.GetReportListRequest;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GetReportListServiceTest {
    /** Service class with update report status service. */
    @SpyBean
    private GetReportListService getReportListService;

    /** Create a mock of report create status component. */
    @MockBean
    private ReportCreateStatusMapper reportCreateStatusMapper;

    /**
     * Target method：getReportList. Condition：Parameter is existed. One ReceptionNumber.
     * Verification result confirmation：The acquired data matches the expected value.
     * 
     * @throws Exception (It does not occur as expected value)
     */
    @Test
    public void testNormalOneReceptionNumber() throws Exception {
        // Prepare Parameter
        String receptionNumber = "TestReceptionNumber";
        String storeCode = "Store001";

        GetReportListRequest request = new GetReportListRequest();
        List<String> receptionNumberList = new ArrayList<String>();
        receptionNumberList.add(receptionNumber);
        request.setReceiptNumberList(receptionNumberList);
        request.setStoreCode(storeCode);
        OffsetDateTime createReportDatetime =
                OffsetDateTime.of(2018, 6, 7, 13, 14, 0, 0, ZoneOffset.UTC);
        request.setCreateReportDatetime(createReportDatetime);

        ReportCreateStatus expectedDto = new ReportCreateStatus();
        expectedDto.setReceiptNumber(receptionNumber);
        expectedDto.setStoreCode(storeCode);
        expectedDto.setCreateReportStatus(1);

        // Mock for DB access.
        when(reportCreateStatusMapper.selectByReceiptNumber(eq(receptionNumber), any()))
                .thenReturn(expectedDto);

        // Method execution
        List<ReportCreateStatus> resultList = getReportListService.getReportList(request);

        // Confirm result
        assertEquals(resultList.size(), 1);

        // verify if the searchList method is called only once successfully
        reset(reportCreateStatusMapper);
    }

    /**
     * Target method：getReportList. Condition：Parameter is existed. Three ReceptionNumber.
     * Verification result confirmation：The acquired data matches the expected value.
     * 
     * @throws Exception (It does not occur as expected value)
     */
    @Test
    public void testNormalMultiReceptionNumber() throws Exception {
        // Prepare Parameter
        GetReportListRequest request = new GetReportListRequest();
        String storeCode = "Store001";
        request.setStoreCode(storeCode);

        String receptionNumber = "TestReceptionNumber";
        String receptionNumber2 = "TestReceptionNumber2";
        String receptionNumber3 = "TestReceptionNumber3";
        List<String> receptionNumberList = new ArrayList<String>();
        receptionNumberList.add(receptionNumber);
        receptionNumberList.add(receptionNumber2);
        receptionNumberList.add(receptionNumber3);

        request.setReceiptNumberList(receptionNumberList);
        OffsetDateTime createReportDatetime =
                OffsetDateTime.of(2018, 6, 7, 13, 14, 0, 0, ZoneOffset.UTC);
        request.setCreateReportDatetime(createReportDatetime);

        ReportCreateStatus expectedDto = new ReportCreateStatus();
        expectedDto.setReceiptNumber(receptionNumber);
        expectedDto.setStoreCode(storeCode);
        expectedDto.setCreateReportStatus(1);

        ReportCreateStatus expectedDto2 = new ReportCreateStatus();
        expectedDto2.setReceiptNumber(receptionNumber2);
        expectedDto2.setStoreCode(storeCode);
        expectedDto2.setCreateReportStatus(2);

        ReportCreateStatus expectedDto3 = new ReportCreateStatus();
        expectedDto3.setReceiptNumber(receptionNumber3);
        expectedDto3.setStoreCode(storeCode);
        expectedDto3.setCreateReportStatus(3);

        // Mock for DB access.
        when(reportCreateStatusMapper.selectByReceiptNumber(eq(receptionNumber), any()))
                .thenReturn(expectedDto);
        when(reportCreateStatusMapper.selectByReceiptNumber(eq(receptionNumber2), any()))
                .thenReturn(expectedDto2);
        when(reportCreateStatusMapper.selectByReceiptNumber(eq(receptionNumber3), any()))
                .thenReturn(expectedDto3);

        // Method execution
        List<ReportCreateStatus> resultList = getReportListService.getReportList(request);

        // Confirm result
        assertEquals(resultList.size(), 3);

        // verify if the searchList method is called only once successfully
        reset(reportCreateStatusMapper);
    }

    /**
     * Target method：getReportList. Condition：Parameter is existed. One ReportId. Verification
     * result confirmation：The acquired data matches the expected value.
     * 
     * @throws Exception (It does not occur as expected value)
     */
    @Test
    public void testNormalOneReportId() throws Exception {
        // Prepare Parameter
        String reportId = "TestReportId";
        String storeCode = "Store001";

        GetReportListRequest request = new GetReportListRequest();
        List<String> reportIdList = new ArrayList<String>();
        reportIdList.add(reportId);
        request.setReportIdList(reportIdList);
        request.setStoreCode(storeCode);
        OffsetDateTime dayFrom =
                OffsetDateTime.parse("2018-02-01T04:20:30Z", DateTimeFormatter.ISO_ZONED_DATE_TIME);
        request.setBusinessDayFrom(dayFrom);
        OffsetDateTime dayTo =
                OffsetDateTime.parse("2018-02-01T04:20:30Z", DateTimeFormatter.ISO_ZONED_DATE_TIME);
        request.setBusinessDayTo(dayTo);
        OffsetDateTime createReportDatetime =
                OffsetDateTime.of(2018, 6, 7, 13, 14, 0, 0, ZoneOffset.UTC);
        request.setCreateReportDatetime(createReportDatetime);

        ReportCreateStatus expectedDto = new ReportCreateStatus();
        String receptionNumber = "TestReceptionNumber";
        expectedDto.setReceiptNumber(receptionNumber);
        expectedDto.setStoreCode(storeCode);
        expectedDto.setReportId(reportId);
        expectedDto.setCreateReportStatus(1);

        List<ReportCreateStatus> expectedDtoList = new ArrayList<ReportCreateStatus>();
        expectedDtoList.add(expectedDto);

        LocalDate businessDayFrom = LocalDate.of(2018, 2, 1);
        LocalDate businessDayTo = LocalDate.of(2018, 2, 1);

        // Mock for DB access.
        when(reportCreateStatusMapper.selectByBusinessDay(eq(reportId), eq(storeCode),
                eq(businessDayFrom), eq(businessDayTo), any())).thenReturn(expectedDtoList);

        // Method execution
        List<ReportCreateStatus> resultList = getReportListService.getReportList(request);

        // Confirm result
        assertEquals(resultList.size(), 1);

        // verify if the searchList method is called only once successfully
        reset(reportCreateStatusMapper);
    }

    /**
     * Target method：getReportList. Condition：Parameter is existed. Three ReportId. Verification
     * result confirmation：The acquired data matches the expected value.
     * 
     * @throws Exception (It does not occur as expected value)
     */
    @Test
    public void testNormalMultiReportId() throws Exception {
        // Prepare Parameter
        String storeCode = "Store001";

        LocalDate businessDayFrom = LocalDate.of(2018, 2, 1);
        LocalDate businessDayTo = LocalDate.of(2018, 2, 1);

        GetReportListRequest request = new GetReportListRequest();
        request.setBusinessDayFrom(
                businessDayFrom.atTime(OffsetTime.of(0, 0, 0, 0, ZoneOffset.of("+09:00"))));
        request.setBusinessDayTo(
                businessDayTo.atTime(OffsetTime.of(0, 0, 0, 0, ZoneOffset.of("+09:00"))));
        request.setStoreCode(storeCode);
        OffsetDateTime createReportDatetime =
                OffsetDateTime.of(2018, 6, 7, 13, 14, 0, 0, ZoneOffset.UTC);
        request.setCreateReportDatetime(createReportDatetime);

        String reportId = "TestReportId";
        String reportId2 = "TestReportId2";
        String reportId3 = "TestReportId3";
        List<String> reportIdList = new ArrayList<String>();
        reportIdList.add(reportId);
        reportIdList.add(reportId2);
        reportIdList.add(reportId3);
        request.setReportIdList(reportIdList);

        ReportCreateStatus expectedDto = new ReportCreateStatus();
        String receptionNumber = "TestReceptionNumber";
        expectedDto.setReceiptNumber(receptionNumber);
        expectedDto.setStoreCode(storeCode);
        expectedDto.setReportId(reportId);
        expectedDto.setCreateReportStatus(1);

        ReportCreateStatus expectedDto2 = new ReportCreateStatus();
        String receptionNumber2 = "TestReceptionNumber2";
        expectedDto2.setReceiptNumber(receptionNumber2);
        expectedDto2.setStoreCode(storeCode);
        expectedDto2.setReportId(reportId2);
        expectedDto2.setCreateReportStatus(2);

        ReportCreateStatus expectedDto3 = new ReportCreateStatus();
        String receptionNumber3 = "TestReceptionNumber3";
        expectedDto3.setReceiptNumber(receptionNumber3);
        expectedDto3.setStoreCode(storeCode);
        expectedDto2.setReportId(reportId3);
        expectedDto3.setCreateReportStatus(3);

        List<ReportCreateStatus> expectedDtoList = new ArrayList<ReportCreateStatus>();
        expectedDtoList.add(expectedDto);
        when(reportCreateStatusMapper.selectByBusinessDay(eq(reportId), eq(storeCode),
                eq(businessDayFrom), eq(businessDayTo), any())).thenReturn(expectedDtoList);
        List<ReportCreateStatus> expectedDtoList2 = new ArrayList<ReportCreateStatus>();
        expectedDtoList.add(expectedDto2);
        when(reportCreateStatusMapper.selectByBusinessDay(eq(reportId2), eq(storeCode),
                eq(businessDayFrom), eq(businessDayTo), any())).thenReturn(expectedDtoList2);
        List<ReportCreateStatus> expectedDtoList3 = new ArrayList<ReportCreateStatus>();
        expectedDtoList.add(expectedDto3);
        when(reportCreateStatusMapper.selectByBusinessDay(eq(reportId3), eq(storeCode),
                eq(businessDayFrom), eq(businessDayTo), any())).thenReturn(expectedDtoList3);

        // Method execution
        List<ReportCreateStatus> resultList = getReportListService.getReportList(request);

        // Confirm result
        assertEquals(resultList.size(), 3);

        // verify if the searchList method is called only once successfully
        reset(reportCreateStatusMapper);
    }

    /**
     * Target method：getReportList. Condition：ReceptionNumberList and ReportIdList is null.
     * Verification result confirmation：The acquired data matches the expected value.
     * 
     * @throws Exception (It does not occur as expected value)
     */
    @Test
    public void testBothListNull() throws Exception {
        // Prepare Parameter
        String receptionNumber = "TestReceptionNumber";
        String storeCode = "Store001";

        GetReportListRequest request = new GetReportListRequest();
        request.setStoreCode(storeCode);

        ReportCreateStatus expectedDto = new ReportCreateStatus();
        expectedDto.setReceiptNumber(receptionNumber);
        expectedDto.setStoreCode(storeCode);
        expectedDto.setCreateReportStatus(1);

        // Mock for DB access.
        when(reportCreateStatusMapper.selectByReceiptNumber(eq(receptionNumber), any()))
                .thenReturn(expectedDto);

        try {
            // Method execution
            getReportListService.getReportList(request);
            fail("exception was not occured.");
        } catch (BusinessException e) {
            assertThat(e.getResultObject().getMessage(),
                    is("Validation error receptionNumberList or reportIdList is required."));
        } catch (Exception e) {
            fail("expected exception was not occured.");
        }
    }

    /**
     * Target method：getReportList. Condition：ReceptionNumberList and ReportIdList is empty.
     * Verification result confirmation：The acquired data matches the expected value.
     * 
     * @throws Exception (It does not occur as expected value)
     */
    @Test
    public void testBothListEmpty() throws Exception {
        // Prepare Parameter
        String receptionNumber = "TestReceptionNumber";
        String storeCode = "Store001";

        ReportCreateStatus expectedDto = new ReportCreateStatus();
        expectedDto.setReceiptNumber(receptionNumber);
        expectedDto.setStoreCode(storeCode);
        expectedDto.setCreateReportStatus(1);

        GetReportListRequest request = new GetReportListRequest();
        request.setStoreCode(storeCode);
        request.setReceiptNumberList(new ArrayList<String>());
        request.setReportIdList(new ArrayList<String>());

        // Mock for DB access.
        when(reportCreateStatusMapper.selectByReceiptNumber(eq(receptionNumber), any()))
                .thenReturn(expectedDto);

        try {
            // Method execution
            getReportListService.getReportList(request);
            fail("exception was not occured.");
        } catch (BusinessException e) {
            assertThat(e.getResultObject().getMessage(),
                    is("Validation error receptionNumberList or reportIdList is required."));
        } catch (Exception e) {
            fail("expected exception was not occured.");
        }
    }

    /**
     * Target method：getReportList. Condition：BusinessException occurred at
     * ReportCreateStatusComponent. Verification result confirmation：The acquired data matches the
     * expected value.
     * 
     * @throws Exception (It does not occur as expected value)
     */
    @Test
    public void testBusinessExceptionFromComponent1() throws Exception {
        // Prepare Parameter
        String receptionNumber = "TestReceptionNumber";
        String storeCode = "Store001";

        GetReportListRequest request = new GetReportListRequest();
        List<String> receptionNumberList = new ArrayList<String>();
        receptionNumberList.add(receptionNumber);
        request.setReceiptNumberList(receptionNumberList);
        request.setStoreCode(storeCode);
        OffsetDateTime createReportDatetime =
                OffsetDateTime.of(2018, 6, 7, 13, 14, 0, 0, ZoneOffset.UTC);
        request.setCreateReportDatetime(createReportDatetime);

        ReportCreateStatus expectedDto = new ReportCreateStatus();
        expectedDto.setReceiptNumber(receptionNumber);
        expectedDto.setStoreCode(storeCode);
        expectedDto.setCreateReportStatus(1);

        ResultObject result = new ResultObject();
        result.setName(ErrorName.Business.BUSINESS_CHECK_ERROR);
        result.setMessage("BusinessException Occurred.");
        BusinessException expectedException = new BusinessException(result);

        // Mock for S3 access.
        when(reportCreateStatusMapper.selectByReceiptNumber(eq(receptionNumber), any()))
                .thenThrow(expectedException);

        try {
            // Method execution
            getReportListService.getReportList(request);
            fail("expected exception was not occured.");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(BusinessException.class);
        }
    }

    /**
     * Target method：getReportList. Condition：BusinessException occurred at
     * ReportCreateStatusComponent. Verification result confirmation：The acquired data matches the
     * expected value.
     * 
     * @throws Exception (It does not occur as expected value)
     */
    @Test
    public void testBusinessExceptionFromComponent2() throws Exception {
        // Prepare Parameter
        String reportId = "TestReportId";
        String storeCode = "Store001";

        GetReportListRequest request = new GetReportListRequest();
        List<String> reportIdList = new ArrayList<String>();
        reportIdList.add(reportId);
        request.setReportIdList(reportIdList);
        request.setStoreCode(storeCode);
        OffsetDateTime createReportDatetime =
                OffsetDateTime.of(2018, 6, 7, 13, 14, 0, 0, ZoneOffset.UTC);
        request.setCreateReportDatetime(createReportDatetime);

        LocalDate businessDayFrom = LocalDate.of(2018, 2, 1);
        LocalDate businessDayTo = LocalDate.of(2018, 2, 1);

        request.setBusinessDayFrom(
                businessDayFrom.atTime(OffsetTime.of(0, 0, 0, 0, ZoneOffset.of("+09:00"))));
        request.setBusinessDayTo(
                businessDayTo.atTime(OffsetTime.of(0, 0, 0, 0, ZoneOffset.of("+09:00"))));

        ReportCreateStatus expectedDto = new ReportCreateStatus();
        String receptionNumber = "TestReceptionNumber";
        expectedDto.setReceiptNumber(receptionNumber);
        expectedDto.setStoreCode(storeCode);
        expectedDto.setReportId(reportId);
        expectedDto.setCreateReportStatus(1);

        List<ReportCreateStatus> expectedDtoList = new ArrayList<ReportCreateStatus>();
        expectedDtoList.add(expectedDto);

        ResultObject result = new ResultObject();
        result.setName(ErrorName.Business.BUSINESS_CHECK_ERROR);
        result.setMessage("BusinessException Occurred.");
        BusinessException expectedException = new BusinessException(result);

        // Mock for S3 access.
        when(reportCreateStatusMapper.selectByBusinessDay(eq(reportId), eq(storeCode),
                eq(businessDayFrom), eq(businessDayTo), any())).thenThrow(expectedException);

        try {
            // Method execution
            getReportListService.getReportList(request);
            fail("expected exception was not occured.");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(BusinessException.class);
        }
    }

}
