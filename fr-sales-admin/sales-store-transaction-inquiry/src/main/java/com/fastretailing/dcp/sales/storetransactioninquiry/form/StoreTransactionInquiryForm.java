/**
 * @(#)StoreTransactionInquiryForm.java
 *
 *                                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.storetransactioninquiry.form;

import java.util.List;
import java.util.Map;
import org.hibernate.validator.constraints.NotEmpty;
import com.fastretailing.dcp.common.validation.annotation.Alphanumeric;
import com.fastretailing.dcp.common.validation.annotation.Number;
import com.fastretailing.dcp.sales.common.entity.optional.SalesReportTransactionHeaderOptional;
import com.fastretailing.dcp.sales.common.entity.optional.StoreTransactionInquiryDetail;
import com.fastretailing.dcp.sales.storetransactioninquiry.constant.DropDownItem;
import com.fastretailing.dcp.storecommon.screen.form.CommonBaseForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Store transaction inquiry form.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class StoreTransactionInquiryForm extends CommonBaseForm {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -3837123870637140098L;

    /**
     * System brand code.
     */
    @NotEmpty
    @Alphanumeric
    private String systemBrandCode;

    /**
     * System brand name.
     */
    private String systemBrandName;

    /**
     * Brand list.
     */
    private List<DropDownItem> brandList;

    /**
     * System country code.
     */
    @NotEmpty
    @Alphanumeric
    private String systemCountryCode;

    /**
     * System country name.
     */
    private String systemCountryName;

    /**
     * Country list.
     */
    private List<DropDownItem> countryList;

    /**
     * Store code.
     */
    private String storeCode;

    /**
     * Business date.
     */
    private String businessDate;

    /**
     * Cash register no.
     */
    private Integer cashRegisterNo;

    /**
     * Data creation date from.
     */
    private String dataCreationDateFrom;

    /**
     * Data creation time from.
     */
    private String dataCreationTimeFrom;

    /**
     * Data creation date to.
     */
    private String dataCreationDateTo;

    /**
     * Data creation date to.
     */
    private String dataCreationTimeTo;

    /**
     * Sales transaction type.
     */
    private String salesTransactionType;

    /**
     * Sales transaction type map.
     */
    private Map<String, String> salesTransactionTypeMap;

    /**
     * Sales transaction type list.
     */
    private List<String> salesTransactionTypeList;

    /**
     * Casher code.
     */
    private String casherCode;

    /**
     * Membership id.
     */
    private String membershipId;

    /**
     * Receipt no from.
     */
    private String receiptNoFrom;

    /**
     * Receipt no to.
     */
    private String receiptNoTo;

    /**
     * Payment tender group.
     */
    private String paymentTenderGroup;

    /**
     * Payment ims tender group.
     */
    private String paymentImsTenderGroup;

    /**
     * Payment tender group list.
     */
    private List<DropDownItem> paymentTenderGroupList;

    /**
     * Payment tender id.
     */
    private Integer paymentTenderId;

    /**
     * Payment ims tender id.
     */
    private Integer paymentImsTenderId;

    /**
     * Payment tender id list.
     */
    private List<DropDownItem> paymentTenderIdList;

    /**
     * Payment amount from.
     */
    @Number
    private String paymentAmountFrom;

    /**
     * Payment amount to.
     */
    @Number
    private String paymentAmountTo;

    /**
     * Deposit amount from.
     */
    @Number
    private String depositAmountFrom;

    /**
     * Deposit amount to.
     */
    @Number
    private String depositAmountTo;

    /**
     * Change amount from.
     */
    @Number
    private String changeAmountFrom;

    /**
     * Change amount to.
     */
    @Number
    private String changeAmountTo;

    /**
     * Taxation type.
     */
    private String taxationType;

    /**
     * Taxation type map.
     */
    private Map<String, String> taxationTypeMap;

    /**
     * Non merchandise item name.
     */
    private String nonMerchandiseItemName;

    /**
     * Non merchandise item code.
     */
    private String nonMerchandiseItemCode;

    /**
     * Non merchandise item list.
     */
    private List<DropDownItem> nonMerchandiseItemList;

    /**
     * Item code.
     */
    private String itemCode;

    /**
     * Discount type.
     */
    private String discountType;

    /**
     * Discount type map.
     */
    private Map<String, String> discountTypeMap;

    /**
     * Sales transaction id count.
     */
    private long salesTransactionIdCount;

    /**
     * Item list.
     */
    private List<StoreTransactionInquiryDetail> itemList;

    /**
     * Discount list.
     */
    private List<StoreTransactionInquiryDetail> discountList;

    /**
     * Tax list.
     */
    private List<StoreTransactionInquiryDetail> taxList;

    /**
     * Payment list.
     */
    private List<StoreTransactionInquiryDetail> paymentList;

    /**
     * Header detail list.
     */
    List<SalesReportTransactionHeaderOptional> headerDetailList;

    /**
     * Header detail.
     */
    StoreTransactionInquiryDetail headerDetail;

    /** Decimal format. */
    private String decimalFormat;

    /** Count index. */
    private int countIndex;

    /** Number of transaction. */
    private Integer numberOfTransaction;

    /**
     * Tax type.
     */
    private String taxType;

    /**
     * Order by clause.
     */
    private Integer orderByClause = 0;

    /**
     * Show detail.
     */
    private boolean showDetail;

    /**
     * Previous next flag.
     */
    private boolean previousNextFlag;

    /**
     * Tax type flag.
     */
    private Boolean taxTypeFlag;

}
