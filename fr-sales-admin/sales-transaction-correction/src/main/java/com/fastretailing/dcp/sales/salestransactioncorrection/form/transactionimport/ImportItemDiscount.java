/**
 * @(#)ImportItemDiscount.java
 *
 *                             Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactioncorrection.form.transactionimport;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fastretailing.dcp.common.validation.annotation.Alphanumeric;
import com.fastretailing.dcp.sales.importtransaction.dto.groups.Correction;
import com.fastretailing.dcp.sales.importtransaction.dto.groups.Insert;
import com.fastretailing.dcp.sales.salestransactioncorrection.common.DeleteCheckFlag;
import com.fastretailing.dcp.storecommon.dto.Price;
import lombok.Data;

/**
 * Import item discount.
 * 
 */
@Data
public class ImportItemDiscount {

    /** Delete check flag. */
    private String deleteCheckFlag = DeleteCheckFlag.NORMAL.getValue();

    /** Item discount detail sub number. */
    @JsonProperty("item_discount_detail_sub_number")
    @Max(9999)
    private Integer itemDiscountDetailSubNumber;

    /** Item discount sub number. */
    @JsonProperty("item_discount_sub_number")
    @Max(9999)
    private Integer itemDiscountSubNumber;

    /** Item promotion type. */
    @JsonProperty("item_promotion_type")
    @Size(max = 4)
    @Alphanumeric
    @NotBlank(groups = {Insert.class, Correction.class})
    private String itemPromotionType;

    /** Item promotion number. */
    @JsonProperty("item_promotion_number")
    @Size(max = 10)
    @Alphanumeric
    private String itemPromotionNumber;

    /** Item store discount type. */
    @JsonProperty("item_store_discount_type")
    @Size(max = 2)
    @Alphanumeric
    private String itemStoreDiscountType;

    /** Item quantity code. */
    @JsonProperty("item_quantity_code")
    @Size(max = 1)
    @Alphanumeric
    @NotBlank(groups = {Insert.class, Correction.class})
    private String itemQuantityCode;

    /** Item discount quantity. */
    @JsonProperty("item_discount_qty")
    @Max(99)
    @NotNull(groups = {Insert.class, Correction.class})
    private Integer itemDiscountQty;

    /** Item unit price tax excluded. */
    @JsonProperty("item_discount_amt_tax_excluded")
    @Valid
    private Price itemDiscountAmtTaxExcluded;

    /** Item unit price tax included. */
    @JsonProperty("item_discount_amt_tax_included")
    @Valid
    private Price itemDiscountAmtTaxIncluded;

}
