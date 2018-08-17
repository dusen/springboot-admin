package com.fastretailing.dcp.sales.common.repository.optional;

import com.fastretailing.dcp.sales.common.entity.optional.AlterationHistorySalesTransactionTaxOptional;
import com.fastretailing.dcp.sales.common.entity.optional.AlterationHistorySalesTransactionTaxOptionalCondition;
import com.fastretailing.dcp.sales.common.entity.optional.AlterationHistorySalesTransactionTaxOptionalKey;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AlterationHistorySalesTransactionTaxOptionalMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_alteration_history_sales_transaction_tax
     *
     */
    long countByCondition(AlterationHistorySalesTransactionTaxOptionalCondition example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_alteration_history_sales_transaction_tax
     *
     */
    int deleteByCondition(AlterationHistorySalesTransactionTaxOptionalCondition example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_alteration_history_sales_transaction_tax
     *
     */
    int deleteByPrimaryKey(AlterationHistorySalesTransactionTaxOptionalKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_alteration_history_sales_transaction_tax
     *
     */
    int insert(AlterationHistorySalesTransactionTaxOptional record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_alteration_history_sales_transaction_tax
     *
     */
    int insertSelective(AlterationHistorySalesTransactionTaxOptional record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_alteration_history_sales_transaction_tax
     *
     */
    List<AlterationHistorySalesTransactionTaxOptional> selectByCondition(AlterationHistorySalesTransactionTaxOptionalCondition example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_alteration_history_sales_transaction_tax
     *
     */
    AlterationHistorySalesTransactionTaxOptional selectByPrimaryKey(AlterationHistorySalesTransactionTaxOptionalKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_alteration_history_sales_transaction_tax
     *
     */
    int updateByConditionSelective(@Param("record") AlterationHistorySalesTransactionTaxOptional record, @Param("example") AlterationHistorySalesTransactionTaxOptionalCondition example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_alteration_history_sales_transaction_tax
     *
     */
    int updateByCondition(@Param("record") AlterationHistorySalesTransactionTaxOptional record, @Param("example") AlterationHistorySalesTransactionTaxOptionalCondition example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_alteration_history_sales_transaction_tax
     *
     */
    int updateByPrimaryKeySelective(AlterationHistorySalesTransactionTaxOptional record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_alteration_history_sales_transaction_tax
     *
     */
    int updateByPrimaryKey(AlterationHistorySalesTransactionTaxOptional record);
}
