/**
 * @(#)StoreLocalDateTimeConverter.java
 * 
 *                                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.util;

import java.time.LocalDateTime;
import java.util.TimeZone;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fastretailing.dcp.common.exception.SystemException;
import com.fastretailing.dcp.storecommon.report.api.config.StoreTimeZoneProperty;
import com.fastretailing.dcp.storecommon.report.api.repository.GeneralStoreTimeZoneMapper;
import com.fastretailing.dcp.storecommon.util.DateUtility;
import lombok.extern.slf4j.Slf4j;

/**
 * Component for converting date time to store local.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
@Slf4j
@Component
public class StoreLocalDateTimeConverter {

    /**
     * Mapper for getting timezone id from general table.
     */
    @Autowired
    private GeneralStoreTimeZoneMapper generalStoreTimeZoneMapper;

    /**
     * Properties for getting timezone id from general table.
     */
    @Autowired
    private StoreTimeZoneProperty reportTimeZoneProperty;

    /**
     * Gets system date time and converts it to store local.
     * 
     * @param storeCode Store code.
     * @return A [yyyyMMddhhmmss] formatted date time string.
     */
    public String getStoreLocalDateTime(String storeCode) {

        String storeLocalTimeZoneId = generalStoreTimeZoneMapper.selectGeneralItem(
                reportTimeZoneProperty.getTableName(), reportTimeZoneProperty.getTargetField(),
                reportTimeZoneProperty.getConditionField(),
                reportTimeZoneProperty.getGeneralPurposeType(), storeCode);

        if (StringUtils.isEmpty(storeLocalTimeZoneId)) {
            log.error(
                    "can not get time zone id.[table:{}][target field:{}]"
                            + "[condition field:{}][type:{}]",
                    reportTimeZoneProperty.getTableName(), reportTimeZoneProperty.getTargetField(),
                    reportTimeZoneProperty.getConditionField(),
                    reportTimeZoneProperty.getGeneralPurposeType());
            throw new SystemException();
        }

        LocalDateTime currentTime = DateUtility.changeTimeZone(LocalDateTime.now(),
                TimeZone.getDefault(), TimeZone.getTimeZone(storeLocalTimeZoneId));

        return DateUtility.formatDateTime(currentTime, DateUtility.DateTimeFormat.UUUUMMDDHHMMSS);
    }
}
