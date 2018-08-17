/**
 * @(#)EdfLinkageRequest.java
 *
 *                            Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.resttemplate.client.entity.edf;

import com.fastretailing.dcp.sales.common.resttemplate.client.entity.edf.GlobalHeaders;
import lombok.Data;

/**
 * Edf linkage request.
 */
@Data
public class EdfLinkageRequest {

    /**
     * Global headers.
     */
    private GlobalHeaders globalHeaders;
}
