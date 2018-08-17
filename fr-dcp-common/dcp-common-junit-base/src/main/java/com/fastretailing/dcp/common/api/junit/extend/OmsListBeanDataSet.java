/**
 * @(#)OmsListBeanDataSet.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.junit.extend;

import org.dbunit.dataset.AbstractDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.DefaultTableIterator;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableIterator;
import org.dbunit.dataset.OrderedTableNameMap;

import java.util.List;

/**
 * OmsListBeanDataSet.<br>
 * @author Fast Retailing
 * @version $Revision$
 */
public class OmsListBeanDataSet extends AbstractDataSet {

    private final OrderedTableNameMap tables;

    public <T> OmsListBeanDataSet(
            List<T> listBean,
            Class<T> clazz,
            String tableName
    ) throws Exception {
        this.tables = super.createTableNameMap();
        this.tables.add(tableName, new OmsListBeanTable(tableName, listBean, clazz));
    }

    @Override
    protected ITableIterator createIterator(boolean reversed) throws DataSetException {
        ITable[] tables = (ITable[]) this.tables.orderedValues().toArray(new ITable[0]);
        return new DefaultTableIterator(tables, reversed);
    }
}
