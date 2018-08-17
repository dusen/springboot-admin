/**
 * @(#)FileHelper.java
 *
 *                     Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fastretailing.dcp.storecommon.message.SystemMessageSource;
import lombok.extern.slf4j.Slf4j;

/**
 * File access utility.
 */
@Slf4j
@Component
public class FileHelper {

    /** File delete message code. */
    private static final String MESSAGE_CODE_FILE_DELETE = "I_***_40000002";

    /** System message source. */
    @Autowired
    private SystemMessageSource systemMessageSource;

    /**
     * If file exists delete it.
     * 
     * @param fullFileName Delete file name.
     * @throws IOException If an I/O error occurs.
     */
    public void deleteFile(String fullFileName) throws IOException {

        // If file is exists delete file.
        if (Files.deleteIfExists(Paths.get(fullFileName))) {
            log.info(systemMessageSource.getMessage(MESSAGE_CODE_FILE_DELETE,
                    new Object[] {fullFileName}));
        }
    }
}
