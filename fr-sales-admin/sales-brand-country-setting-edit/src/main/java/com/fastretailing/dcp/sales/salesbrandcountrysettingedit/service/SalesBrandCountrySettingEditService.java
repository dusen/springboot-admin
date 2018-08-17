/**
 * @(#)SalesBrandCountrySettingEditService.java
 *
 *                                              Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salesbrandcountrysettingedit.service;

import java.util.List;
import com.fastretailing.dcp.sales.common.entity.optional.TranslationStoreCodeMasterOptional;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.Brand;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.BrandCountrySettingEdit;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.BrandCountrySettingEditForm;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.Country;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.SalesIntegrityCheck;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.StoreSetting;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.StoreSettingDetail;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.StoreSettingDetailForm;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.form.StoreSettingForm;

/**
 * Brand country setting edit service.
 *
 */
public interface SalesBrandCountrySettingEditService {

    /**
     * Get brand pull down list.
     * 
     * @return List of brand.
     */
    List<Brand> getBrandList();

    /**
     * Get country pull down list.
     * 
     * @return List of country.
     */
    List<Country> getCountryList();

    /**
     * Get brand country setting edit list.
     * 
     * @param brandCountrySettingEditForm Form for the brand country setting edit page.
     * @return List of brand country setting edit.
     */
    List<BrandCountrySettingEdit> getBrandCountrySettingEditList(
            BrandCountrySettingEditForm brandCountrySettingEditForm);

    /**
     * Get setting item list.
     * 
     * @param storeSettingForm Form for this page.
     * @return Store setting list.
     */
    List<StoreSetting> getStoreSettingList(StoreSettingForm storeSettingForm);

    /**
     * Get common code master name1.
     * 
     * @param code Code from the previous page.
     * @param typeId Type id.
     * @return Common code master name1.
     */
    String getBrandAndCountry(String code, String typeId);

    /**
     * Get translation store code master.
     * 
     * @param storeCode Store code from the previous page.
     * @return Translation store code master optional.
     */
    TranslationStoreCodeMasterOptional getStoreInformation(String storeCode);

    /**
     * Get delete count.
     * 
     * @param storeSettingForm Form for store setting page.
     * @return Delete count.
     */
    int deleteStoreSettingList(StoreSettingForm storeSettingForm);

    /**
     * Get add data count.
     * 
     * @param brandCountrySettingEditForm Form for the brand country setting edit page.
     * @return Add data count.
     */
    int addBrandCountrySettinglist(BrandCountrySettingEditForm brandCountrySettingEditForm);

    /**
     * Get store setting list count.
     * 
     * @param storeSettingForm From for the store setting page.
     * @return Store setting list count.
     */
    int countStoreSettingList(StoreSettingForm storeSettingForm);

    /**
     * Get business setting detail list count.
     * 
     * @param storeSettingDetailForm Form for the store setting detail page.
     * @return Business setting detail list count.
     */
    int countBusinessSettingDetailList(StoreSettingDetailForm storeSettingDetailForm);

    /**
     * Get default store setting detail list.
     * 
     * @param storeSettingDetailForm Form for the store setting detail page.
     * @return Default store setting detail list.
     */
    List<StoreSettingDetail> getDetailDefaultList(StoreSettingDetailForm storeSettingDetailForm);

    /**
     * Get store setting detail list.
     * 
     * @param storeSettingDetailForm Form for the store setting detail page.
     * @return Store setting detail list.
     */
    List<StoreSettingDetail> getDetailList(StoreSettingDetailForm storeSettingDetailForm);

    /**
     * Get order status list.
     * 
     * @param typeCode Type code.
     * @return List of order status.
     */
    List<String> getOrderStatusList(String typeCode);

    /**
     * Get mapping pattern name.
     * 
     * @return Mapping pattern name.
     */
    String getMappingPatternName();

    /**
     * Get insert count.
     * 
     * @param storeSettingDetailForm Form for the store setting detail page.
     * @return Insert count.
     */
    int insertDefaultDetail(StoreSettingDetailForm storeSettingDetailForm);

    /**
     * Get update count.
     * 
     * @param storeSettingDetailForm Form for the store setting detail page.
     * @return Update count.
     */
    int updateDetail(StoreSettingDetailForm storeSettingDetailForm);

    /**
     * Get add insert count.
     * 
     * @param storeSettingDetailForm Form for the store setting detail page.
     * @return Add insert count.
     */
    int insertAddDetail(StoreSettingDetailForm storeSettingDetailForm);

    /**
     * Get sales integrity list.
     * 
     * @param typeId Type id.
     * @return Sales integrity check list.
     */
    List<SalesIntegrityCheck> getSalesIntegrityCheckList(String typeId);
}
