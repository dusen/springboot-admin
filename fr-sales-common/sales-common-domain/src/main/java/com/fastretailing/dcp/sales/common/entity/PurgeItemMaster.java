package com.fastretailing.dcp.sales.common.entity;

import java.time.OffsetDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PurgeItemMaster extends PurgeItemMasterKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_purge_item.process_type
     *
     */
    private Integer processType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_purge_item.system_business_code
     *
     */
    private Integer systemBusinessCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_purge_item.create_user_id
     *
     */
    private String createUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_purge_item.create_datetime
     *
     */
    private OffsetDateTime createDatetime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_purge_item.create_program_id
     *
     */
    private String createProgramId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_purge_item.update_user_id
     *
     */
    private String updateUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_purge_item.update_datetime
     *
     */
    private OffsetDateTime updateDatetime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_purge_item.update_program_id
     *
     */
    private String updateProgramId;
}
