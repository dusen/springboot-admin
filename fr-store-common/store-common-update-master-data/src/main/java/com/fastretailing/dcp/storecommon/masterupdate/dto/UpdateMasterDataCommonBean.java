/**
 * @(#)UpdateMasterDataCommonBean.java
 *
 *                                     Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.masterupdate.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import lombok.Data;

/**
 * Properties file information bean class.
 */
@Data
public class UpdateMasterDataCommonBean {

    /** If work table name. */
    private String ifWorkTableName;

    /** Input table name. */
    private String inputTableName;

    /** Update type. */
    private String updateType;

    /** Update target table name. */
    private String updateTargetTableName;

    /** Bulk count. */
    private Integer bulkCount;

    /** Input table column name set. */
    private Set<String> inputColumnNameSet;

    /** Update target table column name set. */
    private Set<String> targetColumnNameSet;

    /** List of common items name. */
    private List<String> commonItemList;

    /** List of update target items name. */
    private List<String> updateItemList;

    /** List of update key item. */
    private List<String> updateKeyItemList;

    /** Local to utc conversion item name. */
    private String utcConversionItemName;

    /** The list of local to utc conversion item name. */
    private List<String> utcConversionItemNameList;

    /** Local to utc conversion format. */
    private String utcConversionFormat;

    /** The list of local to utc conversion format. */
    private List<String> utcConversionFormatList;

    /** The view item name that with time zone id. */
    private String timeZoneIdItemName;

    /** The map that storage the utc information. */
    private List<Map<String, String>> utcInformationMapList;

    /**
     * Merge the list of utc item name list and format list to map list.
     */
    public void mergeUtcListToMapList() {
        this.utcInformationMapList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(this.utcConversionItemNameList)
                && CollectionUtils.isNotEmpty(this.utcConversionFormatList)) {
            Map<String, String> map = null;
            for (int i = 0; i < this.utcConversionItemNameList.size(); i++) {
                map = new HashMap<>();
                map.put("itemName", this.utcConversionItemNameList.get(i));
                map.put("format", this.utcConversionFormatList.get(i));
                this.utcInformationMapList.add(map);
            }
        }
    }
}
