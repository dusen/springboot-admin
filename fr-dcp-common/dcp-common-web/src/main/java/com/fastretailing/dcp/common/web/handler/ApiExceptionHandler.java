/**
 * @(#)ApiExceptionHandler.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.web.handler;

import com.fastretailing.dcp.common.constants.ErrorName;
import com.fastretailing.dcp.common.constants.LogLevel;
import com.fastretailing.dcp.common.exception.BusinessException;
import com.fastretailing.dcp.common.exception.ClientCannotAcquireLockException;
import com.fastretailing.dcp.common.exception.ExclusionException;
import com.fastretailing.dcp.common.exception.ExternalSystemException;
import com.fastretailing.dcp.common.exception.ForbiddenException;
import com.fastretailing.dcp.common.exception.NoAuthenticationException;
import com.fastretailing.dcp.common.exception.PreconditionFailedException;
import com.fastretailing.dcp.common.exception.ResourceNotFoundException;
import com.fastretailing.dcp.common.exception.SystemException;
import com.fastretailing.dcp.common.model.Detail;
import com.fastretailing.dcp.common.model.ResultObject;
import com.fastretailing.dcp.common.util.CommonUtility;
import com.fastretailing.dcp.common.util.ResultObjectUtility;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * handle the exceptions and business errors.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    /**
     * messageSource.
     */
    @Autowired
    private MessageSource messageSource;

    /**
     * commonUtility.
     */
    @Autowired
    private CommonUtility commonUtility;

    /**
     * map saved validation error type and issueCode.
     */
    private static final Map<String, String> logLevelMap = new HashMap<>();

    /**
     * initialize member variables when load class.
     */
    @PostConstruct
    private void initializeMemberVariables() {
        logLevelMap.put("D", "DEBUG");
        logLevelMap.put("I", "INFO");
        logLevelMap.put("W", "WARN");
        logLevelMap.put("E", "ERROR");
        logLevelMap.put("N", "NOTICE");
        logLevelMap.put("C", "CRITICAL");
    }

    /**
     * Handle the case where no request handler method was found
     * for the particular HTTP request method.
     * The default implementation logs a warning,
     * sends a HTTP 405 error, sets the "Allow" header,
     *
     * @param e the HttpRequestMethodNotSupportedException to be handled
     * @param response current HTTP response
     * @return ResultObject handled exception info
     * @throws IOException I/O exception
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ResultObject> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e, HttpServletResponse response)
            throws IOException {

        // log
        printExceptionStackTrace(commonUtility.getDebugId(LogLevel.WARN.toString(),
                "90000010"), "HttpRequestMethodNotSupportedException", e);

        String[] supportedMethods = e.getSupportedMethods();
        if (supportedMethods != null) {
            response.setHeader("Allow", StringUtils.arrayToDelimitedString(supportedMethods, ", "));
        }

        return setResponseEntity(ErrorName.Basis.UNAVAILABLE_REQUEST,
                "e.common.exception.request-method-not-supported",
                commonUtility.getDebugId(LogLevel.WARN.toString(), "90000010"),
                e,
                HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * Handle the case where no
     * {@linkplain org.springframework.http.converter.HttpMessageConverter
     * message converters}
     * were found that were acceptable for the client (expressed via the {@code Accept} header.
     * The default implementation sends a HTTP 415 error
     * Alternatively, a fallback view could be chosen,
     * or the HttpMediaTypeNotSupportedException
     *
     * @param e the HttpMediaTypeNotSupportedException to be handled
     * @param response current HTTP response
     * @return ResultObject handled exception info
     * @throws IOException I/O exception
     */
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public ResponseEntity<ResultObject> handleHttpMediaTypeNotSupportedException(
            HttpMediaTypeNotSupportedException e, HttpServletResponse response)
            throws IOException {

        // log
        printExceptionStackTrace(commonUtility.getDebugId(LogLevel.WARN.toString(),
                "90000012"), "HttpMediaTypeNotSupportedException", e);

        List<MediaType> mediaTypes = e.getSupportedMediaTypes();
        if (!CollectionUtils.isEmpty(mediaTypes)) {
            response.setHeader("Accept", MediaType.toString(mediaTypes));
        }

        return setResponseEntity(ErrorName.Basis.UNAVAILABLE_REQUEST,
                "e.common.exception.specified-media-type-not-supported",
                commonUtility.getDebugId(LogLevel.WARN.toString(), "90000012"),
                e,
                HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * Handle the case where no
     * {@linkplain org.springframework.http.converter.HttpMessageConverter
     * message converters}
     * were found that were acceptable for the client (expressed via the {@code Accept} header.
     * The default implementation sends a HTTP 406 error
     * Alternatively, a fallback view could be chosen,
     * or the HttpMediaTypeNotAcceptableException
     *
     * @param e the HttpMediaTypeNotAcceptableException to be handled
     * @return ResultObject handled exception info
     * @throws IOException I/O exception
     */
    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    public ResponseEntity<ResultObject> handleHttpMediaTypeNotAcceptable(
            HttpMediaTypeNotAcceptableException e) throws IOException {

        // log
        printExceptionStackTrace(commonUtility.getDebugId(LogLevel.WARN.toString(),
                "90000011"), "HttpMediaTypeNotAcceptableException", e);

        return setResponseEntity(ErrorName.Basis.UNAVAILABLE_REQUEST,
                "e.common.exception.request-media-type-not-supported",
                commonUtility.getDebugId(LogLevel.WARN.toString(), "90000011"),
                e,
                HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * Handle the case when a declared path variable does
     * not match any extracted URI variable.
     * The default implementation sends a HTTP 500 error,
     * Alternatively, a fallback view could be chosen,
     * or the MissingPathVariableException
     * could be rethrown as-is.
     *
     * @param e the MissingPathVariableException to be handled
     * @return ResultObject handled exception info
     * @throws IOException I/O exception
     */
    @ExceptionHandler({MissingPathVariableException.class})
    public ResponseEntity<ResultObject> handleMissingPathVariableException(
            MissingPathVariableException e) throws IOException {

        // log
        printExceptionStackTrace(commonUtility.getDebugId(LogLevel.CRITICAL.toString(),
                "90000001"), "handleMissingPathVariableException", e);

        return setResponseEntity(ErrorName.Basis.SYSTEM_ERROR,
                "e.common.exception.system-error",
                commonUtility.getDebugId(LogLevel.CRITICAL.toString(), "90000001"),
                e,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle the case when a required parameter is missing.
     * The default implementation sends a HTTP 400 error,
     * Alternatively, a fallback view could be chosen,
     * or the MissingServletRequestParameterException
     * could be rethrown as-is.
     *
     * @param e the MissingServletRequestParameterException to be handled
     * @return ResultObject handled exception info
     * @throws IOException I/O exception
     */
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ResponseEntity<ResultObject> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException e) throws IOException {

        // log
        printExceptionStackTrace(commonUtility.getDebugId(LogLevel.WARN.toString(),
                "90000001"), "handleMissingServletRequestParameterException", e);

        return setResponseEntity(ErrorName.Basis.UNAVAILABLE_REQUEST,
                "e.common.exception.required-parameter-missing",
                commonUtility.getDebugId(LogLevel.WARN.toString(), "90000001"),
                e,
                HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle the case when an unrecoverable binding exception occurs - e.g.
     * required header, required cookie.
     * The default implementation sends a HTTP 400 error,
     * Alternatively, a fallback view could be chosen,
     * or the exception could be rethrown as-is.
     *
     * @param e the ServletRequestBindingException to be handled
     * @return ResultObject handled exception info
     * @throws IOException I/O exception
     */
    @ExceptionHandler({ServletRequestBindingException.class})
    public ResponseEntity<ResultObject> handleServletRequestBindingException(
            ServletRequestBindingException e) throws IOException {

        // log
        printExceptionStackTrace(commonUtility.getDebugId(LogLevel.WARN.toString(),
                "90000002"), "handleServletRequestBindingException", e);

        return setResponseEntity(ErrorName.Basis.UNAVAILABLE_REQUEST,
                "e.common.exception.request-body-format-error",
                commonUtility.getDebugId(LogLevel.WARN.toString(), "90000002"),
                e,
                HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle the case when a {@link org.springframework.web.bind.WebDataBinder}
     * conversion cannot occur.
     * The default implementation sends a HTTP 500 error,
     * Alternatively, a fallback view could be chosen,
     * or the TypeMismatchException could be rethrown as-is.
     *
     * @param e the ConversionNotSupportedException to be handled
     * @return ResultObject handled exception info
     * @throws IOException I/O exception
     */
    @ExceptionHandler({ConversionNotSupportedException.class})
    public ResponseEntity<ResultObject> handleConversionNotSupportedException(
            ConversionNotSupportedException e) throws IOException {

        // log
        printExceptionStackTrace(commonUtility.getDebugId(LogLevel.CRITICAL.toString(),
                "90000002"), "handleConversionNotSupportedException", e);

        return setResponseEntity(ErrorName.Basis.SYSTEM_ERROR,
                "e.common.exception.system-error",
                commonUtility.getDebugId(LogLevel.CRITICAL.toString(), "90000002"),
                e,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * validation error occurred on item in the request body.
     * A HTTP 400 error is sent back to the client.
     *
     * @param e MethodArgumentNotValidException BindException
     * @return ResultObject
     */
    @ExceptionHandler({MethodArgumentNotValidException.class,BindException.class})
    public ResponseEntity<ResultObject> handleValidateException(Exception e) {

        // log
        printExceptionStackTrace(commonUtility.getDebugId(LogLevel.INFO.toString(),
                "40000001"), "handleValidateException", e);

        return setResponseEntity(ErrorName.Basis.VALIDATION_ERROR,
                "e.common.exception.parameters-invalid",
                commonUtility.getDebugId(LogLevel.INFO.toString(), "40000001"),
                e,
                HttpStatus.BAD_REQUEST);
    }

    /**
     * validation error occurred on item in the request body.
     * A HTTP 400 error is sent back to the client.
     *
     * @param e TypeMismatchException
     * @return ResultObject
     */
    @ExceptionHandler({TypeMismatchException.class})
    public ResponseEntity<ResultObject> handleTypeMismatchException(TypeMismatchException e) {

        // log
        printExceptionStackTrace(commonUtility.getDebugId(LogLevel.WARN.toString(),
                "90000003"), "handleTypeMismatchException", e);

        return setResponseEntity(ErrorName.Basis.UNAVAILABLE_REQUEST,
                "e.common.exception.request-parameter-type-mismatch",
                commonUtility.getDebugId(LogLevel.WARN.toString(), "90000003"),
                e,
                HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle the case where a
     * {@linkplain org.springframework.http.converter.HttpMessageConverter
     * message converter}
     * cannot read from a HTTP request.
     * The default implementation sends a HTTP 400 error,
     * Alternatively, a fallback view could be chosen,
     * or the HttpMediaTypeNotSupportedException could be
     * rethrown as-is.
     *
     * @param e HttpMessageNotReadableException
     * @return obj
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ResultObject> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e) {

        // log
        printExceptionStackTrace(commonUtility.getDebugId(LogLevel.WARN.toString(),
                "90000004"), "handleHttpMessageNotReadableException", e);

        return setResponseEntity(ErrorName.Basis.UNAVAILABLE_REQUEST,
                "e.common.exception.request-body-format-error",
                commonUtility.getDebugId(LogLevel.WARN.toString(), "90000004"),
                e,
                HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle the case where a
     * {@linkplain org.springframework.http.converter.HttpMessageConverter
     * message converter}
     * cannot write to a HTTP request.
     * The default implementation sends a HTTP 500 error,
     * Alternatively, a fallback view could be chosen,
     * or the HttpMediaTypeNotSupportedException could be rethrown as-is.
     *
     * @param e the HttpMessageNotWritableException to be handled
     * @return ResultObject handled exception info
     * @throws IOException I/O exception
     */
    @ExceptionHandler({HttpMessageNotWritableException.class})
    public ResponseEntity<ResultObject> handleHttpMessageNotWritableException(
            HttpMessageNotWritableException e) throws IOException {

        // log
        printExceptionStackTrace(commonUtility.getDebugId(LogLevel.CRITICAL.toString(),
                "90000003"), "handleHttpMessageNotWritableException", e);

        return setResponseEntity(ErrorName.Basis.SYSTEM_ERROR,
                "e.common.exception.system-error",
                commonUtility.getDebugId(LogLevel.CRITICAL.toString(), "90000003"),
                e,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle the case where an {@linkplain RequestPart @RequestPart}, a {@link MultipartFile},
     * or a {@code javax.servlet.http.Part} argument is required but is missing.
     * A HTTP 400 error is sent back to the client.
     *
     * @param e the MissingServletRequestPartException to be handled
     * @return ResultObject handled exception info
     * @throws IOException I/O exception
     */
    @ExceptionHandler({MissingServletRequestPartException.class})
    public ResponseEntity<ResultObject> handleMissingServletRequestPartException(
            MissingServletRequestPartException e) throws IOException {

        // log
        printExceptionStackTrace(commonUtility.getDebugId(LogLevel.WARN.toString(),
                "90000006"), "handleMissingServletRequestPartException", e);

        return setResponseEntity(ErrorName.Basis.UNAVAILABLE_REQUEST,
                "e.common.exception.request-body-format-error",
                commonUtility.getDebugId(LogLevel.WARN.toString(), "90000006"),
                e,
                HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle the case where no handler was found during the dispatch.
     * The default implementation sends a HTTP 404 error and returns an empty
     *
     * @param e the NoHandlerFoundException to be handled
     * @return ResultObject handled exception info
     */
    @ExceptionHandler({NoHandlerFoundException.class})
    public ResponseEntity<ResultObject> handleNoHandlerFoundException(NoHandlerFoundException e) {

        // log
        printExceptionStackTrace(commonUtility.getDebugId(LogLevel.WARN.toString(),
                "90000009"), "handleNoHandlerFoundException", e);

        return setResponseEntity(ErrorName.Basis.UNAVAILABLE_REQUEST,
                "e.common.exception.resource-not-found",
                commonUtility.getDebugId(LogLevel.WARN.toString(), "90000009"),
                e,
                HttpStatus.NOT_FOUND);
    }

    /**
     * Handle the case that get pessimistic lock's count is 0.
     * The default implementation sends a HTTP 404 error and returns an empty
     *
     * @param e the ExclusionException to be handled
     * @return ResultObject handled exception info
     */
    @ExceptionHandler({ExclusionException.class})
    public ResponseEntity<ResultObject> handleExclusionException(ExclusionException e) {

        // log
        printExceptionStackTrace(e.getResultObject().getDebugId(),
                "handleExclusionException", e);

        return setResponseEntityWithResultObject(e.getResultObject(), HttpStatus.NOT_FOUND, e);
    }

    /**
     * Handle the case that refuses to authorize error.
     * The default implementation sends a HTTP 403 error and returns an empty
     *
     * @param e the ForbiddenException to be handled
     * @return ResultObject handled exception info
     */
    @ExceptionHandler({ForbiddenException.class})
    public ResponseEntity<ResultObject> handleForbiddenException(ForbiddenException e) {

        // log
        printExceptionStackTrace(commonUtility.getDebugId(LogLevel.WARN.toString(),
                "90000015"), "handleForbiddenException", e);

        return e.getMessage() == null
                ? new ResponseEntity<>(e.getResultObject(), HttpStatus.FORBIDDEN) :
                setResponseEntity(ErrorName.Business.FORBIDDEN_AUTHENTICATION,
                e.getMessage(),
                commonUtility.getDebugId(LogLevel.WARN.toString(), "90000015"),
                e,
                HttpStatus.FORBIDDEN);
    }

    /**
     * Handle the case where an async request timed out.
     * The default implementation sends a HTTP 503 error.
     *
     * @param e the AsyncRequestTimeoutException to be handled
     * @param request current HTTP request
     * @param response current HTTP response
     * @return ResultObject handled exception info
     * @throws IOException I/O exception
     */
    @ExceptionHandler({AsyncRequestTimeoutException.class})
    public ResponseEntity<ResultObject> handleAsyncRequestTimeoutException(
            AsyncRequestTimeoutException e,
            HttpServletRequest request,
            HttpServletResponse response)  throws IOException {

        // log
        printExceptionStackTrace(commonUtility.getDebugId(LogLevel.CRITICAL.toString(),
                "90000004"), "handleAsyncRequestTimeoutException", e);

        if (!response.isCommitted()) {
            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        } else if (log.isErrorEnabled()) {
            log.error("Async timeout for "
                    + request.getMethod()
                    + " ["
                    + request.getRequestURI() + "]");
        }

        return setResponseEntity(ErrorName.Basis.ASYNC_TIMEOUT,
                "e.common.exception.async-request-timeout",
                commonUtility.getDebugId(LogLevel.CRITICAL.toString(), "90000004"),
                e,
                HttpStatus.SERVICE_UNAVAILABLE);
    }

    /**
     * handle exception that request method not supported.
     * A HTTP 404 error is sent back to the client.
     *
     * @param e ResourceNotFoundException
     * @return ResponseEntity
     */
    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ResultObject> handleResourceNotFoundException(
            ResourceNotFoundException e) {

        // log
        printExceptionStackTrace(e.getResultObject().getDebugId(),
                "handleResourceNotFoundException", e);

        return setResponseEntityWithResultObject(e.getResultObject(), HttpStatus.NOT_FOUND, e);
    }

    /**
     * Handle the case that business error.
     * The default implementation logs a warning, sends a HTTP 422 error.
     *
     * @param e the BusinessException to be handled
     * @return ResultObject handled exception info
     * @throws IOException I/O exception
     */
    @ExceptionHandler({BusinessException.class})
    public ResponseEntity<ResultObject> handleBusinessException(BusinessException e)
            throws IOException {

        // log
        printExceptionStackTrace(e.getResultObject().getDebugId(),
                "handleBusinessException", e);

        return setResponseEntityWithResultObject(e.getResultObject(),
                HttpStatus.UNPROCESSABLE_ENTITY, e);
    }

    /**
     * Handle the case that param validate error.
     * The default implementation logs a warning, sends a HTTP 400 error.
     *
     * @param e the ConstraintViolationException to be handled
     * @return ResultObject handled exception info
     * @throws IOException I/O exception
     */
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ResultObject> handleConstraintViolationException(
            ConstraintViolationException e) throws IOException {

        // log
        printExceptionStackTrace(commonUtility.getDebugId(LogLevel.INFO.toString(),
                "40000001"), "handleConstraintViolationException", e);

        return setResponseEntity(ErrorName.Basis.VALIDATION_ERROR,
                "e.common.exception.parameters-invalid",
                commonUtility.getDebugId(LogLevel.INFO.toString(),"40000001"),
                e,
                HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle the case that acquire locking failure.
     * The default implementation logs a warning, sends a HTTP 503 error.
     *
     * @param e the ClientCannotAcquireLockException to be handled
     * @return ResultObject handled exception info
     * @throws IOException I/O exception
     */
    @ExceptionHandler({ClientCannotAcquireLockException.class})
    public ResponseEntity<ResultObject> handleClientCannotAcquireLockException(
            ClientCannotAcquireLockException e) throws IOException {

        // log
        printExceptionStackTrace(e.getResultObject().getDebugId(),
                "handleClientCannotAcquireLockException", e);

        return setResponseEntityWithResultObject(e.getResultObject(),
                HttpStatus.SERVICE_UNAVAILABLE, e);
    }

    /**
     * handle systemException
     * request attribute "SystemException" to the exception.
     * The default implementation sends a HTTP 500 error, and returns an empty.
     *
     * @param e the exception to be handled
     * @return ResultObject handled exception info
     * @throws IOException I/O exception
     */
    @ExceptionHandler({SystemException.class})
    public ResponseEntity<ResultObject> handleSystemException(SystemException e)
            throws IOException {

        // log
        printExceptionStackTrace(commonUtility.getDebugId(LogLevel.CRITICAL.toString(),
                "90000007"), "handleSystemException", e);

        return setResponseEntity(ErrorName.Basis.SYSTEM_ERROR,
                "e.common.exception.system-error",
                commonUtility.getDebugId(LogLevel.CRITICAL.toString(), "90000007"),
                e,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle the case that precondition fail error.
     * The default implementation logs a warning, sends a HTTP 422 error,
     *
     * @param e the exception to be handled
     * @return ResultObject handled exception info
     * @throws IOException I/O exception
     */
    @ExceptionHandler({PreconditionFailedException.class})
    public ResponseEntity<ResultObject> handlePreconditionFailedException(
            PreconditionFailedException e) throws IOException {

        // log
        printExceptionStackTrace(e.getResultObject().getDebugId(),
                "handlePreconditionFailedException", e);

        return setResponseEntityWithResultObject(e.getResultObject(),
                HttpStatus.UNPROCESSABLE_ENTITY, e);
    }

    /**
     * Handle the case that authorized parameters do not exist.
     * The default implementation logs a warning, sends a HTTP 401 error,
     *
     * @param e the exception to be handled
     * @return ResultObject handled exception info
     * @throws IOException I/O exception
     */
    @ExceptionHandler({NoAuthenticationException.class})
    public ResponseEntity<ResultObject> handleNoAuthenticationException(NoAuthenticationException e)
            throws IOException {

        // log
        printExceptionStackTrace(e.getResultObject().getDebugId(),
                "handleNoAuthenticationException", e);

        return setResponseEntityWithResultObject(e.getResultObject(), HttpStatus.UNAUTHORIZED, e);
    }

    /**
     * Handle the case that RestTemplate communication error.
     *  The default implementation sends a HTTP 503 error.
     *
     * @param e the exception to be handled
     * @return ResultObject handled exception info
     * @throws IOException I/O exception
     */
    @ExceptionHandler({ResourceAccessException.class})
    public ResponseEntity<ResultObject> handleResourceAccessException(
            ResourceAccessException e) throws IOException {

        // log
        printExceptionStackTrace(commonUtility.getDebugId(LogLevel.CRITICAL.toString(),
                "90000005"),
                "handleResourceAccessException", e);

        return setResponseEntity(ErrorName.Basis.SERVICE_COMMUNICATION_ERROR,
                "e.common.exception.service-communication-error",
                commonUtility.getDebugId(LogLevel.CRITICAL.toString(), "90000005"),
                e,
                HttpStatus.SERVICE_UNAVAILABLE);
    }

    /**
     * Handle the case that when external system throw system exception
     *  The default implementation sends a HTTP 503 error.
     *
     * @param e the exception to be handled
     * @return ResultObject handled exception info
     */
    @ExceptionHandler({ExternalSystemException.class})
    public ResponseEntity<ResultObject> handleExternalSystemException(
            ExternalSystemException e) {

        // log
        printExceptionStackTrace(e.getResultObject().getDebugId(),
                "handleExternalSystemException", e);

        return setResponseEntityWithResultObject(
                e.getResultObject(), HttpStatus.SERVICE_UNAVAILABLE, e
        );
    }

    /**
     * Invoked to send a server error. Sets the status to 500 and also sets the
     * request attribute "javax.servlet.error.exception" to the exception.
     * The default implementation sends a HTTP 500 error, and returns an empty.
     *
     * @param e the exception to be handled
     * @return ResultObject handled exception info
     * @throws IOException I/O exception
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ResultObject> handleDefaultException(Exception e) throws IOException {

        // log
        printExceptionStackTrace(commonUtility.getDebugId(LogLevel.CRITICAL.toString(),
                "90000006"), "handleDefaultException", e);

        return setResponseEntity(ErrorName.Basis.SYSTEM_ERROR,
                "e.common.exception.system-error",
                commonUtility.getDebugId(LogLevel.CRITICAL.toString(), "90000006"),
                e,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * set result object
     * if there are validation errors then set validation errors.
     * @param resultObject ResultObject
     * @param statusCode HTTP status code
     * @param e Throwable
     * @return result object
     */
    private ResponseEntity<ResultObject> setResponseEntityWithResultObject(
            ResultObject resultObject,
            HttpStatus statusCode, Throwable e) {

        setDebugLog(resultObject.getDebugId(), resultObject.getMessage(), e);

        return new ResponseEntity<>(resultObject, statusCode);
    }

    /**
     * set debug log : debug id and message.
     * @param debugId debugId
     * @param message message
     * @param e Throwable
     */
    private void setDebugLog(String debugId, String message, Throwable e) {
        Map<String, String> logMap = new LinkedHashMap<>();

        Marker critical = MarkerFactory.getMarker(logLevelMap.get(debugId.substring(0, 1)));
        logMap.put("message_id", debugId);
        if (e instanceof BusinessException) {
            log.warn(critical, message, logMap);
        } else {
            log.error(critical, message, logMap);
        }
    }

    /**
     * output ExceptionStackTrace.
     * @param debugId debugId
     * @param exceptionName Exception Name
     * @param e Throwable
     */
    private void printExceptionStackTrace(String debugId, String exceptionName, Throwable e) {
        Marker critical = MarkerFactory.getMarker(logLevelMap.get(debugId.substring(0, 1)));
        if (e instanceof BusinessException) {
            log.warn(critical, "Invoking @{} method: {}",exceptionName, extractExceptionStackTrace(e));
        } else {
            log.error(critical, "Invoking @{} method: {}",exceptionName, extractExceptionStackTrace(e));
        }
    }

    /**
     * extract exception stack trace info from exception.
     * @param e Throwable
     * @return exception info with stack trace
     */
    private String extractExceptionStackTrace(Throwable e) {

        StringBuilder stringBuilder = new StringBuilder();
        String tempStackTrace = "";
        if (e != null) {
            tempStackTrace = extractExceptionStackTrace(e.getCause());
        } else {
            return tempStackTrace;
        }

        stringBuilder.append("Caused by:" + e.toString());
        if (e.getStackTrace() != null) {
            Arrays.stream(e.getStackTrace()).forEach(stackTraceElement ->
                    stringBuilder.append(" at ").append(stackTraceElement));
        }
        stringBuilder.append(tempStackTrace);

        return stringBuilder.toString();
    }

    /**
     * set result object
     * if there are validation errors then set validation errors.
     * @param name error json's name
     * @param msgKey message's key
     * @param debugId debug id
     * @param e exception
     * @param statusCode HTTP status code
     * @return result object
     */
    private ResponseEntity<ResultObject> setResponseEntity(ErrorName name,
                                                           String msgKey,
                                                           String debugId,
                                                           Exception e,
                                                           HttpStatus statusCode) {
        String message = messageSource.getMessage(msgKey, null, Locale.getDefault());

        ResultObject obj =
                new ResultObject(name, debugId, message);

        setDebugLog(debugId, message, e);

        // validation check error
        if (e instanceof MethodArgumentNotValidException) {
            // set type mismatch error details
            obj.setDetails(((MethodArgumentNotValidException) e)
                    .getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(this::setFieldErrorDetail)
                    .collect(Collectors.toList()));
        } else if (e instanceof BindException) {
            // set bind exception details
            obj.setDetails(((BindException) e).getBindingResult().getFieldErrors().stream()
                    .map(this::setFieldErrorDetail)
                    .collect(Collectors.toList()));
        } else if (e instanceof ConstraintViolationException) {
            // set constraint violation exception details
            obj.setDetails(((ConstraintViolationException) e).getConstraintViolations().stream()
                    .map(this::setConstraintViolationDetail)
                    .collect(Collectors.toList()));
        }

        return new ResponseEntity<>(obj, statusCode);
    }

    /**
     * set field error detail from filedError.
     * @param fieldError field error
     * @return result object
     */
    private Detail setFieldErrorDetail(FieldError fieldError) {
        return ResultObjectUtility.DetailBuilder.build(fieldError);
    }

    /**
     * set param validate error detail from constraintViolation.
     * @param constraintViolation constraint violation
     * @return result object
     */
    private Detail setConstraintViolationDetail(ConstraintViolation constraintViolation) {
        return ResultObjectUtility.DetailBuilder.build(constraintViolation);
    }

}
