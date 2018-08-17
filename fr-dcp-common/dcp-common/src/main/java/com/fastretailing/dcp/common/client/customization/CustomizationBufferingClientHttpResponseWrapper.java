/**
 * @(#)CustomizationBufferingClientHttpResponseWrapper.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.client.customization;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

/**
 * Simple implementation of {@link ClientHttpResponse} that reads the response's body
 * into memory, thus allowing for multiple invocations of {@link #getBody()}.
 * If the response is null that HttpStatusCode is 500(INTERNAL_SERVER_ERROR).
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public class CustomizationBufferingClientHttpResponseWrapper implements ClientHttpResponse {

    /**
     * Represents a client-side HTTP response.<br>
     */
    private final ClientHttpResponse response;

    /**
     * HttpResponse's body.<br>
     */
    private byte[] body;

    /**
     * Wrap the RestTemplate communication's response object
     * and cache the response's body into memory.
     * If the response is null that HttpStatusCode is 500(INTERNAL_SERVER_ERROR). <br>
     * @param response Response
     */
    public CustomizationBufferingClientHttpResponseWrapper(ClientHttpResponse response) {
        assert response != null;
        this.response = response;
    }

    /**
     * Return the HTTP status code of the response.
     * @return the HTTP status as an HttpStatus enum value
     * @throws IOException in case of I/O errors
     */
    @Override
    public HttpStatus getStatusCode() throws IOException {
        return this.response.getStatusCode();
    }

    /**
     * Return the HTTP status code of the response as integer.
     * @return the HTTP status as an integer
     * @throws IOException in case of I/O errors
     */
    @Override
    public int getRawStatusCode() throws IOException {
        return this.response.getRawStatusCode();
    }

    /**
     * Return the HTTP status text of the response.
     * @return the HTTP status text
     * @throws IOException in case of I/O errors
     */
    @Override
    public String getStatusText() throws IOException {
        return this.response.getStatusText();
    }

    /**
     * Close this response, freeing any resources created.
     */
    @Override
    public void close() {
        this.response.close();
    }

    /**
     * Return the body of the message as an input stream.
     * @return the input stream body (never {@code null})
     * @throws IOException in case of I/O Errors
     */
    @Override
    public InputStream getBody() throws IOException {
        if (this.body == null) {
            this.body = StreamUtils.copyToByteArray(this.response.getBody());
        }
        return new ByteArrayInputStream(this.body);
    }

    /**
     * Return the headers of this message.
     * @return a corresponding HttpHeaders object (never {@code null})
     */
    @Override
    public HttpHeaders getHeaders() {
        return this.response.getHeaders();
    }
}
