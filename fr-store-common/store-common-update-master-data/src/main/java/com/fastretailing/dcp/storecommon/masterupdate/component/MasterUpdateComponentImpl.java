/**
 * @(#)MasterUpdateComponentImpl.java
 *
 *                                    Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.masterupdate.component;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fastretailing.dcp.common.OmsConstants;
import com.fastretailing.dcp.common.constants.ErrorName;
import com.fastretailing.dcp.common.constants.LogLevel;
import com.fastretailing.dcp.common.exception.BusinessException;
import com.fastretailing.dcp.common.model.ResultObject;
import com.fastretailing.dcp.storecommon.CommonFunctionId;
import com.fastretailing.dcp.storecommon.FunctionType;
import com.fastretailing.dcp.storecommon.MessageType;
import com.fastretailing.dcp.storecommon.dto.CommonStatus;
import com.fastretailing.dcp.storecommon.masterupdate.constants.MessagePrefix;
import com.fastretailing.dcp.storecommon.masterupdate.dto.GeneralMasterRequest;
import com.fastretailing.dcp.storecommon.masterupdate.dto.PropertiesItemBean;
import com.fastretailing.dcp.storecommon.masterupdate.dto.UpdateMasterDataCommonBean;
import com.fastretailing.dcp.storecommon.masterupdate.repository.MasterUpdateMapper;
import com.fastretailing.dcp.storecommon.message.SystemMessageSource;
import com.fastretailing.dcp.storecommon.type.EAIUpdateType;
import com.fastretailing.dcp.storecommon.util.DateUtility;
import com.fastretailing.dcp.storecommon.util.DateUtility.DateTimeFormat;
import lombok.extern.slf4j.Slf4j;

/**
 * Master update component.
 */
@Service
@Slf4j
public class MasterUpdateComponentImpl implements MasterUpdateComponent {

    /** Platform name. */
    @Value("${platform.name.short}")
    private String platformShortName;

    /** System message source. */
    @Autowired
    private SystemMessageSource systemMessageSource;

    /** DB access parts(Master table). */
    @Autowired
    private MasterUpdateMapper masterUpdateMapper;

    /** Data of the property file. */
    @Autowired
    private PropertiesItemBean propertiesItemBean;

    /** Map used by multithread members. */
    private static Map<String, UpdateMasterDataCommonBean> currentCommonBeanMap =
            new ConcurrentHashMap<>();

    /** Application user id. */
    private static final String APP_USER_ID = "BATCH";

    /** Application program id. */
    private static final String APP_PROGRAM_ID = "CMN9900101";

    /** String of item name. */
    private static final String ITEM_NAME = "itemName";

    /** String of constraint name. */
    private static final String CONSTRAINT_NAME = "constraintName";

    /** String of update column list. */
    private static final String UPDATE_COLUMN_LIST = "updateColumnList";

    /** String of table name. */
    private static final String TABLE_NAME = "tableName";

    /**
     * Master update component.
     * 
     * @param generalMasterRequest General master request to update.
     * @return Common status.
     */
    @Override
    @Transactional
    public CommonStatus updateMasterData(GeneralMasterRequest generalMasterRequest) {
        // The key to get update information from property file.
        String outboundId = generalMasterRequest.getOutboundId();
        // The number that can get update data from input table and can delete data from
        // work table.
        String lotNumber = generalMasterRequest.getLotNumber();

        // Get data common bean of update information and check it.
        UpdateMasterDataCommonBean dataCommonBean = initializeDataAndCheck(outboundId, lotNumber);

        // Count input data.
        if (masterUpdateMapper.countByLotNumber(dataCommonBean.getInputTableName(), lotNumber,
                null) == 0) {
            log.info(
                    "There is no data in input table, general master data update program normal end.");
            return new CommonStatus();
        }

        // Update target data.
        updateTargetData(dataCommonBean, lotNumber);

        // Delete the updated If work table's data with lotNumber.
        masterUpdateMapper.deleteByLotNumber(dataCommonBean.getIfWorkTableName(), lotNumber);

        return new CommonStatus();
    }

    /**
     * Initialize data and check it integrity.
     * 
     * @param outboundId Out bound id from parameter.
     * @param lotNumber Lot number from parameter.
     */
    private UpdateMasterDataCommonBean initializeDataAndCheck(String outboundId, String lotNumber) {

        // Get update information from properties file by out bound id.
        UpdateMasterDataCommonBean updateMasterDataCommonBean = getPropertiesItem(outboundId);

        // Check others EAI update type.
        // In case of exist others type in input data.
        if (masterUpdateMapper.countEaiUpdateTypeOthers(
                updateMasterDataCommonBean.getInputTableName(), lotNumber) != 0) {
            // Return with error.
            throwError(MessagePrefix.CMN9900101E006_E_OTHERS_EAI_UPDATE_TYPE_ERROR,
                    ErrorName.Business.BUSINESS_CHECK_ERROR, MessageType.BUSINESS_ERROR);
        }

        // Get all columns name of input table.
        updateMasterDataCommonBean.setInputColumnNameSet(
                masterUpdateMapper.getColumnName(updateMasterDataCommonBean.getInputTableName()));
        // Get all columns name of update target table.
        updateMasterDataCommonBean.setTargetColumnNameSet(masterUpdateMapper
                .getColumnName(updateMasterDataCommonBean.getUpdateTargetTableName()));

        // Check the update key items name does exist in input table columns name.
        updateMasterDataCommonBean.getUpdateKeyItemList().forEach(keyItem -> {
            if (!updateMasterDataCommonBean.getInputColumnNameSet().contains(keyItem)) {
                throwError(MessagePrefix.CMN9900101E007_E_CHECK_UPDATE_KEY_ITEMS_NOT_EXIST_ERROR,
                        ErrorName.Basis.VALIDATION_ERROR, MessageType.VALIDATE_ERROR, keyItem,
                        updateMasterDataCommonBean.getInputTableName());
            }
            if (!updateMasterDataCommonBean.getTargetColumnNameSet().contains(keyItem)) {
                throwError(MessagePrefix.CMN9900101E007_E_CHECK_UPDATE_KEY_ITEMS_NOT_EXIST_ERROR,
                        ErrorName.Basis.VALIDATION_ERROR, MessageType.VALIDATE_ERROR, keyItem,
                        updateMasterDataCommonBean.getUpdateTargetTableName());
            }
        });

        List<String> updateItemsList = new ArrayList<>();
        List<String> commonItemsList = new ArrayList<>();

        updateMasterDataCommonBean.getTargetColumnNameSet().forEach(itemName -> {
            // Get column name that exist in target table and in input table also.
            if (updateMasterDataCommonBean.getInputColumnNameSet().contains(itemName)) {
                // Check item name does common item.
                if (ColumnName.isCommonItem(itemName)) {
                    commonItemsList.add(itemName);
                    // Update items.
                } else {
                    updateItemsList.add(itemName);
                }
            }
        });

        // Check common items is empty.
        if (CollectionUtils.isEmpty(commonItemsList)) {
            commonItemsList.add(ColumnName.CREATE_USER_ID);
            commonItemsList.add(ColumnName.CREATE_DATE_TIME);
            commonItemsList.add(ColumnName.CREATE_PROGRAM_ID);
            commonItemsList.add(ColumnName.UPDATE_USER_ID);
            commonItemsList.add(ColumnName.UPDATE_DATE_TIME);
            commonItemsList.add(ColumnName.UPDATE_PROGRAM_ID);
        }
        updateMasterDataCommonBean.setCommonItemList(commonItemsList);
        updateMasterDataCommonBean.setUpdateItemList(updateItemsList);

        return updateMasterDataCommonBean;
    }

    /**
     * Update data to target table .
     * 
     * @param dataCommonBean The bean that contains all of the update information.
     * @param lotNumber Lot number from parameter.
     */
    private void updateTargetData(UpdateMasterDataCommonBean dataCommonBean, String lotNumber) {

        // If update type is all.
        if (UpdateTypeEnum.ALL.getValue().equals(dataCommonBean.getUpdateType())) {
            // Delete all data of update target table.
            log.info("The update type is all, start to delete all data from update target table.");
            masterUpdateMapper.delete(dataCommonBean.getUpdateTargetTableName());
            // If update type is difference.
        } else {
            masterUpdateMapper.deleteByCondition(dataCommonBean.getUpdateTargetTableName(),
                    dataCommonBean.getInputTableName(), dataCommonBean.getUpdateKeyItemList(),
                    lotNumber);
        }

        // Prepare update columns list.
        List<Object> updateColumnList = new ArrayList<>();
        updateColumnList.addAll(dataCommonBean.getUpdateItemList());
        updateColumnList.addAll(dataCommonBean.getCommonItemList());
        // Get constraint name.
        String constraintName =
                masterUpdateMapper.getConstraintName(dataCommonBean.getUpdateTargetTableName());

        // Get the total number of updated data.
        int dataTotal = masterUpdateMapper.countByLotNumber(dataCommonBean.getInputTableName(),
                lotNumber, EAIUpdateType.ADD.getEaiUpdateType());
        // The number of single executions.
        int updateSize = dataCommonBean.getBulkCount();
        // The total number of loops.
        int loopTotal = (int) Math.ceil(dataTotal / (updateSize * 1.0));

        for (int i = 0; i < loopTotal; i++) {
            // Create update data map as parameter of upsert.
            Map<String, Object> mapData = new HashMap<>();
            mapData.put(TABLE_NAME, dataCommonBean.getUpdateTargetTableName());
            mapData.put(CONSTRAINT_NAME, constraintName);
            mapData.put(UPDATE_COLUMN_LIST, updateColumnList);

            // Calculate the starting index.
            int startIndex = i * updateSize;

            // Get input data, add to update data list.
            List<List<Map<String, Object>>> updateDataInfomationList =
                    dataCommonBean.getUpdateItemList().stream().map(columnName -> {
                        String columnType = masterUpdateMapper
                                .getColumnType(dataCommonBean.getInputTableName(), columnName);
                        List<Map<String, Object>> valueList = masterUpdateMapper.getInputData(
                                columnName, dataCommonBean.getInputTableName(), lotNumber,
                                columnType, startIndex, updateSize,
                                dataCommonBean.getUpdateKeyItemList().toString(),
                                dataCommonBean.getTimeZoneIdItemName());
                        // If the item name that need to change to utc format.
                        if (CollectionUtils.isEmpty(dataCommonBean.getUtcInformationMapList())) {
                            return valueList;
                        }
                        dataCommonBean.getUtcInformationMapList()
                                .stream()
                                .filter(map -> columnName.equals(map.get(ITEM_NAME)))
                                .forEach(map -> {
                                    String format = null;
                                    if (StringUtils.isBlank(
                                            map.get(PropertiesFileItemName.MAP_KEY_FORMAT))) {
                                        format = DateUtility.DateTimeFormat.UUUUHMMHDDHHQMIQSS_SSS
                                                .getFormat();
                                    } else {
                                        format = map.get(PropertiesFileItemName.MAP_KEY_FORMAT);
                                    }
                                    covertLocalDateTimeToUtc(format, valueList);
                                });
                        return valueList;
                    }).collect(Collectors.toList());

            List<Map<String, Object>> valueList =
                    CollectionUtils.isEmpty(updateDataInfomationList) ? Collections.emptyList()
                            : updateDataInfomationList.get(0);

            // Create common value list, add to update data infomation list.
            setCommonValueList(updateDataInfomationList, valueList,
                    dataCommonBean.getCommonItemList(), dataCommonBean.getUpdateTargetTableName());

            // The count that needs to be update for create common data list.
            int columnDataCount = valueList.size();

            mapData.put("updateDataInfoList", updateDataInfomationList);

            if (masterUpdateMapper.upsert(mapData) != columnDataCount) {
                throwError(MessagePrefix.CMN9900101E009_E_BULK_UPDATE_FAIL_ERROR,
                        ErrorName.Business.BUSINESS_CHECK_ERROR, MessageType.BUSINESS_ERROR);
            }
        }
    }

    /**
     * Create a list of common value.
     * 
     * @param updateDataInfoList The list of update data list.
     * @param valueList The list of data.
     * @param commonItemsList Common items name list.
     * @param updateTargetTableName Update target table name.
     */
    private void setCommonValueList(List<List<Map<String, Object>>> updateDataInfoList,
            List<Map<String, Object>> valueList, List<String> commonItemsList,
            String updateTargetTableName) {

        // Get current time for create common data list.
        OffsetDateTime utcBusinessDateTime = DateUtility.getZonedDateTimeUtc();

        // Create common data list.
        String userIdtype =
                masterUpdateMapper.getColumnType(updateTargetTableName, ColumnName.CREATE_USER_ID);
        String dateTimetype = masterUpdateMapper.getColumnType(updateTargetTableName,
                ColumnName.CREATE_DATE_TIME);
        String programIdtype = masterUpdateMapper.getColumnType(updateTargetTableName,
                ColumnName.CREATE_PROGRAM_ID);

        // Create common value list for update.
        commonItemsList.forEach(commonItem -> {

            Map<String, Object> column = new HashMap<>();
            switch (commonItem) {
                case ColumnName.CREATE_USER_ID:
                    column.put(PropertiesFileItemName.VALUE, APP_USER_ID);
                    column.put(PropertiesFileItemName.TYPE, userIdtype);
                    break;
                case ColumnName.CREATE_DATE_TIME:
                    column.put(PropertiesFileItemName.VALUE, utcBusinessDateTime);
                    column.put(PropertiesFileItemName.TYPE, dateTimetype);
                    break;
                case ColumnName.CREATE_PROGRAM_ID:
                    column.put(PropertiesFileItemName.VALUE, APP_PROGRAM_ID);
                    column.put(PropertiesFileItemName.TYPE, programIdtype);
                    break;
                case ColumnName.UPDATE_USER_ID:
                    column.put(PropertiesFileItemName.VALUE, APP_USER_ID);
                    column.put(PropertiesFileItemName.TYPE, userIdtype);
                    break;
                case ColumnName.UPDATE_DATE_TIME:
                    column.put(PropertiesFileItemName.VALUE, utcBusinessDateTime);
                    column.put(PropertiesFileItemName.TYPE, dateTimetype);
                    break;
                case ColumnName.UPDATE_PROGRAM_ID:
                    column.put(PropertiesFileItemName.VALUE, APP_PROGRAM_ID);
                    column.put(PropertiesFileItemName.TYPE, programIdtype);
                    break;
                default:
                    throw new IllegalArgumentException();
            }

            List<Map<String, Object>> commonValueList = new ArrayList<>();
            valueList.forEach(index -> commonValueList.add(column));
            updateDataInfoList.add(commonValueList);
        });

    }

    /**
     * Get item from properties file for update.
     * 
     * @param outboundId Out bound id from parameter.
     * @return Common bean.
     */
    private UpdateMasterDataCommonBean getPropertiesItem(String outboundId) {

        // The create common bean is executed only when it is not cached in current.
        return currentCommonBeanMap.computeIfAbsent(outboundId, s -> createCommonBean(outboundId));

    }

    /**
     * Get update master data common bean instance.
     * 
     * @param outboundId Out bound id from parameter.
     * @return Common bean.
     */
    private UpdateMasterDataCommonBean createCommonBean(String outboundId) {

        UpdateMasterDataCommonBean commonBean = new UpdateMasterDataCommonBean();
        Integer bulkCount = propertiesItemBean.getBulkCount();
        // Check bulk count does defined in property file.
        if (bulkCount == null) {
            throwError(MessagePrefix.CMN9900101E010_E_BULK_COUNT_NOT_EXIST_ERROR,
                    ErrorName.Basis.VALIDATION_ERROR, MessageType.VALIDATE_ERROR);
        }
        // Check bulk count does zero.
        if (bulkCount <= 0) {
            throwError(MessagePrefix.CMN9900101E011_E_BULK_COUNT_ZERO_ERROR,
                    ErrorName.Basis.VALIDATION_ERROR, MessageType.VALIDATE_ERROR);
        }
        commonBean.setBulkCount(bulkCount);

        // Get update item info from property file by out bound id.
        Map<String, Map<String, String>> propertiesMap = propertiesItemBean.getProperties();
        Map<String, String> updateInfoMap = propertiesMap.get(outboundId);
        if (updateInfoMap == null) {
            throwError(MessagePrefix.CMN9900101E002_E_CHECK_REQUIRE_ERROR,
                    ErrorName.Business.BUSINESS_CHECK_ERROR, MessageType.BUSINESS_ERROR);
        }

        if (StringUtils.isEmpty(updateInfoMap.get(PropertiesFileItemName.IF_WORK_TABLE_NAME))) {
            throwError(MessagePrefix.CMN9900101E003_E_CHECK_PROPERTIES_ERROR,
                    ErrorName.Basis.VALIDATION_ERROR, MessageType.VALIDATE_ERROR,
                    PropertiesFileItemName.IF_WORK_TABLE_NAME);
        }
        // Check the if work table name does exist in database.
        if (masterUpdateMapper.countTableName(
                updateInfoMap.get(PropertiesFileItemName.IF_WORK_TABLE_NAME)) == 0) {
            throwError(MessagePrefix.CMN9900101E004_E_CHECK_TABLE_NAME_ERROR,
                    ErrorName.Basis.VALIDATION_ERROR, MessageType.VALIDATE_ERROR,
                    updateInfoMap.get(PropertiesFileItemName.IF_WORK_TABLE_NAME));
        }
        commonBean.setIfWorkTableName(updateInfoMap.get(PropertiesFileItemName.IF_WORK_TABLE_NAME));

        if (StringUtils.isEmpty(updateInfoMap.get(PropertiesFileItemName.INPUT_TABLE_NAME))) {
            throwError(MessagePrefix.CMN9900101E003_E_CHECK_PROPERTIES_ERROR,
                    ErrorName.Basis.VALIDATION_ERROR, MessageType.VALIDATE_ERROR,
                    PropertiesFileItemName.INPUT_TABLE_NAME);
        }
        // Check input table name does exist in database.
        if (masterUpdateMapper
                .countTableName(updateInfoMap.get(PropertiesFileItemName.INPUT_TABLE_NAME)) == 0) {
            throwError(MessagePrefix.CMN9900101E004_E_CHECK_TABLE_NAME_ERROR,
                    ErrorName.Basis.VALIDATION_ERROR, MessageType.VALIDATE_ERROR,
                    updateInfoMap.get(PropertiesFileItemName.INPUT_TABLE_NAME));
        }
        commonBean.setInputTableName(updateInfoMap.get(PropertiesFileItemName.INPUT_TABLE_NAME));

        if (StringUtils.isEmpty(updateInfoMap.get(PropertiesFileItemName.UPDATE_TYPE))) {
            throwError(MessagePrefix.CMN9900101E003_E_CHECK_PROPERTIES_ERROR,
                    ErrorName.Basis.VALIDATION_ERROR, MessageType.VALIDATE_ERROR,
                    PropertiesFileItemName.UPDATE_TYPE);
        }
        commonBean.setUpdateType(updateInfoMap.get(PropertiesFileItemName.UPDATE_TYPE));

        if (StringUtils
                .isEmpty(updateInfoMap.get(PropertiesFileItemName.UPDATE_TARGET_TABLE_NAME))) {
            throwError(MessagePrefix.CMN9900101E003_E_CHECK_PROPERTIES_ERROR,
                    ErrorName.Basis.VALIDATION_ERROR, MessageType.VALIDATE_ERROR,
                    PropertiesFileItemName.UPDATE_TARGET_TABLE_NAME);
        }
        // Check the update target table name does exist in database.
        if (masterUpdateMapper.countTableName(
                updateInfoMap.get(PropertiesFileItemName.UPDATE_TARGET_TABLE_NAME)) == 0) {
            throwError(MessagePrefix.CMN9900101E004_E_CHECK_TABLE_NAME_ERROR,
                    ErrorName.Basis.VALIDATION_ERROR, MessageType.VALIDATE_ERROR,
                    updateInfoMap.get(PropertiesFileItemName.UPDATE_TARGET_TABLE_NAME));
        }
        commonBean.setUpdateTargetTableName(
                updateInfoMap.get(PropertiesFileItemName.UPDATE_TARGET_TABLE_NAME));

        // If update key item type is empty.
        if (StringUtils.isEmpty(updateInfoMap.get(PropertiesFileItemName.UPDATE_KEY_ITEM))) {
            throwError(MessagePrefix.CMN9900101E003_E_CHECK_PROPERTIES_ERROR,
                    ErrorName.Basis.VALIDATION_ERROR, MessageType.VALIDATE_ERROR,
                    PropertiesFileItemName.UPDATE_KEY_ITEM);
        }

        List<String> updateKeyItemList =
                Arrays.asList(updateInfoMap.get(PropertiesFileItemName.UPDATE_KEY_ITEM)
                        .split(OmsConstants.COMMA));
        commonBean.setUpdateKeyItemList(updateKeyItemList);

        // Get local to utc conversion item name and that list.
        commonBean.setUtcConversionItemName(
                updateInfoMap.get(PropertiesFileItemName.LOCAL_TO_UTC_CONVERSION_ITEM_NAME));
        if (StringUtils.isNotEmpty(commonBean.getUtcConversionItemName())) {
            commonBean.setUtcConversionItemNameList(
                    Arrays.asList(commonBean.getUtcConversionItemName().split(OmsConstants.COMMA)));
        }

        // Get local to utc conversion format.
        commonBean.setUtcConversionFormat(
                updateInfoMap.get(PropertiesFileItemName.LOCAL_TO_UTC_CONVERSION_FORMAT));
        if (StringUtils.isNotEmpty(commonBean.getUtcConversionFormat())) {
            commonBean.setUtcConversionFormatList(
                    Arrays.asList(commonBean.getUtcConversionFormat().split(OmsConstants.COMMA)));
        }

        // Get time zone id item name.
        commonBean.setTimeZoneIdItemName(
                updateInfoMap.get(PropertiesFileItemName.TIME_ZONE_ID_ITEM_NAME));
        commonBean.mergeUtcListToMapList();
        return commonBean;
    }

    /**
     * Throw business exception error.
     * 
     * @param errorCode Error code.
     * @param errorName Error name.
     * @param messageType Message type.
     * @param messageParameters Parameter of message.
     */
    private void throwError(String errorCode, ErrorName errorName, MessageType messageType,
            Object... messageParameters) {
        ResultObject resultObject = new ResultObject(errorName, getDebugId(messageType),
                systemMessageSource.getMessage(errorCode, messageParameters));
        throw new BusinessException(resultObject);
    }

    /**
     * Get validate error debug id.
     * 
     * @param messageType Message type.
     * 
     * @return Validate debug id.
     */
    private String getDebugId(MessageType messageType) {
        return LogLevel.ERROR.toString() + PropertiesFileItemName.UNDER_SCORE + platformShortName
                + PropertiesFileItemName.UNDER_SCORE + messageType + FunctionType.BATCH
                + CommonFunctionId.STORE_COMMON_UPDATE_MASTER_DATA;
    }

    /**
     * Covert the list of local date time to the list of utc date time.
     * 
     * @param pattern Specified format pattern.
     * @param dateValueList The list of local date time data.
     */
    private void covertLocalDateTimeToUtc(String pattern, List<Map<String, Object>> dateValueList) {
        for (Map<String, Object> valueMap : dateValueList) {
            valueMap.put(PropertiesFileItemName.VALUE,
                    parseOffsetDateTimeWithTimeZone(
                            valueMap.get(PropertiesFileItemName.VALUE).toString(), pattern,
                            valueMap.get(PropertiesFileItemName.TIME_ZONE_ID).toString()));
        }
    }

    /**
     * String formatted date to OffsetDateTime.
     * 
     * @param dateTimeString Specified date time string.
     * @param pattern Specified format pattern.
     * @param timeZoneId Time zone id.
     * @return {@link OffsetDateTime} when success, null otherwise
     */
    private OffsetDateTime parseOffsetDateTimeWithTimeZone(String dateTimeString, String pattern,
            String timeZoneId) {

        try {
            DateTimeFormatter dateTimeFormatter =
                    DateTimeFormatter.ofPattern(pattern).withResolverStyle(ResolverStyle.STRICT);
            return LocalDateTime.parse(dateTimeString, dateTimeFormatter)
                    .atZone(TimeZone.getTimeZone(timeZoneId).toZoneId())
                    .toOffsetDateTime();
        } catch (DateTimeParseException | IllegalArgumentException e) {
            throw e;
        }
    }
}
