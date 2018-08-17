/**
 * @(#)SalesPayoffIntegrityCheckOptionalEntity.java
 *
 *                                                  Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.entity.optional;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class SalesPayoffDataUpdateOptionalEntity {

    /**
     * Payoff type code.
     */
    private String payoffTypeCode;

    /**
     * Payoff type name.
     */
    private String payoffTypeName;
    
    /**
     * Payoff type sub number code.
     */
    private String payoffTypeSubNumberCode;
    
    /**
     * Payoff type sub number name.
     */
    private String payoffTypeSubNumberName;

    /**
     * Payoff amount.
     */
    private BigDecimal payoffAmount;

    /**
     * Payoff quantity.
     */
    private BigDecimal payoffQuantity;

    /**
     * Integrity check type.
     */
    private String integrityCheckType;
    
    /**
     * Pile up payoff amount.
     */
    private BigDecimal pileUpPayoffAmount;

    /**
     * Pile up payoff quantity.
     */
    private BigDecimal pileUpPayoffQuantity;
    /**
     * Pile up payoff data same decide flag.
     */
    private String pileUpPayoffDataSameDecideFlag;
}
