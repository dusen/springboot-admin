/**
 * @(#)ClientRestTemplateHmacAuthenticationInterceptor.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.hmac.interceptor;

import com.fastretailing.dcp.common.exception.SystemException;
import com.fastretailing.dcp.common.hmac.configuration.HmacAuthenticationProperties;
import com.fastretailing.dcp.common.hmac.configuration.HmacProperties;
import com.fastretailing.dcp.common.hmac.context.HmacAuthenticationContext;
import com.fastretailing.dcp.common.hmac.context.HmacAuthenticationContextHolder;
import com.fastretailing.dcp.common.util.CommonUtility;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.MessageDigest;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * the interceptor of client restTemplate hmac authentication.
 * @author Fast Retailing
 * @version $Revision$
 */
@Slf4j
@Component
public class ClientRestTemplateHmacAuthenticationInterceptor
        implements ClientHttpRequestInterceptor {

    /**
     * Signature Base String.
     */
    private static final String HEADER_NAME_SIGNATURE_BASE = "SignatureBase";

    /**
     * header name authorization.
     */
    private static final String HEADER_NAME_AUTHORIZATION = "Authorization";

    /**
     * header name content md5.
     */
    private static final String HEADER_NAME_CONTENT_MD5 = "Content-MD5";

    /**
     * header name date.
     */
    private static final String HEADER_NAME_DATE = "Date";

    /**
     * header name host.
     */
    private static final String HEADER_NAME_HOST = "Host";

    /**
     * header name x fr client id.
     */
    private static final String HEADER_NAME_X_FR_CLIENT_ID = "X-FR-clientid";

    /**
     * header name x fr phumac algo.
     */
    private static final String HEADER_NAME_X_FR_PHUMAC_ALGO = "X-FR-phumac-algo";

    /**
     * header name x fr date.
     */
    private static final String HEADER_NAME_X_FR_DATE = "X-FR-date";

    /**
     * date formatter pattern:yyyy-MM-dd'T'HH:mm:ssxxx.
     */
    private static final String DATE_FORMATTER_PATTERN = "yyyy-MM-dd'T'HH:mm:ssxxx";

    /**
     * Message-Digest Algorithm 5.
     */
    private static final String MESSAGE_DIGEST_ALGORITHM_FIVE  = "MD5";

    /**
     * request header : Content-Type.
     */
    private static final String REQUEST_HEADER_CONTENT_TYPE  = "Content-Type";

    /**
     * Hash-based Message Authentication Code SHA256.
     */
    private static final String HASH_BASED_MESSAGE_AUTHENTICATION_CODE_SHA256 = "HmacSHA256";

    /**
     * Hash-based Message Authentication Code sha1.
     */
    private static final String HASH_BASED_MESSAGE_AUTHENTICATION_CODE_SHA1 = "HmacSHA1";

    /**
     * Hash-based Authentication Code 101.
     */
    private static final String HASH_BASED_AUTHENTICATION_CODE_101 = "101";

    /**
     * authentication header part : Signature.
     */
    private static final String AUTHENTICATION_HEADER_SIGNATURE = "Signature";

    /**
     * authentication header part : signature-type.
     */
    private static final String AUTHENTICATION_HEADER_SIGNATURE_SIGNATURE_TYPE = "signature-type";

    /**
     * authentication header part : FR-DEFAULT.
     */
    private static final String AUTHENTICATION_HEADER_SIGNATURE_FR_DEFAULT = "FR-DEFAULT";

    /**
     * authentication header part : nonce.
     */
    private static final String AUTHENTICATION_HEADER_SIGNATURE_NONCE = "nonce";

    /**
     * authentication header part : Signature.
     */
    private static final String AUTHENTICATION_HEADER_SIGNATURE_SIGNATURE = "signature";

    /**
     * hmac properties.
     */
    @Autowired
    private HmacProperties hmacProperties;

    /**
     * commonUtility.
     */
    @Autowired
    private CommonUtility commonUtility;

    /**
     * set hmac authentication info into request header.
     * @param request HttpRequest
     * @param body request body
     * @param execution ClientHttpRequestExecution
     * @return ClientHttpResponse
     * @throws IOException IOException
     */
    @Override
    public ClientHttpResponse intercept(HttpRequest request,
                                        byte[] body,
                                        ClientHttpRequestExecution execution)
            throws IOException {

        // get context from ThreadLocal
        HmacAuthenticationContext hmacAuthenticationContext =
                HmacAuthenticationContextHolder.getHmacAuthenticationContext();

        if (Objects.nonNull(hmacAuthenticationContext)) {
            try {
                HmacAuthenticationProperties hmacAuthenticationProperties =
                        hmacProperties.getHmac()
                                .get(hmacAuthenticationContext.getPlatformName());

                if (hmacAuthenticationProperties == null) {
                    log.error("The value[" + hmacAuthenticationContext.getPlatformName()
                            + "] of @HmacAuthentication does not exist.");
                    throw new SystemException();
                }

                // date time
                String date = getDate(request);

                // client id
                String clientId = hmacAuthenticationProperties.getClientId();

                // client secret
                String clientSecret = hmacAuthenticationProperties.getClientSecret();

                // get Signature
                Mac digest = getMacDigest(request, clientId, clientSecret);

                // content type
                String contentType = getContentType(request);

                // Content MD5
                String contentMd5 = getContentMd5(body);

                // 32bit number
                int nonce = (int) Math.floor(Math.random() * Integer.MAX_VALUE);

                // signature string
                String signatureString = getSignatureString(request,
                        contentType,
                        date,
                        contentMd5,
                        clientId,
                        nonce);

                // authentication header
                String authenticationHeader =
                        getAuthenticationHeader(signatureString, nonce, digest);

                // set request header
                setRequestHeader(request, authenticationHeader,
                        clientId, date, contentMd5);

                // print log
                printLog(request, signatureString,
                        authenticationHeader, clientId, date, contentMd5);

            } catch (Exception e) {
                log.error("Exception occurred in HmacAuthenticationInterceptor :", e);
                throw new SystemException();
            }
        }

        return execution.execute(request, body);
    }

    /**
     * get date from request header, if date does not exist in header,
     * get system date.
     *
     * @param request http request
     * @return date
     */
    private String getDate(HttpRequest request) {
        StringBuilder date = new StringBuilder();
        if (CollectionUtils.isNotEmpty(request.getHeaders().get(HEADER_NAME_X_FR_DATE))) {
            Optional<String> headerDate = request.getHeaders()
                    .get(HEADER_NAME_X_FR_DATE).stream().findFirst();
            date.append(headerDate.get());
        } else {

            ZonedDateTime now = commonUtility.getOperationAt().atZone(ZoneId.of("UTC"));
            date.append(DateTimeFormatter.ofPattern(DATE_FORMATTER_PATTERN).format(now));
        }

        return date.toString();
    }

    /**
     * get mac digest according request and client secret.
     *
     * @param request http request
     * @param clientId client id
     * @param clientSecret client secret
     * @return mac digest
     */
    private Mac getMacDigest(HttpRequest request, String clientId, String clientSecret)
            throws Exception {

        Mac digest = null;
        SecretKeySpec secretKey = null;

        if (request.getHeaders().get(HEADER_NAME_X_FR_PHUMAC_ALGO) != null
                && request.getHeaders().get(HEADER_NAME_X_FR_PHUMAC_ALGO)
                .contains(HASH_BASED_AUTHENTICATION_CODE_101)) {
            secretKey = new SecretKeySpec(clientSecret.getBytes(),
                    HASH_BASED_MESSAGE_AUTHENTICATION_CODE_SHA1);
            digest = Mac.getInstance(HASH_BASED_MESSAGE_AUTHENTICATION_CODE_SHA1);
            log.warn("client id : " + clientId
                    + ", Hash-based Message Authentication Code: "
                    + HASH_BASED_MESSAGE_AUTHENTICATION_CODE_SHA1);
        } else {
            secretKey = new SecretKeySpec(clientSecret.getBytes(),
                    HASH_BASED_MESSAGE_AUTHENTICATION_CODE_SHA256);
            digest = Mac.getInstance(HASH_BASED_MESSAGE_AUTHENTICATION_CODE_SHA256);
        }

        digest.init(secretKey);
        return digest;
    }

    /**
     * get content type according request.
     *
     * @param request http request
     * @return content type
     */
    private String getContentType(HttpRequest request) throws Exception {


        List<String> contentTypeList = request.getHeaders().get(REQUEST_HEADER_CONTENT_TYPE);
        String contentType = null;
        if (CollectionUtils.isNotEmpty(contentTypeList)) {
            contentType = contentTypeList.stream()
                    .findFirst()
                    .orElse(MediaType.APPLICATION_JSON_VALUE);
        } else {
            contentType = MediaType.APPLICATION_JSON_VALUE;
            request.getHeaders().set(REQUEST_HEADER_CONTENT_TYPE, contentType);
        }

        return contentType;
    }

    /**
     * get content md5 according body.
     *
     * @param body body
     * @return content md5
     */
    private String getContentMd5(byte[] body) throws Exception {

        MessageDigest md5 = MessageDigest.getInstance(MESSAGE_DIGEST_ALGORITHM_FIVE);
        return Base64.getEncoder().encodeToString(md5.digest(
                Objects.nonNull(body) ? body : StringUtils.EMPTY.getBytes()));
    }

    /**
     * get signature string.
     *
     * @param contentType contentType
     * @param date date
     * @param contentMd5 contentMd5
     * @param clientId clientId
     * @param nonce nonce
     * @return AuthenticationHeader
     */
    private String getSignatureString(HttpRequest request,
                                      String contentType,
                                      String date,
                                      String contentMd5,
                                      String clientId,
                                      Integer nonce) throws Exception {


        StringBuilder signatureString = new StringBuilder();
        signatureString.append(request.getMethod().name());
        signatureString.append(request.getURI().getPath());
        signatureString.append(contentType);
        signatureString.append(date);
        signatureString.append(contentMd5);
        signatureString.append(clientId);
        signatureString.append(nonce);
        return signatureString.toString();

    }

    /**
     * get authentication header.
     *
     * @param signatureString signature string
     * @param nonce nonce
     * @param digest digest
     * @return AuthenticationHeader
     */
    private String getAuthenticationHeader(String signatureString,
                                           Integer nonce,
                                           Mac digest) throws Exception {

        // 32bit number
        digest.update(signatureString.getBytes());

        // get signature
        String signature = Base64.getEncoder().encodeToString(digest.doFinal());

        // get authentication header
        StringBuilder authenticationHeader = new StringBuilder();
        authenticationHeader.append(AUTHENTICATION_HEADER_SIGNATURE)
                .append(" ")
                .append(AUTHENTICATION_HEADER_SIGNATURE_SIGNATURE_TYPE)
                .append(":\"")
                .append(AUTHENTICATION_HEADER_SIGNATURE_FR_DEFAULT)
                .append("\",")
                .append(AUTHENTICATION_HEADER_SIGNATURE_NONCE)
                .append(":\"")
                .append(nonce)
                .append("\",")
                .append(AUTHENTICATION_HEADER_SIGNATURE_SIGNATURE)
                .append(":\"")
                .append(signature)
                .append("\"");

        return authenticationHeader.toString();
    }

    /**
     * set log.
     *
     * @param request HttpRequest
     * @param signatureString signature string
     * @param authenticationHeader authentication header
     * @param clientId client id
     * @param date date
     * @param contentMd5 content md5
     */
    private void printLog(HttpRequest request,
                          String signatureString,
                          String authenticationHeader,
                          String clientId,
                          String date,
                          String contentMd5) throws Exception {

        log.debug(HEADER_NAME_SIGNATURE_BASE + ":" + signatureString.toString());
        log.debug(HEADER_NAME_AUTHORIZATION + ":" + authenticationHeader);
        log.debug(HEADER_NAME_X_FR_CLIENT_ID + ":" + clientId);
        log.debug(HEADER_NAME_DATE + ":" + date);
        log.debug(HEADER_NAME_CONTENT_MD5 + ":" + contentMd5);

        log.info("Hmac Authentication Information Start:");
        log.info(request.getURI().toString());
        log.info(request.getURI().getHost());
        log.info(request.getMethod().toString());
        log.info(request.getHeaders().toString());
        log.info("Hmac Authentication Information End.");
    }

    /**
     * set request header.
     *
     * @param request HttpRequest
     * @param authenticationHeader authentication header
     * @param clientId client id
     * @param date date
     * @param contentMd5 content md5
     */
    private void setRequestHeader(HttpRequest request,
                                  String authenticationHeader,
                                  String clientId,
                                  String date,
                                  String contentMd5) throws Exception {

        request.getHeaders().set(HEADER_NAME_AUTHORIZATION, authenticationHeader);
        request.getHeaders().set(HEADER_NAME_X_FR_CLIENT_ID, clientId);
        request.getHeaders().set(HEADER_NAME_DATE, date);
        request.getHeaders().set(HEADER_NAME_CONTENT_MD5, contentMd5);
        request.getHeaders().set(HEADER_NAME_HOST, request.getURI().getHost());
    }
}


