/**
 * @(#)ReportCreateStatusMapper.java
 * 
 *                                   Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.fastretailing.dcp.storecommon.report.api.entity.ReportCreateStatus;

/**
 * Repository class for t_report_create_status table.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
@Mapper
public interface ReportCreateStatusMapper {

    /**
     * Gets create report status in the database corresponding to receipt number.
     * 
     * @param receiptNumber Receipt number.
     * @param createReportDatetime Date time when report created.
     * @return DTO of report create status.
     */
    ReportCreateStatus selectByReceiptNumber(@Param("receipt_number") String receiptNumber,
            @Param("create_report_datetime") LocalDateTime createReportDatetime);

    /**
     * Gets create report status in the database corresponding to as below.<br>
     * report Id, business day(From), business day(To).
     * 
     * @param reportId Report Id.
     * @param storeCode Store code.
     * @param businessDayFrom Business day(From).
     * @param businessDayTo Business day(To).
     * @param createReportDatetime Date time when report created.
     * @return DTO of create report status.
     */
    List<ReportCreateStatus> selectByBusinessDay(@Param("report_id") String reportId,
            @Param("store_code") String storeCode,
            @Param("business_day_from") LocalDate businessDayFrom,
            @Param("business_day_to") LocalDate businessDayTo,
            @Param("create_report_datetime") LocalDateTime createReportDatetime);

    /**
     * Gets create report status in the database corresponding to as below.<br>
     * create report status, delete report business day.
     * 
     * @param createReportStatus Create report status.
     * @param deleteReportBusinessDay Date when report delete.
     * @return DTO of create report status.
     */
    List<ReportCreateStatus> selectByDeleteReportBusinessDay(
            @Param("create_report_status") int createReportStatus,
            @Param("delete_report_business_day") LocalDate deleteReportBusinessDay);

    /**
     * Registers new create report status in the database.
     * 
     * @param status Report crate status.
     * @return Registration success count.
     */
    int insertReportCreateStatus(@Param("status") ReportCreateStatus status);

    /**
     * In case of Normal. <br>
     * Updates create report status in the database.
     * 
     * @param status Report crate status.
     * @return Updated record count.
     */
    int updateCreateStatus(@Param("status") ReportCreateStatus status);

    /**
     * In case of Update Auto Print Status. <br>
     * Updates auto print status in the database.
     * 
     * @param status Report crate status.
     * @return Registration success count.
     */
    int updateAutoPrintStatus(@Param("status") ReportCreateStatus status);

    /**
     * Delete create report status in the database corresponding to receipt number.
     * 
     * @param receiptNumber Report receipt number.
     * @return Deleted record count.
     */
    int deleteByReceiptNumber(@Param("receipt_number") String receiptNumber);
}
