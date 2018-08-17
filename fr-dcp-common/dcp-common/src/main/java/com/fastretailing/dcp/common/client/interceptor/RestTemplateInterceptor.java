/**
 * @(#)RestTemplateInterceptor.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.client.interceptor;

import com.fastretailing.dcp.common.client.customization.CustomizationBufferingClientHttpResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * RestTemplate's interceptor.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Slf4j
public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

    /**
     * Http Header Connection:close
     */
    protected static final String HTTP_HEADER_CONNECTION_CLOSE = "close";

    /**
     * Intercept the given request, and return a response.<br>
     * @param request HttpRequest
     * @param body body the body of the request
     * @param execution execution the request execution
     * @return the response
     * @throws IOException IOException
     */
    @Override
    public ClientHttpResponse intercept(
            HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution
    ) throws IOException {

        this.logRequest(request, body);

        LocalDateTime start = LocalDateTime.now();

        request.getHeaders().setConnection(HTTP_HEADER_CONNECTION_CLOSE);
        request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        ClientHttpResponse response = execution.execute(request, body);
        CustomizationBufferingClientHttpResponseWrapper resWrapper
                = new CustomizationBufferingClientHttpResponseWrapper(response);
        LocalDateTime end = LocalDateTime.now();

        this.logResponse(resWrapper, start, end);

        return resWrapper;
    }

    /**
     * Log Request's information.<br>
     * @param request HttpRequest
     * @param body the body of the request
     */
    protected void logRequest(HttpRequest request, byte[] body)
            throws UnsupportedEncodingException {
        // Need to check debug enabled flag because it may take some time to make log messages.
        if (log.isDebugEnabled()) {
            log.debug("RestTemplate Request {");
            log.debug("      URI          : {}", request.getURI());
            log.debug("      Method       : {}", request.getMethod());
            log.debug("      Headers      : {}", request.getHeaders());
            log.debug("      Body         : {}",
                    StringUtils.toEncodedString(body, Charset.forName("UTF-8"))
            );
            log.debug("}");
        }
    }

    /**
     * Log Response's information.<br>
     * @param response ClientHttpResponse
     * @param start Request's start time
     * @param end Request's end time
     * @throws IOException IOException
     */
    protected void logResponse(
            ClientHttpResponse response,
            LocalDateTime start,
            LocalDateTime end
    ) throws IOException {
        // Need to check debug enabled flag because it may take some time to make log messages.
        if (log.isDebugEnabled()) {
            log.debug("RestTemplate Response {");
            log.debug("      Status       : {} {}",
                    response.getStatusCode(),
                    response.getStatusCode().getReasonPhrase()
            );
            log.debug("      Headers      : {}", response.getHeaders());
            log.debug("      Body         : {}",
                    StringUtils.toEncodedString(
                            StreamUtils.copyToByteArray(response.getBody()),
                            Charset.forName("UTF-8")
                    )
            );
            log.debug("      Performance  : {");
            log.debug("            Start    : {}", start);
            log.debug("            End      : {}", end);
            log.debug("            Duration : {}msec", Duration.between(start, end).toMillis());
            log.debug("      }");
            log.debug("}");
        }
    }
}
