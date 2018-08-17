/**
 * @(#)BodyReaderHttpServletRequestWrapper.java
 *
 *  Copyright (c) 2017 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.common.api.log;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.HttpMethod;
import org.springframework.util.StreamUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;
import com.fastretailing.dcp.common.util.JsonUtility;
import lombok.Getter;

/**
 * <pre>
 * BodyReaderHttpServletRequestWrapper<br>
 *     Copy and save the InputStream in the HttpServletRequest,<br>
 *     so that the body of the request can be read repeatedly.
 * </pre>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public class BodyReaderHttpServletRequestWrapper extends ContentCachingRequestWrapper {

    /**
     * The request's InputStream is saved in requestBody.
     */
    @Getter
    private final byte[] requestBody;

    /**
     * Byte array requestBody conversion saved in bodyObject.
     */
    @Getter
    private Object bodyObject;

    public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.requestBody = StreamUtils.copyToByteArray(request.getInputStream());
        if (ArrayUtils.isNotEmpty(requestBody) && isJsonRequest(request)) {
            this.bodyObject = JsonUtility
                    .toObject(new String(requestBody, Charset.forName("UTF-8")), Object.class);
        }
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    /**
     * Use the current class object can read InputStream repeatedly
     *
     * @return InputStream
     * @throws IOException
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {

        final ByteArrayInputStream stream = new ByteArrayInputStream(requestBody);
        return new ServletInputStream() {

            @Override
            public int read() throws IOException {
                return stream.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }
        };
    }

    /**
     * Judge whether the request is json format.
     * 
     * @param request Http request.
     * @return True: json format, False: not json format.
     */
    private boolean isJsonRequest(HttpServletRequest request) {
        return !HttpMethod.GET.matches(request.getMethod())
                && request.getContentType().toUpperCase().indexOf("JSON") >= 0;
    }
}
