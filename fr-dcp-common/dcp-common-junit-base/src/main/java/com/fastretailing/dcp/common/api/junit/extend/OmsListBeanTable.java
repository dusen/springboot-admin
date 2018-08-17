/**
 * @(#)OmsListBeanTable.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.junit.extend;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.dbunit.dataset.AbstractTable;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.DefaultTableMetaData;
import org.dbunit.dataset.ITableMetaData;
import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.DataTypeException;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * OmsListBeanTable.<br>
 * @author Fast Retailing
 * @version $Revision$
 */
public class OmsListBeanTable extends AbstractTable {

    private final ITableMetaData tableMetaData;
    private final List listBean;
    private final Class targetClass;

    /**
     * Create OmsListBeanTable.<br>
     * @param tableName Table name
     * @param list List
     * @param clazz Class
     * @param <T> Type
     * @throws Exception Exception
     */
    public <T> OmsListBeanTable(String tableName, List<T> list, Class<T> clazz) throws Exception {
        this.tableMetaData = createMetaData(tableName, clazz);
        this.listBean = list;
        this.targetClass = clazz;
    }

    private static <T> ITableMetaData createMetaData(
            String tableName,
            Class<T> clazz
    ) throws Exception {

        List<Column> columnList = new ArrayList<>();

        for (Field field : OmsListBeanTable.getAllFields(clazz)) {
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
            columnList.add(new Column(pd.getName(), getDataType(pd)));
        }

        return new DefaultTableMetaData(tableName, columnList.toArray(new Column[0]));
    }

    private static DataType getDataType(PropertyDescriptor pd) {

        switch (pd.getPropertyType().getSimpleName()) {
            case "boolean":
            case "Boolean":
                return DataType.BOOLEAN;
            case "char":
            case "Character":
                return DataType.CHAR;
            case "byte":
            case "Byte":
                return DataType.TINYINT;
            case "short":
            case "Short":
                return DataType.SMALLINT;
            case "int":
            case "Integer":
                return DataType.INTEGER;
            case "long":
            case "Long":
                return DataType.BIGINT;
            case "float":
            case "Float":
                return DataType.FLOAT;
            case "double":
            case "Double":
                return DataType.DOUBLE;
            case "String":
                return DataType.VARCHAR;
            case "BigDecimal":
                return DataType.DECIMAL;
            case "LocalDate":
                return DataType.DATE;
            case "LocalTime":
                return DataType.TIME;
            case "LocalDateTime":
                return DataType.UNKNOWN;
            default:
                return DataType.UNKNOWN;
        }
    }


    @Override
    public ITableMetaData getTableMetaData() {
        return this.tableMetaData;
    }

    @Override
    public int getRowCount() {
        return listBean.size();
    }

    @Override
    public Object getValue(int row, String columnName) throws DataSetException {

        super.assertValidRowIndex(row);

        try {
            PropertyDescriptor pd = new PropertyDescriptor(columnName, this.targetClass);
            Object result = pd.getReadMethod().invoke(this.listBean.get(row));

            if (result instanceof LocalDateTime) {
                return ((LocalDateTime) result)
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
            }

            return result;

        } catch (Throwable e) {
            throw new DataTypeException(
                    "Unsupported type at row=" + row
                        + ", column=" + columnName
                        + ", error=" + e.getMessage());
        }

    }

    private static <T> List<Field> getAllFields(Class<T> clazz) {

        List<Field> parentFields = ClassUtils.getAllSuperclasses(clazz)
                .stream()
                .filter(c -> !StringUtils.containsIgnoreCase(c.getName(), "java.lang.Object"))
                .map(Class::getDeclaredFields)
                .collect(Collectors.toList())
                .stream()
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());

        List<Field> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        fields.addAll(parentFields);

        fields = fields
                .stream()
                .filter(f ->
                        !StringUtils.equalsAny(f.getName(),"serialVersionUID", "$jacocoData"))
                .collect(Collectors.toList());

        return fields;
    }
}
