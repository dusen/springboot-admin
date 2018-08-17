/**
 * @(#)StringEnumTypeHandler.java
 *
 *                                Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import com.fastretailing.dcp.storecommon.type.StringEnumHasValue;

/**
 * Enum (has string) type handler.
 */
public class StringEnumTypeHandler<E extends Enum<E> & StringEnumHasValue>
        extends BaseTypeHandler<E> {

    /** Enum objects. */
    private final E[] enums;
    /** Enum value object map. */
    private final Map<String, E> valueMap;

    /**
     * Constructor.
     * 
     * @param type Enum type.
     */
    public StringEnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.enums = type.getEnumConstants();
        if (this.enums == null) {
            throw new IllegalArgumentException(
                    type.getSimpleName() + " does not represent an enum type.");
        }

        // Make value object map.
        valueMap = new HashMap<>();

        Arrays.stream(this.enums).forEach(e -> {
            valueMap.put(e.getValue(), e);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, E parameter,
            JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, parameter.getValue());
        if (jdbcType == null) {
            preparedStatement.setString(i, parameter.getValue());
        } else {
            preparedStatement.setObject(i, parameter.getValue(), jdbcType.TYPE_CODE);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        String s = resultSet.getString(columnName);
        return getValue(s);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        String s = resultSet.getString(columnIndex);
        return getValue(s);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E getNullableResult(CallableStatement callableStatement, int columnIndex)
            throws SQLException {
        String s = callableStatement.getString(columnIndex);
        return getValue(s);
    }

    /**
     * Convert string value to enum.
     * 
     * @param s String value.
     * @return Enum.
     */
    private E getValue(String s) {
        return (s == null) ? null : valueMap.get(s);
    }

}
