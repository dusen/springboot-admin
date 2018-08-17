/**
 * @(#)ItemListHeaderMasterOptionalMapper.java
 *
 *                                             Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.repository.optional;

import java.time.OffsetDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Item list header master optional mapper.
 */
@Mapper
public interface ItemListHeaderMasterOptionalMapper {

    /**
     * Insert table item list header master.
     * 
     * @param updateUserId Update and insert user id.
     * @param updateDatetime Update and insert date time.
     * @param updateProgramId Update and insert program id.
     * @param lotNumber Lot number from request.
     * @return Insert result count.
     */
    int insertBySkulistParentWork(@Param("updateUserId") String updateUserId,
            @Param("updateDatetime") OffsetDateTime updateDatetime,
            @Param("updateProgramId") String updateProgramId, @Param("lotNumber") String lotNumber);

    /**
     * Update table item list header master.
     * 
     * @param updateUserId Update user id.
     * @param updateDatetime Update date time.
     * @param updateProgramId Update program id.
     * @param lotNumber Lot number from request.
     * @return Update result count.
     */
    int updateBySkulistParentWork(@Param("updateUserId") String updateUserId,
            @Param("updateDatetime") OffsetDateTime updateDatetime,
            @Param("updateProgramId") String updateProgramId, @Param("lotNumber") String lotNumber);

    /**
     * Update all column in table item list header master. Equals to delete all then insert.
     * 
     * @param updateUserId Update user id.
     * @param updateDatetime Update date time.
     * @param updateProgramId Update program id.
     * @param lotNumber Lot number from request.
     * @return Update result count.
     */
    int updateAllBySkulistParentWork(@Param("updateUserId") String updateUserId,
            @Param("updateDatetime") OffsetDateTime updateDatetime,
            @Param("updateProgramId") String updateProgramId, @Param("lotNumber") String lotNumber);

    /**
     * Delete table item list header master.
     * 
     * @param lotNumber Lot number from request.
     * @return Delete result count.
     */
    int deleteBySkulistParentWork(@Param("lotNumber") String lotNumber);

    /**
     * Check if master data in table translation business code.
     * 
     * @param lotNumber Lot number from request.
     * @return List of system brand code.
     */
    List<String> getSystemBrandCode(@Param("lotNumber") String lotNumber);

}
