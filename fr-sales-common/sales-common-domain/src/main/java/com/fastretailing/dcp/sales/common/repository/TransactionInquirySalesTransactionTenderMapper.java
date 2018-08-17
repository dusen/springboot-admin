package com.fastretailing.dcp.sales.common.repository;

import com.fastretailing.dcp.sales.common.entity.TransactionInquirySalesTransactionTender;
import com.fastretailing.dcp.sales.common.entity.TransactionInquirySalesTransactionTenderCondition;
import com.fastretailing.dcp.sales.common.entity.TransactionInquirySalesTransactionTenderKey;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TransactionInquirySalesTransactionTenderMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_transaction_inquiry_sales_transaction_tender
     *
     */
    long countByCondition(TransactionInquirySalesTransactionTenderCondition example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_transaction_inquiry_sales_transaction_tender
     *
     */
    int deleteByCondition(TransactionInquirySalesTransactionTenderCondition example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_transaction_inquiry_sales_transaction_tender
     *
     */
    int deleteByPrimaryKey(TransactionInquirySalesTransactionTenderKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_transaction_inquiry_sales_transaction_tender
     *
     */
    int insert(TransactionInquirySalesTransactionTender record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_transaction_inquiry_sales_transaction_tender
     *
     */
    int insertSelective(TransactionInquirySalesTransactionTender record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_transaction_inquiry_sales_transaction_tender
     *
     */
    List<TransactionInquirySalesTransactionTender> selectByCondition(TransactionInquirySalesTransactionTenderCondition example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_transaction_inquiry_sales_transaction_tender
     *
     */
    TransactionInquirySalesTransactionTender selectByPrimaryKey(TransactionInquirySalesTransactionTenderKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_transaction_inquiry_sales_transaction_tender
     *
     */
    int updateByConditionSelective(@Param("record") TransactionInquirySalesTransactionTender record, @Param("example") TransactionInquirySalesTransactionTenderCondition example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_transaction_inquiry_sales_transaction_tender
     *
     */
    int updateByCondition(@Param("record") TransactionInquirySalesTransactionTender record, @Param("example") TransactionInquirySalesTransactionTenderCondition example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_transaction_inquiry_sales_transaction_tender
     *
     */
    int updateByPrimaryKeySelective(TransactionInquirySalesTransactionTender record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_transaction_inquiry_sales_transaction_tender
     *
     */
    int updateByPrimaryKey(TransactionInquirySalesTransactionTender record);
}
