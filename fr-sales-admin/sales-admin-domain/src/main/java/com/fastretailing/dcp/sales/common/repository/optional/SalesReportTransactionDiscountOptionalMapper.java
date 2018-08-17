/**
 * @(#)SalesReportTransactionDiscountOptionalMapper.java
 *
 *                                                       Copyright (c) 2018 Fast Retailing
 *                                                       Corporation.
 */

package com.fastretailing.dcp.sales.common.repository.optional;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.fastretailing.dcp.sales.common.entity.optional.SalesReportTransactionDiscountOptional;

/**
 * Repository class for sales report transaction discount table.
 */
@Mapper
public interface SalesReportTransactionDiscountOptionalMapper {
    /**
     * From the table retrieve all relevant items.
     * 
     * @Param salesTransactionId Sales Transaction id.
     * @return Sales report transaction discount optional list.
     * 
     */
    @Select("SELECT /* SLS0300202-007 */ " + "sales_transaction_id, " + "detail_sub_number, "
            + "quantity_code, " + "discount_quantity, " + "discount_amount_tax_excluded, "
            + "discount_amount_tax_included " + "FROM t_sales_report_transaction_discount "
            + "WHERE sales_transaction_id = #{salesTransactionId} "
            + " AND detail_sub_number = #{detailSubNumber} ")
    List<SalesReportTransactionDiscountOptional> selectSalesReportTransactionDiscount(
            @Param("salesTransactionId") String salesTransactionId,
            @Param("detailSubNumber") Integer detailSubNumber);
}
