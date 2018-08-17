/**
 * @(#)SalesPayoffUnmatchListTest.java
 *
 *										  Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salespayoffunmatchlist.templates.testcase;

import static org.hamcrest.CoreMatchers.is;
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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import com.fastretailing.dcp.sales.salespayoffunmatchlist.templates.constants.Constants;
import com.fastretailing.dcp.sales.salespayoffunmatchlist.templates.elements.SalesPayoffUnmatchListElements;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
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
@Sql(scripts = "/junit_create_table_sales.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/junit_drop_table_sales.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public abstract class SalesPayoffUnmatchListTest {

    private static final String DISPLAY_STRING_STATUS_IS_CORRECTING = "補正中";

    /** Driver for performing browser operation. */
    protected WebDriver webDriver;

    /** Wait processing of webdriver. */
    protected WebDriverWait webDriverWait;

    /** Screen element (tag) information. */
    protected SalesPayoffUnmatchListElements element;

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

    @Test
    @DatabaseSetup("SalesPayoffUnmatchListTest.xml")
    public void test() throws Exception {

        // Confirm initial display.
        assertInitialDisplay();

        // Confirm whether the transition destination is the item list screen.
        assertUrl(Constants.SALES_PAYOFF_UNMATCH_LIST_URL);

        // select brand.
        selectOtionOfSelectBox(element.brandNameSelectBox, 2);
        // select country
        selectOtionOfSelectBox(element.countrySelectBox, 2);
        // input sotre.
        element.storeCodeInput.sendKeys("9720");
        // input register no.
        element.registerNoInput.sendKeys("100");
        // select payoff date from.
        selectDate(element.payoffDateFromInput, 2018, 6, 5);
        // select payoff date to.
        selectDate(element.payoffDateToInput, 2018, 6, 25);
        // select error contents.
        selectOtionOfSelectBox(element.errorContentsSelectBox, 2);

        // chech search conditions.
        assertThat(element.brandNameInput.getAttribute("value"), is("UQ"));
        assertThat(element.countryInput.getAttribute("value"), is("Japan"));
        assertThat(element.storeCodeInput.getAttribute("value"), is("9720"));
        assertThat(element.registerNoInput.getAttribute("value"), is("100"));
        assertThat(element.payoffDateFromInput.getAttribute("value"), is("2018/06/05"));
        assertThat(element.payoffDateToInput.getAttribute("value"), is("2018/06/25"));
        assertThat(element.errorContentsInput.getAttribute("value"), is("区分不一致"));

        // click search button.
        element.searchButton.click();
        webDriverWait.until(dirver -> {
            return !element.progressBox.isDisplayed();
        });

        // check search result.
        assertThat(element.salesPayoffUnmatchList.findElements(By.xpath("./tr")).size(), is(4));
        assertSearchResult(1, "UQ", "Japan", "112081", "SSC", "100", "20180605", "区分不一致", false);
        assertSearchResult(2, "UQ", "Japan", "112081", "SSC", "100", "20180610", "区分不一致", false);
        assertSearchResult(3, "UQ", "Japan", "112081", "SSC", "100", "20180617", "区分不一致", true);
        assertSearchResult(4, "UQ", "Japan", "112081", "SSC", "100", "20180625", "区分不一致", false);

        // click clear button.
        element.clearButton.click();
        webDriverWait.until(dirver -> {
            return element.storeCodeInput.getAttribute("value").equals("");
        });

        // chech search conditions.
        assertThat(element.brandNameInput.getAttribute("value"), is(""));
        assertThat(element.countryInput.getAttribute("value"), is(""));
        assertThat(element.storeCodeInput.getAttribute("value"), is(""));
        assertThat(element.registerNoInput.getAttribute("value"), is(""));
        assertThat(element.payoffDateFromInput.getAttribute("value"), is(""));
        assertThat(element.payoffDateToInput.getAttribute("value"), is(""));
        assertThat(element.errorContentsInput.getAttribute("value"), is(""));

        // check search result.(not changed)
        assertThat(element.salesPayoffUnmatchList.findElements(By.xpath("./tr")).size(), is(4));
        assertSearchResult(1, "UQ", "Japan", "112081", "SSC", "100", "20180605", "区分不一致", false);
        assertSearchResult(2, "UQ", "Japan", "112081", "SSC", "100", "20180610", "区分不一致", false);
        assertSearchResult(3, "UQ", "Japan", "112081", "SSC", "100", "20180617", "区分不一致", true);
        assertSearchResult(4, "UQ", "Japan", "112081", "SSC", "100", "20180625", "区分不一致", false);

        // click search button.
        element.searchButton.click();
        webDriverWait.until(dirver -> {
            return !element.progressBox.isDisplayed();
        });

        // check search result.
        assertThat(element.salesPayoffUnmatchList.findElements(By.xpath("./tr")).size(), is(11));
        assertSearchResult(1, "UQ", "Japan", "112081", "SSC", "100", "20180610", "区分不一致", false);
        assertSearchResult(2, "UQ", "UK", "112081", "SSC", "100", "20180612", "区分不一致", false);
        assertSearchResult(3, "UQ", "Japan", "112226", "Yorkdale Shopping Center", "100",
                "20180610", "区分不一致", false);
        assertSearchResult(4, "UQ", "Japan", "112081", "SSC", "101", "20180610", "区分不一致", false);
        assertSearchResult(5, "UQ", "Japan", "112081", "SSC", "100", "20180604", "区分不一致", false);
        assertSearchResult(6, "UQ", "Japan", "112081", "SSC", "100", "20180605", "区分不一致", false);
        assertSearchResult(7, "UQ", "Japan", "112081", "SSC", "100", "20180625", "区分不一致", false);
        assertSearchResult(8, "UQ", "Japan", "112081", "SSC", "100", "20180626", "区分不一致", false);
        assertSearchResult(9, "UQ", "Japan", "112081", "SSC", "100", "20180615", "貸借不一致", false);
        assertSearchResult(10, "UQ", "Japan", "112081", "SSC", "100", "20180617", "区分不一致", true);
        assertSearchResult(11, "GU", "Japan", "112081", "SSC", "100", "20180611", "区分不一致", false);
    }

    @Test
    @DatabaseSetup("SalesPayoffUnmatchListTest.xml")
    public void testSearchVariation01() throws Exception {

        // Confirm initial display.
        assertInitialDisplay();

        // select brand.
        selectOtionOfSelectBox(element.brandNameSelectBox, 3);

        // chech search conditions.
        assertThat(element.brandNameInput.getAttribute("value"), is("GU"));

        // click search button.
        element.searchButton.click();
        webDriverWait.until(dirver -> {
            return !element.progressBox.isDisplayed();
        });

        // check search result.
        assertThat(element.salesPayoffUnmatchList.findElements(By.xpath("./tr")).size(), is(1));
        assertSearchResult(1, "GU", "Japan", "112081", "SSC", "100", "20180611", "区分不一致", false);
    }

    @Test
    @DatabaseSetup("SalesPayoffUnmatchListTest.xml")
    public void testSearchVariation02() throws Exception {

        // Confirm initial display.
        assertInitialDisplay();

        // select country
        selectOtionOfSelectBox(element.countrySelectBox, 3);

        // chech search conditions.
        assertThat(element.countryInput.getAttribute("value"), is("UK"));

        // click search button.
        element.searchButton.click();
        webDriverWait.until(dirver -> {
            return !element.progressBox.isDisplayed();
        });

        // check search result.
        assertThat(element.salesPayoffUnmatchList.findElements(By.xpath("./tr")).size(), is(1));
        assertSearchResult(1, "UQ", "UK", "112081", "SSC", "100", "20180612", "区分不一致", false);
    }

    @Test
    @DatabaseSetup("SalesPayoffUnmatchListTest.xml")
    public void testSearchVariation03() throws Exception {

        // Confirm initial display.
        assertInitialDisplay();

        // input store.
        element.storeCodeInput.sendKeys("9720");
        // input register no.
        element.registerNoInput.sendKeys("101");

        // chech search conditions.
        assertThat(element.storeCodeInput.getAttribute("value"), is("9720"));
        assertThat(element.registerNoInput.getAttribute("value"), is("101"));

        // click search button.
        element.searchButton.click();
        webDriverWait.until(dirver -> {
            return !element.progressBox.isDisplayed();
        });

        // check search result.
        assertThat(element.salesPayoffUnmatchList.findElements(By.xpath("./tr")).size(), is(1));
        assertSearchResult(1, "UQ", "Japan", "112081", "SSC", "101", "20180610", "区分不一致", false);

    }


    @Test
    @DatabaseSetup("SalesPayoffUnmatchListTest.xml")
    public void testSearchVariation04() throws Exception {

        // Confirm initial display.
        assertInitialDisplay();

        // select payoff date from.
        selectDate(element.payoffDateFromInput, 2018, 6, 5);
        // select payoff date to.
        selectDate(element.payoffDateToInput, 2018, 6, 25);

        // chech search conditions.
        assertThat(element.payoffDateFromInput.getAttribute("value"), is("2018/06/05"));
        assertThat(element.payoffDateToInput.getAttribute("value"), is("2018/06/25"));

        // click search button.
        element.searchButton.click();
        webDriverWait.until(dirver -> {
            return !element.progressBox.isDisplayed();
        });

        // check search result.
        assertThat(element.salesPayoffUnmatchList.findElements(By.xpath("./tr")).size(), is(9));
        assertSearchResult(1, "UQ", "Japan", "112081", "SSC", "100", "20180610", "区分不一致", false);
        assertSearchResult(2, "GU", "Japan", "112081", "SSC", "100", "20180611", "区分不一致", false);
        assertSearchResult(3, "UQ", "UK", "112081", "SSC", "100", "20180612", "区分不一致", false);
        assertSearchResult(4, "UQ", "Japan", "112226", "Yorkdale Shopping Center", "100",
                "20180610", "区分不一致", false);
        assertSearchResult(5, "UQ", "Japan", "112081", "SSC", "101", "20180610", "区分不一致", false);
        assertSearchResult(6, "UQ", "Japan", "112081", "SSC", "100", "20180605", "区分不一致", false);
        assertSearchResult(7, "UQ", "Japan", "112081", "SSC", "100", "20180625", "区分不一致", false);
        assertSearchResult(8, "UQ", "Japan", "112081", "SSC", "100", "20180615", "貸借不一致", false);
        assertSearchResult(9, "UQ", "Japan", "112081", "SSC", "100", "20180617", "区分不一致", true);

    }

    @Test
    @DatabaseSetup("SalesPayoffUnmatchListTest.xml")
    public void testSearchVariation05() throws Exception {

        // Confirm initial display.
        assertInitialDisplay();

        // select error contents.
        selectOtionOfSelectBox(element.errorContentsSelectBox, 3);

        // chech search conditions.
        assertThat(element.errorContentsInput.getAttribute("value"), is("貸借不一致"));

        // click search button.
        element.searchButton.click();
        webDriverWait.until(dirver -> {
            return !element.progressBox.isDisplayed();
        });

        // check search result.
        assertThat(element.salesPayoffUnmatchList.findElements(By.xpath("./tr")).size(), is(1));
        assertSearchResult(1, "UQ", "Japan", "112081", "SSC", "100", "20180615", "貸借不一致", false);
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

    /**
     * check index-th row.
     * 
     * @param index index
     * @param expectedBrand expected brand
     * @param expectedCountry expected country
     * @param expectedStore expected store
     * @param expectedStoreName expected store name
     * @param expectedRegisterNo expected register no
     * @param expectedPayoffDate expected payoff date
     * @param expectedErrorContents expected error contents
     * @param expectedStatusIsCorrecting expected status is correcting
     */
    private void assertSearchResult(int index, String expectedBrand, String expectedCountry,
            String expectedStore, String expectedStoreName, String expectedRegisterNo,
            String expectedPayoffDate, String expectedErrorContents,
            boolean expectedStatusIsCorrecting) {

        String expectedDisplayStringStatus =
                expectedStatusIsCorrecting ? DISPLAY_STRING_STATUS_IS_CORRECTING : "";

        // TODO UT実施時、未実装のため確認対象外とする。
        // check that No is link or label.
        // if (expectedStatusIsCorrecting) {
        // assertThat(existElement(element.salesPayoffUnmatchList, "./tr[" + index +
        // "]/td[1]/label"), is(true));
        // assertThat(existElement(element.salesPayoffUnmatchList, "./tr[" + index + "]/td[1]/a"),
        // is(false));
        // } else {
        // assertThat(existElement(element.salesPayoffUnmatchList, "./tr[" + index +
        // "]/td[1]/label"), is(false));
        // assertThat(existElement(element.salesPayoffUnmatchList, "./tr[" + index + "]/td[1]/a"),
        // is(true));
        // }

        // check text.
        assertThat(element.salesPayoffUnmatchList.findElement(By.xpath("./tr[" + index + "]/td[1]"))
                .getText(), is(String.valueOf(index)));
        assertThat(element.salesPayoffUnmatchList.findElement(By.xpath("./tr[" + index + "]/td[2]"))
                .getText(), is(expectedBrand));
        assertThat(element.salesPayoffUnmatchList.findElement(By.xpath("./tr[" + index + "]/td[3]"))
                .getText(), is(expectedCountry));
        assertThat(element.salesPayoffUnmatchList.findElement(By.xpath("./tr[" + index + "]/td[4]"))
                .getText(), is(expectedStore));
        assertThat(element.salesPayoffUnmatchList.findElement(By.xpath("./tr[" + index + "]/td[5]"))
                .getText(), is(expectedStoreName));
        assertThat(element.salesPayoffUnmatchList.findElement(By.xpath("./tr[" + index + "]/td[6]"))
                .getText(), is(expectedRegisterNo));
        assertThat(element.salesPayoffUnmatchList.findElement(By.xpath("./tr[" + index + "]/td[7]"))
                .getText(), is(expectedPayoffDate));
        assertThat(element.salesPayoffUnmatchList.findElement(By.xpath("./tr[" + index + "]/td[8]"))
                .getText(), is(expectedErrorContents));
        assertThat(element.salesPayoffUnmatchList.findElement(By.xpath("./tr[" + index + "]/td[9]"))
                .getText(), is(expectedDisplayStringStatus));

        // check color.
        // TODO 背景色と文字色をチェックする
        String expectedBackgroundColor =
                expectedStatusIsCorrecting ? "rgba(255, 255, 255, 1)" : "rgba(255, 255, 255, 1)";
        assertThat(element.salesPayoffUnmatchList.findElement(By.xpath("./tr[" + index + "]/td[1]"))
                .getCssValue("background-color"), is(expectedBackgroundColor));
        assertThat(element.salesPayoffUnmatchList.findElement(By.xpath("./tr[" + index + "]/td[2]"))
                .getCssValue("background-color"), is(expectedBackgroundColor));
        assertThat(element.salesPayoffUnmatchList.findElement(By.xpath("./tr[" + index + "]/td[3]"))
                .getCssValue("background-color"), is(expectedBackgroundColor));
        assertThat(element.salesPayoffUnmatchList.findElement(By.xpath("./tr[" + index + "]/td[4]"))
                .getCssValue("background-color"), is(expectedBackgroundColor));
        assertThat(element.salesPayoffUnmatchList.findElement(By.xpath("./tr[" + index + "]/td[5]"))
                .getCssValue("background-color"), is(expectedBackgroundColor));
        assertThat(element.salesPayoffUnmatchList.findElement(By.xpath("./tr[" + index + "]/td[6]"))
                .getCssValue("background-color"), is(expectedBackgroundColor));
        assertThat(element.salesPayoffUnmatchList.findElement(By.xpath("./tr[" + index + "]/td[7]"))
                .getCssValue("background-color"), is(expectedBackgroundColor));
        assertThat(element.salesPayoffUnmatchList.findElement(By.xpath("./tr[" + index + "]/td[8]"))
                .getCssValue("background-color"), is(expectedBackgroundColor));
        assertThat(element.salesPayoffUnmatchList.findElement(By.xpath("./tr[" + index + "]/td[9]"))
                .getCssValue("background-color"), is(expectedBackgroundColor));
        // rgba(255, 255, 255, 1)
        System.out.println(
                element.salesPayoffUnmatchList.findElement(By.xpath("./tr[" + index + "]/td[2]"))
                        .getCssValue("background-color"));
    }

    /**
     * Check the initial display screen.
     */
    private void assertInitialDisplay() {

        assertThat(element.brandNameInput.getAttribute("value"), is(""));
        assertThat(element.countryInput.getAttribute("value"), is(""));
        assertThat(element.storeCodeInput.getAttribute("value"), is(""));
        assertThat(element.registerNoInput.getAttribute("value"), is(""));
        assertThat(element.payoffDateFromInput.getAttribute("value"), is(""));
        assertThat(element.payoffDateToInput.getAttribute("value"), is(""));
        assertThat(element.errorContentsInput.getAttribute("value"), is(""));

        // check search result.
        assertThat(element.salesPayoffUnmatchList.findElements(By.xpath("./tr")).size(), is(0));


        // check brand select box options.
        assertSelectBoxOption(element.brandNameSelectBox, "Clear", "UQ", "GU");

        // check country select box options.
        assertSelectBoxOption(element.countrySelectBox, "Clear", "Japan", "UK", "China", "U.S.A.",
                "Korea");

        // check error contents select box options.
        assertSelectBoxOption(element.errorContentsSelectBox, "Clear", "区分不一致", "貸借不一致");
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
}
