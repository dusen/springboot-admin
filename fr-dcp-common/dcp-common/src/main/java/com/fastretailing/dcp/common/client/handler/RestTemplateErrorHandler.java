/**
 * @(#)RestTemplateErrorHandler.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.client.handler;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * RestTemplate's ErrorHandler.<br>
 * This error handler will be ignore the IOException and continue to follow-up processing.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Component
@Slf4j
public class RestTemplateErrorHandler extends DefaultResponseErrorHandler {

    /**
     * The method always return false(has not error) that
     * ignore the RestTemplate communication's IOException and continue to follow-up processing.
     * Please judge the RestTemplate communication's status in the business layer.<br>
     *
     * @param response ClientHttpResponse
     * @return always return true (has not error)
     * @throws IOException IOException
     */
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        try {
            if (response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR
                    || response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {
                if (log.isInfoEnabled()) {
                    log.info(
                                  "Http Status:"
                                + response.getStatusCode().value()
                                + " "
                                + response.getStatusCode().getReasonPhrase()
                    );
                }
            }
        } catch (IOException ex) {
            if (log.isInfoEnabled()) {
                log.info("Http Status:" + ex.toString());
            }
        }
        return false;
    }

}
