package com.fastretailing.dcp.sales.importtransaction.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SalesErrorSalesTransactionTotalAmount extends SalesErrorSalesTransactionTotalAmountKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sales_error_sales_transaction_total_amount.total_amount_sub_number
     *
     * @mbg.generated
     */
    private Integer totalAmountSubNumber;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sales_error_sales_transaction_total_amount.total_amount_tax_excluded_currency_code
     *
     * @mbg.generated
     */
    private String totalAmountTaxExcludedCurrencyCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sales_error_sales_transaction_total_amount.total_amount_tax_excluded_value
     *
     * @mbg.generated
     */
    private BigDecimal totalAmountTaxExcludedValue;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sales_error_sales_transaction_total_amount.total_amount_tax_included_currency_code
     *
     * @mbg.generated
     */
    private String totalAmountTaxIncludedCurrencyCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sales_error_sales_transaction_total_amount.total_amount_tax_included_value
     *
     * @mbg.generated
     */
    private BigDecimal totalAmountTaxIncludedValue;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sales_error_sales_transaction_total_amount.tax_rate
     *
     * @mbg.generated
     */
    private BigDecimal taxRate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sales_error_sales_transaction_total_amount.sales_transaction_information_1
     *
     * @mbg.generated
     */
    private String salesTransactionInformation1;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sales_error_sales_transaction_total_amount.sales_transaction_information_2
     *
     * @mbg.generated
     */
    private String salesTransactionInformation2;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sales_error_sales_transaction_total_amount.sales_transaction_information_3
     *
     * @mbg.generated
     */
    private String salesTransactionInformation3;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sales_error_sales_transaction_total_amount.create_user_id
     *
     * @mbg.generated
     */
    private String createUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sales_error_sales_transaction_total_amount.create_datetime
     *
     * @mbg.generated
     */
    private LocalDateTime createDatetime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sales_error_sales_transaction_total_amount.create_program_id
     *
     * @mbg.generated
     */
    private String createProgramId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sales_error_sales_transaction_total_amount.update_user_id
     *
     * @mbg.generated
     */
    private String updateUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sales_error_sales_transaction_total_amount.update_datetime
     *
     * @mbg.generated
     */
    private LocalDateTime updateDatetime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sales_error_sales_transaction_total_amount.update_program_id
     *
     * @mbg.generated
     */
    private String updateProgramId;
}