/**
 * @(#)ReportMasterMapper.java
 * 
 *                             Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Repository class for m_report_master table.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
@Mapper
public interface GeneralStoreTimeZoneMapper {

    /**
     * Get report master in the database corresponding to as below. Report Id, Report Type, Country
     * code
     * 
     * @param tableName Table name.
     * @param targetField Target field.
     * @param conditionField Condition field.
     * @param generalPurposeType General type.
     * @return General type value.
     */
    String selectGeneralItem(@Param("tableName") String tableName,
            @Param("targetField") String targetField,
            @Param("conditionField") String conditionField,
            @Param("generalPurposeType") String generalPurposeType,
            @Param("storeCode") String storeCode);
}
