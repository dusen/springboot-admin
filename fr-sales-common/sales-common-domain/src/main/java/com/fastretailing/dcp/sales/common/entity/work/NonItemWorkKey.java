package com.fastretailing.dcp.sales.common.entity.work;

import java.time.OffsetDateTime;
import lombok.Data;

@Data
public class NonItemWorkKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column w_non_item.lot_num
     *
     */
    private String lotNum;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column w_non_item.system_country_code
     *
     */
    private String systemCountryCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column w_non_item.system_brand_code
     *
     */
    private String systemBrandCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column w_non_item.non_item_code
     *
     */
    private String nonItemCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column w_non_item.apply_start_date
     *
     */
    private OffsetDateTime applyStartDate;
}
