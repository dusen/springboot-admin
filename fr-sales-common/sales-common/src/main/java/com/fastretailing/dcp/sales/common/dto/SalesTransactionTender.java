/**
 * @(#)SalesTransactionTender.java
 * 
 *                                 Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fastretailing.dcp.storecommon.dto.Price;
import lombok.Data;

/**
 * Sales transaction tender.
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen",
        date = "2018-05-16T10:34:00.975+09:00")

@Data
public class SalesTransactionTender {
    @JsonProperty("tender_sub_number")
    private Integer tenderSubNumber = null;

    @JsonProperty("tender_group_id")
    private String tenderGroupId = null;

    @JsonProperty("tender_id")
    private String tenderId = null;

    @JsonProperty("payment_sign")
    private String paymentSign = null;

    @JsonProperty("tax_included_payment_amount")
    private Price taxIncludedPaymentAmount = null;

    @JsonProperty("tender_info_list")
    private TenderInformation tenderInfoList = new TenderInformation();

}

