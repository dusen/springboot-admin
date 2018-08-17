/**
 * @(#)StoreTransactionInquiryOptionalMapper.java
 *
 *                                                Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.repository.optional;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.fastretailing.dcp.sales.common.entity.optional.SalesReportTransactionDiscountDetailOptional;
import com.fastretailing.dcp.sales.common.entity.optional.SalesReportTransactionHeaderOptional;
import com.fastretailing.dcp.sales.common.entity.optional.SalesReportTransactionItemDetailOptional;
import com.fastretailing.dcp.sales.common.entity.optional.SalesReportTransactionTaxOptional;
import com.fastretailing.dcp.sales.common.entity.optional.SalesReportTransactionTenderOptional;
import com.fastretailing.dcp.sales.common.entity.optional.StoreTransactionInquiryCondition;
import com.fastretailing.dcp.sales.common.entity.optional.StoreTransactionInquiryDetail;
import com.fastretailing.dcp.sales.common.entity.optional.TranslationTenderMasterOptional;

/**
 * Store transaction inquiry optional mapper.
 */
@Mapper
public interface StoreTransactionInquiryOptionalMapper {
    /**
     * Select store transaction inquiry detail by condition.
     *
     * @param storeTransactionInquiryCondition.
     * @return Store transaction inquiry detail list.
     */
    List<TranslationTenderMasterOptional> selectPaymentTenderGroup(
            StoreTransactionInquiryCondition storeTransactionInquiryCondition);

    /**
     * Select store transaction inquiry detail by condition.
     *
     * @param storeTransactionInquiryCondition.
     * @return Store transaction inquiry detail list.
     */
    List<TranslationTenderMasterOptional> selectPaymentTenderId(
            StoreTransactionInquiryCondition storeTransactionInquiryCondition);

    /**
     * Select store transaction inquiry detail by condition.
     *
     * @param storeTransactionInquiryCondition.
     * @return Store transaction inquiry detail list.
     */
    List<StoreTransactionInquiryDetail> selectByCountryBrandCode(
            StoreTransactionInquiryCondition storeTransactionInquiryCondition);

    /**
     * Select number of transaction.
     *
     * @return The count of transaction id.
     */
    Integer selectTransactionIdCount();

    /**
     * Select store transaction inquiry detail by condition.
     *
     * @param storeTransactionInquiryCondition.
     * @return Store transaction inquiry detail list.
     */
    List<StoreTransactionInquiryDetail> selectStoreTransactionInquiryDetailByCondition(
            StoreTransactionInquiryCondition storeTransactionInquiryCondition);

    /**
     * Select sales report transaction header by condition.
     *
     * @param storeTransactionInquiryCondition.
     * @return Sales report transaction header list.
     */
    List<SalesReportTransactionHeaderOptional> selectSalesReportTransactionHeaderByCondition(
            StoreTransactionInquiryCondition storeTransactionInquiryCondition);

    /**
     * Select sales report transaction detail by condition.
     *
     * @param storeTransactionInquiryCondition.
     * @return Sales report transaction detail list.
     */
    List<SalesReportTransactionItemDetailOptional> selectSalesReportTransactionDetailByCondition(
            StoreTransactionInquiryCondition storeTransactionInquiryCondition);

    /**
     * Select sales report transaction discount by condition.
     *
     * @param storeTransactionInquiryCondition.
     * @return Sales report transaction discount list.
     */
    List<SalesReportTransactionDiscountDetailOptional> selectSalesReportTransactionDiscountDetailByCondition(
            StoreTransactionInquiryCondition storeTransactionInquiryCondition);

    /**
     * Select sales report transaction tax by condition.
     *
     * @param storeTransactionInquiryCondition.
     * @return Sales report transaction tax list.
     */
    List<SalesReportTransactionTaxOptional> selectSalesReportTransactionTaxDetailByCondition(
            StoreTransactionInquiryCondition storeTransactionInquiryCondition);

    /**
     * Select sales report transaction tender by condition.
     *
     * @param storeTransactionInquiryCondition.
     * @return Sales report transaction tender list.
     */
    List<SalesReportTransactionTenderOptional> selectSalesReportTransactionTenderDetailByCondition(
            StoreTransactionInquiryCondition storeTransactionInquiryCondition);

}
