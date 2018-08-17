/**
 * @(#)CsvFileList.java
 *
 *                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactioncsv.dto;

import java.util.List;
import lombok.Data;

/**
 * Csv file list.
 */
@Data
public class CsvFileList {

    /**
     * Order information csv list.
     */
    private List<OrderInformationCsv> orderInformationCsvList;
    /**
     * Item detail csv list.
     */
    private List<ItemDetailCsv> itemDetailCsvList;
    /**
     * Item detail discount csv list.
     */
    private List<ItemDetailDiscountCsv> itemDetailDiscountCsvList;

    /**
     * Item detail tax csv list.
     */
    private List<ItemDetailTaxCsv> itemDetailTaxCsvList;

    /**
     * Sales transaction header csv list.
     */
    private List<SalesTransactionHeaderCsv> salesTransactionHeaderCsvList;

    /**
     * Sales transaction payment csv list.
     */
    private List<SalesTransactionPaymentInformationCsv> salesTransactionPaymentCsvList;

    /**
     * Sales transaction tax csv list.
     */
    private List<SalesTransactionTaxCsv> salesTransactionTaxCsvList;

    /**
     * Sales transaction total csv list.
     */
    private List<SalesTransactionTotalCsv> salesTransactionTotalCsvList;
}
