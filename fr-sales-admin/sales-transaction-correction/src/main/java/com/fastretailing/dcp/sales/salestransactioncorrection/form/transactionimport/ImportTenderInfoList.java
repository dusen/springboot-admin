/**
 * @(#)ImportTenderInfoList.java
 *
 *                               Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactioncorrection.form.transactionimport;

import java.math.BigDecimal;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fastretailing.dcp.storecommon.dto.Price;
import lombok.Data;

/**
 * Import tender info.
 * 
 */
@Data
public class ImportTenderInfoList {

    /** Discount amount. */
    @JsonProperty("discount_amount")
    @Valid
    private Price discountAmount;

    /** Discount rate. */
    @JsonProperty("discount_rate")
    @Digits(fraction = 4, integer = 12)
    private BigDecimal discountRate;

    /** Discount code id corporate id. */
    @JsonProperty("discount_code_id_corporate_id")
    @Size(max = 30)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String discountCodeIdCorporateId;

    /** Coupon type. */
    @JsonProperty("coupon_type")
    @Size(max = 6)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String couponType;

    /** Coupon discount amount setting. */
    @JsonProperty("coupon_discount_amount_setting")
    @Valid
    private Price couponDiscountAmountSetting;

    /** Coupon min usage amount threshold. */
    @JsonProperty("coupon_min_usage_amount_threshold")
    @Valid
    private Price couponMinUsageAmountThreshold;

    /** Coupon user ID. */
    @JsonProperty("coupon_user_id")
    @Size(max = 30)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String couponUserId;

    /** Card no. */
    @JsonProperty("card_no")
    @Size(max = 30)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String cardNo;

    /** Credit approval code. */
    @JsonProperty("credit_approval_code")
    @Size(max = 30)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String creditApprovalCode;

    /** Credit processing serial number. */
    @JsonProperty("credit_processing_serial_number")
    @Size(max = 30)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String creditProcessingSerialNumber;

    /** Credit payment type. */
    @JsonProperty("credit_payment_type")
    @Size(max = 30)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String creditPaymentType;

    /** Credit payment count. */
    @JsonProperty("credit_payment_count")
    @Max(99999)
    private Integer creditPaymentCount;

    /** Credit affiliated store number. */
    @JsonProperty("credit_affiliated_store_number")
    @Size(max = 30)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String creditAffiliatedStoreNumber;

}
