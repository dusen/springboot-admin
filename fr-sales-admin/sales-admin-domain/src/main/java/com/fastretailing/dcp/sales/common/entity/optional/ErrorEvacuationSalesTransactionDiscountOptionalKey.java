package com.fastretailing.dcp.sales.common.entity.optional;

import lombok.Data;

@Data
public class ErrorEvacuationSalesTransactionDiscountOptionalKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_error_evacuation_sales_transaction_discount.transaction_id
     *
     */
    private String transactionId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_error_evacuation_sales_transaction_discount.order_sub_number
     *
     */
    private Integer orderSubNumber;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_error_evacuation_sales_transaction_discount.sales_transaction_id
     *
     */
    private String salesTransactionId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_error_evacuation_sales_transaction_discount.detail_sub_number
     *
     */
    private Integer detailSubNumber;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_error_evacuation_sales_transaction_discount.promotion_type
     *
     */
    private String promotionType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_error_evacuation_sales_transaction_discount.promotion_no
     *
     */
    private String promotionNo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_error_evacuation_sales_transaction_discount.store_discount_type
     *
     */
    private String storeDiscountType;
}
