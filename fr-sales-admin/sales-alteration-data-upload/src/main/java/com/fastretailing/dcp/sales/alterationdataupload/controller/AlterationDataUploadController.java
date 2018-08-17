/**
 * @(#)AlterationDataUploadController.java
 *
 *                                         Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.alterationdataupload.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastretailing.dcp.sales.alterationdataupload.common.Constants;
import com.fastretailing.dcp.sales.alterationdataupload.form.AlterationDataUploadForm;
import com.fastretailing.dcp.sales.alterationdataupload.service.AlterationDataUploadService;
import com.fastretailing.dcp.sales.common.constants.MessagePrefix;
import com.fastretailing.dcp.storecommon.screen.form.CommonBaseForm;
import com.fastretailing.dcp.storecommon.screen.form.FormBuilder;
import com.fastretailing.dcp.storecommon.screen.message.LocaleMessageSource;
import com.fastretailing.dcp.storecommon.screen.message.MessageType;

/**
 * The controller class for alteration data upload page.
 */

@Controller
@RequestMapping(path = "/{brand}/{region}/screen/alteration-dataup")
public class AlterationDataUploadController {
    /** Zip file size. */
    private static final int ZIP_FILE_ENTRY_CHECK_MAX_LIST_SIZE = 8;
    /** Alteration data upload page. */
    private static final String ALTERATION_DATA_UPLOAD = "alteration-data-upload";
    /** File type zip. */
    private static final String FILE_TYPE_ZIP = "zip";
    /** File type JSON. */
    private static final String FILE_TYPE_JSON = "json";
    /** File type csv. */
    private static final String FILE_TYPE_CSV = "csv";
    /** Sales transaction data. */
    private static final String SALES_TRANSACTION_DATA = "0";
    /** Payoff data. */
    private static final String PAY_OFF_DATA = "1";
    /** Dot. */
    private static final String DOT = ".";
    /** Integrity check error. */
    private static final String INTEGRITY_CHECK_ERROR = "integrityCheckError";
    /** Revised by another user. */
    private static final String REVISED_BY_ANOTHER_USER = "revisedByAnotherUser";
    /** Alteration data upload form. */
    private static final String ALTERATION_DATA_UPLOAD_FORM = "alterationDataUploadForm";

    /** Component for getting message by locale. */
    @Autowired
    private LocaleMessageSource localeMessageSource;

    /** Alteration data upload service. */
    @Autowired
    private AlterationDataUploadService alterationDataUploadService;

    /**
     * Display the alteration data upload page.
     *
     * @param commonBaseForm The form of alteration data upload page.
     * @param model The model attribute for this page.
     * @return The name of alteration data upload page.
     */
    @RequestMapping(path = "")
    public String displayInitPage(CommonBaseForm commonBaseForm, Model model) {

        AlterationDataUploadForm alterationDataUploadForm =
                FormBuilder.build(AlterationDataUploadForm.class, commonBaseForm);
        alterationDataUploadForm.setAlterationDataType(SALES_TRANSACTION_DATA);
        model.addAttribute(ALTERATION_DATA_UPLOAD_FORM, alterationDataUploadForm);
        return ALTERATION_DATA_UPLOAD;
    }

    /**
     * Receiver the file from client.
     *
     * @param multipartFile The upload file.
     * @param alterationDataUploadForm The alteration data upload form.
     * @return The form of alteration data upload form.
     * @throws Exception that occurred.
     */
    @PostMapping("/upload")
    @ResponseBody
    public AlterationDataUploadForm upload(@RequestParam("file") MultipartFile[] multipartFile,
            AlterationDataUploadForm alterationDataUploadForm) throws Exception {

        Path path = Paths.get(multipartFile[0].getOriginalFilename());
        String fileName = path.getFileName().toString();

        // Uploaded file's extension check.
        String alterationDataType = alterationDataUploadForm.getAlterationDataType();
        String extension = fileName.substring(fileName.indexOf(DOT) + 1);
        if (SALES_TRANSACTION_DATA.equals(alterationDataType)) {
            if (FILE_TYPE_ZIP.equals(extension)) {

                // The extension of the uploaded file is zip.
                List<String> fileFiexedList = new ArrayList<>();
                fileFiexedList.add(Constants.ORDER_INFORMATION_FILE_NAME_CSV);
                fileFiexedList.add(Constants.SALES_TRANSACTION_HEADER_FILE_NAME_CSV);
                fileFiexedList.add(Constants.ITEM_DETAIL_FILE_NAME_CSV);
                fileFiexedList.add(Constants.ITEM_DETAIL_DISCOUNT_FILE_NAME_CSV);
                fileFiexedList.add(Constants.ITEM_DETAIL_TAX_FILE_NAME_CSV);
                fileFiexedList.add(Constants.SALES_TRANSACTION_TAX_FILE_NAME_CSV);
                fileFiexedList.add(Constants.SALES_TRANSACTION_PAYMENT_FILE_NAME_CSV);
                fileFiexedList.add(Constants.SALES_TRANSACTION_TOTAL_FILE_NAME_CSV);

                List<String> fileFromZipList = new ArrayList<>();
                try {
                    fileFromZipList = getFileListFromZip(multipartFile[0]);
                } catch (IOException e) {
                    alterationDataUploadForm.setDetailError(MessagePrefix.E_SLS_66000177, null,
                            localeMessageSource.getMessage(MessagePrefix.E_SLS_66000177),
                            new String[] {}, MessageType.ERROR.getType());
                    return alterationDataUploadForm;
                }

                if (fileFromZipList.size() < ZIP_FILE_ENTRY_CHECK_MAX_LIST_SIZE) {
                    alterationDataUploadForm.setDetailError(MessagePrefix.E_SLS_66000177, null,
                            localeMessageSource.getMessage(MessagePrefix.E_SLS_66000177),
                            new String[] {}, MessageType.ERROR.getType());
                    return alterationDataUploadForm;
                }
                for (int i = 0; i < fileFromZipList.size(); i++) {
                    String fileNameFromZip = fileFromZipList.get(i);
                    String fileExtension =
                            fileNameFromZip.substring(fileNameFromZip.indexOf(DOT) + 1);
                    if (!FILE_TYPE_CSV.equals(fileExtension)) {
                        alterationDataUploadForm.setDetailError(MessagePrefix.E_SLS_66000176, null,
                                localeMessageSource.getMessage(MessagePrefix.E_SLS_66000176),
                                new String[] {}, MessageType.ERROR.getType());
                        return alterationDataUploadForm;
                    }
                }
                boolean matchResult =
                        fileFromZipList.stream().allMatch(name -> fileFiexedList.contains(name));
                if (!matchResult) {
                    alterationDataUploadForm.setDetailError(MessagePrefix.E_SLS_66000177, null,
                            localeMessageSource.getMessage(MessagePrefix.E_SLS_66000177),
                            new String[] {}, MessageType.ERROR.getType());
                    return alterationDataUploadForm;
                }
                alterationDataUploadService.handleTransactionZip(multipartFile[0],
                        alterationDataUploadForm.getLoginUserId());

                // The extension of the uploaded file is JSON.
            } else if (FILE_TYPE_JSON.equals(extension)) {
                if (isNotValidJson(multipartFile[0])) {
                    alterationDataUploadForm.setDetailError(MessagePrefix.E_SLS_66000178, null,
                            localeMessageSource.getMessage(MessagePrefix.E_SLS_66000178),
                            new String[] {}, MessageType.ERROR.getType());
                    return alterationDataUploadForm;
                }
                alterationDataUploadService.handleTransactionJson(multipartFile[0],
                        alterationDataUploadForm.getLoginUserId());
            } else {
                alterationDataUploadForm.setDetailError(MessagePrefix.E_SLS_66000176, null,
                        localeMessageSource.getMessage(MessagePrefix.E_SLS_66000176),
                        new String[] {}, MessageType.ERROR.getType());
                return alterationDataUploadForm;
            }
        } else if (PAY_OFF_DATA.equals(alterationDataType)) {
            // The extension of the uploaded file is not JSON.
            if (FILE_TYPE_JSON.equals(extension)) {
                // The extension of the uploaded file is JSON,but the content is not JSON format.
                if (isNotValidJson(multipartFile[0])) {
                    alterationDataUploadForm.setDetailError(MessagePrefix.E_SLS_66000178, null,
                            localeMessageSource.getMessage(MessagePrefix.E_SLS_66000178),
                            new String[] {}, MessageType.ERROR.getType());
                    return alterationDataUploadForm;
                }

                String checkResult = alterationDataUploadService.handlePayoffJson(multipartFile[0],
                        alterationDataUploadForm.getLoginUserId());
                if (INTEGRITY_CHECK_ERROR.equals(checkResult)) {
                    alterationDataUploadForm.setDetailError(MessagePrefix.E_SLS_66000124, null,
                            localeMessageSource.getMessage(MessagePrefix.E_SLS_66000124),
                            new String[] {}, MessageType.ERROR.getType());
                    return alterationDataUploadForm;
                } else if (REVISED_BY_ANOTHER_USER.equals(checkResult)) {
                    alterationDataUploadForm.setDetailError(MessagePrefix.E_SLS_66000101, null,
                            localeMessageSource.getMessage(MessagePrefix.E_SLS_66000101),
                            new String[] {}, MessageType.ERROR.getType());
                    return alterationDataUploadForm;
                }

            } else {
                alterationDataUploadForm.setDetailError(MessagePrefix.E_SLS_66000176, null,
                        localeMessageSource.getMessage(MessagePrefix.E_SLS_66000176),
                        new String[] {}, MessageType.ERROR.getType());
                return alterationDataUploadForm;
            }
        }

        // Handle the request success.
        alterationDataUploadForm.setDetailError(MessagePrefix.I_SLS_06000108, null,
                localeMessageSource.getMessage(MessagePrefix.I_SLS_06000108), new String[] {},
                MessageType.INFORMATION.getType());
        return alterationDataUploadForm;
    }

    /**
     * Get file name list.
     *
     * @param multipartFile The uncompressed files.
     * @return File name list.
     * @throws IOException io exception.
     */
    private List<String> getFileListFromZip(MultipartFile multipartFile) throws IOException {
        List<String> fileNameList = new ArrayList<>();
        try (ZipInputStream zipInputStream = new ZipInputStream(multipartFile.getInputStream())) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                fileNameList.add(entry.getName());
                zipInputStream.closeEntry();
            }
            zipInputStream.close();
        }
        return fileNameList;
    }

    /**
     * Check if the file is correct JSON format.
     * 
     * @param multipartFile The file to check.
     * @return The result of check file.
     */
    private boolean isNotValidJson(MultipartFile multipartFile) {
        boolean valid = false;
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.readTree(multipartFile.getInputStream());
        } catch (IOException e) {
            valid = true;
        }
        return valid;
    }
}
