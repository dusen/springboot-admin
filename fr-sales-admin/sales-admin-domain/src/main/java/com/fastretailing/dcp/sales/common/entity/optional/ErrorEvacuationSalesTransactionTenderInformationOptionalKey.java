package com.fastretailing.dcp.sales.common.entity.optional;

import lombok.Data;

@Data
public class ErrorEvacuationSalesTransactionTenderInformationOptionalKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_error_evacuation_sales_transaction_tender_info.transaction_id
     *
     */
    private String transactionId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_error_evacuation_sales_transaction_tender_info.order_sub_number
     *
     */
    private Integer orderSubNumber;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_error_evacuation_sales_transaction_tender_info.sales_transaction_id
     *
     */
    private String salesTransactionId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_error_evacuation_sales_transaction_tender_info.tender_group
     *
     */
    private String tenderGroup;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_error_evacuation_sales_transaction_tender_info.tender_id
     *
     */
    private String tenderId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_error_evacuation_sales_transaction_tender_info.tender_sub_number
     *
     */
    private Integer tenderSubNumber;
}
