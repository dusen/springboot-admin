/**
 * @(#)TenderAndTransformMasterOptionalMapper.java
 *
 *                                                 Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.repository.optional;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.fastretailing.dcp.sales.common.entity.optional.TenderAndTransformInformationOptional;

/**
 * Tender and transform master optional mapper.
 */
@Mapper
public interface TenderAndTransformMasterOptionalMapper {

    /**
     * Get tender info by tender id.
     * 
     * @param tenderId Tender id.
     * @return Tender and transform info.
     */
    TenderAndTransformInformationOptional getTenderInfoByTenderId(
            @Param("storeCode") String storeCode, @Param("tenderId") String tenderId);

    /**
     * Get tender info by ims tender id.
     * 
     * @param tenderId Tender id.
     * @return Tender and transform info.
     */
    TenderAndTransformInformationOptional getTenderInfoByImsTenderId(
            @Param("storeCode") String storeCode, @Param("imsTenderId") Integer imsTenderId);

    /**
     * Get tender info by store code.
     * 
     * @param tenderId Tender id.
     * @return Tender and transform info.
     */
    List<TenderAndTransformInformationOptional> getTenderInfoByStoreCode(
            @Param("storeCode") String storeCode);

}
