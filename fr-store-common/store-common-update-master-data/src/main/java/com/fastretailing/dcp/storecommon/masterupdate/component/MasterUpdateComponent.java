/**
 * @(#)MasterUpdateComponent.java
 *
 *                                Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.masterupdate.component;

import com.fastretailing.dcp.storecommon.dto.CommonStatus;
import com.fastretailing.dcp.storecommon.masterupdate.dto.GeneralMasterRequest;

/**
 * Master update component.
 */
public interface MasterUpdateComponent {
    /**
     * Master update.
     * 
     * @param generalMasterRequest General master request to update.
     * @return Common status.
     */
    CommonStatus updateMasterData(GeneralMasterRequest generalMasterRequest);
}
