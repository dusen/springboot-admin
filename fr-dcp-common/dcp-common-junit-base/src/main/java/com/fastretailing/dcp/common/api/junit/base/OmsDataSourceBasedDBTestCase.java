package com.fastretailing.dcp.common.api.junit.base;

import com.fastretailing.dcp.common.api.junit.extend.OmsXlsDataSet;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.ext.mssql.InsertIdentityOperation;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.operation.TransactionOperation;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Slf4j
public abstract class OmsDataSourceBasedDBTestCase extends OmsAssertorBase {

    /**
     * Clean table's data SQL.<br>
     */
    private static final String DELETE_ALL_TABLE_DATA_SQL
            = "META-INF/junit/base/DatabaseInitialization.sql";

    /**
     * SQL file's encoding.<br>
     */
    private static final String SQL_FILE_ENCODING = "UTF-8";

    /**
     * Data file path.<br>
     */
    private String excelFilePath;

    /**
     * DataSource.<br>
     */
    @Autowired(required = false)
    private DataSource dataSource;

    /**
     * truncated sql.<br>
     */
    private String truncateSql;

    /**
     * Initialized flag.<br>
     */
    private static boolean initialized = false;

    /**
     * Sometimes several tests need to share computationally expensive setup.<br>
     */
    @BeforeClass
    public static void beforeClass() {
        // Do Nothing.
    }

    /**
     * If you allocate expensive external resources
     * in a @BeforeClass method you need to release them
     * after all the tests in the class have run.<br>
     */
    @AfterClass
    public static void afterClass() {
        // Do Nothing.
    }

    /**
     * Initialize test env.<br>
     * @throws Exception Exception
     */
    @Before
    @Override
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        if (!initialized) {
            this.truncateSql = this.genericTruncateSql();
        }
        this.cleanDataBaseAllData();
        initialized = true;
    }

    /**
     * Clean up test env.<br>
     * @throws Exception Exception
     */
    @After
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Get DataSource.<br>
     * @return DataSource.<br>
     */
    @Override
    public DataSource getDataSource() {
        return this.dataSource;
    }

    /**
     * Returns the test dataset.
     */
    @Override
    public IDataSet getDataSet() throws Exception {
        return StringUtils.isNotBlank(this.excelFilePath)
            ? this.buildDataSet(this.excelFilePath) : null;
    }

    /**
     * Designed to be overridden by subclasses in order to
     * set additional configuration parameters for the Connection.<br>
     * @param config configuration
     */
    @Override
    public void setUpDatabaseConfig(DatabaseConfig config) {
        config.setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS, true);
    }

    /**
     * Return the database operation executed in test setup.
     * @return database operation
     * @throws Exception Exception
     */
    @Override
    public DatabaseOperation getSetUpOperation() throws Exception {
        return InsertIdentityOperation.CLEAN_INSERT;
    }

    /**
     * Return the database operation executed in test cleanup.
     * @return database operation
     * @throws Exception Exception
     */
    @Override
    public DatabaseOperation getTearDownOperation() throws Exception {
        return TransactionOperation.TRANSACTION(DatabaseOperation.DELETE_ALL);
    }

    /**
     * Using excel file.<br>
     * @param excelFilePath excel file's path
     * @return OmsWebDataSourceTestBase
     */
    public OmsDataSourceBasedDBTestCase using(String excelFilePath) {
        this.excelFilePath = excelFilePath;
        return this;
    }

    /**
     * Push data into Database.<br>
     * @throws Exception Exception
     */
    public void push() throws Exception {
        super.setUp();
    }

    /**
     * Create a IDataSet's instance.<br>
     * @param filePath excel file's path
     * @return IDataSet's instance.
     * @throws Exception Exception
     */
    protected IDataSet buildDataSet(String filePath) throws Exception {
        ReplacementDataSet dataSet
                = new ReplacementDataSet(new OmsXlsDataSet(ResourceUtils.getFile(filePath)));
        dataSet.addReplacementObject("[null]", null);
        dataSet.addReplacementObject("['']", "");
        return dataSet;
    }

    /**
     * Init Database, clean all record.<br>
     */
    public void cleanDataBaseAllData() {
        if (StringUtils.isNotBlank(this.truncateSql)) {
            new JdbcTemplate(dataSource).execute(this.truncateSql);
        }
    }

    /**
     * Generic truncated sql.<br>
     *
     * @return truncated sql
     */
    public String genericTruncateSql() {
        String sql = StringUtils.EMPTY;
        try {
            List<String> sqlList = new ArrayList<>(
                    IOUtils.readLines(
                            this.getClass()
                                .getClassLoader()
                                .getResourceAsStream(DELETE_ALL_TABLE_DATA_SQL),
                            SQL_FILE_ENCODING
                    )
            );
            sql = sqlList.stream().reduce(StringUtils.EMPTY, String::concat);
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
        return sql;
    }

}
