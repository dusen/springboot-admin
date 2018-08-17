/**
 * @(#)ErrorEvacuationSalesTransactionHeaderDetailOptionalMapper.java
 *
 *                                                                    Copyright (c) 2018 Fast
 *                                                                    Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.repository.optional;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.fastretailing.dcp.sales.common.entity.optional.ErrorEvacuationSalesTransactionHeaderDetailOptional;

/**
 * Error evacuation sales transaction header detail optional mapper.
 */
@Mapper
public interface ErrorEvacuationSalesTransactionHeaderDetailOptionalMapper {

    /**
     * Find all error evacuation header detail data by sales transaction id.
     *
     * @param salesTransactionErrorId Sales transaction error id.
     * @return List of error evacuation sales transaction header detail optional data.
     */
    List<ErrorEvacuationSalesTransactionHeaderDetailOptional> selectErrorEvacuationHeaderDetailBySalesTransactionId(
            @Param("salesTransactionErrorId") String salesTransactionErrorId);

}
