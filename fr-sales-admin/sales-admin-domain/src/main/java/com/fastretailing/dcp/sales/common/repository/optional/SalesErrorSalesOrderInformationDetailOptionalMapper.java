/**
 * @(#)SalesErrorSalesOrderInformationDetailOptionalMapper.java
 *
 *                                                              Copyright (c) 2018 Fast Retailing
 *                                                              Corporation.
 */

package com.fastretailing.dcp.sales.common.repository.optional;

import org.apache.ibatis.annotations.Mapper;
import com.fastretailing.dcp.sales.common.entity.optional.SalesErrorSalesOrderDetailInformationOptional;
import com.fastretailing.dcp.sales.common.entity.optional.SalesErrorSalesOrderInformationOptionalKey;

/**
 * Sales error sales order information detail optional mapper.
 */
@Mapper
public interface SalesErrorSalesOrderInformationDetailOptionalMapper {

    /**
     * Find sales error sales order information detail data by transaction id.
     *
     * @param salesErrorSalesOrderInformationOptionalKey Sales error sales order information key.
     * @return Sales error sales order detail information optional.
     */
    SalesErrorSalesOrderDetailInformationOptional selectOrderInformationDetailByTransactionId(
            SalesErrorSalesOrderInformationOptionalKey salesErrorSalesOrderInformationOptionalKey);

}
