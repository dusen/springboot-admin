package com.fastretailing.dcp.sales.importtransaction.entity;

import lombok.Data;

@Data
public class AlterationHistorySalesTransactionHeaderKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_alteration_history_sales_transaction_header.transaction_id
     *
     * @mbg.generated
     */
    private String transactionId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_alteration_history_sales_transaction_header.order_sub_number
     *
     * @mbg.generated
     */
    private Integer orderSubNumber;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_alteration_history_sales_transaction_header.sales_transaction_id
     *
     * @mbg.generated
     */
    private String salesTransactionId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_alteration_history_sales_transaction_header.history_type
     *
     * @mbg.generated
     */
    private Integer historyType;
}