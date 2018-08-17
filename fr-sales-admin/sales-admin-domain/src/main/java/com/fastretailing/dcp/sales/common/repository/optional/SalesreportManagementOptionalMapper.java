/**
 * @(#)SalesreportManagementOptionalMapper.java
 *
 *                                              Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.repository.optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.fastretailing.dcp.sales.common.entity.optional.SalesreportManagementOptional;

/**
 * Repository class for sales report management table.
 */
@Mapper
public interface SalesreportManagementOptionalMapper {
    /**
     * From the table retrieve all relevant items.
     * 
     * @Param storeCode Store code.
     * @param businessDate Business date.
     * @return Sales report management optional.
     * 
     */
    SalesreportManagementOptional selectByPrimaryKey(@Param("storeCode") String storeCode,
            @Param("businessDate") String businessDate);
}
