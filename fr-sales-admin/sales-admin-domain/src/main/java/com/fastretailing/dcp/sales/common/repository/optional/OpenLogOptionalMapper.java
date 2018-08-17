/**
 * @(#)OpenLogOptionalMapper.java
 *
 *                                Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.repository.optional;

import org.apache.ibatis.annotations.Mapper;
import com.fastretailing.dcp.sales.common.entity.optional.OpenLogOptional;

/**
 * Open log optional mapper.
 */
@Mapper
public interface OpenLogOptionalMapper {

    /**
     * Select open log optional by primary key.
     * 
     * @param condition Open log optional.
     * @return Open log optional.
     */
    OpenLogOptional selectByPrimaryKey(OpenLogOptional condition);

}
