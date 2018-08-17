package com.fastretailing.dcp.storecommon.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneId;

/**
 * Convert utc datetime from db timestamp.
 */
@MappedTypes(OffsetDateTime.class)
public class OffsetDatetimeTypeHandler extends BaseTypeHandler<OffsetDateTime> {

    /** UTC Zone id. */
    private static final ZoneId UTC_ZONE_ID = ZoneId.of("UTC");

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int columnIndex,
            OffsetDateTime offsetDateTime, JdbcType jdbcType) throws SQLException {
        preparedStatement.setTimestamp(columnIndex,
                Timestamp.valueOf(offsetDateTime.toLocalDateTime()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OffsetDateTime getNullableResult(ResultSet resultSet, String columnName)
            throws SQLException {
        Timestamp timestamp = resultSet.getTimestamp(columnName);
        if (timestamp == null) {
            return null;
        }
        return getUtcOffsetDateTime(timestamp);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OffsetDateTime getNullableResult(ResultSet resultSet, int columnIndex)
            throws SQLException {
        Timestamp timestamp = resultSet.getTimestamp(columnIndex);
        if (timestamp == null) {
            return null;
        }
        return getUtcOffsetDateTime(timestamp);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OffsetDateTime getNullableResult(CallableStatement callableStatement, int columnIndex)
            throws SQLException {
        Timestamp timestamp = callableStatement.getTimestamp(columnIndex);

        return getUtcOffsetDateTime(timestamp);
    }

    /**
     * Convert to utc datetime.
     * 
     * @param timestamp Timestamp.
     * @return Utc datetime.
     */
    private OffsetDateTime getUtcOffsetDateTime(Timestamp timestamp) {
        return OffsetDateTime.ofInstant(timestamp.toInstant(), UTC_ZONE_ID);
    }
}
