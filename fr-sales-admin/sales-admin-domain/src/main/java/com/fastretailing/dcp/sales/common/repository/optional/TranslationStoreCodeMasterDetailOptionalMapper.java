/**
 * @(#)TranslationStoreCodeMasterDetailOptionalMapper.java
 *
 *                                                      Copyright (c) 2018 Fast Retailing
 *                                                      Corporation.
 */

package com.fastretailing.dcp.sales.common.repository.optional;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.fastretailing.dcp.sales.common.entity.optional.TranslationStoreCodeMasterOptional;

/**
 * Translation store code master optional mapper.
 */
@Mapper
public interface TranslationStoreCodeMasterDetailOptionalMapper {

	/**
	 * Select translation store code master optional by primary key.
	 * 
	 * @param storeCode
	 *            Store code.
	 * @return Translation store code master optional.
	 */
	TranslationStoreCodeMasterOptional selectByPrimaryKey(String storeCode);

	/**
	 * Select translation store code master optional by business primary key.
	 * 
	 * @param condition
	 *            Translation store code master optional.
	 * @return Translation store code master optional list.
	 */
	List<TranslationStoreCodeMasterOptional> selectByBusinessPrimaryKey(
			TranslationStoreCodeMasterOptional translationStoreCodeMasterOptional);

}
