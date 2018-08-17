/**
 * @(#)OrderInfo.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.dto;


import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import lombok.Data;

/**
 * This class is DTO of order history. The following values are set as the initial value.
 * <UL>
 * <LI>businessDate: null.
 * <LI>posNo: null.
 * <LI>receiptNo: null.
 * <LI>salesTime: null.
 * <LI>transactionType: null.
 * <LI>detail: null.
 * </UL>
 */
@Data
public class OrderInfo {

    /**
     * The business date of transaction sales.
     */
    @NotBlank
//    @StringDate
    private String businessDate;

    /**
     * POS terminal number corresponding to transaction sales data.
     */
    @NotBlank
    @Size(min = 1, max = 10)
    private String posNo;

    /**
     * Receipt number corresponding to transaction sales data.
     */
    @NotBlank
    @Size(min = 1, max = 10)
    private String receiptNo;

    /**
     * Sales time of transaction sales data.
     */
    @NotBlank
    @Size(min = 1, max = 10)
    private String salesTime;

    /**
     * Transaction type corresponding to transaction sales data.
     */
    @NotBlank
    @Size(min = 1, max = 10)
    private String transactionType;

    /** Detail Sales data. */
    @Size(min = 1, max = 1000000000)
    @Valid
    private List<OrderDetailInfo> detail;

}
