package com.fastretailing.dcp.sales.importtransaction.entity;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BusinessCountryStateSettingMaster extends BusinessCountryStateSettingMasterKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_business_country_state_setting.system_brand_code
     *
     * @mbg.generated
     */
    private String systemBrandCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_business_country_state_setting.system_business_code
     *
     * @mbg.generated
     */
    private String systemBusinessCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_business_country_state_setting.system_country_code
     *
     * @mbg.generated
     */
    private String systemCountryCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_business_country_state_setting.state_code
     *
     * @mbg.generated
     */
    private String stateCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_business_country_state_setting.create_user_id
     *
     * @mbg.generated
     */
    private String createUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_business_country_state_setting.create_datetime
     *
     * @mbg.generated
     */
    private LocalDateTime createDatetime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_business_country_state_setting.create_program_id
     *
     * @mbg.generated
     */
    private String createProgramId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_business_country_state_setting.update_user_id
     *
     * @mbg.generated
     */
    private String updateUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_business_country_state_setting.update_datetime
     *
     * @mbg.generated
     */
    private LocalDateTime updateDatetime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_business_country_state_setting.update_program_id
     *
     * @mbg.generated
     */
    private String updateProgramId;
}