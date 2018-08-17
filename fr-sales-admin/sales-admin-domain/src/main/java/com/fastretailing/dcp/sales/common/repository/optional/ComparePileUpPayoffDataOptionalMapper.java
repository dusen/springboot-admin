/**
 * @(#)ComparePileUpPayoffDataOptionalMapper.java
 *
 *                                                Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.repository.optional;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.fastretailing.dcp.sales.common.entity.optional.ComparePileUpPayoffDataOptional;
import com.fastretailing.dcp.sales.common.entity.optional.SalesPayoffIntegrityCheckOptionalCondition;

@Mapper
public interface ComparePileUpPayoffDataOptionalMapper {

    /**
     * Select compare pile up payoff data.
     * 
     * @param condition Sales payoff integrity check optional condition.
     * @return Compare pile up payoff data optional list.
     */
    List<ComparePileUpPayoffDataOptional> selectComparePileUpPayoffDataList(
            SalesPayoffIntegrityCheckOptionalCondition condition);
}
