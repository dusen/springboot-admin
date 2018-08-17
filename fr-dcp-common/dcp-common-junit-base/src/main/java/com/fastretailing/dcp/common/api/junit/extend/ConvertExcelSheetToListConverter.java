/**
 * @(#)ConvertExcelSheetToListConverter.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.junit.extend;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel's data to List converter.<br>
 * @author Fast Retailing
 * @version $Revision$
 */
public class ConvertExcelSheetToListConverter {

    /**
     * Convert excel to list.<br>
     * @param file File Instance
     * @param sheetName Sheet Name
     * @param clazz Class
     * @param <T> Type
     * @return List
     * @throws Exception Exception
     */
    public static <T> List<T> convertExcelSheetToList(
            File file,
            String sheetName,
            Class<T> clazz
    ) throws Exception {
        List<T> targetBeanList = new ArrayList<>();
        try (Workbook wb = WorkbookFactory.create(new FileInputStream(file))) {
            for (int sheetNum = 0; sheetNum < wb.getNumberOfSheets(); sheetNum++) {
                if (sheetName.equalsIgnoreCase(wb.getSheetName(sheetNum))) {
                    Sheet sheet = wb.getSheetAt(sheetNum);
                    List<String> fieldsStr = getFieldList(sheet.getRow(0));
                    for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                        Row row = sheet.getRow(rowNum);
                        if (row != null) {
                            T targetBean = addDataToTargetBean(clazz, fieldsStr, row);
                            targetBeanList.add(targetBean);
                        } else {
                            throw new Exception(
                                    "There is an empty row in Sheet ["
                                        + sheetName + "] at Line [" + rowNum + "]."
                            );
                        }
                    }
                    break;
                }
            }
        }
        return targetBeanList;
    }

    private static List<String> getFieldList(Row firstRow) throws Exception {

        if (firstRow == null) {
            throw new Exception("The first row of Sheet Must not be empty.");
        }

        List<String> fieldList = new ArrayList<>();
        for (Cell cell : firstRow) {
            String fieldValue = cell.getRichStringCellValue().getString();
            if (StringUtils.isEmpty(fieldValue)) {
                throw new Exception("The first row of Sheet Must not have empty cell value.");
            }
            fieldList.add(fieldValue);
        }
        return fieldList;
    }

    private static <T> T addDataToTargetBean(
            Class<T> clazz,
            List<String> fieldList,
            Row row
    ) throws Exception {

        T targetBean = clazz.newInstance();
        Field[] fields = clazz.getDeclaredFields();

        for (Field f : fields) {

            if ("serialVersionUID".equals(f.getName()) || "$jacocoData".equals(f.getName())) {
                continue;
            }

            PropertyDescriptor pd = new PropertyDescriptor(f.getName(), clazz);
            Method method = pd.getWriteMethod();
            for (int cellNum = 0; cellNum < fieldList.size(); cellNum++) {
                if (pd.getName().equals(fieldList.get(cellNum))) {
                    method.invoke(
                            targetBean,
                            convertDataType(
                                    row.getCell(cellNum),
                                    pd.getPropertyType().getSimpleName()
                            )
                    );
                    break;
                }
            }
        }

        return targetBean;
    }

    private static Object convertDataType(Cell cell, String typeName) throws Exception {

        if (cell == null) {
            return null;
        }

        int type = cell.getCellType();

        String value = null;
        switch (type) {
            case Cell.CELL_TYPE_NUMERIC:
                value = String.valueOf(cell.getNumericCellValue());
                break;

            case Cell.CELL_TYPE_STRING:
                value = cell.getRichStringCellValue().getString();
                break;

            case Cell.CELL_TYPE_FORMULA:
                throw new Exception("CELL_TYPE_FORMULA");

            case Cell.CELL_TYPE_BLANK:
                break;

            case Cell.CELL_TYPE_BOOLEAN:
                value = (cell.getBooleanCellValue() ? Boolean.TRUE : Boolean.FALSE).toString();
                break;

            case Cell.CELL_TYPE_ERROR:
                throw new Exception("CELL_TYPE_ERROR");

            default:
                throw new Exception("Unsupported type");
        }

        if ("[null]".equals(value) || value == null) {
            return null;
        }

        Object object;

        switch (typeName) {
            case "boolean":
            case "Boolean":
                object = ("true".equalsIgnoreCase(value) || "1".equals(value));
                break;

            case "char":
            case "Character":
                if ("['']".equals(value)) {
                    object = '\u0000';
                } else if (value.length() != 1) {
                    throw new Exception("Convert String to Char ERROR.");
                } else {
                    object = value.charAt(0);
                }
                break;

            case "byte":
            case "Byte":
                object = new BigDecimal(value).byteValue();
                break;

            case "short":
            case "Short":
                object = new BigDecimal(value).shortValue();
                break;

            case "int":
            case "Integer":
                object = new BigDecimal(value).intValue();
                break;

            case "long":
            case "Long":
                object = new BigDecimal(value).longValue();
                break;

            case "float":
            case "Float":
                object = new BigDecimal(value).floatValue();
                break;

            case "double":
            case "Double":
                object = new BigDecimal(value).doubleValue();
                break;

            case "String":
                if ("['']".equals(value)) {
                    object = "";
                } else {
                    object = value;
                }
                break;

            case "BigDecimal":
                object = new BigDecimal(value);
                break;

            case "LocalDate":
                object = LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                break;

            case "LocalTime":
                object = LocalTime.parse(value, DateTimeFormatter.ofPattern("HH:mm"));
                break;

            case "LocalDateTime":
                object = LocalDateTime.parse(
                        value,
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
                );
                break;
            default:
                object = value;
        }

        return object;
    }
}
