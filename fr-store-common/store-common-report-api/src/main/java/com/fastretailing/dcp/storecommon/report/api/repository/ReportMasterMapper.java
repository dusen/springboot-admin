/**
 * @(#)ReportMasterMapper.java
 * 
 *                             Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.fastretailing.dcp.storecommon.report.api.entity.ReportMaster;

/**
 * Repository class for m_report_master table.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
@Mapper
public interface ReportMasterMapper {

    /**
     * Get report master in the database.
     * 
     * @param reportId Report Id.
     * @param reportType Report Type.
     * @param countryCode Country Code.
     * @return DTO of report master.
     */
    ReportMaster select(@Param("report_id") String reportId,
            @Param("report_type") String reportType, @Param("country_code") String countryCode);

}
