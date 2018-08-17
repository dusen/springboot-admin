/**
 * @(#)AlterationPayoffDataRequest.java
 *
 *                                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.declareirregularsettlements.form;

import java.util.List;
import lombok.Data;

/**
 * Alteration payoff data request.
 *
 */
@Data
public class AlterationPayoffDataRequest {

    /**
     * Store code.
     */
    private String storeCode;

    /**
     * Cash register number.
     */
    private String cashRegisterNo;

    /**
     * Payoff date.
     */
    private String payoffDate;

    /**
     * Login user id.
     */
    private String loginUserId;

    /**
     * Payoff information List.
     */
    private List<PayoffInformation> payoffInformationList;
}
