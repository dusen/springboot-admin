/**
 * @(#)TransactionInquiryOptionalMapper.java
 *
 *                                           Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.repository.optional;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.fastretailing.dcp.sales.common.entity.optional.TransactionInquiryOptional;

@Mapper
public interface TransactionInquiryOptionalMapper {

    /**
     * Select by integrated order id and inquiry pattern.
     * 
     * @param integratedOrderId Integrated order id.
     * @param inquiryPattern Inquiry pattern.
     * @return List of transaction inquiry tables.
     */
    List<TransactionInquiryOptional> selectByIntegratedOrderIdInquiryPattern(
            @Param("integratedOrderId") String integratedOrderId,
            @Param("inquiryPattern") String inquiryPattern);

    /**
     * Select by integrated order id and order sub number
     * 
     * @param integratedOrderId Integrated order id.
     * @param orderSubNumber Order sub number.
     * @return List of transaction inquiry tables.
     */
    List<TransactionInquiryOptional> selectByIntegratedOrderIdOrderSubNumber(
            @Param("integratedOrderId") String integratedOrderId,
            @Param("orderSubNumber") Integer orderSubNumber);

    /**
     * Select by sales transaction id.
     * 
     * @param integratedOrderId Integrated order id.
     * @param orderSubNumber Order sub number.
     * @param salesTransactionId Sales transaction id.
     * @return List of transaction inquiry tables.
     */
    List<TransactionInquiryOptional> selectByTransactionId(
            @Param("integratedOrderId") String integratedOrderId,
            @Param("orderSubNumber") Integer orderSubNumber,
            @Param("salesTransactionId") String salesTransactionId);
}
