/**
 * @(#)DBItem.java
 * 
 *                 Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.constants;

/**
 * The constants class is used to define the db item .
 *
 */
public class DBItem {

    /** Error-Table invalid. */
    public static final String TABLE_NAME_T_OPEN_LOG_TABLE = "t_open_log";
    public static final String TABLE_NAME_M_TRANS_STORE_CODE_TABLE = "m_trans_store_code";

    /** Error-Table-Column invalid. */
    public static final String COLUMN_NAME_PAYOFF_FLAG = "payoff_flag";

    /** Error-Table invalid. */
    public static final String TABLE_NAME_T_TRANSACTION_INQUIRY_SALES_TRANSACTION_HEADER =
            "t_transaction_inquiry_sales_transaction_header";
    public static final String TABLE_NAME_T_TRANSACTION_INQUIRY_SALES_TRANSACTION_DETAIL =
            "t_transaction_inquiry_sales_transaction_detail";
    /** Error-Table-Column invalid. */
    public static final String COLUMN_NAME_RETURN_COMPLETE_FLAG = "return_complete_flag";
    public static final String COLUMN_NAME_CANCELLED_FLAG = "cancelled_flag";

    /** Error-Table invalid. */
    public static final String TABLE_NAME_T_SALES_REPORT_TRANSACTION_HEADER =
            "t_sales_report_transaction_header";
    public static final String TABLE_NAME_T_SALES_REPORT_TRANSACTION_DETAIL =
            "t_sales_report_transaction_detail";

    /** Error-Table-Column invalid. */
    public static final String COLUMN_NAME_STORE_CODE = "store_code";

    /** Table m_store. */
    public static final String TABLE_NAME_M_STORE = "m_store";

    /** Table m_store_control. */
    public static final String TABLE_NAME_M_STORE_CONTROL = "m_store_control";

    /** Table t_sales_order_information. */
    public static final String TABLE_NAME_T_SALES_ORDER_INFORMATION = "t_sales_order_information";

    /** Table t_sales_transaction_header. */
    public static final String TABLE_NAME_T_SALES_TRANSACTION_HEADER = "t_sales_transaction_header";

    /** Table t_sales_transaction_detail. */
    public static final String TABLE_NAME_T_SALES_TRANSACTION_DETAIL = "t_sales_transaction_detail";

    /** Table t_sales_transaction_tender. */
    public static final String TABLE_NAME_T_SALES_TRANSACTION_TENDER = "t_sales_transaction_tender";

    /** Table t_sales_transaction_tax. */
    public static final String TABLE_NAME_T_SALES_TRANSACTION_TAX = "t_sales_transaction_tax";

    /** Table t_sales_transaction_total_amount. */
    public static final String TABLE_NAME_T_SALES_TRANSACTION_TOTAL_AMOUNT =
            "t_sales_transaction_total_amount";

    /** Table t_sales_transaction_discount. */
    public static final String TABLE_NAME_T_SALES_TRANSACTION_DISCOUNT =
            "t_sales_transaction_discount";

}
