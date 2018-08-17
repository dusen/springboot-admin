/**
 * @(#)BusinessProcessorStatusMapper.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.businessprocessor.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import com.fastretailing.dcp.storecommon.transaction.businessprocessor.dto.TBusinessProcessorStatus;

/**
 * Business processor status table access class.
 */
@Mapper
public interface BusinessProcessorStatusMapper {

    /**
     * Insert.
     * 
     * @param record entity.
     * @return Insert count.
     */
    @Insert({
            "insert into t_business_processor_status_p${partitioningNo} ", 
            "(transaction_id, business_process_name, create_date, create_user, retry_count)",
            "values (#{transactionId}, #{businessProcessName}, now(), ",
            "#{createUser}, #{retryCount}) ON CONFLICT DO NOTHING"})
    int insert(TBusinessProcessorStatus record);

}
