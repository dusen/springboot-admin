package com.fastretailing.dcp.sales.common.entity;

import java.time.OffsetDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PayOffSummaryMappingMaster extends PayOffSummaryMappingMasterKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_pay_off_summary_mapping.mapping_ptn_name
     *
     */
    private String mappingPtnName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_pay_off_summary_mapping.mapping_sub_pattern
     *
     */
    private Integer mappingSubPattern;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_pay_off_summary_mapping.system_brand_code
     *
     */
    private String systemBrandCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_pay_off_summary_mapping.system_business_code
     *
     */
    private String systemBusinessCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_pay_off_summary_mapping.system_country_code
     *
     */
    private String systemCountryCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_pay_off_summary_mapping.create_user_id
     *
     */
    private String createUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_pay_off_summary_mapping.create_datetime
     *
     */
    private OffsetDateTime createDatetime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_pay_off_summary_mapping.create_program_id
     *
     */
    private String createProgramId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_pay_off_summary_mapping.update_user_id
     *
     */
    private String updateUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_pay_off_summary_mapping.update_datetime
     *
     */
    private OffsetDateTime updateDatetime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_pay_off_summary_mapping.update_program_id
     *
     */
    private String updateProgramId;
}
