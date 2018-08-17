/**
 * @(#)ReportReceiptNumberSeqMapper.java
 * 
 *                                       Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.api.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Repository class for getting next sequence value.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
@Mapper
public interface ReportReceiptNumberSeqMapper {

    /**
     * Get next value of sequence.
     * 
     * @return next value of report_receipt_number_seq.
     */
    @Select("SELECT to_char(nextval('report_receipt_number_seq'), 'fm000000000')")
    String nextValue();
}
