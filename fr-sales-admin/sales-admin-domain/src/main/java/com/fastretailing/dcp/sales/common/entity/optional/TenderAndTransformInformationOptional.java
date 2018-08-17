/**
 * @(#)TenderAndTransformInfoOptional.java
 *
 *                                         Copyright (c) 2018 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.sales.common.entity.optional;

import lombok.Data;

/**
 * Tender and transform info optional.
 */
@Data
public class TenderAndTransformInformationOptional {
    /** Ims tender group. */
    private String imsTenderGroup;

    /** Ims tender id. */
    private String imsTenderId;

    /** Tender name. */
    private String tenderName;
    
    /** Tender id. */
    private String tenderId;
    
    /** Tender group. */
    private String tenderGroup;
}
