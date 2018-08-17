/**
 * @(#)CurrencyTypeHandler.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Currency;

/**
 * Convert java util currency class from db string.
 */
@MappedTypes(Currency.class)
public class CurrencyTypeHandler extends BaseTypeHandler<Currency> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Currency parameter,
            JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getCurrencyCode());
        if (jdbcType == null) {
            ps.setString(i, parameter.getCurrencyCode());
        } else {
            ps.setObject(i, parameter.getCurrencyCode(), jdbcType.TYPE_CODE);
        }
    }

    @Override
    public Currency getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        String result = resultSet.getString(columnName);
        if (result == null) {
            return null;
        }
        return Currency.getInstance(result);
    }

    @Override
    public Currency getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        String result = resultSet.getString(columnIndex);
        if (result == null) {
            return null;
        }
        return Currency.getInstance(result);
    }

    @Override
    public Currency getNullableResult(CallableStatement resultSet, int columnIndex)
            throws SQLException {
        String result = resultSet.getString(columnIndex);
        if (result == null) {
            return null;
        }
        return Currency.getInstance(result);
    }

}
