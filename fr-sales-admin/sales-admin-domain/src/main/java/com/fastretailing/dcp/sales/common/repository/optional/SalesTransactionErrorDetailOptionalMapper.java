/**
 * @(#)SalesTransactionErrorDetailOptionalMapper.java
 *
 *                                                    Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.repository.optional;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.fastretailing.dcp.sales.common.entity.optional.SalesTransactionErrorDetail;
import com.fastretailing.dcp.sales.common.entity.optional.SalesTransactionErrorDetailCondition;

/**
 * Sales transaction error detail optional mapper.
 */
@Mapper
public interface SalesTransactionErrorDetailOptionalMapper {

    /**
     * Find all sales transaction error detail data by condition.
     *
     * @param salesTransactionErrorDetailCondition Sales transaction error detail condition.
     * @return List of sales transaction error detail data.
     */
    List<SalesTransactionErrorDetail> selectSalesTransactionErrorDetailByCondition(
            SalesTransactionErrorDetailCondition salesTransactionErrorDetailCondition);

    /**
     * Update data alteration status type.
     *
     * @param dataAlterationStatusType Data alteration status type.
     * @return Count of update.
     */
    int updateDataAlterationStatusType(
            @Param("dataAlterationStatusType") String dataAlterationStatusType,
            @Param("salesTransactionErrorId") String salesTransactionErrorId);

    /**
     * Select time zone by store code.
     * 
     * @param storeCode store Code.
     * @return Code.
     */
    List<String> selectTimeZoneByStoreCode(@Param("storeCode") String storeCode);

    /**
     * Update by primary key selective.
     * 
     * @param salesTransactionErrorDetailrd Sales transaction error detail.
     * @return
     */
    int updateByPrimaryKeySelective(SalesTransactionErrorDetail salesTransactionErrorDetail);
}
