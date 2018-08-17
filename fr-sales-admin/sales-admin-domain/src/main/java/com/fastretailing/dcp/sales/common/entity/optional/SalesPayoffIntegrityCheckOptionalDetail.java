/**
 * @(#)SalesPayoffIntegrityCheckOptionalDetail.java
 *
 *                                                  Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.entity.optional;

import java.util.List;
import lombok.Data;

/**
 * Sales payoff integrity check optional detail.
 */
@Data
public class SalesPayoffIntegrityCheckOptionalDetail {

    /**
     * Sales payoff integrity check optional list.
     */
    private List<SalesPayoffIntegrityCheckOptional> salesPayoffIntegrityCheckOptionalList;

    /**
     * Payoff type check target flag.
     */
    private String payoffTypeCheckTargetFlag;

    /**
     * Balance check target flag.
     */
    private String balanceCheckTargetFlag;
}
