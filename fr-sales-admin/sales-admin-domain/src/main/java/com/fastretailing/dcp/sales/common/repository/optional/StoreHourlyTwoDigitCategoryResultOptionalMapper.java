/**
 * @(#)StoreHourlyTwoDigitCategoryResultOptionalMapper.java
 *
 *                                                          Copyright (c) 2018 Fast Retailing
 *                                                          Corporation.
 */

package com.fastretailing.dcp.sales.common.repository.optional;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.fastretailing.dcp.sales.common.entity.optional.StoreHourlyTwoDigitCategoryResultOptional;

/**
 * Repository class for store hourly two digit category result table.
 */
@Mapper
public interface StoreHourlyTwoDigitCategoryResultOptionalMapper {
    /**
     * From the table retrieve all relevant items.
     * 
     * @Param storeCode Store code.
     * @Param linkageDate Linkage date.
     * @Param businessDate Business date.
     * @Param majorCategoryCode Major category code.
     * @Param middleCategoryCode Middle category code.
     * @Param classCode Class code.
     * @Param orderStatusDate Order status date.
     * @return Sales hourly two digit category result optional list.
     * 
     */
    @Select("SELECT /* SLS0300202-009 */ " + "business_code, " + "store_code, " + "business_date, "
            + "time_local, " + "system_brand_code, " + "system_business_code, "
            + "tran_create_date, " + "time_utc, " + "sales_hour, "
            + "sales_result_amount_of_money, " + "sales_quantity, " + "number_of_customers, "
            + "sales_discount_amount, " + "sales_return_amount, " + "non_item_sales_amount, "
            + "major_category_code, " + "class_code, " + "middle_category_code, "
            + "create_user_id, " + "create_datetime, " + "create_program_id, " + "update_user_id, "
            + "update_datetime, " + "update_program_id "
            + "FROM t_store_hourly_two_digit_category_result " + "WHERE store_code = #{storeCode} "
            + " AND business_date = #{linkageDate} " + " AND time_utc = #{orderStatusDate} "
            + " AND major_category_code = #{majorCategoryCode} "
            + " AND middle_category_code = #{middleCategoryCode} "
            + " AND class_code = #{classCode} " + " AND tran_create_date = #{businessDate} ")
    List<StoreHourlyTwoDigitCategoryResultOptional> selectStoreHourlyTwoDigitCategoryResult(
            @Param("storeCode") String storeCode, @Param("linkageDate") String linkageDate,
            @Param("businessDate") String businessDate,
            @Param("majorCategoryCode") String majorCategoryCode,
            @Param("middleCategoryCode") String middleCategoryCode,
            @Param("classCode") String classCode, @Param("orderStatusDate") String orderStatusDate);

    /**
     * Update store hourly two digit category result table.
     * 
     * @param storeHourlyTwoDigitCategoryResultOptional Store hourly two digit category result
     *        optional.
     * @return Update count.
     */
    @Update("UPDATE /* SLS0300202-010 */" + " t_store_hourly_two_digit_category_result " + " SET "
            + " number_of_customers = #{numberOfCustomers}, "
            + " update_datetime = #{updateDatetime}, " + " update_program_id = #{updateProgramId} "
            + " WHERE store_code = #{storeCode} " + " AND business_date = #{businessDate} "
            + " AND time_utc = #{timeUtc} " + " AND tran_create_date = #{tranCreateDate} "
            + " AND major_category_code = #{majorCategoryCode} "
            + " AND middle_category_code = #{middleCategoryCode} "
            + " AND class_code = #{classCode} ")
    int updateBySelective(
            StoreHourlyTwoDigitCategoryResultOptional storeHourlyTwoDigitCategoryResultOptional);

    /**
     * Update store hourly two digit category result table.
     * 
     * @param storeHourlyTwoDigitCategoryResultOptional Store hourly two digit category result
     *        optional.
     * @return Update count.
     */
    @Update("UPDATE /* SLS0300202-011 */" + " t_store_hourly_two_digit_category_result " + " SET "
            + " sales_result_amount_of_money = #{salesResultAmountOfMoney}, "
            + " sales_quantity = #{salesQuantity}, "
            + " sales_discount_amount = #{salesDiscountAmount}, "
            + " sales_return_amount = #{salesReturnAmount}, "
            + " non_item_sales_amount = #{nonItemSalesAmount}, "
            + " update_datetime = #{updateDatetime}, " + " update_program_id = #{updateProgramId} "
            + " WHERE store_code = #{storeCode} " + " AND business_date = #{businessDate} "
            + " AND time_local = #{timeLocal} " + " AND tran_create_date = #{tranCreateDate} ")
    int updateStoreHourlyTwoDigitCategoryResult(
            StoreHourlyTwoDigitCategoryResultOptional storeHourlyTwoDigitCategoryResultOptional);

    /**
     * Insert into store hourly two digit category result table.
     * 
     * @param storeHourlyTwoDigitCategoryResultOptional Store hourly two digit category result
     *        optional.
     * @return Insert count.
     */
    @Insert(" INSERT INTO /* SLS0300202-012 */ t_store_hourly_two_digit_category_result "
            + " (business_code, store_code, business_date, time_local, "
            + " system_brand_code, system_business_code, tran_create_date, "
            + " time_utc, sales_hour, sales_result_amount_of_money, "
            + " sales_quantity, number_of_customers, sales_discount_amount, "
            + " sales_return_amount, non_item_sales_amount, major_category_code, "
            + " class_code, middle_category_code, "
            + " create_user_id, create_datetime, create_program_id, "
            + " update_user_id, update_datetime, update_program_id) "
            + " VALUES ( #{businessCode}, #{storeCode}, #{businessDate}, "
            + " #{timeLocal}, #{systemBrandCode}, #{systemBusinessCode}, "
            + " #{tranCreateDate}, #{timeUtc}, #{salesHour}, "
            + " #{salesResultAmountOfMoney}, #{salesQuantity}, #{numberOfCustomers}, "
            + " #{salesDiscountAmount}, #{salesReturnAmount}, #{nonItemSalesAmount}, "
            + " #{majorCategoryCode}, #{classCode}, #{middleCategoryCode}, "
            + " #{createUserId}, #{createDatetime}, "
            + " #{createProgramId}, #{updateUserId}, #{updateDatetime}, "
            + " #{updateProgramId} ) ")
    void insert(
            StoreHourlyTwoDigitCategoryResultOptional storeHourlyTwoDigitCategoryResultOptional);
}
