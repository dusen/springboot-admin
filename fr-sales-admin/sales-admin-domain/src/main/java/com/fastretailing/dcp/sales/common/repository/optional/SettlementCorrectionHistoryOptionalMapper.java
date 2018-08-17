/**
 * @(#)SettlementCorrectionHistoryOptionalMapper.java
 *
 *                                                    Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.repository.optional;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.fastretailing.dcp.sales.common.entity.optional.SettlementCorrectionHistoryOptional;
import com.fastretailing.dcp.sales.common.entity.optional.SettlementCorrectionHistoryOptionalCondition;

/**
 * Settlement correction history optional mapper.
 */
@Mapper
public interface SettlementCorrectionHistoryOptionalMapper {

    /**
     * Search all settlement correction history record by condition.
     *
     * @param settlementCorrectionHistoryCondition.
     * @return List of write off confirm detail data.
     */
    List<SettlementCorrectionHistoryOptional> selectSettlementCorrectionHistoryListByCondition(
            SettlementCorrectionHistoryOptionalCondition settlementCorrectionHistoryCondition);

    List<SettlementCorrectionHistoryOptional> selectStoreCodeAndNameByViewStoreCode(String viewStoreCode);

}
