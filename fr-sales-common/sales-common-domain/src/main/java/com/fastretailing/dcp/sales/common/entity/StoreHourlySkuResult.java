package com.fastretailing.dcp.sales.common.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StoreHourlySkuResult extends StoreHourlySkuResultKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_store_hourly_sku_result.sku_code
     *
     */
    private String skuCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_store_hourly_sku_result.transaction_create_date
     *
     */
    private String transactionCreateDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_store_hourly_sku_result.time_utc
     *
     */
    private String timeUtc;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_store_hourly_sku_result.sales_hour
     *
     */
    private Integer salesHour;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_store_hourly_sku_result.sales_quantity_code
     *
     */
    private String salesQuantityCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_store_hourly_sku_result.sales_quantity
     *
     */
    private BigDecimal salesQuantity;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_store_hourly_sku_result.create_user_id
     *
     */
    private String createUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_store_hourly_sku_result.create_datetime
     *
     */
    private OffsetDateTime createDatetime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_store_hourly_sku_result.create_program_id
     *
     */
    private String createProgramId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_store_hourly_sku_result.update_user_id
     *
     */
    private String updateUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_store_hourly_sku_result.update_datetime
     *
     */
    private OffsetDateTime updateDatetime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_store_hourly_sku_result.update_program_id
     *
     */
    private String updateProgramId;
}
