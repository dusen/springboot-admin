package com.fastretailing.dcp.sales.importtransaction.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ErrorEvacuationSalesTransactionTenderInfo extends ErrorEvacuationSalesTransactionTenderInfoKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_error_evacuation_sales_transaction_tender_info.sales_transaction_error_id
     *
     * @mbg.generated
     */
    private String salesTransactionErrorId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_error_evacuation_sales_transaction_tender_info.discount_value_currency_code
     *
     * @mbg.generated
     */
    private String discountValueCurrencyCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_error_evacuation_sales_transaction_tender_info.discount_value
     *
     * @mbg.generated
     */
    private BigDecimal discountValue;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_error_evacuation_sales_transaction_tender_info.discount_rate
     *
     * @mbg.generated
     */
    private BigDecimal discountRate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_error_evacuation_sales_transaction_tender_info.discount_code_id_corporate_id
     *
     * @mbg.generated
     */
    private String discountCodeIdCorporateId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_error_evacuation_sales_transaction_tender_info.coupon_type
     *
     * @mbg.generated
     */
    private String couponType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_error_evacuation_sales_transaction_tender_info.coupon_discount_amount_setting_currency_code
     *
     * @mbg.generated
     */
    private String couponDiscountAmountSettingCurrencyCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_error_evacuation_sales_transaction_tender_info.coupon_discount_amount_setting_value
     *
     * @mbg.generated
     */
    private BigDecimal couponDiscountAmountSettingValue;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_error_evacuation_sales_transaction_tender_info.coupon_min_usage_amount_threshold_currency_code
     *
     * @mbg.generated
     */
    private String couponMinUsageAmountThresholdCurrencyCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_error_evacuation_sales_transaction_tender_info.coupon_min_usage_amount_threshold_value
     *
     * @mbg.generated
     */
    private BigDecimal couponMinUsageAmountThresholdValue;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_error_evacuation_sales_transaction_tender_info.coupon_user_id
     *
     * @mbg.generated
     */
    private String couponUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_error_evacuation_sales_transaction_tender_info.card_no
     *
     * @mbg.generated
     */
    private String cardNo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_error_evacuation_sales_transaction_tender_info.credit_approval_code
     *
     * @mbg.generated
     */
    private String creditApprovalCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_error_evacuation_sales_transaction_tender_info.credit_processing_serial_number
     *
     * @mbg.generated
     */
    private String creditProcessingSerialNumber;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_error_evacuation_sales_transaction_tender_info.credit_payment_type
     *
     * @mbg.generated
     */
    private String creditPaymentType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_error_evacuation_sales_transaction_tender_info.credit_payment_count
     *
     * @mbg.generated
     */
    private Integer creditPaymentCount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_error_evacuation_sales_transaction_tender_info.credit_affiliated_store_number
     *
     * @mbg.generated
     */
    private String creditAffiliatedStoreNumber;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_error_evacuation_sales_transaction_tender_info.create_user_id
     *
     * @mbg.generated
     */
    private String createUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_error_evacuation_sales_transaction_tender_info.create_datetime
     *
     * @mbg.generated
     */
    private LocalDateTime createDatetime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_error_evacuation_sales_transaction_tender_info.create_program_id
     *
     * @mbg.generated
     */
    private String createProgramId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_error_evacuation_sales_transaction_tender_info.update_user_id
     *
     * @mbg.generated
     */
    private String updateUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_error_evacuation_sales_transaction_tender_info.update_datetime
     *
     * @mbg.generated
     */
    private LocalDateTime updateDatetime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_error_evacuation_sales_transaction_tender_info.update_program_id
     *
     * @mbg.generated
     */
    private String updateProgramId;
}