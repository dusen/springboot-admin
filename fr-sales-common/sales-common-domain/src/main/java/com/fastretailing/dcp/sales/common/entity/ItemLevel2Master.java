package com.fastretailing.dcp.sales.common.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ItemLevel2Master extends ItemLevel2MasterKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.system_business_code
     *
     */
    private String systemBusinessCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.item_code_type
     *
     */
    private String itemCodeType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.view_item_code
     *
     */
    private String viewItemCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.year_code
     *
     */
    private String yearCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.view_year_type
     *
     */
    private String viewYearType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.season_code
     *
     */
    private String seasonCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.detail_season_code
     *
     */
    private String detailSeasonCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.sales_country_code
     *
     */
    private String salesCountryCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.color_code
     *
     */
    private String colorCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.size_code
     *
     */
    private String sizeCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.pattern_length_code
     *
     */
    private String patternLengthCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.major_category_code
     *
     */
    private String majorCategoryCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.class_code
     *
     */
    private String classCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.middle_category_code
     *
     */
    private String middleCategoryCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.small_category_value
     *
     */
    private Integer smallCategoryValue;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.item_parent
     *
     */
    private String itemParent;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.item_crand_parent
     *
     */
    private String itemCrandParent;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.item_level
     *
     */
    private Integer itemLevel;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.transaction_level
     *
     */
    private Integer transactionLevel;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.pack_flag
     *
     */
    private boolean packFlag;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.simple_item_pack_flag
     *
     */
    private boolean simpleItemPackFlag;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.contains_inner_kind
     *
     */
    private String containsInnerKind;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.sellable_flag
     *
     */
    private boolean sellableFlag;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.orderable_flag
     *
     */
    private boolean orderableFlag;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.pack_status
     *
     */
    private String packStatus;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.order_unit_type
     *
     */
    private String orderUnitType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.asort_code
     *
     */
    private String asortCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.fob
     *
     */
    private BigDecimal fob;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.cost_price_sequence
     *
     */
    private Integer costPriceSequence;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.stock_buying_type_code
     *
     */
    private String stockBuyingTypeCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.initial_selling_price
     *
     */
    private BigDecimal initialSellingPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.sales_zone_group
     *
     */
    private Integer salesZoneGroup;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.standard_sales_unit
     *
     */
    private String standardSalesUnit;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.item_name
     *
     */
    private String itemName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.intern_item_name
     *
     */
    private String internItemName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.pos_item_name
     *
     */
    private String posItemName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.second_pos_item_name
     *
     */
    private String secondPosItemName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.external_item_name_1
     *
     */
    private String externalItemName1;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.external_item_name_2
     *
     */
    private String externalItemName2;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.external_item_name_3
     *
     */
    private String externalItemName3;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.external_item_name_4
     *
     */
    private String externalItemName4;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.second_item_name
     *
     */
    private String secondItemName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.picking_shipment_flag
     *
     */
    private boolean pickingShipmentFlag;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.direct_delivery_unit_quantity
     *
     */
    private Integer directDeliveryUnitQuantity;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.regular_item_flag
     *
     */
    private boolean regularItemFlag;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.color_size_management_flag
     *
     */
    private boolean colorSizeManagementFlag;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.b_item_return_possible_flag
     *
     */
    private boolean bItemReturnPossibleFlag;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.status
     *
     */
    private String status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.delivery_type_code
     *
     */
    private String deliveryTypeCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.supplier_code
     *
     */
    private String supplierCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.stock_buying_cost
     *
     */
    private BigDecimal stockBuyingCost;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.packing_quantity
     *
     */
    private Integer packingQuantity;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.picking_unit_quantity
     *
     */
    private Integer pickingUnitQuantity;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.selling_price
     *
     */
    private BigDecimal sellingPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.selling_unit
     *
     */
    private String sellingUnit;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.taxation_flag
     *
     */
    private boolean taxationFlag;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.batch_region
     *
     */
    private Integer batchRegion;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.create_user_id
     *
     */
    private String createUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.create_datetime
     *
     */
    private OffsetDateTime createDatetime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.create_program_id
     *
     */
    private String createProgramId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.update_user_id
     *
     */
    private String updateUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.update_datetime
     *
     */
    private OffsetDateTime updateDatetime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_item_level_2.update_program_id
     *
     */
    private String updateProgramId;
}
