package com.fastretailing.dcp.sales.common.repository.optional;

import com.fastretailing.dcp.sales.common.entity.optional.AlterationHistorySalesTransactionTenderOptional;
import com.fastretailing.dcp.sales.common.entity.optional.AlterationHistorySalesTransactionTenderOptionalCondition;
import com.fastretailing.dcp.sales.common.entity.optional.AlterationHistorySalesTransactionTenderOptionalKey;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AlterationHistorySalesTransactionTenderOptionalMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_alteration_history_sales_transaction_tender
     *
     */
    long countByCondition(AlterationHistorySalesTransactionTenderOptionalCondition example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_alteration_history_sales_transaction_tender
     *
     */
    int deleteByCondition(AlterationHistorySalesTransactionTenderOptionalCondition example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_alteration_history_sales_transaction_tender
     *
     */
    int deleteByPrimaryKey(AlterationHistorySalesTransactionTenderOptionalKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_alteration_history_sales_transaction_tender
     *
     */
    int insert(AlterationHistorySalesTransactionTenderOptional record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_alteration_history_sales_transaction_tender
     *
     */
    int insertSelective(AlterationHistorySalesTransactionTenderOptional record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_alteration_history_sales_transaction_tender
     *
     */
    List<AlterationHistorySalesTransactionTenderOptional> selectByCondition(AlterationHistorySalesTransactionTenderOptionalCondition example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_alteration_history_sales_transaction_tender
     *
     */
    AlterationHistorySalesTransactionTenderOptional selectByPrimaryKey(AlterationHistorySalesTransactionTenderOptionalKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_alteration_history_sales_transaction_tender
     *
     */
    int updateByConditionSelective(@Param("record") AlterationHistorySalesTransactionTenderOptional record, @Param("example") AlterationHistorySalesTransactionTenderOptionalCondition example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_alteration_history_sales_transaction_tender
     *
     */
    int updateByCondition(@Param("record") AlterationHistorySalesTransactionTenderOptional record, @Param("example") AlterationHistorySalesTransactionTenderOptionalCondition example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_alteration_history_sales_transaction_tender
     *
     */
    int updateByPrimaryKeySelective(AlterationHistorySalesTransactionTenderOptional record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_alteration_history_sales_transaction_tender
     *
     */
    int updateByPrimaryKey(AlterationHistorySalesTransactionTenderOptional record);
}
