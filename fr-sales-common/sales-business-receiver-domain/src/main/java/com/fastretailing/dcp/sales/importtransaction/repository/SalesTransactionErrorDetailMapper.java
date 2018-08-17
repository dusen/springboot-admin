package com.fastretailing.dcp.sales.importtransaction.repository;

import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionErrorDetail;
import com.fastretailing.dcp.sales.importtransaction.entity.SalesTransactionErrorDetailCondition;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SalesTransactionErrorDetailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sales_transaction_error_detail
     *
     * @mbg.generated
     */
    int insertSelective(SalesTransactionErrorDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sales_transaction_error_detail
     *
     * @mbg.generated
     */
    List<SalesTransactionErrorDetail> selectByCondition(SalesTransactionErrorDetailCondition example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_sales_transaction_error_detail
     *
     * @mbg.generated
     */
    int updateByConditionSelective(@Param("record") SalesTransactionErrorDetail record, @Param("example") SalesTransactionErrorDetailCondition example);
}