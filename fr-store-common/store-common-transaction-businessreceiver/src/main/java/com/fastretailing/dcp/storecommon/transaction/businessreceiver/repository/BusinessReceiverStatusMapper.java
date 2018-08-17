/**
 * @(#)BusinessReceiverStatusMapper.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.businessreceiver.repository;

import com.fastretailing.dcp.storecommon.transaction.businessreceiver.dto.TBusinessReceiverStatus;
// import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
// import org.apache.ibatis.annotations.Select;
// import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Mapper;

/**
 * Business receiver status table access class.
 */
@Mapper
public interface BusinessReceiverStatusMapper {

    /**
     * Insert.
     * 
     * @param record entity.
     * @return Insert count.
     */
    @Insert({"insert into t_business_receiver_status (transaction_id, business_receiver_name, ",
            "create_date, create_user, retry_count) values (#{transactionId}, #{businessReceiverName}, ",
            "now(), #{createUser}, #{retryCount}) ON CONFLICT DO NOTHING"})
    int insert(TBusinessReceiverStatus record);

}
