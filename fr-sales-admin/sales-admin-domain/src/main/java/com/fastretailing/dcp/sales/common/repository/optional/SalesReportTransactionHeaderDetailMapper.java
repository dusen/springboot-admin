/**
 * @(#)SalesReportTransactionHeaderDetailMapper.java
 *
 *                                                   Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.repository.optional;

import java.time.OffsetDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.fastretailing.dcp.sales.common.entity.optional.SalesReportTransactionHeaderDetailOptional;
import com.fastretailing.dcp.sales.common.entity.optional.SalesReportTransactionHeaderOptional;

/**
 * Repository class for excepted child if work table.
 */
@Mapper
public interface SalesReportTransactionHeaderDetailMapper {

    /**
     * From the table retrieve all relevant items.
     * 
     * @Param storeCode Store code.
     * @Param businessDate Business date.
     * @return Single item loss worst list detail.
     */
    int updateSalesReportTransactionHeader(
            @Param("departmentSummaryFlag") Boolean departmentSummaryFlag,
            @Param("salesTransactionId") String salesTransactionId,
            @Param("updateUserId") String updateUserId,
            @Param("updateProgramId") String updateProgramId,
            @Param("updateDatetime") OffsetDateTime updateDatetime);

    /**
     * From the table retrieve all relevant items.
     * 
     * @Param storeCode Store code.
     * @return Sales report transaction header optional entity list.
     * 
     */
    List<SalesReportTransactionHeaderDetailOptional> selectSalesReportTransactionHeaderByStoreCode(
            @Param("storeCode") String storeCode);

    /**
     * From the table retrieve all relevant items.
     * 
     * @return Sales report transaction header optional entity list.
     * 
     */
    List<SalesReportTransactionHeaderDetailOptional> selectSalesReportTransactionHeader();

    /**
     * From the table retrieve all relevant items.
     * 
     * @Param originalTransactionId Original transaction id.
     * @return Sales report transaction header optional entity list.
     * 
     */
    SalesReportTransactionHeaderDetailOptional selectSalesReportTransactionHeaderByKey(
            @Param("originalTransactionId") String originalTransactionId);
    
    /**
     * Select max receipt number.
     * 
     * @param condition Sales report transaction header optional condition.
     * @return Max receipt number.
     */
    Integer selectMaxReceiptNumber(SalesReportTransactionHeaderOptional condition);
}
