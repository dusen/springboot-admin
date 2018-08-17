/**
 * @(#)CsvUtility.java
 *
 *                     Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactionerrorlist.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import org.supercsv.prefs.CsvPreference;
import org.supercsv.quote.AlwaysQuoteMode;
import com.github.mygreen.supercsv.io.CsvAnnotationBeanWriter;

public class CsvUtility {

    /**
     * Write all data to csv file.
     * 
     * @param writeFile Write file.
     * @param dataList List of data(super-csv annotation CsvBean and CsvColumn).
     * @param dataType Type of list elements.(No setting required).
     * @throws IOException If an I/O error occurs.
     */
    @SuppressWarnings("unchecked")
    public static <T> void writeAll(File writeFile, List<T> dataList, T... dataType)
            throws IOException {
        // Get data type.
        Class<T> classType = (Class<T>) dataType.getClass().getComponentType();
        writeAllImpl(Paths.get(writeFile.toURI()), dataList, classType);
        return;
    }

    /**
     * Write all data to csv file.
     * 
     * @param writeFilePath Output file path.
     * @param dataList List of data(super-csv annotation CsvBean and CsvColumn).
     * @param classType Type of list elements.
     * @throws IOException If an I/O error occurs.
     */
    private static <T> void writeAllImpl(Path writeFilePath, List<T> dataList, Class<T> classType)
            throws IOException {
        // Build csv preference.
        CsvPreference csvPreference = new CsvPreference.Builder(CsvPreference.STANDARD_PREFERENCE)
                .useQuoteMode(new AlwaysQuoteMode())
                .build();
        // Write csv.
        try (CsvAnnotationBeanWriter<T> csvWriter =
                new CsvAnnotationBeanWriter<>(classType, Files.newBufferedWriter(writeFilePath,
                        StandardCharsets.UTF_8, StandardOpenOption.CREATE), csvPreference)) {
            csvWriter.writeAll(dataList);
        }
    }
}
