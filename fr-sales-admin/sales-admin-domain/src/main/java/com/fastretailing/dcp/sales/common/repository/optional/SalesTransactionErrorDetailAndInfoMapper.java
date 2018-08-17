/**
 * @(#)SalesTransactionErrorDetailAndInfoMapper.java
 *
 *                                                   Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.repository.optional;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.fastretailing.dcp.sales.common.entity.optional.SalesErrorSalesTransactionDetailAndInfo;
import com.fastretailing.dcp.sales.common.entity.optional.SalesErrorSalesTransactionTenderAndInfo;


/**
 * Sales transaction error detail and info mapper.
 */
@Mapper
public interface SalesTransactionErrorDetailAndInfoMapper {

    /**
     * Get detail and information.
     * 
     * @param transactionId Transaction id.
     * @return Sales error sales transaction detail and info.
     */
    List<SalesErrorSalesTransactionDetailAndInfo> getDetailAndInfo(String transactionId);

    /**
     * Get tender and information.
     * 
     * @param transactionId Transaction id.
     * @return Sales error sales transaction tender and info.
     */
    List<SalesErrorSalesTransactionTenderAndInfo> getTenderAndInfo(String transactionId);

    /**
     * Get error evacuation detail and information.
     * 
     * @param transactionId Transaction id.
     * @return Error evacuation sales transaction detail and info.
     */
    List<SalesErrorSalesTransactionDetailAndInfo> getErrorEvacuationDetailAndInfo(
            String transactionId);

    /**
     * Get error evacuation tender and information.
     * 
     * @param transactionId Transaction id.
     * @return Error evacuation sales transaction tender and info.
     */
    List<SalesErrorSalesTransactionTenderAndInfo> getErrorEvacuationTenderAndInfo(
            String transactionId);


}
