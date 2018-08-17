/**
 * @(#)SalesPayoffDataUpdateOptionalMapper.java
 *
 *                                                  Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.repository.optional;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import com.fastretailing.dcp.sales.common.entity.optional.SalesPayoffDataUpdateErrorStatusOptionalCondition;
import com.fastretailing.dcp.sales.common.entity.optional.SalesPayoffDataUpdateOptionalCondition;
import com.fastretailing.dcp.sales.common.entity.optional.SalesPayoffDataUpdateOptionalEntity;

@Mapper
public interface SalesPayoffDataUpdateOptionalMapper {

    /**
     * Select sales payoff error status.
     * 
     * @return Sales payoff data record sum.
     */
    int selectSalesPayoffDataUpdateErrorStatus(
    		SalesPayoffDataUpdateErrorStatusOptionalCondition conditon);

    /**
     * Select sales payoff data update list.
     *
     * @param contidion Sales payoff data update list select optional contidion.
     * @return Sales payoff data update entity list.
     */
    List<SalesPayoffDataUpdateOptionalEntity> selectSalesPayoffDataUpdateList(
    		SalesPayoffDataUpdateOptionalCondition condition);
}
