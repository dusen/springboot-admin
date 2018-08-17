/**
 * @(#)MessagePrefix.java
 * 
 *                        Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.common.constants;

/**
 * The constants class is used to define the message id for error or log .
 *
 */
public final class MessagePrefix {

    /** Check exist of selected data from open log. */
    public static final String SLS0600101_E_NO_OPEN_LOG_DATA = "SLS0600101E001";
    /** Payoff flag is in end status in open log. */
    public static final String SLS0600101_E_PAYOFF_FLAG_END = "SLS0600101E002";
    /** Check exist of selected data from store translation. */
    public static final String SLS0600101_E_NO_STORE_TRANSLATION_DATA = "SLS0600101E003";
    /** Check exist of selected data from store translation. */
    public static final String SLS0600101_E_INVALID = "SLS0600101E004";
    /** Parameters invalid. */
    public static final String SLS0600101_E_PARAMETER_INVALID = "SLS0600101E005";
    /** Parameters invalid. */
    public static final String SLS0600101_E_PARAMETER_DATE_INVALID = "SLS0600101E006";
    /** This API can not execute. Please check details and modify it. */
    public static final String SLS0600101_E_NOT_EXIST_ERROR = "SLS0600101E007";
    /** Pay off flag selected from open log table data is in end status. */
    public static final String SLS0600101_E_EXIST_ERROR = "SLS0600101E008";
    /** Check exist of selected data. */
    public static final String SLS0600204_E_BUSINESS_ERROR = "SLS0600204E001";
    /** Details issue. */
    public static final String SLS0600204_E_DETAILS_ISSUE = "E_SLS_62000102";

    /** Parameters invalid. */
    public static final String SLS0300301_E_SPECIFY_ONE_ERROR = "SLS0300301E001";

    /** Parameters invalid. */
    public static final String SLS0300301_E_SPECIFY_ALL_ERROR = "SLS0300301E002";

    /** Check returns object not exist error. */
    public static final String SLS0300102E001_E_RETURN_NOT_EXIST = "SLS0300102E001";
    /** Check have already returned error. */
    public static final String SLS0300102E002_E_HAVE_ALREADY_RETURN = "SLS0300102E002";
    /** Check already cancel returned error. */
    public static final String SLS0300102E003_E_ALREADY_CANCEL_RETURN = "SLS0300102E003";
    /** Check cancel object not exist error. */
    public static final String SLS0300102E004_E_CANCEL_NOT_EXIST = "SLS0300102E004";
    /** Check already cancel error. */
    public static final String SLS0300102E005_E_ALREADY_CANCEL = "SLS0300102E005";
    /** Check already returned cancel error. */
    public static final String SLS0300102E006_E_ALREADY_RETURN_CANCEL = "SLS0300102E006";
    /** Check already returned error. */
    public static final String SLS0300102E007_E_ALREADY_RETURN = "SLS0300102E007";

    /** Check returns object does not exist error. */
    public static final String SLS0300103E001_E_RETURN_NOT_EXIST = "SLS0300103E001";
    /** Check have already returned error. */
    public static final String SLS0300103E002_E_HAVE_ALREADY_RETURN = "SLS0300103E002";
    /** Check already cancel returned error. */
    public static final String SLS0300103E003_E_ALREADY_CANCEL_RETURN = "SLS0300103E003";
    /** Check cancel object does not exist error. */
    public static final String SLS0300103E004_E_CANCEL_NOT_EXIST = "SLS0300103E004";
    /** Check already cancel error. */
    public static final String SLS0300103E005_E_ALREADY_CANCEL = "SLS0300103E005";
    /** Check already returned cancel error. */
    public static final String SLS0300103E006_E_ALREADY_RETURN_CANCEL = "SLS0300103E006";
    /** Check already returned error. */
    public static final String SLS0300103E007_E_ALREADY_RETURN = "SLS0300103E006";

    /** Check have already returned error. */
    public static final String SLS1000103E002_E_HAVE_ALREADY_RETURN = "SLS1000103E002";
    /** Check have already returned error. */
    public static final String SLS1000103E003_E_HAVE_ALREADY_RETURN = "SLS1000103E003";
    /** Check returns object does not exist error. */
    public static final String SLS1000103E004_E_RETURN_NOT_EXIST = "SLS1000103E004";

    /** Check data existence at translation store code table. */
    public static final String SLS1000104E002_E_MANY_TRANSACTION_ERROR_DATA = "E_SLS_62000110";
    /** Check data existence at payoff data table. */
    public static final String SLS1000104E003_E_MANY_PAYOFF_ERROR_DATA = "E_SLS_62000111";
    /** Check data existence at translation store code table. */
    public static final String SLS1000104E004_E_NO_TRANSLATION_STORE_CODE_DATA = "E_SLS_62000104";
    /** Check data existence at type table. */
    public static final String SLS1000104E005_E_NO_TYPE_DATA = "E_SLS_62000105";
    /** Check data existence open log table. */
    public static final String SLS1000104E006_E_NO_OPEN_LOG_DATA = "E_SLS_62000106";

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

    /** Check translation store code. */
    public static final String E_SLS_66000131_NO_TRANSLATION_STORE_CODE = "E_SLS_66000131";
    /** Check common code. */
    public static final String E_SLS_66000128_NO_COMMON_CODE = "E_SLS_66000128";
    /** Check non item. */
    public static final String E_SLS_66000153_NO_NON_ITEM = "E_SLS_66000153";
    /** Check translation tender. */
    public static final String E_SLS_66000170_NO_TRANSLATION_TENDER = "E_SLS_66000170";
    /** Check range. */
    public static final String E_SLS_66000151_RANGE_ERROR = "E_SLS_66000151";
    /** Check data. */
    public static final String E_SLS_66000134_NO_DATA = "E_SLS_66000134";
    /** Check business country state setting master. */
    public static final String E_SLS_66000162_NO_BUSINESS_COUNTRY_STATE_SETTING = "E_SLS_66000162";

    /** Bulk update or insert fail exception. */
    public static final String CMN9900101E009_E_BULK_UPDATE_FAIL_ERROR = "CMN9900101E009";
    /** Bulk count item not exist in property file exception. */
    public static final String CMN9900101E010_E_BULK_COUNT_NOT_EXIST_ERROR = "CMN9900101E010";
    /** Bulk count is zero exception. */
    public static final String CMN9900101E011_E_BULK_COUNT_ZERO_ERROR = "CMN9900101E011";

    /** Details issue. */
    public static final String SLS0700102_E_DETAILS_ISSUE = "E_SLS_62000107";

    /** Issue - store is not defined . */
    public static final String SLS0300501E001_STORE_NULL_ISSUE = "E_SLS_62000114";

    /** Issue - pay off data is not defined . */
    public static final String E_SLS_62000115_PAY_OFF_DATA_NULL_ISSUE = "E_SLS_62000115";

    /** Issue - business county is not defined . */
    public static final String SLS0300501E002_BUSINESS_COUNTY_NULL_ISSUE = "E_SLS_62000108";

    /** Issue - zone id is not defined . */
    public static final String SLS0300501E003_ZONE_ID_NULL_ISSUE = "E_SLS_62000113";

    /** Table count is zero exception. */
    public static final String E_SLS_64000106_E_BUSINESS_ERROR = "E_SLS_64000106";

    /** Error code - this API can not execute. Please check details and modify it. */
    public static final String E_SLS_62000101_INEXCUTABLE_API = "E_SLS_62000101";

    /** Error code - this API can not execute. Please check details and modify it. */
    public static final String E_SLS_64000101_INEXCUTABLE_API = "E_SLS_64000101";

    /** Error code - unable to get integrated order ID parameter. */
    public static final String E_SLS_64000114_NO_PARAMETER_EXISTS = "E_SLS_64000114";

    /** Error code - unable to get inquiry pattern parameter. */
    public static final String E_SLS_64000115_NO_PARAMETER_EXISTS = "E_SLS_64000115";

    /** Error code - unable to get order sub number parameter. */
    public static final String E_SLS_64000116_NO_PARAMETER_EXISTS = "E_SLS_64000116";

    /** Error code - unable to get order barcode number parameter. */
    public static final String E_SLS_64000117_NO_PARAMETER_EXISTS = "E_SLS_64000117";

    /** Error code - unable to get sales transaction ID parameter. */
    public static final String E_SLS_64000118_NO_PARAMETER_EXISTS = "E_SLS_64000118";

    /** Error code - unable to get any receipt information parameter. */
    public static final String E_SLS_64000119_NO_PARAMETER_EXISTS = "E_SLS_64000119";

    /** Error code - unable to get a receipt information parameter. */
    public static final String E_SLS_64000120_NO_PARAMETER_EXISTS = "E_SLS_64000120";

    /** Error code - unable to get open log. */
    public static final String E_SLS_64000103_NO_DATA_EXISTS = "E_SLS_64000103";

    /** Error code - it is payoff complete data. */
    public static final String E_SLS_64000104_PAYOFF_COMPLETE = "E_SLS_64000104";

    /** Error code - no data exists in trans store code master. */
    public static final String E_SLS_64000105_NO_DATA_EXISTS = "E_SLS_64000105";

    /** Issue - payoffdata is not defined. */
    public static final String E_SLS_64000109_STORECONTROL_NULL_ISSUE = "E_SLS_64000109";

    /** Issue - payoffdata is not update. */
    public static final String E_SLS_64000110_STORECONTROL_UPDATE_FAIL_ERROR = "E_SLS_64000110";

    /** Issue - No data exists in store control. */
    public static final String E_SLS_64000111_STORECONTROL_NO_EXISTS = "E_SLS_64000111";

    /** Issue - POS data already exists in open log table. */
    public static final String E_SLS_64000113_STORECONTROL_NO_EXISTS = "E_SLS_64000113";

    /** Error code - no data exists in store general purpose master. */
    public static final String E_SLS_62000113_NO_DATA_EXISTS = "E_SLS_62000113";

    /** Check data business at translation store code table. */
    public static final String E_SLS_62000108_NO_BUSINESS_COUNTRY_STATE_SETTING = "E_SLS_62000108";

    /** Check data payoff summary mapping table. */
    public static final String E_SLS_62000109_NO_PAYOFF_SUMMARY_MAPPING = "E_SLS_62000109";

    /** Check data skulist header table. */
    public static final String E_SLS_62000123_NO_DATA_IN_SKULIST_HEADER = "E_SLS_62000123";

    /** Check parameter has invalid data. */
    public static final String E_SLS_44000101_INVALID_PARAMETER_DATA = "E_SLS_44000101";

    /** Check input parameters include invalid values. */
    public static final String E_SLS_44000101_PARAMETERS_INCLUDE_VALUES = "E_SLS_44000101";


    /** Error code - invalid payoff type code is specified. */
    public static final String E_SLS_62000152_INVALID_PAYOFF_TYPE_CODE = "E_SLS_62000152";

    /** Error code - no data exists in sales transaction error detail. */
    public static final String E_SLS_64000147_NO_DATA_EXISTS = "E_SLS_64000147";

    /** Check empty parameter item. */
    public static final String E_SLS_44000102_EMPTY_PARAMETER_ITEM = "E_SLS_44000102";

    /** Check aggregation code may not be empty. */
    public static final String E_SLS_44000102_AGGREGATION_CODE_EMPTY = "E_SLS_44000102";

    /** Ec closing process of job net is not execute. */
    public static final String E_SLS_64000148 = "E_SLS_64000148";

    /** Check store code. */
    public static final String E_SLS_62000158_NO_STORE_CODE = "E_SLS_62000158";

    /** Check business date. */
    public static final String E_SLS_62000159_NO_BUSINESS_DATE = "E_SLS_62000159";

    /** Error code - no data exists in trans business code table. */
    public static final String E_SLS_62000129_NO_DATA_EXISTS = "E_SLS_62000129";

    /** Check data common code master table. */
    public static final String E_SLS_62000126 = "E_SLS_62000126";

    /** Check data sales report transaction header table. */
    public static final String E_SLS_62000133 = "E_SLS_62000133";

    /** Check data sales report transaction detail table. */
    public static final String E_SLS_62000134 = "E_SLS_62000134";

    /** Check data g class link table. */
    public static final String E_SLS_62000116 = "E_SLS_62000116";

    /** Check invalid eai update type. */
    public static final String E_SLS_62000160 = "E_SLS_62000160";

    /** No data exists in translation tender master table. */
    public static final String E_SLS_64000143_NO_DATA_EXISTS = "E_SLS_64000143";

    /** Unable to get initial value. */
    public static final String E_SLS_64000152_NO_INITIAL_VALUE = "E_SLS_64000152";

    /** Error code - no data exists in translation store code table. */
    public static final String E_SLS_62000155 = "E_SLS_62000155";

    /** Check record exit. */
    public static final String E_SLS_66000114_NO_RECORD_EXIT = "E_SLS_66000114";

    /** Check pay off summary mapping exit. */
    public static final String E_SLS_66000163_NO_PAY_OFF_SUMMARY_MAPPING__EXIT = "E_SLS_66000163";

    /** Check default setting exit. */
    public static final String E_SLS_66000113_DEFAULT_SETTING_EXIT = "E_SLS_66000113";

    /** Check record is deleted by others. */
    public static final String E_SLS_66000115_HAS_DELETED_BY_OTHERS = "E_SLS_66000115";

    /** Delete confirm pop up. */
    public static final String I_SLS_06000103_DELETE_CONFIRM = "I_SLS_06000103";

    /** Update but deleted by other people. */
    public static final String E_SLS_66000115_UPDATE_BUT_DELETED = "E_SLS_66000115";

    /** Update but deleted by other people. */
    public static final String E_SLS_66000116_DATA_DOUBLE_CHECK = "E_SLS_66000116";

    /** Update but deleted by other people. */
    public static final String I_SLS_06000112_DATA_REGISTRATION_CONFIRM = "I_SLS_06000112";

    /** Date updating. */
    public static final String E_SLS_66000101_UPDATEING = "E_SLS_66000101";

    /** Date checked error. */
    public static final String E_SLS_66000104_NO_DATE_CHECKED = "E_SLS_66000104";

    /** Date delete failed. */
    public static final String E_SLS_66000105_DATA_DELETE_FAILED = "E_SLS_66000105";

    /** Date upload failed. */
    public static final String E_SLS_66000112_DATA_UPLOAD_FAILED = "E_SLS_66000112";

    /** Download failed. */
    public static final String E_SLS_66000103_DOWN_FAILED = "E_SLS_66000103";

    /** Check error. */
    public static final String E_SLS_66000107_CHACK_ERROR = "E_SLS_66000107";

    /** Alteration data uploading process has been completed. */
    public static final String I_SLS_06000108 = "I_SLS_06000108";

    /** It becomes error in integrity check. */
    public static final String E_SLS_66000124 = "E_SLS_66000124";

    /** An error occurs in transaction receiving process, so it cannot upload. */
    public static final String E_SLS_66000125 = "E_SLS_66000125";

    /** Payoff data import error occurs, so it cannot upload. */
    public static final String E_SLS_66000126 = "E_SLS_66000126";

    /** The file format is not correct. */
    public static final String E_SLS_66000176 = "E_SLS_66000176";

    /** Transaction data file is not correct. */
    public static final String E_SLS_66000177 = "E_SLS_66000177";

    /** It is not JSON format. */
    public static final String E_SLS_66000178 = "E_SLS_66000178";

    /** It becomes error in integrity check. */
    public static final String E_SLS_66000179 = "E_SLS_66000179";

    /** It becomes error in input check. */
    public static final String E_SLS_66000180 = "E_SLS_66000180";

    /**
     * The same data with store/business date/cash register number of target data are being revised
     * by another user.
     */
    public static final String E_SLS_66000101 = "E_SLS_66000101";

    /** Check data in common code master table. */
    public static final String E_SLS_62000126_NO_DATA_IN_COMMON_CODE = "E_SLS_62000126";
    /** Check data in sales report transaction detail table. */
    public static final String E_SLS_62000134_NO_DATA_IN_TRANSACTION_DETAIL = "E_SLS_62000134";
    /** Check data in g department class linkage master. */
    public static final String E_SLS_62000135_NO_DATA_IN_G_CLASS_LINKAGE = "E_SLS_62000135";
    /** Check data in color size master. */
    public static final String E_SLS_62000119_NO_DATA_IN_COLOR_SIZE_MASTER = "E_SLS_62000119";
    /** Check data in skulist detail. */
    public static final String E_SLS_62000124_NO_DATA_IN_SKULIST_DETAIL = "E_SLS_62000124";
    /** Check data in plu master. */
    public static final String E_SLS_62000130_NO_DATA_IN_PLU_MASTER = "E_SLS_62000130";

    /** Transaction data import error occurs so it cannot upload. */
    public static final String E_SLS_66000127 = "E_SLS_66000127";

    /** Error code - no data exists in common code table. */
    public static final String E_SLS_66000128_NO_DATA_EXISTS = "E_SLS_66000128";

    /** Error code - no data exists in translation store code table. */
    public static final String E_SLS_66000131_NO_DATA_EXISTS = "E_SLS_66000131";

    /** Error code - no data exists in business country state setting table. */
    public static final String E_SLS_66000162_NO_DATA_EXISTS = "E_SLS_66000162";

    /** Error code - no data exists by condition. */
    public static final String E_SLS_66000134_NO_DATA_EXISTS = "E_SLS_66000134";

    /** Code - payoff complete. */
    public static final String E_SLS_66000145_PAYOFF_COMPLETE = "E_SLS_66000145";

    /** Error Code - payoff data import error perform. */
    public static final String E_SLS_66000147_ERROR_PERFORM = "E_SLS_66000147";

    /** Code - alteration payoff data register complete. */
    public static final String I_SLS_06000109_REGISTER_COMPLETE = "I_SLS_06000109";

    /** Error code - no data exists in business country state setting table. */
    public static final String E_SLS_62000161_NO_DATA_EXISTS = "E_SLS_62000161";

    /** Error code - no data exists in store control master table. */
    public static final String E_SLS_62000162_NO_DATA_EXISTS = "E_SLS_62000162";

    /** Check Register No. */
    public static final String E_SLS_66000148 = "E_SLS_66000148";

    /** Error code -  Target data of sales payoff integrity check does not exist. */
    public static final String E_SLS_66000149 = "E_SLS_66000149";
    
    /** It becomes error in not null item check. */
    public static final String E_SLS_66000181 = "E_SLS_66000181";
    
    /** It becomes error in business date check. */
    public static final String E_SLS_66000182 = "E_SLS_66000182";
    
    /** It becomes error in correlation check. */
    public static final String E_SLS_66000183 = "E_SLS_66000183";
    
    /** It becomes error in tender id check. */
    public static final String E_SLS_66000184 = "E_SLS_66000184";
    
    /** It becomes error in total amount check. */
    public static final String E_SLS_66000185 = "E_SLS_66000185";
    
    /** Item master delete error. */
    public static final String E_SLS_62000153 = "E_SLS_62000153";
    
    /** Purge item master delete error. */
    public static final String E_SLS_62000154 = "E_SLS_62000154";
}
