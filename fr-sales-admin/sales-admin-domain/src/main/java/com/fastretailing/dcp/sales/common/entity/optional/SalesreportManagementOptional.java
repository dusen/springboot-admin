/**
 * @(#)SalesreportManagementOptional.java
 *
 *                                        Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.entity.optional;

import lombok.Data;

/**
 * Sales report management optional.
 */
@Data
public class SalesreportManagementOptional {

    /** Store code. */
    private String storeCode;

    /** Business date. */
    private String businessDate;

    /** Reception Number. */
    private String receptionNumber;
}
