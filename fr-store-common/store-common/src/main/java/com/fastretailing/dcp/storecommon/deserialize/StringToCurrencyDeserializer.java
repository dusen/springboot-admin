/**
 * @(#)StringToCurrencyDeserializer.java
 *
 *                                       Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.deserialize;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fastretailing.dcp.common.constants.ErrorName;
import com.fastretailing.dcp.common.model.Detail;
import com.fastretailing.dcp.common.model.ResultObject;
import com.fastretailing.dcp.storecommon.exception.JsonParseToCurrencyException;
import com.fastretailing.dcp.storecommon.message.SystemMessageSource;

/**
 * String to java.util.Currency converter.
 */
public class StringToCurrencyDeserializer extends JsonDeserializer<Currency> {

    /** Message source. */
    @Autowired
    SystemMessageSource systemMessageSource;

    @Value("${platform.name.short}")
    private String platformShortName;

    /** Message id when an invalid value is acquired. */
    private static final String MESSAGE_ID_DESERIALIZE_ERROR = "I_***_40000001";

    /**
     * Converts the character string set in the target field to java.util.Currency.<br />
     * If an invalid value is entered, return null.
     * 
     * @param parser Parser for loading JSON content.
     * @param context Context for the process of deserialization a single root-level value.
     * 
     * @throws IOException Thrown when some input / output exception occurs.
     * 
     * @return Currency or null.
     */
    @Override
    public Currency deserialize(JsonParser parser, DeserializationContext context)
            throws IOException {

        String targetText = parser.getText();
        String fieldName = parser.getCurrentName();

        if (targetText == null) {
            return null;
        }

        try {
            return Currency.getInstance(targetText);
        } catch (IllegalArgumentException e) {
            ResultObject resultObject = new ResultObject(ErrorName.Basis.VALIDATION_ERROR,
                    MESSAGE_ID_DESERIALIZE_ERROR.replace("***", platformShortName),
                    "Input parameters include invalid values.");

            List<Detail> details = new ArrayList<>();
            String[] messageArray = {fieldName, "ISO 4217 Currency code", "null"};
            Detail detail = new Detail(fieldName, targetText,
                    systemMessageSource.getMessage(MESSAGE_ID_DESERIALIZE_ERROR, messageArray),
                    null);
            details.add(detail);
            resultObject.setDetails(details);

            throw new JsonParseToCurrencyException(resultObject, parser,
                    "An undefined character string is set.");
        }
    }
}
