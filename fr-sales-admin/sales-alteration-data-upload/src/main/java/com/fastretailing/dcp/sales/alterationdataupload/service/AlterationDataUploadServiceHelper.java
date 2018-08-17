/**
 * @(#)AlterationDataUploadServiceHelper.java
 *
 *                                            Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.alterationdataupload.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fastretailing.dcp.sales.common.constants.CodeConstants;

/**
 * Alteration data upload service helper.
 */
@Component
public class AlterationDataUploadServiceHelper {

    /**
     * @param zipInputStream Zip input stream.
     * @param type Class type.
     * @return List of each class.
     * @throws IOException io exception.
     */
    public <T> List<T> convertCsvToList(ZipInputStream zipInputStream, Class<?> type)
            throws IOException {
        List<T> outputList = new ArrayList<>();
        CsvMapper mapper = new CsvMapper();
        CsvSchema orderInformationSchema = mapper.schemaFor(type).withHeader().withColumnSeparator(
                CodeConstants.COMMA_SEPARATOR.charAt(0));
        ObjectReader orderInformationReader = mapper.readerFor(type).with(orderInformationSchema);
        try (Reader reader = new InputStreamReader(convertToInputStream(zipInputStream))) {
            MappingIterator<T> mi = orderInformationReader.readValues(reader);
            while (mi.hasNext()) {
                outputList.add(mi.next());
            }
        }

        return outputList;

    }

    /**
     * Handle the zip file input stream.
     * 
     * @param inputStream The zip file of input stream.
     * @return Byte array input stream.
     * @throws IOException io exception.
     */
    private static InputStream convertToInputStream(final ZipInputStream inputStream)
            throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOUtils.copy(inputStream, out);
        return new ByteArrayInputStream(out.toByteArray());
    }
}
