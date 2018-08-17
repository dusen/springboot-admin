/**
 * @(#)OrderDetailInfo.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.dto;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import lombok.Data;

/**
 * This class is DTO of sales detail data. The following values are set as the initial value.
 * <UL>
 * <LI>salesId:null.
 * <LI>itemCode:null.
 * <LI>salesNum:0.
 * <LI>unitPrice:0.
 * <LI>itemSales:0.
 * </UL>
 */
@Data
public class OrderDetailInfo {

    /** ID corresponding to a unit of sales data. */
    @NotBlank
    @Size(min = 1, max = 10)
    private String salesId;

    /** Item code corresponding to sales data. */
    @NotBlank
    @Size(min = 1, max = 10)
    private String itemCode;

    /** Sales number of transaction sales data. */
    @Min(0)
    @Max(1000000000)
    private int salesNum;

    /** Unit price corresponding to an item. */
    @Min(0)
    @Max(1000000000)
    private int unitPrice;

    /** Total item sales of transaction sales data. */
    @Min(0)
    @Max(1000000000)
    private int itemSales;

}
