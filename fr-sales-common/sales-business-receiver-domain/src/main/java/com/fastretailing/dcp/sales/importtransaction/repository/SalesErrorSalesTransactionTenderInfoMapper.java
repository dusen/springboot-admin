package com.fastretailing.dcp.sales.importtransaction.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionTenderInfo;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesErrorSalesTransactionTenderInfoCondition;

@Mapper
public interface SalesErrorSalesTransactionTenderInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sales_error_sales_transaction_tender_info
     *
     * @mbg.generated
     */
    int deleteByCondition(SalesErrorSalesTransactionTenderInfoCondition example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sales_error_sales_transaction_tender_info
     *
     * @mbg.generated
     */
    int insertSelective(SalesErrorSalesTransactionTenderInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sales_error_sales_transaction_tender_info
     *
     * @mbg.generated
     */
    List<SalesErrorSalesTransactionTenderInfo> selectByCondition(SalesErrorSalesTransactionTenderInfoCondition example);
}