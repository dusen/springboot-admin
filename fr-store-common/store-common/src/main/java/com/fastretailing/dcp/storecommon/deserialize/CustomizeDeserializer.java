/**
 * @(#)CustomizeDeserializer.java
 *
 *                                Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.deserialize;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fastretailing.dcp.common.constants.ErrorName;
import com.fastretailing.dcp.common.model.Detail;
import com.fastretailing.dcp.common.model.ResultObject;
import com.fastretailing.dcp.storecommon.exception.JsonParseToBooleanException;
import com.fastretailing.dcp.storecommon.message.SystemMessageSource;

/**
 * Abstract class that converts a string to Boolean.
 */
public abstract class CustomizeDeserializer extends JsonDeserializer<Boolean> {

    /** Message source. */
    @Autowired
    SystemMessageSource systemMessageSource;

    /** Platform short name. */
    @Value("${platform.name.short}")
    private String platformShortName;

    /** Gets a value synonymous with true. */
    public abstract String getTrueValue();

    /** Gets a value synonymous with false. */
    public abstract String getFalseValue();

    /** Message id when an invalid value is acquired. */
    private static final String MESSAGE_ID_DESERIALIZE_ERROR = "I_***_40000001";

    /**
     * Converts the character string set in the target field to Boolean. If an invalid value is
     * entered, throw an exception {@link JsonParseToBooleanException} with error information.
     * 
     * @param parser Parser for loading JSON content.
     * @param context Context for the process of deserialization a single root-level value.
     * 
     * @throws IOException Thrown when some input / output exception occurs.
     * @throws JsonProcessingException Thrown when an exception occurs in JSON content processing
     *         (analysis, generation) that is not a pure I / O problem.
     * 
     * @return Converted Boolean value (null / true / false).
     */
    @Override
    public Boolean deserialize(JsonParser parser, DeserializationContext context)
            throws IOException, JsonProcessingException {
        Boolean returnValue = null;
        String targetText = parser.getText();

        // Convert to boolean.
        if (targetText == null) {
            returnValue = null;
        } else if (getTrueValue().equals(targetText)) {
            returnValue = Boolean.TRUE;
        } else if (getFalseValue().equals(targetText)) {
            returnValue = Boolean.FALSE;
        } else {
            String fieldName = parser.getCurrentName();
            ResultObject resultObject = new ResultObject(ErrorName.Basis.VALIDATION_ERROR,
                    MESSAGE_ID_DESERIALIZE_ERROR.replace("***", platformShortName),
                    "Input parameters include invalid values.");

            List<Detail> details = new ArrayList<>();
            String[] messageArray = {fieldName, getTrueValue(), getFalseValue()};
            Detail detail = new Detail(fieldName, targetText,
                    systemMessageSource.getMessage(MESSAGE_ID_DESERIALIZE_ERROR, messageArray),
                    null);
            details.add(detail);
            resultObject.setDetails(details);

            throw new JsonParseToBooleanException(resultObject, parser,
                    "An undefined character string is set.");
        }

        return returnValue;
    }
}
