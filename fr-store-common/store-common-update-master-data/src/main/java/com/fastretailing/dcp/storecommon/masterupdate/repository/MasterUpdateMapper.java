/**
 * @(#)MasterUpdateMapper.java
 *
 *                             Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.masterupdate.repository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Master table access class.
 */
@Mapper
public interface MasterUpdateMapper {

    /**
     * Get input data record count.
     * 
     * @param tableName Table name.
     * @param lotNumber Lot number.
     * @param eaiType EAI type.
     * @return input count.
     */
    int countByLotNumber(@Param("tableName") String tableName, @Param("lotNumber") String lotNumber,
            @Param("eaiType") String eaiType);

    /**
     * Get table name count.
     * 
     * @param tableName Table name.
     * @return record count.
     */
    int countTableName(@Param("tableName") String tableName);

    /**
     * Delete all data of update target table.
     * 
     * @param tableName Table name.
     * @return Delete count.
     */
    int delete(@Param("tableName") String tableName);

    /**
     * Get constraint name of update target table.
     * 
     * @param updateTableName The update target table name.
     * @return Constraint name.
     */
    String getConstraintName(@Param("updateTableName") String updateTableName);

    /**
     * Delete data of If work table by lot number.
     * 
     * @param tableName If work table name.
     * @param lotNumber Lot number.
     * @return Delete count.
     */
    int deleteByLotNumber(@Param("tableName") String tableName,
            @Param("lotNumber") String lotNumber);

    /**
     * Get all columns name of table.
     * 
     * @param tableName Table name.
     * @return Set of column name.
     */
    Set<String> getColumnName(@Param("tableName") String tableName);

    /**
     * Get others EAI update type of input table.
     * 
     * @param tableName Input table.
     * @param lotNumber Lot number.
     * @return Count of data.
     */
    int countEaiUpdateTypeOthers(@Param("tableName") String tableName,
            @Param("lotNumber") String lotNumber);

    /**
     * Get input data of that column, but not included data that EAI type is 3.
     * 
     * @param columnName Column name required to acquire data.
     * @param tableName Input table name.
     * @param lotNumber Lot number.
     * @param columnType Column data type.
     * @param startIndex Start index.
     * @param size Limit count.
     * @param key Key.
     * @param fieldName The field name that storage the data`s time zone id.
     * @return Input data.
     */
    List<Map<String, Object>> getInputData(@Param("columnName") String columnName,
            @Param("tableName") String tableName, @Param("lotNumber") String lotNumber,
            @Param("columnType") String columnType, @Param("startIndex") int startIndex,
            @Param("size") int size, @Param("key") String key,
            @Param("fieldName") String fieldName);

    /**
     * Delete master table data.
     * 
     * @param updateTargetTable Update target table name.
     * @param inputTable Input table name.
     * @param updateKeyItmeList Update key items list.
     * @param lotNumber Lot number.
     * @return Delete count.
     */
    @DeleteProvider(type = CreateDynamicSql.class, method = "builderDeleteByConditionSql")
    int deleteByCondition(String updateTargetTable, String inputTable,
            List<String> updateKeyItmeList, String lotNumber);

    /**
     * Get column name and column data type of input table.
     * 
     * @param tableName Update target table name.
     * @param columnName The column name that need to get data type.
     * @return Column type.
     */
    String getColumnType(@Param("tableName") String tableName,
            @Param("columnName") String columnName);

    /**
     * Insert or update data.
     * 
     * @param mapData The map that include update items and update data.
     * @return Inserted or updated count.
     */
    int upsert(Map<String, Object> mapData);

}
