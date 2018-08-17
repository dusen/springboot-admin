/**
 * @(#)OmsAssertorBase.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.junit.base;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fastretailing.dcp.common.api.junit.extend.OmsListBeanDataSet;
import com.fastretailing.dcp.common.api.junit.extend.OmsXlsDataSet;
import com.fastretailing.dcp.common.timezone.DateTimeDeserializer;
import com.fastretailing.dcp.common.timezone.DateTimeSerializer;
import com.fastretailing.dcp.common.timezone.TimeDeserializer;
import com.fastretailing.dcp.common.timezone.TimeSerializer;
import lombok.Getter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dbunit.Assertion;
import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.FilteredDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Excel assert base.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public abstract class OmsAssertorBase extends DataSourceBasedDBTestCase {

    /**
     * Jackson's ObjectMapper instance.<br>
     */
    @Getter
    @Autowired(required = false)
    protected ObjectMapper jacksonMapper;

    private OmsAssertorBase init() {

        if (jacksonMapper == null) {

            jacksonMapper = new ObjectMapper();
            SimpleModule localDateTimeModule = new SimpleModule();
            localDateTimeModule.addDeserializer(LocalDateTime.class, new DateTimeDeserializer());
            localDateTimeModule.addSerializer(LocalDateTime.class, new DateTimeSerializer());

            SimpleModule localTimeModule = new SimpleModule();
            localTimeModule.addDeserializer(LocalTime.class, new TimeDeserializer());
            localTimeModule.addSerializer(LocalTime.class, new TimeSerializer());

            jacksonMapper.registerModule(localDateTimeModule);
            jacksonMapper.registerModule(localTimeModule);

        }

        return this;

    }

    /**
     * Convert JSON to List Bean.<br>
     * @param json JSON string
     * @param listClazz List's type
     * @param elementClazz List's element's type
     * @return List Bean
     * @throws IOException IOException
     */
    protected <T extends List, E> List<E> readJsonToList(
        String json,
        Class<T> listClazz,
        Class<E> elementClazz
    ) throws IOException {
        return this.init().jacksonMapper.readValue(
            json,
            this.getGenericType(jacksonMapper, listClazz, elementClazz)
        );
    }

    /**
     * Convert JSON to Bean.<br>
     * @param json JSON string
     * @param clazz Bean's type
     * @return Bean
     * @throws IOException IOException
     */
    protected <T> T readJsonToBean(String json, Class<T> clazz) throws IOException {
        return this.init().jacksonMapper.readValue(json, clazz);
    }


    /**
     * Get generic collection's actual type.<br>
     *
     * @param mapper ObjectMapper's instance
     * @param collectionClass collection's type
     * @param elementClasses collection's generic's type
     * @return actual type
     */
    private JavaType getGenericType(
        ObjectMapper mapper,
        Class<?> collectionClass,
        Class<?>... elementClasses
    ) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    /**
     * Asserts that two List Bean are equals.<br>
     * @param expectedList expected List Bean
     * @param actualList actual List Bean
     * @param clazz Class
     * @param <T> generic type
     * @throws Exception Exception
     */
    public static <T> void assertListBean(
            List<T> expectedList,
            List<T> actualList,
            Class<T> clazz
    ) throws Exception {
        MatcherAssert.assertThat(
                CollectionUtils.size(actualList),
                Matchers.is(CollectionUtils.size(expectedList))
        );
        for (Field field : clazz.getDeclaredFields()) {
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
            for (int idx = 0; idx < actualList.size(); idx++) {
                MatcherAssert.assertThat(
                        pd.getReadMethod().invoke(actualList.get(idx)),
                        Matchers.is(pd.getReadMethod().invoke(expectedList.get(idx)))
                );
            }
        }
    }

    /**
     * Asserts that List Bean and excel's data are equals.<br>
     * @param expectedExcelPath expected excel's path
     * @param sheetName sheet name
     * @param actualList actual List Bean
     * @param clazz Class
     * @param <T> generic type
     * @throws Exception Exception
     */
    public <T> void assertListBeanEqualsExcel(
            String expectedExcelPath,
            String sheetName,
            List<T> actualList,
            Class<T> clazz
    ) throws Exception {

        IDataSet expected = this.buildDataSet(expectedExcelPath);
        if (sheetName != null) {
            expected = new FilteredDataSet(new String[]{sheetName}, expected);
        }

        Assert.assertThat(expected.getTableNames().length, org.hamcrest.core.Is.is(1));
        Assertion.assertEquals(
                expected,
                new OmsListBeanDataSet(actualList, clazz, expected.getTableNames()[0])
        );
    }

    /**
     * Asserts that List Bean and excel's data are equals.<br>
     * @param expectedExcelPath expected excel's path
     * @param actualList actual List Bean
     * @param clazz Class
     * @param <T> generic type
     * @throws Exception Exception
     */
    public <T> void assertListBeanEqualsExcel(
            String expectedExcelPath,
            List<T> actualList,
            Class<T> clazz
    ) throws Exception {
        this.assertListBeanEqualsExcel(expectedExcelPath, null, actualList, clazz);
    }



    /**
     * Asserts that Bean and excel's data are equals.<br>
     * @param expectedExcelPath expected excel's path
     * @param actualBean actual Bean
     * @param clazz Class
     * @param <T> generic type
     * @throws Exception Exception
     */
    public <T> void assertBeanEqualsExcel(
            String expectedExcelPath,
            T actualBean,
            Class<T> clazz
    ) throws Exception {
        this.assertListBeanEqualsExcel(expectedExcelPath, Arrays.asList(actualBean), clazz);
    }

    /**
     * Asserts that Bean and excel's data are equals.<br>
     * @param expectedExcelPath expected excel's path
     * @param sheetName sheet name
     * @param actualBean actual Bean
     * @param clazz Class
     * @param <T> generic type
     * @throws Exception Exception
     */
    public <T> void assertBeanEqualsExcel(
            String expectedExcelPath,
            String sheetName,
            T actualBean,
            Class<T> clazz
    ) throws Exception {
        this.assertListBeanEqualsExcel(
                expectedExcelPath,
                sheetName,
                Arrays.asList(actualBean),
                clazz
        );
    }



    /**
     * Asserts that List Bean and excel's data are equals.<br>
     * Ignore columns support.
     *
     * @param expectedExcelPath expected excel's path
     * @param actualList actual List Bean
     * @param clazz class
     * @param ignoreColumns ignore columns
     * @param <T> generic type
     * @throws Exception Exception
     */
    public <T> void assertListBeanEqualsExcelWithIgnoreCols(
            String expectedExcelPath,
            List<T> actualList,
            Class<T> clazz,
            String... ignoreColumns
    ) throws Exception {
        this.assertListBeanEqualsExcelWithIgnoreCols(
                expectedExcelPath,
                null,
                actualList,
                clazz,
                ignoreColumns
        );
    }


    /**
     * Asserts that List Bean and excel's data are equals.<br>
     * Ignore columns support.
     *
     * @param expectedExcelPath expected excel's path
     * @param sheetName sheet name
     * @param actualList actual List Bean
     * @param clazz class
     * @param ignoreColumns ignore columns
     * @param <T> generic type
     * @throws Exception Exception
     */
    public <T> void assertListBeanEqualsExcelWithIgnoreCols(
            String expectedExcelPath,
            String sheetName,
            List<T> actualList,
            Class<T> clazz,
            String... ignoreColumns
    ) throws Exception {

        IDataSet expected = this.buildDataSet(expectedExcelPath);
        if (StringUtils.isNotBlank(sheetName)) {
            expected = new FilteredDataSet(new String[] {sheetName}, expected);
        }

        Assert.assertThat(expected.getTableNames().length, org.hamcrest.core.Is.is(1));
        Assertion.assertEqualsIgnoreCols(
                expected,
                new OmsListBeanDataSet(
                    actualList, clazz, expected.getTableNames()[0]
                ),
                expected.getTableNames()[0],
                ignoreColumns
        );
    }

    /**
     * Asserts that Bean and excel's data are equals.<br>
     * Ignore columns support.
     *
     * @param expectedExcelPath expected excel's path
     * @param actualBean actual Bean
     * @param clazz class
     * @param ignoreColumns ignore columns
     * @param <T> generic type
     * @throws Exception Exception
     */
    public <T> void assertBeanEqualsExcelWithIgnoreCols(
            String expectedExcelPath,
            T actualBean,
            Class<T> clazz,
            String... ignoreColumns
    ) throws Exception {
        this.assertListBeanEqualsExcelWithIgnoreCols(
                expectedExcelPath,
                Arrays.asList(actualBean),
                clazz,
                ignoreColumns
        );
    }

    /**
     * Asserts that Bean and excel's data are equals.<br>
     * Ignore columns support.
     *
     * @param expectedExcelPath expected excel's path
     * @param sheetName sheet name
     * @param actualBean actual Bean
     * @param clazz class
     * @param ignoreColumns ignore columns
     * @param <T> generic type
     * @throws Exception Exception
     */
    public <T> void assertBeanEqualsExcelWithIgnoreCols(
            String expectedExcelPath,
            String sheetName,
            T actualBean,
            Class<T> clazz,
            String... ignoreColumns
    ) throws Exception {
        this.assertListBeanEqualsExcelWithIgnoreCols(
                expectedExcelPath,
                sheetName,
                Arrays.asList(actualBean),
                clazz,
                ignoreColumns
        );
    }

    /**
     * Asserts that database's data and excel's data are equals.<br>
     * @param expectedExcelPath expected excel file's path
     * @throws Exception Exception
     */
    public void assertDb(String expectedExcelPath) throws Exception {
        IDataSet expected = this.buildDataSet(expectedExcelPath);
        Assertion.assertEquals(
                expected,
                (new DatabaseConnection(getDataSource().getConnection()))
                        .createDataSet(expected.getTableNames()));
    }

    /**
     * Asserts that database's data and excel's data are equals.<br>
     * Ignore columns support.
     * @param expectedExcelPath expected excel file's path
     * @param ignoreColumns ignore columns
     * @throws Exception Exception
     */
    public void assertDb(
                String expectedExcelPath,
                Map<String,
                String[]> ignoreColumns
    ) throws Exception {
        IDataSet expected = this.buildDataSet(expectedExcelPath);
        IDataSet actual = (new DatabaseConnection(getDataSource().getConnection()))
                .createDataSet(expected.getTableNames());
        for (String tableName : expected.getTableNames()) {
            Assertion.assertEqualsIgnoreCols(
                    expected,
                    actual,
                    tableName,
                    ignoreColumns.get(tableName)
            );
        }
    }

    /**
     * Asserts that database's data and excel's data are equals.<br>
     * Ignore columns support.
     *
     * @param expectedExcelPath expected excel file's path
     * @param tableName table's name
     * @param ignoreColumns ignore columns
     * @throws Exception Exception
     */
    public void assertDb(
            String expectedExcelPath,
            String tableName,
            String... ignoreColumns
    ) throws Exception {
        IDataSet expected = this.buildDataSet(expectedExcelPath);
        IDataSet actual = (new DatabaseConnection(getDataSource().getConnection()))
                .createDataSet(expected.getTableNames());
        Assertion.assertEqualsIgnoreCols(expected, actual, tableName, ignoreColumns);
    }

    /**
     * Create a IDataSet's instance.<br>
     * @param filePath excel file's path
     * @return IDataSet's instance.
     * @throws Exception Exception
     */
    protected abstract IDataSet buildDataSet(String filePath) throws Exception;

}
