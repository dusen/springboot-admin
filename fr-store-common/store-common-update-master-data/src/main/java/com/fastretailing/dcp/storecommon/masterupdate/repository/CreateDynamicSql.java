/**
 * @(#)CreateDynamicallySql.java
 *
 *                               Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.masterupdate.repository;

import java.util.List;
import org.apache.ibatis.jdbc.SQL;

/**
 * Create delete method.
 */
public class CreateDynamicSql {

    /**
     * Create SQL of the delete.
     * 
     * @param updateTargetTable The update target table name.
     * @param inputTable The input table name.
     * @param updateKeyItmesList Update key items list.
     * @param lotNumber Lot number.
     * @return Sql statement.
     */
    public String builderDeleteByConditionSql(String updateTargetTable, String inputTable,
            List<String> updateKeyItmesList, String lotNumber) {
        SQL sqlHeadBuilder = new SQL();
        String updateTargetTableSql = updateTargetTable + " AS DEL ";
        String inputTableSql = " USING " + inputTable + " AS SRC ";
        sqlHeadBuilder.DELETE_FROM(updateTargetTableSql + " /* CMN9900101-009 */ " + inputTableSql);
        updateKeyItmesList.forEach(keyName -> {
            sqlHeadBuilder.WHERE("DEL." + keyName + " = " + "SRC." + keyName);
        });
        sqlHeadBuilder.WHERE("SRC.eai_update_type IN ('1','3','9')");
        sqlHeadBuilder.WHERE("SRC.lot_number = '" + lotNumber + "'");

        return sqlHeadBuilder.toString();
    }
}
