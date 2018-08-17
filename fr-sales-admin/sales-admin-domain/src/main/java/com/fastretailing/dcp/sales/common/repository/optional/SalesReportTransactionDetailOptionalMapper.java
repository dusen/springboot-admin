/**
 * @(#)SalesReportTransactionDetailOptionalMapper.java
 *
 *                                                     Copyright (c) 2018 Fast Retailing
 *                                                     Corporation.
 */

package com.fastretailing.dcp.sales.common.repository.optional;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.fastretailing.dcp.sales.common.entity.optional.SalesReportTransactionDetailOptional;

/**
 * Repository class for sales report transaction detail table.
 */
@Mapper
public interface SalesReportTransactionDetailOptionalMapper {
    /**
     * From the table retrieve all relevant items.
     * 
     * @Param salesTransactionId Sales Transaction id.
     * @return Sales report transaction detail optional list.
     * 
     */
    @Select("SELECT /* SLS0300202-006 */ " + "sales_transaction_id, " + "detail_sub_number, "
            + "item_detail_sub_number, " + "sales_transaction_type, " + "system_brand_code, "
            + "system_business_code, " + "system_country_code, " + "l3_item_code, "
            + "product_classification, " + "non_md_code, " + "g_department_code, "
            + "major_category_code, " + "quantity_code, " + "detail_quantity, "
            + "sales_amount_tax_excluded, " + "sales_amount_tax_included, "
            + "calculation_unavailable_type " + "FROM t_sales_report_transaction_detail "
            + "WHERE sales_transaction_id = #{salesTransactionId} ")
    List<SalesReportTransactionDetailOptional> selectByCondition(
            @Param("salesTransactionId") String salesTransactionId);
}
