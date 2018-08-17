/**
 * @(#)ApiExceptionHandlerTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.web.handler;

import com.fastretailing.dcp.common.constants.ErrorName;
import com.fastretailing.dcp.common.exception.BusinessException;
import com.fastretailing.dcp.common.exception.ClientCannotAcquireLockException;
import com.fastretailing.dcp.common.exception.ExclusionException;
import com.fastretailing.dcp.common.exception.ExternalSystemException;
import com.fastretailing.dcp.common.exception.ForbiddenException;
import com.fastretailing.dcp.common.exception.NoAuthenticationException;
import com.fastretailing.dcp.common.exception.PreconditionFailedException;
import com.fastretailing.dcp.common.exception.ResourceNotFoundException;
import com.fastretailing.dcp.common.exception.SystemException;
import com.fastretailing.dcp.common.model.ResultObject;
import com.fastretailing.dcp.common.util.CommonUtility;
import com.fastretailing.dcp.common.util.ResultObjectUtility;
import com.fastretailing.dcp.common.validation.annotation.MagnitudeRelation;
import com.fastretailing.dcp.common.validation.validator.MagnitudeRelationValidator;
import lombok.Data;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.AggregateResourceBundleLocator;
import org.hibernate.validator.spi.resourceloading.ResourceBundleLocator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.internal.invocation.MockitoMethod;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Fast Retailing
 * @version $Revision$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        @ContextConfiguration(locations = {
                "classpath*:com/fastretailing/dcp/common/web/handler/test-context.xml"
        })
})

public class ApiExceptionHandlerTest extends MessageTest {

    private MockHttpServletResponse response;
    private MockHttpServletRequest request;
    private MockitoMethod method;

    @Autowired
    private MagnitudeRelationValidator MagnitudeRelation;

    @InjectMocks
    private ApiExceptionHandler mockApiExceptionHandler =  new ApiExceptionHandler();

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ResultObjectUtility resultObjectUtility;

    @Rule
    public ExpectedException thrown= ExpectedException.none();
//
//    @Autowired
//    private Validator validator;

    private CommonUtility commonUtility = new CommonUtility();

    @Before
    public void setUp() throws Exception {

        ReflectionTestUtils.setField(commonUtility, "platformShortName" , "BKT");
        ReflectionTestUtils.setField(mockApiExceptionHandler, "messageSource" , messageSource);
        ReflectionTestUtils.setField(mockApiExceptionHandler, "commonUtility" , commonUtility);

        ReflectionTestUtils.setField(resultObjectUtility, "autowiredMessageSource", messageSource);
        ReflectionTestUtils.setField(resultObjectUtility, "autowiredCommonUtility", commonUtility);


        Map<String, String> logLevelMap =
        (Map<String, String>)ReflectionTestUtils.getField(mockApiExceptionHandler,"logLevelMap");
        logLevelMap.put("D", "DEBUG");
        logLevelMap.put("I", "INFO");
        logLevelMap.put("W", "WARN");
        logLevelMap.put("E", "ERROR");
        logLevelMap.put("N", "NOTICE");
        logLevelMap.put("C", "CRITICAL");

        response = new MockHttpServletResponse();
        request = new MockHttpServletRequest();
    }


    @Test
    public void testHttpRequestMethodNotSupportedException() throws Exception {
        String[] supportedMethods = {"1", "2"};
        HttpRequestMethodNotSupportedException exception = new HttpRequestMethodNotSupportedException("test", supportedMethods);
        ResponseEntity<ResultObject> responseEntity = mockApiExceptionHandler.handleHttpRequestMethodNotSupportedException(exception, response);
        ResultObject resultObj = responseEntity.getBody();

        HttpStatus statusCode = responseEntity.getStatusCode();
        assertThat(statusCode, is(HttpStatus.METHOD_NOT_ALLOWED));

        assertThat(resultObj.getName(), is(ErrorName.Basis.UNAVAILABLE_REQUEST));
        assertThat(resultObj.getMessage(), is("Request method not supported."));
        assertThat(resultObj.getDebugId(), is("W_BKT_90000010"));
        assertThat(response.getHeader("Allow"), is("1, 2"));
        assertNull(resultObj.getDetails());
    }

    @Test
    public void testHttpRequestMethodNotSupportedNullException() throws Exception {
        String[] supportedMethods = null;
        HttpRequestMethodNotSupportedException exception = new HttpRequestMethodNotSupportedException("test", supportedMethods);
        ResponseEntity<ResultObject> responseEntity = mockApiExceptionHandler.handleHttpRequestMethodNotSupportedException(exception, response);
        ResultObject resultObj = responseEntity.getBody();

        HttpStatus statusCode = responseEntity.getStatusCode();
        assertThat(statusCode, is(HttpStatus.METHOD_NOT_ALLOWED));

        assertThat(resultObj.getName(), is(ErrorName.Basis.UNAVAILABLE_REQUEST));
        assertThat(resultObj.getMessage(), is("Request method not supported."));
        assertThat(resultObj.getDebugId(), is("W_BKT_90000010"));
        assertNull(response.getHeader("Allow"));
        assertNull(resultObj.getDetails());
    }

    @Test
    public void testHttpMediaTypeNotAcceptableException() throws Exception {
        HttpMediaTypeNotAcceptableException exception = new HttpMediaTypeNotAcceptableException("test");
        ResponseEntity<ResultObject> responseEntity = mockApiExceptionHandler.handleHttpMediaTypeNotAcceptable(exception);
        ResultObject resultObj = responseEntity.getBody();

        HttpStatus statusCode = responseEntity.getStatusCode();
        assertThat(statusCode, is(HttpStatus.NOT_ACCEPTABLE));

        assertThat(resultObj.getName(), is(ErrorName.Basis.UNAVAILABLE_REQUEST));
        assertThat(resultObj.getMessage(), is("Request response media type not supported."));
        assertThat(resultObj.getDebugId(), is("W_BKT_90000011"));
        assertNull(resultObj.getDetails());
    }

    @Test
    public void testHttpMediaTypeNotSupportedException1() throws Exception {

        HttpMediaTypeNotSupportedException exception = new HttpMediaTypeNotSupportedException("test");
        ResponseEntity<ResultObject> responseEntity = mockApiExceptionHandler.handleHttpMediaTypeNotSupportedException(exception, response);

        ResultObject resultObj = responseEntity.getBody();

        HttpStatus statusCode = responseEntity.getStatusCode();
        assertThat(resultObj.getName(), is(ErrorName.Basis.UNAVAILABLE_REQUEST));
        assertThat(resultObj.getMessage(), is("Specified media type in the request body not supported."));
        assertThat(resultObj.getDebugId(), is("W_BKT_90000012"));
        assertThat(statusCode, is(HttpStatus.UNSUPPORTED_MEDIA_TYPE));
        assertNull(resultObj.getDetails());
    }

    @Test
    public void testHttpMediaTypeNotSupportedException2() throws Exception {

        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(new MediaType("test"));

        MediaType mediaType = new MediaType("test");

        HttpMediaTypeNotSupportedException exception = new HttpMediaTypeNotSupportedException(mediaType, mediaTypes);
        ResponseEntity<ResultObject> responseEntity = mockApiExceptionHandler.handleHttpMediaTypeNotSupportedException(exception, response);

        ResultObject resultObj = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();
        assertThat(resultObj.getName(), is(ErrorName.Basis.UNAVAILABLE_REQUEST));
        assertThat(resultObj.getMessage(), is("Specified media type in the request body not supported."));
        assertThat(resultObj.getDebugId(), is("W_BKT_90000012"));
        assertThat(statusCode, is(HttpStatus.UNSUPPORTED_MEDIA_TYPE));
        assertThat(response.getHeader("Accept"), is(MediaType.toString(mediaTypes)));
        assertNull(resultObj.getDetails());
    }


    @Test
    public void testMissingPathVariableException() throws Exception {

        Method methodForTest = MissingPathVariableException.class.getMethods()[0];
        MethodParameter s =new MethodParameter(methodForTest, -1);

        MissingPathVariableException exception = new MissingPathVariableException("test", s);


        ResponseEntity<ResultObject> responseEntity = mockApiExceptionHandler.handleMissingPathVariableException(exception);
        ResultObject resultObj = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();
        assertThat(resultObj.getName(), is(ErrorName.Basis.SYSTEM_ERROR));
        assertThat(resultObj.getDebugId(), is("C_BKT_90000001"));
        assertThat(resultObj.getMessage(), is("System error occurred."));
        assertThat(statusCode, is(HttpStatus.INTERNAL_SERVER_ERROR));
        assertNull(resultObj.getDetails());
    }

    @Test
    public void testMissingServletRequestParameterException() throws Exception {

        MissingServletRequestParameterException exception = new MissingServletRequestParameterException("test", "String");


        ResponseEntity<ResultObject> responseEntity = mockApiExceptionHandler.handleMissingServletRequestParameterException(exception);
        ResultObject resultObj = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();
        assertThat(resultObj.getName(), is(ErrorName.Basis.UNAVAILABLE_REQUEST));
        assertThat(resultObj.getDebugId(), is("W_BKT_90000001"));
        assertThat(resultObj.getMessage(), is("A required parameter is missing."));
        assertThat(statusCode, is(HttpStatus.BAD_REQUEST));
        assertNull(resultObj.getDetails());
    }

    @Test
    public void testServletRequestBindingException() throws Exception {

        Throwable throwable = new Throwable();
        ServletRequestBindingException exception = new ServletRequestBindingException("wrong message", throwable);

        ResponseEntity<ResultObject> responseEntity = mockApiExceptionHandler.handleServletRequestBindingException(exception);
        ResultObject resultObj = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();
        assertThat(resultObj.getName(), is(ErrorName.Basis.UNAVAILABLE_REQUEST));
        assertThat(resultObj.getDebugId(), is("W_BKT_90000002"));
        assertThat(resultObj.getMessage(), is("Request body format error occurred."));
        assertThat(statusCode, is(HttpStatus.BAD_REQUEST));
        assertNull(resultObj.getDetails());
    }

    @Test
    public void testConversionNotSupportedException() throws Exception {

        Throwable throwable = new Throwable();
        ConversionNotSupportedException exception = new ConversionNotSupportedException("value", String.class, throwable);
        ResponseEntity<ResultObject> responseEntity = mockApiExceptionHandler.handleConversionNotSupportedException(exception);
        ResultObject resultObj = responseEntity.getBody();

        HttpStatus statusCode = responseEntity.getStatusCode();
        assertThat(resultObj.getName(), is(ErrorName.Basis.SYSTEM_ERROR));
        assertThat(resultObj.getDebugId(), is("C_BKT_90000002"));
        assertThat(resultObj.getMessage(), is("System error occurred."));
        assertThat(statusCode, is(HttpStatus.INTERNAL_SERVER_ERROR));
        assertNull(resultObj.getDetails());
    }

    @Test
    public void testTypeMismatchException() throws Exception {

        Throwable throwable = new Throwable();
        TypeMismatchException exception = new TypeMismatchException("value", String.class, throwable);

        ResponseEntity<ResultObject> responseEntity = mockApiExceptionHandler.handleTypeMismatchException(exception);
        ResultObject resultObj = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();
        assertThat(resultObj.getName(), is(ErrorName.Basis.UNAVAILABLE_REQUEST));
        assertThat(resultObj.getDebugId(), is("W_BKT_90000003"));
        assertThat(resultObj.getMessage(), is("Type mismatch error occurred in request parameter or header or path variable."));
        assertThat(statusCode, is(HttpStatus.BAD_REQUEST));
        assertNull(resultObj.getDetails());
    }

    @Test
    public void testHttpMessageNotReadableException() throws Exception {

        HttpMessageNotReadableException exception = new HttpMessageNotReadableException("value");

        ResponseEntity<ResultObject> responseEntity = mockApiExceptionHandler.handleHttpMessageNotReadableException(exception);
        ResultObject resultObj = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();
        assertThat(resultObj.getName(), is(ErrorName.Basis.UNAVAILABLE_REQUEST));
        assertThat(resultObj.getDebugId(), is("W_BKT_90000004"));
        assertThat(resultObj.getMessage(), is("Request body format error occurred."));
        assertThat(statusCode, is(HttpStatus.BAD_REQUEST));
        assertNull(resultObj.getDetails());
    }

    @Test
    public void handleHttpMessageNotWritableException() throws Exception {

        HttpMessageNotWritableException exception = new HttpMessageNotWritableException("value");



        ResponseEntity<ResultObject> responseEntity = mockApiExceptionHandler.handleHttpMessageNotWritableException(exception);
        ResultObject resultObj = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();
        assertThat(resultObj.getName(), is(ErrorName.Basis.SYSTEM_ERROR));
        assertThat(resultObj.getDebugId(), is("C_BKT_90000003"));
        assertThat(resultObj.getMessage(), is("System error occurred."));
        assertThat(statusCode, is(HttpStatus.INTERNAL_SERVER_ERROR));
        assertNull(resultObj.getDetails());
    }

    @Test
    public void testMethodArgumentNotValidException() throws Exception {

        Method methodForTest = MissingPathVariableException.class.getMethods()[0];
        MethodParameter s =new MethodParameter(methodForTest, -1);

        Map<String, String> errMap = new HashMap<>();

        errMap.put("filed1" ,"filedValue1");
        errMap.put("filed2" ,"filedValue2");
        MapBindingResult bindingResult = new MapBindingResult(errMap,"USER");

        bindingResult.rejectValue("filed1", "NotNull", "The field is required.");
        bindingResult.rejectValue("filed2", "NotNull", "The field is required.");

        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(s, bindingResult);

        ResponseEntity<ResultObject> responseEntity = mockApiExceptionHandler.handleValidateException(exception);
        ResultObject resultObj = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();
        assertThat(resultObj.getName(), is(ErrorName.Basis.VALIDATION_ERROR));
        assertThat(resultObj.getDebugId(), is("I_BKT_40000001"));
        assertThat(resultObj.getMessage(), is("Input parameters include invalid values."));
        assertThat(statusCode, is(HttpStatus.BAD_REQUEST));
        assertThat(resultObj.getDetails().size(), is(2));
        assertThat(resultObj.getDetails().get(0).getField(), is("filed_1"));
        assertThat(resultObj.getDetails().get(0).getIssue(), is("The field is required."));
        assertThat(resultObj.getDetails().get(0).getIssueCode(), is("400-001"));
        assertThat(resultObj.getDetails().get(0).getValue(), is("filedValue1"));
        assertThat(resultObj.getDetails().get(1).getField(), is("filed_2"));
        assertThat(resultObj.getDetails().get(1).getIssue(), is("The field is required."));
        assertThat(resultObj.getDetails().get(1).getIssueCode(), is("400-001"));
        assertThat(resultObj.getDetails().get(1).getValue(), is("filedValue2"));
    }
    @Test
    public void testConstraintViolationException() throws Exception {


        HibernateValidatorConfiguration configure = Validation.byProvider(HibernateValidator.class).configure();

        List<String> resources = new ArrayList<String>();
        resources.add("ValidationMessages");

        ResourceBundleLocator aggregateResourceBundleLocator = new AggregateResourceBundleLocator(resources);

        configure.messageInterpolator(new ResourceBundleMessageInterpolator(aggregateResourceBundleLocator));
        ValidatorFactory factory = configure.buildValidatorFactory();
         factory.getValidator();
        Validator validator = factory.getValidator();
        User user = new User();
        user.setField2("aa");

        ConstraintViolationException exception = new ConstraintViolationException(validator.validate(user));

        ResponseEntity<ResultObject> responseEntity = mockApiExceptionHandler.handleValidateException(exception);
        ResultObject resultObj = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();
        assertThat(resultObj.getName(), is(ErrorName.Basis.VALIDATION_ERROR));
        assertThat(resultObj.getDebugId(), is("I_BKT_40000001"));
        assertThat(resultObj.getMessage(), is("Input parameters include invalid values."));
        assertThat(statusCode, is(HttpStatus.BAD_REQUEST));
        assertThat(resultObj.getDetails().size(), is(1));


        assertTrue(("field_1").contains(resultObj.getDetails().get(0).getField()));
        assertThat(resultObj.getDetails().get(0).getIssue(), is("The field is required."));
        assertThat(resultObj.getDetails().get(0).getIssueCode(), is("400-001"));

    }
    @Test
    public void testConstraintViolationExceptionNotEmpty() throws Exception {


        HibernateValidatorConfiguration configure = Validation.byProvider(HibernateValidator.class).configure();

        List<String> resources = new ArrayList<String>();
        resources.add("ValidationMessages");

        ResourceBundleLocator aggregateResourceBundleLocator = new AggregateResourceBundleLocator(resources);

        configure.messageInterpolator(new ResourceBundleMessageInterpolator(aggregateResourceBundleLocator));
        ValidatorFactory factory = configure.buildValidatorFactory();
         factory.getValidator();
        Validator validator = factory.getValidator();
        User user = new User();
        user.setField2("");
        user.setField1("");

        ConstraintViolationException exception = new ConstraintViolationException(validator.validate(user));

        ResponseEntity<ResultObject> responseEntity = mockApiExceptionHandler.handleValidateException(exception);
        ResultObject resultObj = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();
        assertThat(resultObj.getName(), is(ErrorName.Basis.VALIDATION_ERROR));
        assertThat(resultObj.getDebugId(), is("I_BKT_40000001"));
        assertThat(resultObj.getMessage(), is("Input parameters include invalid values."));
        assertThat(statusCode, is(HttpStatus.BAD_REQUEST));
        assertThat(resultObj.getDetails().size(), is(1));

        assertTrue(("field_2").contains(resultObj.getDetails().get(0).getField()));
        assertThat(resultObj.getDetails().get(0).getIssue(), is("The field is required."));
        assertThat(resultObj.getDetails().get(0).getIssueCode(), is("400-018"));
    }

    @Test
    public void handleMissingServletRequestPartException() throws Exception {


        MissingServletRequestPartException exception = new MissingServletRequestPartException("a");

        ResponseEntity<ResultObject> responseEntity = mockApiExceptionHandler.handleMissingServletRequestPartException(exception);
        ResultObject resultObj = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();
        assertThat(resultObj.getName(), is(ErrorName.Basis.UNAVAILABLE_REQUEST));
        assertThat(resultObj.getDebugId(), is("W_BKT_90000006"));
        assertThat(resultObj.getMessage(), is("Request body format error occurred."));
        assertThat(statusCode, is(HttpStatus.BAD_REQUEST));
        assertNull(resultObj.getDetails());
    }

    @Test
    public void handleResourceAccessException() throws Exception {


        ResourceAccessException resourceAccessException = new ResourceAccessException("test");


        ResponseEntity<ResultObject> responseEntity = mockApiExceptionHandler.handleResourceAccessException(resourceAccessException);
        ResultObject resultObj = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();
        assertThat(resultObj.getName(), is(ErrorName.Basis.SERVICE_COMMUNICATION_ERROR));
        assertThat(resultObj.getDebugId(), is("C_BKT_90000005"));
        assertThat(resultObj.getMessage(), is("Service communication error."));
        assertNull(resultObj.getDetails());

    }
    @Test
    public void handleNoAuthenticationException() throws Exception {

        ResultObject resultObject = new ResultObject();

        resultObject.setName(ErrorName.Business.FORBIDDEN_AUTHENTICATION);
        resultObject.setDebugId("E_BKT_60300302");
        resultObject.setMessage("The authentication can not execute the API.");

        NoAuthenticationException noAuthenticationException = new NoAuthenticationException(resultObject);

        ResponseEntity<ResultObject> responseEntity = mockApiExceptionHandler.handleNoAuthenticationException(noAuthenticationException);
        ResultObject resultObj = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();
        assertThat(resultObj.getName(), is(ErrorName.Business.FORBIDDEN_AUTHENTICATION));
        assertThat(resultObj.getDebugId(), is("E_BKT_60300302"));
        assertThat(resultObj.getMessage(), is("The authentication can not execute the API."));
        assertNull(resultObj.getDetails());

    }
    @Test
    public void handleExternalSystemException() throws Exception {

        ResultObject resultObject = new ResultObject();

        resultObject.setName(ErrorName.Basis.SYSTEM_ERROR);
        resultObject.setDebugId("E_BKT_60300302");
        resultObject.setMessage("The authentication can not execute the API.");

        ExternalSystemException externalSystemException = new ExternalSystemException(resultObject);

        ResponseEntity<ResultObject> responseEntity = mockApiExceptionHandler.handleExternalSystemException(externalSystemException);
        ResultObject resultObj = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();
        assertThat(resultObj.getName(), is(ErrorName.Basis.SYSTEM_ERROR));
        assertThat(resultObj.getDebugId(), is("E_BKT_60300302"));
        assertThat(resultObj.getMessage(), is("The authentication can not execute the API."));
        assertNull(resultObj.getDetails());

    }



    @Test
    public void handleBindException() throws Exception {


        Map<String, String> errMap = new HashMap<>();

        errMap.put("filed1" ,"filedValue1");
        errMap.put("filed2" ,null);
        MapBindingResult bindingResult = new MapBindingResult(errMap,"USER");

        bindingResult.rejectValue("filed1", "MagnitudeRelation", "larger must be larger than small.");
        bindingResult.rejectValue("filed2", "MagnitudeRelation", "larger must be larger than or equal to small.");
        BindException exception = new BindException(bindingResult);

        ResponseEntity<ResultObject> responseEntity = mockApiExceptionHandler.handleValidateException(exception);
        ResultObject resultObj = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();
        assertThat(resultObj.getName(), is(ErrorName.Basis.VALIDATION_ERROR));
        assertThat(resultObj.getDebugId(), is("I_BKT_40000001"));
        assertThat(resultObj.getMessage(), is("Input parameters include invalid values."));
        assertThat(resultObj.getDetails().size(), is(2));
        assertThat(resultObj.getDetails().get(0).getField(), is("filed_1"));
        assertThat(resultObj.getDetails().get(0).getIssue(), is("larger must be larger than small."));
        assertThat(resultObj.getDetails().get(0).getIssueCode(), is("400-032"));
        assertThat(resultObj.getDetails().get(0).getValue(), is("filedValue1"));
        assertThat(resultObj.getDetails().get(1).getField(), is("filed_2"));
        assertThat(resultObj.getDetails().get(1).getIssue(), is("larger must be larger than or equal to small."));
        assertThat(resultObj.getDetails().get(1).getIssueCode(), is("400-033"));
        assertNull(resultObj.getDetails().get(1).getValue());
    }

    @Test
    public void testHandleNoHandlerFoundException() throws Exception {


        HttpHeaders headers = new HttpHeaders();
        NoHandlerFoundException exception = new NoHandlerFoundException("get", "requestUrl", headers);

        ResponseEntity<ResultObject> responseEntity = mockApiExceptionHandler.handleNoHandlerFoundException(exception);
        ResultObject resultObj = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();
        assertThat(resultObj.getName(), is(ErrorName.Basis.UNAVAILABLE_REQUEST));
        assertThat(resultObj.getDebugId(), is("W_BKT_90000009"));
        assertThat(resultObj.getMessage(), is("Resource not found."));

        assertThat(statusCode, is(HttpStatus.NOT_FOUND));
        assertNull(resultObj.getDetails());
    }

    @Test
    public void testAsyncRequestTimeoutException() throws Exception {


        AsyncRequestTimeoutException exception = new AsyncRequestTimeoutException();

        response.setCommitted(false);

        ResponseEntity<ResultObject> responseEntity = mockApiExceptionHandler.handleAsyncRequestTimeoutException(exception, request, response);
        HttpStatus statusCode = responseEntity.getStatusCode();
        ResultObject resultObj = responseEntity.getBody();
        assertThat(resultObj.getName(), is(ErrorName.Basis.ASYNC_TIMEOUT));
        assertThat(resultObj.getDebugId(), is("C_BKT_90000004"));
        assertThat(resultObj.getMessage(), is("An async request timed out."));
        assertThat(statusCode, is(HttpStatus.SERVICE_UNAVAILABLE));
    }

    @Test
    public void testAsyncRequestTimeoutException2() throws Exception {


        AsyncRequestTimeoutException exception = new AsyncRequestTimeoutException();

        response.setCommitted(true);

        ResponseEntity<ResultObject> responseEntity = mockApiExceptionHandler.handleAsyncRequestTimeoutException(exception, request, response);
        ResultObject resultObj = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();
        assertThat(resultObj.getName(), is(ErrorName.Basis.ASYNC_TIMEOUT));
        assertThat(resultObj.getDebugId(), is("C_BKT_90000004"));
        assertThat(resultObj.getMessage(), is("An async request timed out."));

        assertThat(statusCode, is(HttpStatus.SERVICE_UNAVAILABLE));
    }

    @Test
    public void testResourceNotFoundException() throws Exception {

        ResultObject resultObject = new ResultObject();

        resultObject.setDebugId("W_BKT_90000016");
        resultObject.setName(ErrorName.Basis.UNAVAILABLE_REQUEST);
        resultObject.setMessage("Resource not found.");

        ResourceNotFoundException exception = new ResourceNotFoundException(resultObject);

        ResponseEntity<ResultObject> responseEntity = mockApiExceptionHandler.handleResourceNotFoundException(exception);
        ResultObject resultObj = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();
        assertThat(resultObj.getName(), is(ErrorName.Basis.UNAVAILABLE_REQUEST));
        assertThat(resultObj.getDebugId(), is("W_BKT_90000016"));
        assertThat(resultObj.getMessage(), is("Resource not found."));

        assertThat(statusCode, is(HttpStatus.NOT_FOUND));
        assertNull(resultObj.getDetails());
    }

    @Test
    public void testExclusionException() throws Exception {

        ResultObject resultObject = new ResultObject();

        resultObject.setDebugId("W_BKT_90000016");
        resultObject.setName(ErrorName.Basis.UNAVAILABLE_REQUEST);
        resultObject.setMessage("Resource not found.");
        ExclusionException exception = new ExclusionException(resultObject);

        ResponseEntity<ResultObject> responseEntity = mockApiExceptionHandler.handleExclusionException(exception);
        ResultObject resultObj = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();
        assertThat(resultObj.getName(), is(ErrorName.Basis.UNAVAILABLE_REQUEST));
        assertThat(resultObj.getDebugId(), is("W_BKT_90000016"));
        assertThat(resultObj.getMessage(), is("Resource not found."));

        assertThat(statusCode, is(HttpStatus.NOT_FOUND));
        assertNull(resultObj.getDetails());
    }
    @Test
    public void testPreconditionFailedException() throws Exception {

        ResultObject resultObject = new ResultObject();

        resultObject.setDebugId("W_BKT_90000016");
        resultObject.setName(ErrorName.Business.ETAG_ERROR);
        resultObject.setMessage("If-Match in HTTP Header is invalid. Please check the latest information with Etag.");
        PreconditionFailedException exception = new PreconditionFailedException(resultObject);

        ResponseEntity<ResultObject> responseEntity = mockApiExceptionHandler.handlePreconditionFailedException(exception);
        ResultObject resultObj = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();
        assertThat(resultObj.getName(), is(ErrorName.Business.ETAG_ERROR));
        assertThat(resultObj.getDebugId(), is("W_BKT_90000016"));
        assertThat(resultObj.getMessage(), is("If-Match in HTTP Header is invalid. Please check the latest information with Etag."));

        assertThat(statusCode, is(HttpStatus.UNPROCESSABLE_ENTITY));
        assertNull(resultObj.getDetails());
    }

    @Test
    public void testBusinessException() throws Exception {


        ResultObject resultObject = new ResultObject();
        resultObject.setDebugId("E_BKT_60100301");
        resultObject.setName(ErrorName.Business.BUSINESS_CHECK_ERROR);
        resultObject.setMessage("This API can not execute. Please check details and modify it.");
        BusinessException exception = new BusinessException(resultObject);

        ResponseEntity<ResultObject> responseEntity = mockApiExceptionHandler.handleBusinessException(exception);
        ResultObject resultObj = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();
        assertThat(resultObj.getName(), is(ErrorName.Business.BUSINESS_CHECK_ERROR));
        assertThat(resultObj.getDebugId(), is("E_BKT_60100301"));
        assertThat(resultObj.getMessage(), is("This API can not execute. Please check details and modify it."));

        assertThat(statusCode, is(HttpStatus.UNPROCESSABLE_ENTITY));
        assertNull(resultObj.getDetails());
    }

    @Test
    public void testClientCannotAcquireLockException() throws Exception {


        ResultObject resultObject = new ResultObject();
        resultObject.setDebugId("E_BKT_60100301");
        resultObject.setName(ErrorName.Business.BUSINESS_CHECK_ERROR);
        resultObject.setMessage("This API can not execute. Please check details and modify it.");

        ClientCannotAcquireLockException exception = new ClientCannotAcquireLockException(resultObject, new Throwable());

        ResponseEntity<ResultObject> responseEntity = mockApiExceptionHandler.handleClientCannotAcquireLockException(exception);
        ResultObject resultObj = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();
        assertThat(resultObj.getName(), is(ErrorName.Business.BUSINESS_CHECK_ERROR));
        assertThat(resultObj.getDebugId(), is("E_BKT_60100301"));
        assertThat(resultObj.getMessage(), is("This API can not execute. Please check details and modify it."));

        assertThat(statusCode, is(HttpStatus.SERVICE_UNAVAILABLE));
        assertNull(resultObj.getDetails());
    }
    @Test
    public void testForbiddenExceptionWithResult() throws Exception {


        ResultObject resultObject = new ResultObject();
        resultObject.setDebugId("E_BKT_60100301");
        resultObject.setName(ErrorName.Business.BUSINESS_CHECK_ERROR);
        resultObject.setMessage("This API can not execute. Please check details and modify it.");

        ForbiddenException exception = new ForbiddenException(resultObject);

        ResponseEntity<ResultObject> responseEntity = mockApiExceptionHandler.handleForbiddenException(exception);
        ResultObject resultObj = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();
        assertThat(resultObj.getName(), is(ErrorName.Business.BUSINESS_CHECK_ERROR));
        assertThat(resultObj.getDebugId(), is("E_BKT_60100301"));
        assertThat(resultObj.getMessage(), is("This API can not execute. Please check details and modify it."));

        assertThat(statusCode, is(HttpStatus.FORBIDDEN));
        assertNull(resultObj.getDetails());
    }

    @Test
    public void testForbiddenException() throws Exception {

        ForbiddenException exception = new ForbiddenException("e.common.exception.resource-not-found");

        ResponseEntity<ResultObject> responseEntity = mockApiExceptionHandler.handleForbiddenException(exception);
        ResultObject resultObj = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();
        assertThat(resultObj.getName(), is(ErrorName.Business.FORBIDDEN_AUTHENTICATION));
        assertThat(resultObj.getDebugId(), is("W_BKT_90000015"));
        assertThat(resultObj.getMessage(), is("Resource not found."));

        assertThat(statusCode, is(HttpStatus.FORBIDDEN));
        assertNull(resultObj.getDetails());
    }

    @Test
    public void testSystemException() throws Exception {

        SystemException exception = new SystemException();

        ResponseEntity<ResultObject> responseEntity = mockApiExceptionHandler.handleSystemException(exception);
        ResultObject resultObj = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();
        assertThat(resultObj.getName(), is(ErrorName.Basis.SYSTEM_ERROR));
        assertThat(resultObj.getDebugId(), is("C_BKT_90000007"));
        assertThat(resultObj.getMessage(), is("System error occurred."));

        assertThat(statusCode, is(HttpStatus.INTERNAL_SERVER_ERROR));
        assertNull(resultObj.getDetails());
    }


    @Test
    public void testDefaultException() throws Exception {

        Exception exception = new Exception("e.common.exception.resource-not-found");

        ResponseEntity<ResultObject> responseEntity = mockApiExceptionHandler.handleDefaultException(exception);
        ResultObject resultObj = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();
        assertThat(resultObj.getName(), is(ErrorName.Basis.SYSTEM_ERROR));
        assertThat(resultObj.getDebugId(), is("C_BKT_90000006"));
        assertThat(resultObj.getMessage(), is("System error occurred."));

        assertThat(statusCode, is(HttpStatus.INTERNAL_SERVER_ERROR));
        assertNull(resultObj.getDetails());
    }

    @Test
    public void testConstraintViolationMagnitudeRelation() throws Exception {


        HibernateValidatorConfiguration configure = Validation.byProvider(HibernateValidator.class).configure();

        List<String> resources = new ArrayList<String>();
        resources.add("ValidationMessages");

        ResourceBundleLocator aggregateResourceBundleLocator = new AggregateResourceBundleLocator(resources);

        configure.messageInterpolator(new ResourceBundleMessageInterpolator(aggregateResourceBundleLocator));
        ValidatorFactory factory = configure.buildValidatorFactory();
        factory.getValidator();
        Validator validator = factory.getValidator();
        MagnitudeRelationForm magnitudeRelationForm = new MagnitudeRelationForm(2, 2);

        ConstraintViolationException exception = new ConstraintViolationException(validator.validate(magnitudeRelationForm));

        ResponseEntity<ResultObject> responseEntity = mockApiExceptionHandler.handleConstraintViolationException(exception);
        ResultObject resultObj = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();
        assertThat(resultObj.getName(), is(ErrorName.Basis.VALIDATION_ERROR));
        assertThat(resultObj.getDebugId(), is("I_BKT_40000001"));
        assertThat(resultObj.getMessage(), is("Input parameters include invalid values."));
        assertThat(statusCode, is(HttpStatus.BAD_REQUEST));
        assertThat(resultObj.getDetails().size(), is(1));

        assertTrue(("small_target").contains(resultObj.getDetails().get(0).getField()));
        assertThat(resultObj.getDetails().get(0).getIssue(), is("{1} must be larger than {0}."));
        assertThat(resultObj.getDetails().get(0).getIssueCode(), is("400-032"));
    }

    @Test
    public void testConstraintViolationMagnitudeRelationCaneEqual() throws Exception {


        HibernateValidatorConfiguration configure = Validation.byProvider(HibernateValidator.class).configure();

        List<String> resources = new ArrayList<String>();
        resources.add("ValidationMessages");

        ResourceBundleLocator aggregateResourceBundleLocator = new AggregateResourceBundleLocator(resources);

        configure.messageInterpolator(new ResourceBundleMessageInterpolator(aggregateResourceBundleLocator));
        ValidatorFactory factory = configure.buildValidatorFactory();
        factory.getValidator();
        Validator validator = factory.getValidator();
        MagnitudeRelationCanEqualForm magnitudeRelationCanEqualForm = new MagnitudeRelationCanEqualForm(3, 2);

        ConstraintViolationException exception = new ConstraintViolationException(validator.validate(magnitudeRelationCanEqualForm));

        ResponseEntity<ResultObject> responseEntity = mockApiExceptionHandler.handleConstraintViolationException(exception);
        ResultObject resultObj = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();
        assertThat(resultObj.getName(), is(ErrorName.Basis.VALIDATION_ERROR));
        assertThat(resultObj.getDebugId(), is("I_BKT_40000001"));
        assertThat(resultObj.getMessage(), is("Input parameters include invalid values."));
        assertThat(statusCode, is(HttpStatus.BAD_REQUEST));
        assertThat(resultObj.getDetails().size(), is(1));

        assertTrue(("small_target_test_123444").contains(resultObj.getDetails().get(0).getField()));
        assertThat(resultObj.getDetails().get(0).getIssue(), is("larger must be larger than or equal to small."));
        assertThat(resultObj.getDetails().get(0).getIssueCode(), is("400-033"));
    }

    @Test
    public void testConstraintViolationMagnitudeRelationCaneEqual2() throws Exception {


        HibernateValidatorConfiguration configure = Validation.byProvider(HibernateValidator.class).configure();

        List<String> resources = new ArrayList<String>();
        resources.add("ValidationMessages");

        ResourceBundleLocator aggregateResourceBundleLocator = new AggregateResourceBundleLocator(resources);

        configure.messageInterpolator(new ResourceBundleMessageInterpolator(aggregateResourceBundleLocator));
        ValidatorFactory factory = configure.buildValidatorFactory();
        factory.getValidator();
        Validator validator = factory.getValidator();
        ConverterToUnderLineWithNumberNotLastForm magnitudeRelationCanEqualForm = new ConverterToUnderLineWithNumberNotLastForm(3, 2, 3,3,3);

        ConstraintViolationException exception = new ConstraintViolationException(validator.validate(magnitudeRelationCanEqualForm));

        ResponseEntity<ResultObject> responseEntity = mockApiExceptionHandler.handleConstraintViolationException(exception);
        ResultObject resultObj = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();
        assertThat(resultObj.getName(), is(ErrorName.Basis.VALIDATION_ERROR));
        assertThat(resultObj.getDebugId(), is("I_BKT_40000001"));
        assertThat(resultObj.getMessage(), is("Input parameters include invalid values."));
        assertThat(statusCode, is(HttpStatus.BAD_REQUEST));
        assertThat(resultObj.getDetails().size(), is(4));

        List<String> fields = new ArrayList<>();
        fields.add(resultObj.getDetails().get(0).getField());
        fields.add(resultObj.getDetails().get(1).getField());
        fields.add(resultObj.getDetails().get(2).getField());
        fields.add(resultObj.getDetails().get(3).getField());

        assertTrue(fields.contains("small2_target_test_123444"));
        assertTrue(fields.contains("s2_target_test"));
        assertTrue(fields.contains("small123_target_test"));
        assertTrue(fields.contains("small_target_test_1"));
        assertThat(resultObj.getDetails().get(0).getIssue(), is("larger must be larger than or equal to small."));
        assertThat(resultObj.getDetails().get(0).getIssueCode(), is("400-033"));
    }


    @Data
    @MagnitudeRelation(small = "smallTarget", large = "large", canEqualFlg = false, messageParam={"MagnitudeRelationMessageParam"})
    private class MagnitudeRelationForm {

        public MagnitudeRelationForm(Integer smallTarget, Integer large) {
            super();
            this.smallTarget = smallTarget;
            this.large = large;
        }

        private Integer smallTarget;

        private Integer large;

    }

    @Data
    @MagnitudeRelation(small = "smallTargetTest123444", large = "large", messageParam={"MagnitudeRelationMessageParam"})
    private class MagnitudeRelationCanEqualForm {

        public MagnitudeRelationCanEqualForm(Integer smallTargetTest1, Integer large) {
            super();
            this.smallTargetTest123444 = smallTargetTest1;
            this.large = large;
        }

        private Integer smallTargetTest123444;

        private Integer large;

    }

    @Data
    @MagnitudeRelation.List({
            @MagnitudeRelation(small = "small2TargetTest123444", large = "large", messageParam={"MagnitudeRelationMessageParam"}),
            @MagnitudeRelation(small = "small123TargetTest", large = "large", messageParam={"MagnitudeRelationMessageParam"}),
            @MagnitudeRelation(small = "smallTargetTest1", large = "large", messageParam={"MagnitudeRelationMessageParam"}),
            @MagnitudeRelation(small = "s2TargetTest", large = "large", messageParam={"MagnitudeRelationMessageParam"})
    })
    private class ConverterToUnderLineWithNumberNotLastForm {

        public ConverterToUnderLineWithNumberNotLastForm(Integer small2TargetTest123444, Integer large, Integer small123TargetTest, Integer smallTargetTest1, Integer s2TargetTest) {
            super();
            this.small2TargetTest123444 = small2TargetTest123444;
            this.large = large;
            this.small123TargetTest = small123TargetTest;
            this.smallTargetTest1 = smallTargetTest1;
            this.s2TargetTest = s2TargetTest;
        }

        private Integer small2TargetTest123444;

        private Integer large;

        private Integer small123TargetTest;

        private Integer smallTargetTest1;

        private Integer s2TargetTest;
    }
}


