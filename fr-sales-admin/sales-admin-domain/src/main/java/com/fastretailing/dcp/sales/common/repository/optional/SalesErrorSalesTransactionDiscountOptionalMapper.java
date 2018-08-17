package com.fastretailing.dcp.sales.common.repository.optional;

import com.fastretailing.dcp.sales.common.entity.optional.SalesErrorSalesTransactionDiscountOptional;
import com.fastretailing.dcp.sales.common.entity.optional.SalesErrorSalesTransactionDiscountOptionalCondition;
import com.fastretailing.dcp.sales.common.entity.optional.SalesErrorSalesTransactionDiscountOptionalKey;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SalesErrorSalesTransactionDiscountOptionalMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sales_error_sales_transaction_discount
     *
     */
    long countByCondition(SalesErrorSalesTransactionDiscountOptionalCondition example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sales_error_sales_transaction_discount
     *
     */
    int deleteByCondition(SalesErrorSalesTransactionDiscountOptionalCondition example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sales_error_sales_transaction_discount
     *
     */
    int deleteByPrimaryKey(SalesErrorSalesTransactionDiscountOptionalKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sales_error_sales_transaction_discount
     *
     */
    int insert(SalesErrorSalesTransactionDiscountOptional record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sales_error_sales_transaction_discount
     *
     */
    int insertSelective(SalesErrorSalesTransactionDiscountOptional record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sales_error_sales_transaction_discount
     *
     */
    List<SalesErrorSalesTransactionDiscountOptional> selectByCondition(SalesErrorSalesTransactionDiscountOptionalCondition example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sales_error_sales_transaction_discount
     *
     */
    SalesErrorSalesTransactionDiscountOptional selectByPrimaryKey(SalesErrorSalesTransactionDiscountOptionalKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sales_error_sales_transaction_discount
     *
     */
    int updateByConditionSelective(@Param("record") SalesErrorSalesTransactionDiscountOptional record, @Param("example") SalesErrorSalesTransactionDiscountOptionalCondition example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sales_error_sales_transaction_discount
     *
     */
    int updateByCondition(@Param("record") SalesErrorSalesTransactionDiscountOptional record, @Param("example") SalesErrorSalesTransactionDiscountOptionalCondition example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sales_error_sales_transaction_discount
     *
     */
    int updateByPrimaryKeySelective(SalesErrorSalesTransactionDiscountOptional record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sales_error_sales_transaction_discount
     *
     */
    int updateByPrimaryKey(SalesErrorSalesTransactionDiscountOptional record);
}
