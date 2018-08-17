/**
 * @(#)SalesPayoffUnmatchListOptionalMapper.java
 *
 *                                               Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.repository.optional;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.fastretailing.dcp.sales.common.entity.optional.SalesPayoffUnmatchCondition;
import com.fastretailing.dcp.sales.common.entity.optional.SalesPayoffUnmatchOptionalEntity;

/**
 * Sales payoff unmatch optional mapper.
 */
@Mapper
public interface SalesPayoffUnmatchListOptionalMapper {

    /**
     * Find all sales transaction history detail data by condition.
     *
     * @param salesPayoffUnmatchCondition Sales payoff unmatch optional condition.
     * @return List of sales payoff unmatch data.
     */
    List<SalesPayoffUnmatchOptionalEntity> selectSalesPayoffUnmatchOptionalByCondition(
            SalesPayoffUnmatchCondition salesPayoffUnmatchCondition);
}
