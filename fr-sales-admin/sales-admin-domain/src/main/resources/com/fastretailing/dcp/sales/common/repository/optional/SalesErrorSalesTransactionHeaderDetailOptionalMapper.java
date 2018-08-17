/**
 * @(#)SalesErrorSalesTransactionHeaderDetailOptionalMapper.java
 *
 *                                                               Copyright (c) 2018 Fast Retailing
 *                                                               Corporation.
 */

package com.fastretailing.dcp.sales.common.repository.optional;

import org.apache.ibatis.annotations.Mapper;
import com.fastretailing.dcp.sales.common.entity.optional.SalesErrorSalesTransactionHeaderDetailOptional;
import com.fastretailing.dcp.sales.common.entity.optional.SalesErrorSalesTransactionHeaderDetailOptionalCondition;

/**
 * Sales error sales transaction header detail mapper.
 */
@Mapper
public interface SalesErrorSalesTransactionHeaderDetailOptionalMapper {

    /**
     * Find all Sales error sales transaction header detail by condition.
     *
     * @param salesErrorSalesTransactionHeaderDetailOptionalCondition Sales error sales transaction
     *        header detail condition.
     * @return Sales error sales transaction header detail optional data.
     */
    SalesErrorSalesTransactionHeaderDetailOptional selectSalesErrorSalesTransactionHeaderDetailByCondition(
            SalesErrorSalesTransactionHeaderDetailOptionalCondition salesErrorSalesTransactionHeaderDetailOptionalCondition);

}
