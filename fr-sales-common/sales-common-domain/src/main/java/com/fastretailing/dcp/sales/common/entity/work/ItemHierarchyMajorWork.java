package com.fastretailing.dcp.sales.common.entity.work;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ItemHierarchyMajorWork extends ItemHierarchyMajorWorkKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column w_item_hierarchy_major_category.eai_update_type
     *
     */
    private String eaiUpdateType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column w_item_hierarchy_major_category.eai_send_status
     *
     */
    private String eaiSendStatus;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column w_item_hierarchy_major_category.eai_send_date
     *
     */
    private String eaiSendDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column w_item_hierarchy_major_category.major_category_name
     *
     */
    private String majorCategoryName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column w_item_hierarchy_major_category.batch_region
     *
     */
    private Integer batchRegion;
}
