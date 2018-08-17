package com.fastretailing.dcp.sales.common.entity.optional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SalesErrorSalesTransactionTaxOptional extends SalesErrorSalesTransactionTaxOptionalKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sales_error_sales_transaction_tax.tax_amount_sign
     *
     */
    private String taxAmountSign;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sales_error_sales_transaction_tax.tax_amount_currency_code
     *
     */
    private String taxAmountCurrencyCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sales_error_sales_transaction_tax.tax_amount_value
     *
     */
    private BigDecimal taxAmountValue;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sales_error_sales_transaction_tax.tax_rate
     *
     */
    private BigDecimal taxRate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sales_error_sales_transaction_tax.tax_name
     *
     */
    private String taxName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sales_error_sales_transaction_tax.create_user_id
     *
     */
    private String createUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sales_error_sales_transaction_tax.create_datetime
     *
     */
    private OffsetDateTime createDatetime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sales_error_sales_transaction_tax.create_program_id
     *
     */
    private String createProgramId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sales_error_sales_transaction_tax.update_user_id
     *
     */
    private String updateUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sales_error_sales_transaction_tax.update_datetime
     *
     */
    private OffsetDateTime updateDatetime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sales_error_sales_transaction_tax.update_program_id
     *
     */
    private String updateProgramId;
}
