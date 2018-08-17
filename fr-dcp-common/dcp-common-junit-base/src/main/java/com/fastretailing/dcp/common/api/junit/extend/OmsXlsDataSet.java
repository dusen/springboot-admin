/**
 * @(#)OmsXlsDataSet.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.junit.extend;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.dbunit.dataset.AbstractDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.DefaultTableIterator;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableIterator;
import org.dbunit.dataset.OrderedTableNameMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * OmsXlsDataSet.<br>
 * @author Fast Retailing
 * @version $Revision$
 */
public class OmsXlsDataSet extends AbstractDataSet {

    private static final String EXCEL_SHEET_ALIAS_FILE
            = "META-INF/junit/base/ExcelSheetNameAliasMapper.config";

    private final OrderedTableNameMap orderedTableNameMap = super.createTableNameMap();

    public OmsXlsDataSet(File file) throws Exception {
        this(new FileInputStream(file));
    }

    private OmsXlsDataSet(InputStream in) throws Exception {

        try (Workbook wb = WorkbookFactory.create(in)) {
            Map<String, String> aliasMapper = this.initExcelSheetNameAliasMapper();
            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                ITable table;
                if (aliasMapper.containsKey(wb.getSheetName(i))) {
                    table = new OmsXlsTable(aliasMapper.get(wb.getSheetName(i)), wb.getSheetAt(i));
                } else {
                    table = new OmsXlsTable(wb.getSheetName(i), wb.getSheetAt(i));
                }
                orderedTableNameMap.add(table.getTableMetaData().getTableName(), table);
            }
        }

    }

    @Override
    protected ITableIterator createIterator(boolean reversed) throws DataSetException {
        ITable[] tables = (ITable[]) orderedTableNameMap.orderedValues().toArray(new ITable[0]);
        return new DefaultTableIterator(tables, reversed);
    }

    private Map<String, String> initExcelSheetNameAliasMapper() throws IOException {

        Map<String, String> mapper = new HashMap<>();
        List<String> lines = IOUtils.readLines(
                this.getClass().getClassLoader()
                    .getResourceAsStream(EXCEL_SHEET_ALIAS_FILE), "UTF-8");
        if (CollectionUtils.isEmpty(lines)) {
            return mapper;
        }

        for (String line : lines) {
            if (StringUtils.startsWith(line, "#") || !StringUtils.contains(line, "=")) {
                continue;
            }
            String[] mappers = StringUtils.splitByWholeSeparatorPreserveAllTokens(line, "=");
            if (ArrayUtils.isNotEmpty(mappers)) {
                String excelSheetName = StringUtils.trim(mappers[0]);
                String dbTableName = StringUtils.trim(mappers[1]);
                if (StringUtils.isNotBlank(excelSheetName) && StringUtils.isNotBlank(dbTableName)) {
                    mapper.put(excelSheetName, dbTableName);
                }
            }
        }
        return mapper;
    }
}
