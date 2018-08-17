/**
 * @(#)ImportSalesTransactionTaxDetail.java
 *
 *                                          Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactioncorrectionhistorydetail.form.transactionomport;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fastretailing.dcp.common.validation.annotation.Alphanumeric;
import com.fastretailing.dcp.sales.importtransaction.dto.groups.Correction;
import com.fastretailing.dcp.sales.importtransaction.dto.groups.Insert;
import com.fastretailing.dcp.sales.importtransaction.dto.groups.PosInsert;
import com.fastretailing.dcp.storecommon.dto.Price;
import lombok.Data;

/**
 * Import sales transaction tender.
 * 
 */
@Data
public class ImportSalesTransactionTender {

    /** Ims tender group. */
    private String imsTenderGroup;

    /** Ims tender id. */
    private String imsTenderId;

    /** Tender name. */
    private String tenderName;

    /** Tender sub number. */
    @JsonProperty("tender_sub_number")
    @Max(9999)
    private Integer tenderSubNumber;

    /** Tender group id. */
    @JsonProperty("tender_group_id")
    @NotBlank(groups = {Insert.class, Correction.class, PosInsert.class})
    @Size(max = 50)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String tenderGroupId;

    /** Tender id. */
    @JsonProperty("tender_id")
    @NotBlank(groups = {Insert.class, Correction.class, PosInsert.class})
    @Size(max = 50)
    @Pattern(regexp = "[a-zA-Z0-9-_]*")
    private String tenderId;

    /** Payment sign. */
    @JsonProperty("payment_sign")
    @NotBlank(groups = {Insert.class, Correction.class, PosInsert.class})
    @Size(max = 1)
    @Alphanumeric
    private String paymentSign;

    /** Tax included payment amount. */
    @JsonProperty("tax_included_payment_amount")
    @NotNull(groups = {Insert.class, Correction.class, PosInsert.class})
    @Valid
    private Price taxIncludedPaymentAmount;

    /** Tender info list. */
    @JsonProperty("tender_info_list")
    private ImportTenderInfoList tenderInfoList;

}
