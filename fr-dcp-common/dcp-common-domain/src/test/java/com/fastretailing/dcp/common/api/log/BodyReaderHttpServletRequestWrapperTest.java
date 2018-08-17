/**
 * @(#)BodyReaderHttpServletRequestWrapper.java
 *
 *  Copyright (c) 2017 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.common.api.log;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.amazonaws.HttpMethod;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({@ContextConfiguration(
        locations = {"classpath*:com/fastretailing/dcp/common/api/log/test-context.xml"})})
public class BodyReaderHttpServletRequestWrapperTest {

    /**
     * Check the request is json and method is get.
     * 
     * @throws IOException
     */
    @Test
    public void testGetMethodRequest() throws IOException {

        HttpServletRequest request = mock(HttpServletRequest.class);
        ServletInputStream instream = createServletInputStream("\"aaa\": \"1\"", "UTF-8");

        when(request.getMethod()).thenReturn(HttpMethod.GET.name());
        when(request.getContentType()).thenReturn("application/json");
        when(request.getInputStream()).thenReturn(instream);

        BodyReaderHttpServletRequestWrapper bodyReader =
                new BodyReaderHttpServletRequestWrapper(request);

        assertNull(bodyReader.getBodyObject());
    }

    /**
     * Check the request is json and method is post.
     * 
     * @throws IOException
     */
    @Test
    public void testPostJsonRequest() throws IOException {

        HttpServletRequest request = mock(HttpServletRequest.class);
        ServletInputStream instream = createServletInputStream("\"aaa\": \"1\"", "UTF-8");

        when(request.getMethod()).thenReturn(HttpMethod.POST.name());
        when(request.getContentType()).thenReturn("application/json");
        when(request.getInputStream()).thenReturn(instream);

        BodyReaderHttpServletRequestWrapper bodyReader =
                new BodyReaderHttpServletRequestWrapper(request);

        assertNotNull(bodyReader.getBodyObject());
    }
    
    /**
     * Check the request is json and method is put.
     * 
     * @throws IOException
     */
    @Test
    public void testPutJsonRequest() throws IOException {

        HttpServletRequest request = mock(HttpServletRequest.class);
        ServletInputStream instream = createServletInputStream("\"aaa\": \"1\"", "UTF-8");

        when(request.getMethod()).thenReturn(HttpMethod.PUT.name());
        when(request.getContentType()).thenReturn("application/json");
        when(request.getInputStream()).thenReturn(instream);

        BodyReaderHttpServletRequestWrapper bodyReader =
                new BodyReaderHttpServletRequestWrapper(request);

        assertNotNull(bodyReader.getBodyObject());
    }

    /**
     * Check the request is form/data and method is post.
     * 
     * @throws IOException
     */
    @Test
    public void testPostAndJsonRequest() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        ServletInputStream instream = createServletInputStream("\"aaa\": \"1\"", "UTF-8");

        when(request.getMethod()).thenReturn(HttpMethod.POST.name());
        when(request.getContentType()).thenReturn("multipart/form-data");
        when(request.getInputStream()).thenReturn(instream);

        BodyReaderHttpServletRequestWrapper bodyReader =
                new BodyReaderHttpServletRequestWrapper(request);

        assertNull(bodyReader.getBodyObject());
    }

    /**
     * Return the servletInputStream from string.
     * 
     * @param content Content.
     * @param charset Charset.
     * @return Servlet input stream.
     */
    private ServletInputStream createServletInputStream(String content, String charset) {
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        try {
            byteOutputStream.write(content.getBytes(charset));
        } catch (Exception e) {
            throw new RuntimeException("No support charset.");
        }

        final InputStream byteInputStream = new ByteArrayInputStream(byteOutputStream.toByteArray());

        return new ServletInputStream() {

            @Override
            public int read() throws IOException {
                return byteInputStream.read();
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
            public void setReadListener(ReadListener listener) {

            }
        };
    }
}

