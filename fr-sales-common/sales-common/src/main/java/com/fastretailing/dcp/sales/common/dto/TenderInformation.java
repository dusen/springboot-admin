/**
 * @(#)TenderInformation.java
 * 
 *                            Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.dto;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fastretailing.dcp.storecommon.dto.Price;
import lombok.Data;

/**
 * Tender information.
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen",
        date = "2018-05-16T10:34:00.975+09:00")

@Data
public class TenderInformation {
    @JsonProperty("discount_amount")
    private Price discountAmount = null;

    @JsonProperty("discount_rate")
    private BigDecimal discountRate = null;

    @JsonProperty("discount_code_id_corporate_id")
    private String discountCodeIdCorporateId = null;

    @JsonProperty("coupon_type")
    private String couponType = null;

    @JsonProperty("coupon_discount_amount_setting")
    private Price couponDiscountAmountSetting = null;

    @JsonProperty("coupon_min_usage_amount_threshold")
    private Price couponMinUsageAmountThreshold = null;

    @JsonProperty("coupon_user_id")
    private String couponUserId = null;

    @JsonProperty("card_no")
    private String cardNo = null;

    @JsonProperty("credit_approval_code")
    private String creditApprovalCode = null;

    @JsonProperty("credit_processing_serial_number")
    private String creditProcessingSerialNumber = null;

    @JsonProperty("credit_payment_type")
    private String creditPaymentType = null;

    @JsonProperty("credit_payment_count")
    private Integer creditPaymentCount = null;

    @JsonProperty("credit_affiliated_store_number")
    private String creditAffiliatedStoreNumber = null;

}

