/**
 * @(#)EdfLinkageResponse.java
 *
 *                             Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.resttemplate.client.entity.edf;

import com.fastretailing.dcp.storecommon.dto.CommonStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Edf linkage response.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EdfLinkageResponse extends CommonStatus {

    /**
     * Job sequence.
     */
    private String jobSeq;
}
