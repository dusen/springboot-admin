/**
 * @(#)BusinessItem.java
 * 
 *                       Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.constants;

/**
 * The constants class is used to define the db item .
 *
 */
public class BusinessItem {
    /** Result Code - NORMAL. */
    public static final Integer RESULT_CODE_NORMAL = 0;

    /** Result Code - ABNORMAL. */
    public static final Integer RESULT_CODE_ABNORMAL = 1;

    /** Zone id for utc. */
    public static final String ZONE_ID_UTC = "Z";

    /** Error-Field invalid. */
    public static final String SLS0600101_E_CHANNEL_TYPE = "channel_type";
    public static final String SLS0600101_E_CHANNEL_CODE = "channel_code";
    public static final String SLS0600101_E_STORE_CODE = "store_code";
    public static final String SLS0600101_E_CASH_REGISTER_NO = "cash_register_no";
    public static final String SLS0600101_E_PAYOFF_DATE = "payoff_date";
    public static final String SLS0600101_E_BUSINESS_DAY = "business_day";
    public static final String SLS0600101_E_BUSINESS_DATE = "business_date";

    public static final String SLS1000104_E_STORE_CODE = "store_code";
    public static final String SLS1000104_E_PAYOFF_DATE = "payoff_date";

    /** SLS0500105 Parameter Item. */
    public static final String SLS0500105_DEPARTMENT_CODE = "department_code";
    /** SLS0500105 Parameter Item. */
    public static final String SLS0500105_GDEPARTMENT_CODE = "g_department_code";
    /** SLS0500105 Parameter Item. */
    public static final String SLS0500105_AGGREGATION_CODE = "aggregation_code";

    /** SLS0300501 Application program id. */
    public static final String SLS0300501_PROGRAM_ID = "SLS0300501";
    /** SLS0300501 Application user id. */
    public static final String SLS0300501_USER_ID = "BATCH";
    /** SLS0300501 Application user id. */
    public static final String SLS0300501_STORE_CODE = "store_code";
    /** SLS0300501 Adding zero on the left is used. */
    public static final String SLS0300501_LEFTPAD_USED_VALUE = "0";
    /** SLS0300501 Unit type is EA. */
    public static final String SLS0300501_UNIT_TYPE_EA = "EA";
    /** SLS0300501 Pos use type is Y. */
    public static final String SLS0300501_POS_USE_TYPE = "Y";
    /** SLS0300501 Factory direct Shipment not covered is N. */
    public static final String SLS0300501_FACTORY_DIRECT_SHIPMENT_NOT_COVERED = "N";
    /** SLS0300501 Action flag is no action. */
    public static final String SLS0300501_ACTION_FLAG_NO_ACTION = "0";
    /** SLS0300501 Store code max length. */
    public static final Integer SLS0300501_STORE_CODE_MAX_LENGTH = 10;
    /** SLS0300501 Store code max length. */
    public static final Integer SLS0300501_ONE_SECOND = 1;
    /** SLS0300501 Ims linkage flag. */
    public static final String SLS0300501_IMS_LINKAGE_FLAG = "1";
    /** SLS0300501 Ims linkage tax vat. */
    public static final String SLS0300501_IMSLINKAGETAX_VAT = "VAT";

    /** Discount type mix match. */
    public static final String DISCOUNT_TYPE_MIX_MATCH = "100000";
    /** Discount type multi unit. */
    public static final String DISCOUNT_TYPE_MULTI_UNIT = "100200";
    /** Discount type single promotion. */
    public static final String DISCOUNT_TYPE_SINGLE_PROMOTION = "100300";
    /** Discount type store line discount. */
    public static final String DISCOUNT_TYPE_STORE_LINE_DISCOUNT = "100401";
    /** Discount type store total discount. */
    public static final String DISCOUNT_TYPE_STORE_TOTAL_DISCOUNT = "100403";
    /** Discount type store discount price override. */
    public static final String DISCOUNT_TYPE_STORE_DISCOUNT_PRICE_OVERRIDE = "100404";
    /** Discount type limited. */
    public static final String DISCOUNT_TYPE_LIMITED = "100700";
    /** Code l1 tax type. */
    public static final String CODE_L1_TAXTYPE = "TAXTYPE";
    /** Code l3 rtlog. */
    public static final String CODE_L3_RTLOG = "RTLOG";
    /** Code l1 decimal. */
    public static final String CODE_L1_DECIMAL = "DECIMAL";
    /** Code l3 fraction. */
    public static final String CODE_L3_FRACTION = "FRACTION";
    /** tax type vat. */
    public static final String TAX_TYPE_VAT = "VAT";
    /** Code l1 slstx. */
    public static final String TAX_TYPE_SLSTX = "SLSTX";
    /** Code l3 cnsmp. */
    public static final String TAX_TYPE_CNSMP = "CNSMP";

    /** Sign code positive. */
    public static final String SIGN_CODE_POSITIVE = "P";
    /** Sign code negative. */
    public static final String SIGN_CODE_NEGATIVE = "N";
    /** Item list hierarchy code L1. */
    public static final String ITEM_LIST_HIERARCHY_CODE_L1 = "L1";
    /** Item list hierarchy code L2. */
    public static final String ITEM_LIST_HIERARCHY_CODE_L2 = "L2";
    /** Tax type. */
    public static final String TAX_TYPE = "TAXTYPE";
    /** Rtlog. */
    public static final String RTLOG = "RTLOG";
    /** Pay off data. */
    public static final String PAY_OFF_DATA = "PAYOFFDATA";
    /** Calculation type. */
    public static final String CALCULATION_TYPE = "CALCULATIONTYPE";
    /** Pile up pay off data. */
    public static final String PILE_UP_PAY_OFF_DATA = "PILEUPPAYOFFDATA";
}
