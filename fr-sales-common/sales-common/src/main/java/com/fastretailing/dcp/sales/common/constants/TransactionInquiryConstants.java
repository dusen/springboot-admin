/**
 * @(#)TransactionInquiryConstants.java
 * 
 *                                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.constants;

/**
 * Transaction inquiry constants.
 */
public final class TransactionInquiryConstants {
    /**
     * Latest inquiry pattern constant value.
     */
    public static String LATEST = "LATEST";

    /**
     * Initial inquiry pattern constant value.
     */
    public static String INITIAL = "INITIAL";

    /**
     * Both inquiry pattern constant value.
     */
    public static String BOTH = "BOTH";

    /**
     * Initial state.
     */
    public static Integer INITIAL_STATE = 1;

    /**
     * Item product classification keyword.
     */
    public static String ITEM_PRODUCT_CLASSIFICATION = "ITEM";

    /**
     * Non-item product classification keyword.
     */
    public static String NON_ITEM_PRODUCT_CLASSIFICATION = "NMITEM";

    /**
     * SQL query ordered by order sub number and sales transaction ID clause.
     */
    public static String ORDER_BY_ORDER_SUB_NUMBER_AND_SALES_TRANSACTION_ID =
            "order_sub_number ASC, sales_transaction_id ASC";

    /**
     * SQL query ordered by detail sub number.
     */
    public static String ORDER_BY_DETAIL_SUB_NUMBER = "detail_sub_number ASC";

    /**
     * SQL query ordered by item discount sub number.
     */
    public static String ORDER_BY_ITEM_DISCOUNT_SUB_NUMBER = "item_discount_sub_number ASC";

    /**
     * SQL query ordered by tender sub number.
     */
    public static String ORDER_BY_TENDER_SUB_NUMBER = "tender_sub_number ASC";

    /**
     * SQL query ordered by tax sub number.
     */
    public static String ORDER_BY_TAX_SUB_NUMBER = "tax_sub_number ASC";

    /**
     * SQL query ordered by total amount sub number.
     */
    public static String ORDER_BY_TOTAL_AMOUNT_SUB_NUMBER = "total_amount_sub_number ASC";

    /**
     * Item detail sub number constant for detail mapper.
     */
    public static Integer ZERO_ITEM_DETAIL_SUB_NUMBER = 0;

    /**
     * Data does not exist error ID.
     */
    public static String TABLE_DATA_NO_EXISTS = "E_SLS_64000101";

    /**
     * Integrated ID is not set error message.
     */
    public static String INTEGRATED_ID_NOT_SET = "E_SLS_64000114";

    /**
     * Inquiry pattern is not set error message.
     */
    public static String INQUIRY_PATTERN_NOT_SET = "E_SLS_64000115";

    /**
     * Integrated order ID inquiry pattern designated store control.
     */
    public static String TABLE_NAME_STORE_CONTROL = "SLS0300302_STORE_CONTROL";
}
