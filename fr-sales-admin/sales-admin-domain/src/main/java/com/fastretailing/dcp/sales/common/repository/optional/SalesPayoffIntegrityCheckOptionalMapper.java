/**
 * @(#)SalesPayoffIntegrityCheckOptionalMapper.java
 *
 *                                                  Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.repository.optional;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.fastretailing.dcp.sales.common.entity.optional.SalesPayoffIntegrityCheckOptional;
import com.fastretailing.dcp.sales.common.entity.optional.SalesPayoffIntegrityCheckOptionalCondition;

@Mapper
public interface SalesPayoffIntegrityCheckOptionalMapper {

    /**
     * Select sales payoff integrity check list from screen.
     * 
     * @return Sales payoff integrity check entity list.
     */
    List<SalesPayoffIntegrityCheckOptional> selectSalesPayoffIntegrityListFromScreen();

    /**
     * Select sales payoff integrity check list from end of day process.
     *
     * @param condition Sales payoff integrity check optional condition.
     * @return Sales payoff integrity check entity list.
     */
    List<SalesPayoffIntegrityCheckOptional> selectSalesPayoffIntegrityListFromEndOfDayProcess(
            SalesPayoffIntegrityCheckOptionalCondition condition);
}
