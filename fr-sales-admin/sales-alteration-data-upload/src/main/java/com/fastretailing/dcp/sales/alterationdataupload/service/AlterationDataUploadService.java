/**
 * @(#)AlterationDataUploadService.java
 *
 *                                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.alterationdataupload.service;

import org.springframework.web.multipart.MultipartFile;
import com.fastretailing.dcp.common.exception.BusinessException;

/**
 * Service interface for alteration data upload service.
 * 
 */
public interface AlterationDataUploadService {
    /**
     * Handle transaction zip file.
     * 
     * @param multipartFile The transaction zip file.
     * @param loginUserId The user id of login user.
     * @return The result of handle transaction zip.
     * @throws BusinessException business exception.
     */
    boolean handleTransactionZip(MultipartFile multipartFile, String loginUserId)
            throws BusinessException;

    /**
     * Handle transaction JSON data.
     * 
     * @param multipartFile The transaction JSON file.
     * @param loginUserId The user id of login user.
     * @return The result of handle transaction json.
     * @throws BusinessException business exception.
     */
    boolean handleTransactionJson(MultipartFile multipartFile, String loginUserId)
            throws BusinessException;

    /**
     * Handle pay off JSON data.
     * 
     * @param multipartFile The payoff JSON file.
     * @param loginUserId The user id of login user.
     * @return Display form of transfer out instruction detail.
     * @throws BusinessException business exception.
     */
    String handlePayoffJson(MultipartFile multipartFile, String loginUserId)
            throws BusinessException;
}
