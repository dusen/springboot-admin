/**
 * @(#)StockStatusReferTest.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.stockstatusrefer.templates.testcase;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import com.fastretailing.dcp.sales.stockstatusrefer.templates.constants.Constants;
import com.fastretailing.dcp.sales.stockstatusrefer.templates.elements.StockStatusReferElements;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;

/**
 * Test class of sales payoff unmatch list screen screen.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners(mergeMode = MergeMode.MERGE_WITH_DEFAULTS,
        value = {DependencyInjectionTestExecutionListener.class,
                DirtiesContextTestExecutionListener.class,
                TransactionDbUnitTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = ReplacementDataSetLoader.class)
// @Sql(scripts = "/junit_create_table_sales.sql", executionPhase =
// ExecutionPhase.BEFORE_TEST_METHOD)
// @Sql(scripts = "/junit_drop_table_sales.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public abstract class StockStatusReferTest {

    private static final String DISPLAY_STRING_STATUS_IS_CORRECTING = "補正中";

    /** Driver for performing browser operation. */
    protected WebDriver webDriver;

    /** Wait processing of webdriver. */
    protected WebDriverWait webDriverWait;

    /** Screen element (tag) information. */
    protected StockStatusReferElements element;

    /** The environment in current application. */
    @Autowired
    protected Environment environment;

    /**
     * Initialization processing. Configure the browser to be tested. Set Element information of the
     * screen to be tested to web driver.
     *
     * @throws Exception (It does not occur as expected value).
     */
    @Before
    public abstract void setUp() throws Exception;

    /**
     * Test end processing. Close the displayed browser.
     *
     * @throws Exception (It does not occur as expected value).
     */
    @After
    public void tearDown() throws Exception {
        webDriver.quit();
    }

    // protected WebElement getSelectBoxOption(WebElement selectElement, int index) {
    // return selectElement.findElement(By.xpath(".//div[contains(@class, 'select-elem')]/ul/li[" +
    // index + "]"));
    // }

    @Test
    // @DatabaseSetup("SalesPayoffUnmatchListTest.xml")
    public void test() throws Exception {

        // Confirm initial display.
        assertInitialDisplay();

        // Confirm whether the transition destination is the item list screen.
        assertUrl(Constants.STOCK_STATUS_REFER_URL);

        // TODO 試しに実装
        assertThat(element.stockStatusList.findElements(By.xpath("./tr")).size(), is(5));
        assertSearchResult(1, "113", "1234-567", "カラースキニーフィットアンクル\nジーンズ", "51\nGREEN", "025", "000",
                "34", "011", "1", "-1", "0", "0", "0", "3");

        selectRadioButton(element.gDepartmentCodeRadioButton);

        selectOtionOfSelectBox(element.stockStatusSelectBox, 2);
        selectAllOfSelectBoxWitChild(element.departmentNameSelectBox);
        selectParentOfSelectBoxWitChild(element.departmentNameSelectBox, 2);
        selectChildOfSelectBoxWitChild(element.departmentNameSelectBox, 2, 2);

        Thread.sleep(3000);
    }

    /**
     * Check the initial display screen.
     */
    private void assertInitialDisplay() {

        // check radio button.
        assertThat(element.itemCodeRadioBUtton.getAttribute("checked"), notNullValue());
        assertThat(element.gDepartmentCodeRadioButton.getAttribute("checked"), nullValue());
        assertThat(element.pluCodeRaioButton.getAttribute("checked"), nullValue());

        // check item code search conditions.
        assertInitialDisplayForItemCodeConditions();

        // click G department code radio button
        selectRadioButton(element.gDepartmentCodeRadioButton);

        // check G department code search conditions.
        assertInitialDisplayForGDepartmentCodeConditions();

        // click PLU code radio button
        selectRadioButton(element.pluCodeRaioButton);

        // checkPLU code search conditions.
        assertInitialDisplayForPluCodeConditions();
    }

    private void assertInitialDisplayForItemCodeConditions() {

        // check element is displayed.
        assertThat(element.stockStatusSelectBox.isDisplayed(), is(true));
        assertThat(element.itemCodeInput.isDisplayed(), is(true));
        assertThat(element.itemColerSelectBox.isDisplayed(), is(true));
        assertThat(element.itemSizeSelectBox.isDisplayed(), is(true));
        assertThat(element.patternLengthSelectBox.isDisplayed(), is(true));
        assertThat(element.departmentNameSelectBox.isDisplayed(), is(false));
        assertThat(element.pluCodeInput.isDisplayed(), is(false));
        assertThat(element.clearButton.isDisplayed(), is(true));
        assertThat(element.displayButton.isDisplayed(), is(true));

        // check element value.
        assertThat(element.stockStatusInput.getAttribute("value"), is(""));
        assertThat(element.itemCodeInput.getAttribute("value"), is(""));
        assertThat(element.itemColerInput.getAttribute("value"), is(""));
        assertThat(element.itemSizeInput.getAttribute("value"), is(""));
        assertThat(element.patternLengthInput.getAttribute("value"), is(""));

        // check stock status select box options.
        assertSelectBoxOption(element.stockStatusSelectBox, "現在庫数", "廃棄予定在庫", "客注在庫", "調査中在庫",
                "店舗ストック在庫");

        // check item color select box options.
        assertSelectBoxOption(element.itemColerSelectBox, "ダミー 赤", "ダミー青", "ダミー黄", "ダミー緑", "ダミー白");

        // check item size select box options.
        assertSelectBoxOption(element.itemSizeSelectBox, "ダミー SSサイズ", "ダミー Sサイズ", "ダミー Mサイズ",
                "ダミー Lサイズ", "ダミー LLサイズ");

        // check pattern length select box options.
        assertSelectBoxOption(element.patternLengthSelectBox, "ダミー柄1", "ダミー柄2", "ダミー柄3", "ダミー柄4",
                "ダミー柄5");
    }

    private void assertInitialDisplayForPluCodeConditions() {

        // check element is displayed.
        assertThat(element.stockStatusSelectBox.isDisplayed(), is(true));
        assertThat(element.itemCodeInput.isDisplayed(), is(false));
        assertThat(element.itemColerSelectBox.isDisplayed(), is(false));
        assertThat(element.itemSizeSelectBox.isDisplayed(), is(false));
        assertThat(element.patternLengthSelectBox.isDisplayed(), is(false));
        assertThat(element.departmentNameSelectBox.isDisplayed(), is(false));
        assertThat(element.pluCodeInput.isDisplayed(), is(true));
        assertThat(element.clearButton.isDisplayed(), is(false));
        assertThat(element.displayButton.isDisplayed(), is(true));

        // check element value.
        assertThat(element.stockStatusInput.getAttribute("value"), is(""));
        assertThat(element.departmentNameInput.getAttribute("value"), is(""));

        // check stock status select box options.
        assertSelectBoxOption(element.stockStatusSelectBox, "現在庫数", "廃棄予定在庫", "客注在庫", "調査中在庫",
                "店舗ストック在庫");

        // check department name select box options.
        // TODO チェックする
    }

    private void assertInitialDisplayForGDepartmentCodeConditions() {

        // check element is displayed.
        assertThat(element.stockStatusSelectBox.isDisplayed(), is(true));
        assertThat(element.itemCodeInput.isDisplayed(), is(false));
        assertThat(element.itemColerSelectBox.isDisplayed(), is(false));
        assertThat(element.itemSizeSelectBox.isDisplayed(), is(false));
        assertThat(element.patternLengthSelectBox.isDisplayed(), is(false));
        assertThat(element.departmentNameSelectBox.isDisplayed(), is(true));
        assertThat(element.pluCodeInput.isDisplayed(), is(false));
        assertThat(element.clearButton.isDisplayed(), is(false));
        assertThat(element.displayButton.isDisplayed(), is(true));

        // check element value.
        assertThat(element.stockStatusInput.getAttribute("value"), is(""));
        assertThat(element.pluCodeInput.getAttribute("value"), is(""));

        // check stock status select box options.
        assertSelectBoxOption(element.stockStatusSelectBox, "現在庫数", "廃棄予定在庫", "客注在庫", "調査中在庫",
                "店舗ストック在庫");
    }

    /**
     * Check select box options.
     * 
     * @param selectElement select box
     * @param expectedOptionText expected text of select box option
     */
    private void assertSelectBoxOption(WebElement selectElement, String... expectedOptionText) {

        // Show select option..
        selectElement.click();
        webDriverWait.until(dirver -> {
            return selectElement.findElement(By.xpath(".//div[contains(@class, 'select-elem')]"))
                    .isDisplayed();
        });

        // Confirm result.
        AtomicInteger index = new AtomicInteger(1);
        Arrays.stream(expectedOptionText)
                .forEach(e -> assertThat(
                        getSelectBoxOption(selectElement, index.getAndIncrement()).getText(),
                        is(e)));

        // hide select option.
        selectElement.click();
        webDriverWait.until(dirver -> {
            return !selectElement.findElement(By.xpath(".//div[contains(@class, 'select-elem')]"))
                    .isDisplayed();
        });
    }

    private void assertSearchResult(int index, String expectedCategory, String expectedItemCode,
            String expectedItemName, String expectedItemColor, String expectedItemSize,
            String expectedPatternLengs, String expectedYearSeason,
            String expectedCostPriceSequence, String expectedAllStockQuantity,
            String expectedStockQuantity, String expectedWriteOffExpected,
            String expectedCustomerOrder, String expectedInvestigatingStock,
            String expectedStoreStock) {

        // check text.
        assertThat(element.stockStatusList.findElement(By.xpath("./tr[" + index + "]/td[1]"))
                .getText(), is(expectedCategory));
        assertThat(element.stockStatusList.findElement(By.xpath("./tr[" + index + "]/td[2]"))
                .getText(), is(expectedItemCode));
        assertThat(element.stockStatusList.findElement(By.xpath("./tr[" + index + "]/td[3]"))
                .getText(), is(expectedItemName));
        assertThat(element.stockStatusList.findElement(By.xpath("./tr[" + index + "]/td[4]"))
                .getText(), is(expectedItemColor));
        assertThat(element.stockStatusList.findElement(By.xpath("./tr[" + index + "]/td[5]"))
                .getText(), is(expectedItemSize));
        assertThat(element.stockStatusList.findElement(By.xpath("./tr[" + index + "]/td[6]"))
                .getText(), is(expectedPatternLengs));
        assertThat(element.stockStatusList.findElement(By.xpath("./tr[" + index + "]/td[7]"))
                .getText(), is(expectedYearSeason));
        assertThat(element.stockStatusList.findElement(By.xpath("./tr[" + index + "]/td[8]"))
                .getText(), is(expectedCostPriceSequence));
        assertThat(element.stockStatusList.findElement(By.xpath("./tr[" + index + "]/td[9]"))
                .getText(), is(expectedAllStockQuantity));
        assertThat(element.stockStatusList.findElement(By.xpath("./tr[" + index + "]/td[10]"))
                .getText(), is(expectedStockQuantity));
        assertThat(element.stockStatusList.findElement(By.xpath("./tr[" + index + "]/td[11]"))
                .getText(), is(expectedWriteOffExpected));
        assertThat(element.stockStatusList.findElement(By.xpath("./tr[" + index + "]/td[12]"))
                .getText(), is(expectedCustomerOrder));
        assertThat(element.stockStatusList.findElement(By.xpath("./tr[" + index + "]/td[13]"))
                .getText(), is(expectedInvestigatingStock));
        assertThat(element.stockStatusList.findElement(By.xpath("./tr[" + index + "]/td[14]"))
                .getText(), is(expectedStoreStock));
    }


    /**
     * URL verification. Confirm whether you can transition to target URL.
     *
     * @param url URL.
     */
    private void assertUrl(final String actualUrl) {
        assertThat(webDriver.getCurrentUrl(), is(actualUrl));
    }

    /**
     * Retrun the index-th option.
     * 
     * @param selectElement select box
     * @param index one-based index
     * @return option of select box
     */
    protected WebElement getSelectBoxOption(WebElement selectElement, int index) {
        return selectElement.findElement(
                By.xpath(".//div[contains(@class, 'select-elem')]/ul/li[" + index + "]"));
    }

    /**
     * Select the index-th option.
     * 
     * @param selectElement select box
     * @param index one-based index
     */
    protected void selectOtionOfSelectBox(WebElement selectElement, int index) {
        selectElement.click();
        webDriverWait.until(dirver -> {
            return selectElement.findElement(By.xpath(".//div[contains(@class, 'select-elem')]"))
                    .isDisplayed();
        });
        WebElement selectOption = getSelectBoxOption(selectElement, index);
        selectOption.click();
        webDriverWait.until(dirver -> {
            return "selected".equals(selectOption.getAttribute("class"));
        });
    }

    /** Month text to Month integer mapping. */
    private Map<String, Integer> monthMap = new HashMap<>();
    {
        monthMap.put("January", 1);
        monthMap.put("February", 2);
        monthMap.put("March", 3);
        monthMap.put("April", 4);
        monthMap.put("May", 5);
        monthMap.put("June", 6);
        monthMap.put("July", 7);
        monthMap.put("August", 8);
        monthMap.put("September", 9);
        monthMap.put("October", 10);
        monthMap.put("November", 11);
        monthMap.put("December", 12);
    }

    /**
     * Select date.
     * 
     * @param inputElement input element
     * @param year year to select
     * @param month month to select
     * @param day day to select
     */
    protected void selectDate(WebElement inputElement, int year, int month, int day) {
        inputElement.click();
        webDriverWait.until(dirver -> {
            return element.datepicker.isDisplayed();
        });

        WebElement moveElement = null;
        int currentYear = Integer.parseInt(element.datepickerYear.getText());
        if (currentYear < year) {
            moveElement = element.datepickerNext;
        } else if (year < currentYear) {
            moveElement = element.datepickerPrev;
        } else {
            int currentMonth = monthMap.get(element.datepickerMonth.getText());
            if (currentMonth < month) {
                moveElement = element.datepickerNext;
            } else if (month < currentMonth) {
                moveElement = element.datepickerPrev;
            }
        }

        while (!(Integer.parseInt(element.datepickerYear.getText()) == year
                && monthMap.get(element.datepickerMonth.getText()) == month)) {
            moveElement.click();
        }

        element.datepickerCalendar.findElement(By.xpath(".//a[contains(text(), '" + day + "')]"))
                .click();;

        webDriverWait.until(dirver -> {
            return !element.datepicker.isDisplayed();
        });
    }

    /**
     * chek exist element.
     * 
     * @param element web element
     * @param xpath xpath(element based relative path)
     * @return
     */
    private boolean existElement(WebElement element, String xpath) {
        try {
            element.findElement(By.xpath(xpath));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private void selectRadioButton(WebElement raidoElement) {
        raidoElement.findElement(By.xpath("./../label")).click();
        webDriverWait.until(dirver -> {
            return raidoElement.getAttribute("checked") != null;
        });
    }

    private void selectAllOfSelectBoxWitChild(WebElement selectElement) {
        selectElement.click();
        webDriverWait.until(dirver -> {
            return selectElement.findElement(By.xpath(".//div[contains(@class, 'select-elem')]"))
                    .isDisplayed();
        });
        WebElement selectOption = selectElement
                .findElement(By.xpath(".//div[contains(@class, 'select-elem')]/div[1]/ul/li"));
        selectOption.click();
        webDriverWait.until(dirver -> {
            return "selected".equals(selectOption.getAttribute("class"));
        });
    }

    private void selectParentOfSelectBoxWitChild(WebElement selectElement, int index) {
        selectElement.click();
        webDriverWait.until(dirver -> {
            return selectElement.findElement(By.xpath(".//div[contains(@class, 'select-elem')]"))
                    .isDisplayed();
        });
        WebElement selectOption = selectElement.findElement(
                By.xpath(".//div[contains(@class, 'select-elem')]/div[2]/ul[" + index + "]/li"));
        selectOption.findElement(By.xpath("./span")).click();
        webDriverWait.until(dirver -> {
            return "selected".equals(selectOption.getAttribute("class"));
        });
    }

    private void selectChildOfSelectBoxWitChild(WebElement selectElement, int parentIndex,
            int childIndex) {
        selectElement.click();
        webDriverWait.until(dirver -> {
            return selectElement.findElement(By.xpath(".//div[contains(@class, 'select-elem')]"))
                    .isDisplayed();
        });
        WebElement selectOption = selectElement
                .findElement(By.xpath(".//div[contains(@class, 'select-elem')]/div[2]/ul["
                        + parentIndex + "]/li/ul/li[" + childIndex + "]"));
        selectOption.click();
        webDriverWait.until(dirver -> {
            return "selected".equals(selectOption.getAttribute("class"));
        });
    }
}
