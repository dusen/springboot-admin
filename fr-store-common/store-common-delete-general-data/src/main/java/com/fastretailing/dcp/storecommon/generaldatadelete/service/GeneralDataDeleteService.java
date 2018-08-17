/**
 * @(#)GeneralDataDeleteService.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.generaldatadelete.service;

import com.fastretailing.dcp.storecommon.dto.CommonStatus;

/**
 * General data delete common interface.
 */
public interface GeneralDataDeleteService {

    /**
     * General data delete preparation entry.
     * 
     * @param processingTargetType Process target type.
     * @return Common status.
     */
    CommonStatus delete(String processingTargetType);

}
