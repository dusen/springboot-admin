/**
 * @(#)IssueCode.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.model;

import java.util.HashMap;
import java.util.Map;

/**
 * IssueCode's entity.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public class IssueCode {

    /**
     * map saved validation error type and issueCode.
     */
    public static final Map<String, String> CODE_MAP = new HashMap<>();

    static {
        CODE_MAP.put("NotNull",                     "400-001");
        CODE_MAP.put("Null",                        "400-002");
        CODE_MAP.put("Pattern",                     "400-003");
        CODE_MAP.put("Min",                         "400-004");
        CODE_MAP.put("Max",                         "400-005");
        CODE_MAP.put("DecimalMin",                  "400-006");
        CODE_MAP.put("DecimalMax",                  "400-007");
        CODE_MAP.put("Size",                        "400-008");
        CODE_MAP.put("Range",                       "400-009");
        CODE_MAP.put("AssertTrue",                  "400-010");
        CODE_MAP.put("AssertFalse",                 "400-011");
        CODE_MAP.put("Future",                      "400-012");
        CODE_MAP.put("Past",                        "400-013");
        CODE_MAP.put("CreditCardNumber",            "400-014");
        CODE_MAP.put("Email",                       "400-015");
        CODE_MAP.put("URL",                         "400-016");
        CODE_MAP.put("NotBlank",                    "400-017");
        CODE_MAP.put("NotEmpty",                    "400-018");
        CODE_MAP.put("Alphabet",                    "400-019");
        CODE_MAP.put("AlphaNumberMajuscule",        "400-020");
        CODE_MAP.put("Alphanumeric",                "400-021");
        CODE_MAP.put("ByteLength",                  "400-022");
        CODE_MAP.put("CombinationRequire",          "400-023");
        CODE_MAP.put("ConditionalEmpty",            "400-024");
        CODE_MAP.put("ConditionalRequire",          "400-025");
        CODE_MAP.put("FieldEquals",                 "400-026");
        CODE_MAP.put("FieldNotEquals",              "400-027");
        CODE_MAP.put("FixedLength",                 "400-028");
        CODE_MAP.put("FullWidth",                   "400-029");
        CODE_MAP.put("FutureDate",                  "400-030");
        CODE_MAP.put("ExistInCodeList",             "400-031");
        CODE_MAP.put("MagnitudeRelationNotEqual",   "400-032");
        CODE_MAP.put("MagnitudeRelationEqual",      "400-033");
        CODE_MAP.put("Number",                      "400-034");
        CODE_MAP.put("PastDate",                    "400-035");
        CODE_MAP.put("PositiveNumber",              "400-036");
        CODE_MAP.put("RequireCorrelation",          "400-037");
        CODE_MAP.put("StringDate",                  "400-038");
        CODE_MAP.put("Digits",                      "400-039");
        CODE_MAP.put("FrontMemberIdNotFound",       "401-002");
        CODE_MAP.put("AdminUserIdNotFound",         "401-003");
    }

}
