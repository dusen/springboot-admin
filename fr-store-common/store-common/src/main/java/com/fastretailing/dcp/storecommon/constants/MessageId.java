/**
 * @(#)MessageId.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.storecommon.constants;

/**
 * The constants class is used to define the message id for error or log .
 *
 */
public final class MessageId {

    /** Error serial number represents a include invalid values. */
    public static final String E_PARAMETERS = "000101";

    /** Error serial number represents a may not be empty. */
    public static final String E_REQUIRED = "000102";

    /** Error serial number represents a size must be between min and max. */
    public static final String E_NUMBER_RANGE = "000103";

    /** Error serial number represents a must be half-width alphanumeric. */
    public static final String E_HALF_WIDTH_ALPHANUMERIC = "000104";

    /** Error serial number represents a correct date. */
    public static final String E_DATE = "000105";

    /** Error serial number represents a bussiness. */
    public static final String E_BUSINESS = "000101";

    /** Check parameter does null. */
    public static final String CMN9900101E001_E_CHECK_PARAMETERS_ERROR = "CMN9900101E001";
    /** Check out bound id does exist in property file. */
    public static final String CMN9900101E002_E_CHECK_REQUIRE_ERROR = "CMN9900101E002";
    /** Check properties item does exist in property file. */
    public static final String CMN9900101E003_E_CHECK_PROPERTIES_ERROR = "CMN9900101E003";
    /** Check table name does exist in data base. */
    public static final String CMN9900101E004_E_CHECK_TABLE_NAME_ERROR = "CMN9900101E004";
    /** Check update type does correct format. */
    public static final String CMN9900101E005_E_CHECK_UPDATE_TYPE_ERROR = "CMN9900101E005";

    /** Check exist of others EAI update type. */
    public static final String CMN9900101E006_E_OTHERS_EAI_UPDATE_TYPE_ERROR = "CMN9900101E006";
    /** Check Check update key item name does exist in input table. */
    public static final String CMN9900101E007_E_CHECK_UPDATE_KEY_ITEMS_NOT_EXIST_ERROR =
            "CMN9900101E007";
    /** Check update items does exist. */
    public static final String CMN9900101E008_E_CHECK_CHECK_UPDATE_ITEMS_EXIST_ERROR =
            "CMN9900101E008";
    /** Bulk update or insert fail exception */
    public static final String CMN9900101E009_E_BULK_UPDATE_FAIL_ERROR = "CMN9900101E009";
    /** Bulk count item not exist in property file exception */
    public static final String CMN9900101E010_E_BULK_COUNT_NOT_EXIST_ERROR = "CMN9900101E010";
    /** Bulk count is zero exception */
    public static final String CMN9900101E011_E_BULK_COUNT_ZERO_ERROR = "CMN9900101E011";
    

}
