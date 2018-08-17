package com.fastretailing.dcp.sales.common.entity.optional;

import lombok.Data;

@Data
public class AlterationHistorySalesTransactionHeaderOptionalKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_alteration_history_sales_transaction_header.transaction_id
     *
     */
    private String transactionId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_alteration_history_sales_transaction_header.order_sub_number
     *
     */
    private Integer orderSubNumber;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_alteration_history_sales_transaction_header.sales_transaction_id
     *
     */
    private String salesTransactionId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_alteration_history_sales_transaction_header.history_type
     *
     */
    private Integer historyType;
}
