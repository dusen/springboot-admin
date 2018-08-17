/**
 * @(#)TenderPaymentMapper.java
 *
 *                              Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.repository.optional;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.fastretailing.dcp.sales.common.entity.optional.TenderPaymentOptional;
import com.fastretailing.dcp.sales.common.entity.optional.TenderPaymentOptionalCondition;

/**
 * Tender payment optional mapper.
 */
@Mapper
public interface TenderPaymentOptionalMapper {

    /**
     * Select tender payment data list.
     * 
     * @param condition Tender payment optional condition.
     * @return Tender payment optional list.
     */
    List<TenderPaymentOptional> selectTenderPaymentDataList(
            TenderPaymentOptionalCondition condition);

}
