/**
 * @(#)OmsXlsTable.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.junit.extend;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.dbunit.dataset.AbstractTable;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.DefaultTableMetaData;
import org.dbunit.dataset.ITableMetaData;
import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.DataTypeException;
import org.dbunit.dataset.excel.XlsDataSetWriter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

/**
 * OmsXlsTable.<br>
 * @author Fast Retailing
 * @version $Revision$
 */
@Slf4j
public class OmsXlsTable extends AbstractTable {

    private final ITableMetaData tableMetaData;
    private final Sheet sheet;
    private final DecimalFormatSymbols symbols = new DecimalFormatSymbols();

    /**
     * Create OmsXlsTable.<br>
     * @param sheetName Sheet name
     * @param sheet sheet object
     */
    public OmsXlsTable(String sheetName, Sheet sheet) {
        if (sheet.getLastRowNum() >= 0 && sheet.getRow(0) != null) {
            tableMetaData = createMetaData(sheetName, sheet.getRow(0));
        } else {
            tableMetaData = new DefaultTableMetaData(sheetName, new Column[0]);
        }
        this.sheet = sheet;
        // Needed for later "BigDecimal"/"Number" conversion
        symbols.setDecimalSeparator('.');
    }

    private static ITableMetaData createMetaData(String tableName, Row sampleRow) {

        List<Column> columnList = new ArrayList<>();

        for (int i = 0;; i++) {
            Cell cell = sampleRow.getCell(i);
            if (cell == null) {
                break;
            }

            String columnName = cell.getRichStringCellValue().getString();
            if (columnName != null) {
                columnName = columnName.trim();
            }

            // Bugfix for issue ID 2818981 - if a cell has a formatting but no name also ignore it
            if (StringUtils.isEmpty(columnName)) {
                break;
            }

            Column column = new Column(columnName, DataType.UNKNOWN);
            columnList.add(column);
        }

        Column[] columns = columnList.toArray(new Column[0]);

        return new DefaultTableMetaData(tableName, columns);
    }

    public int getRowCount() {
        return sheet.getLastRowNum();
    }

    public ITableMetaData getTableMetaData() {
        return tableMetaData;
    }

    /**
     * Get the value.<br>
     * @param row Row index
     * @param column Column name
     * @return Value
     * @throws DataSetException DataSetException
     */
    public Object getValue(int row, String column) throws DataSetException {

        assertValidRowIndex(row);

        int columnIndex = getColumnIndex(column);
        Cell cell = sheet.getRow(row + 1).getCell(columnIndex);
        if (cell == null) {
            return null;
        }

        int type = cell.getCellType();
        switch (type) {
            case Cell.CELL_TYPE_NUMERIC:
                CellStyle style = cell.getCellStyle();
                if (DateUtil.isCellDateFormatted(cell)) {
                    return getDateValue(cell);
                } else if (XlsDataSetWriter.DATE_FORMAT_AS_NUMBER_DBUNIT
                        .equals(style.getDataFormatString())) {
                    return getDateValueFromJavaNumber(cell);
                } else {
                    return getNumericValue(cell);
                }
            case Cell.CELL_TYPE_STRING:
                return cell.getRichStringCellValue().getString();
            case Cell.CELL_TYPE_FORMULA:
                throw new DataTypeException(
                    "Formula not supported at row=" + row + ", column=" + column);
            case Cell.CELL_TYPE_BLANK:
                return null;
            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue() ? Boolean.TRUE : Boolean.FALSE;
            case Cell.CELL_TYPE_ERROR:
                throw new DataTypeException("Error at row=" + row + ", column=" + column);
            default:
                throw new DataTypeException(
                    "Unsupported type at row=" + row + ", column=" + column);
        }
    }

    private Object getDateValueFromJavaNumber(Cell cell) {
        double numericValue = cell.getNumericCellValue();
        BigDecimal numericValueBd = new BigDecimal(String.valueOf(numericValue));
        numericValueBd = stripTrailingZeros(numericValueBd);
        return numericValueBd.longValue();
    }

    private Object getDateValue(Cell cell) {
        return DateUtil.getJavaDate(cell.getNumericCellValue()).getTime();
    }

    /**
     * Removes all trailing zeros from the end of
     * the given BigDecimal value up to the decimal point.
     * @param value The value to be stripped
     * @return The value without trailing zeros
     */
    private BigDecimal stripTrailingZeros(BigDecimal value) {
        if (value.scale() <= 0) {
            return value;
        }

        String valueAsString = String.valueOf(value);
        int idx = valueAsString.indexOf('.');
        if (idx == -1) {
            return value;
        }

        for (int i = valueAsString.length() - 1; i > idx; i--) {
            if (valueAsString.charAt(i) == '0') {
                valueAsString = valueAsString.substring(0, i);
            } else if (valueAsString.charAt(i) == '.') {
                valueAsString = valueAsString.substring(0, i);
                // Stop when decimal point is reached
                break;
            } else {
                break;
            }
        }

        return new BigDecimal(valueAsString);
    }

    private BigDecimal getNumericValue(Cell cell) {

        String formatString = cell.getCellStyle().getDataFormatString();
        String resultString = null;
        double cellValue = cell.getNumericCellValue();



        if (StringUtils.isNotBlank(formatString)
                && !StringUtils.equalsAny(formatString, "General", "@")) {
            log.debug("formatString={}", formatString);
            resultString = new DecimalFormat(formatString, symbols).format(cellValue);
        }

        BigDecimal result;
        if (resultString != null) {
            try {
                result = new BigDecimal(resultString);
            } catch (NumberFormatException e) {
                log.debug(
                        "Exception occurred while trying create a BigDecimal. value={}",
                        resultString
                );
                result = toBigDecimal(cellValue);
            }
        } else {
            result = toBigDecimal(cellValue);
        }

        return result;

    }

    private BigDecimal toBigDecimal(double cellValue) {
        String resultString = String.valueOf(cellValue);
        if (resultString.endsWith(".0")) {
            resultString = resultString.substring(0, resultString.length() - 2);
        }
        return new BigDecimal(resultString);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName()).append("[");
        sb.append("tableMetaData=");
        sb.append(this.tableMetaData == null ? "null" : this.tableMetaData.toString());
        sb.append(", sheet=").append(this.sheet == null ? "null" : "" + this.sheet);
        sb.append(", symbols=").append(this.symbols);
        sb.append("]");
        return sb.toString();
    }

}
