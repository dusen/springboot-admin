/**
 * @(#)GeneralDataDeleteServiceImpl.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.generaldatadelete.service;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.fastretailing.dcp.storecommon.dto.CommonStatus;
import com.fastretailing.dcp.storecommon.generaldatadelete.dto.GeneralDataDeleteEntity;
import com.fastretailing.dcp.storecommon.generaldatadelete.repository.GeneralDataDeleteConfigMapper;
import com.fastretailing.dcp.storecommon.generaldatadelete.repository.ObjectTableDeleteMapper;
import com.fastretailing.dcp.storecommon.util.DateUtility;

/**
 * General data delete service class.
 */
@Component
public class GeneralDataDeleteServiceImpl implements GeneralDataDeleteService {

    /** Setting value constants. */
    private static final String PROCESSING_TARGET_COMMON = "COMMON";

    /** DB access parts. */
    @Autowired
    private GeneralDataDeleteConfigMapper generalDataDeleteConfigMapper;

    /** DB access parts. */
    @Autowired
    private ObjectTableDeleteMapper objectTableDeleteMapper;


    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public CommonStatus delete(String parammeterProcessingTarget) {

        // If the check processing target type is null, value is "COMMON".
        String processingTarget = StringUtils.defaultIfEmpty(parammeterProcessingTarget,
                PROCESSING_TARGET_COMMON);

        // Select general data from m_general_delete_setting.
        List<GeneralDataDeleteEntity> generalDataDeleteList =
                generalDataDeleteConfigMapper.selectByProcessingTargetType(processingTarget);
        // Delete the obtained object table.
        generalDataDeleteList.forEach(generalDataDeleteEntity -> {
            // Limited date(UTC) minus saved_days.
            String limitedDate = DateUtility.formatDateTime(DateUtility.getZonedDateTimeUtc()
                    .minusDays(generalDataDeleteEntity.getSavedDays()), 
                        DateUtility.ZONE_ID_UTC, DateUtility.OFFSET_DATE_TIME_FORMAT);
                    
            objectTableDeleteMapper.delete(generalDataDeleteEntity, limitedDate);
        });

        return new CommonStatus();
    }
}
