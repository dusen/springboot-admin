package com.fastretailing.dcp.sales.importtransaction.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PayOffData extends PayOffDataKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pay_off_data.payoff_amount_currency_code
     *
     * @mbg.generated
     */
    private String payoffAmountCurrencyCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pay_off_data.payoff_amount
     *
     * @mbg.generated
     */
    private BigDecimal payoffAmount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pay_off_data.payoff_quantity
     *
     * @mbg.generated
     */
    private BigDecimal payoffQuantity;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pay_off_data.report_output_count
     *
     * @mbg.generated
     */
    private Integer reportOutputCount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pay_off_data.processing_flag
     *
     * @mbg.generated
     */
    private boolean processingFlag;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pay_off_data.integrity_check_type
     *
     * @mbg.generated
     */
    private String integrityCheckType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pay_off_data.accounting_record_status
     *
     * @mbg.generated
     */
    private Integer accountingRecordStatus;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pay_off_data.batch_region
     *
     * @mbg.generated
     */
    private Integer batchRegion;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pay_off_data.system_brand_code
     *
     * @mbg.generated
     */
    private String systemBrandCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pay_off_data.system_business_code
     *
     * @mbg.generated
     */
    private String systemBusinessCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pay_off_data.system_country_code
     *
     * @mbg.generated
     */
    private String systemCountryCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pay_off_data.regular_time_error_notification_flag
     *
     * @mbg.generated
     */
    private boolean regularTimeErrorNotificationFlag;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pay_off_data.daily_summary_error_notification_flag
     *
     * @mbg.generated
     */
    private boolean dailySummaryErrorNotificationFlag;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pay_off_data.data_alteration_status
     *
     * @mbg.generated
     */
    private String dataAlterationStatus;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pay_off_data.close_complete_flag
     *
     * @mbg.generated
     */
    private boolean closeCompleteFlag;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pay_off_data.create_user_id
     *
     * @mbg.generated
     */
    private String createUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pay_off_data.create_datetime
     *
     * @mbg.generated
     */
    private LocalDateTime createDatetime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pay_off_data.create_program_id
     *
     * @mbg.generated
     */
    private String createProgramId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pay_off_data.update_user_id
     *
     * @mbg.generated
     */
    private String updateUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pay_off_data.update_datetime
     *
     * @mbg.generated
     */
    private LocalDateTime updateDatetime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pay_off_data.update_program_id
     *
     * @mbg.generated
     */
    private String updateProgramId;
}