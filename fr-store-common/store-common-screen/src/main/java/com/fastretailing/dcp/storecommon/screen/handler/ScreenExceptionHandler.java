/**
 * @(#)ScreenExceptionHandler.java
 *
 *                                 Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.handler;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.fastretailing.dcp.common.exception.BusinessException;
import com.fastretailing.dcp.storecommon.screen.exception.AjaxScreenException;
import com.fastretailing.dcp.storecommon.screen.exception.ScreenException;
import com.fastretailing.dcp.storecommon.screen.form.CommonBaseForm;
import com.fastretailing.dcp.storecommon.screen.form.DetailError;
import com.fastretailing.dcp.storecommon.screen.form.FormBuilder;
import com.fastretailing.dcp.storecommon.screen.message.LocaleMessageSource;
import com.fastretailing.dcp.storecommon.screen.message.MessageType;
import lombok.extern.slf4j.Slf4j;

/**
 * The class is used to process the exception that occurred from screen application.
 *
 */
@Slf4j
@ControllerAdvice
public class ScreenExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * Http header for judging ajax request or not.
     */
    private static final String HTTP_HEADER_X_REQUESTED_WITH = "X-Requested-With";

    /**
     * Value of http header for judging ajax request or not.
     */
    private static final String HTTP_HEADER_VALUE_XML_HTTP_REQUEST = "XMLHttpRequest";

    /**
     * Common error page.
     */
    @Value("${screen.error.systemException.page:common-error}")
    private String systemExceptionErrorPage;

    /**
     * System error title.
     */
    @Value("${screen.error.systemException.title:common.error.systemError.title}")
    private String systemExceptionTitle;

    /**
     * System error message.
     */
    @Value("${screen.error.systemException.message:common.error.systemError.message}")
    private String systemExceptionMessage;

    /**
     * Common error page.
     */
    @Value("${screen.error.businessException.page:common-error}")
    private String businessExceptionErrorPage;

    /**
     * Business error title.
     */
    @Value("${screen.error.businessException.title:common.error.businessError.title}")
    private String businessExceptionTitle;

    /**
     * Local message source.
     */
    @Autowired
    private LocaleMessageSource localMessageSource;

    /**
     * Process the screen exception.
     *
     * @param screenException Occur exception.
     * @return The target page and data model.
     */
    @ExceptionHandler(value = ScreenException.class)
    public Object screenExceptionHandler(ScreenException screenException,
            HttpServletRequest request) {

        if (isAjaxRequest(request)) {
            if (screenException.getBindResult() != null) {
                AjaxScreenException ajaxScreenException =
                        new AjaxScreenException(screenException.getBindResult().getFieldErrors());
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                return new ResponseEntity<>(ajaxScreenException, headers, HttpStatus.BAD_REQUEST);
            } else {
                AjaxScreenException ajaxScreenException = new AjaxScreenException(
                        MessageType
                                .getTypeByValue(screenException.getDetailError().getMessageType()),
                        screenException.getDetailError());
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                return new ResponseEntity<>(ajaxScreenException, headers, HttpStatus.BAD_REQUEST);
            }
        }

        ModelAndView modelView = new ModelAndView();

        CommonBaseForm commonBaseForm =
                (CommonBaseForm) screenException.getModelMap().get("commonBaseForm");
        DetailError detailError = screenException.getDetailError();
        String url = screenException.getTargetUrl();

        modelView.setViewName(url);
        commonBaseForm.setDetailError(detailError);
        screenException.getModelMap().put("commonBaseForm", commonBaseForm);

        modelView.addAllObjects(screenException.getModelMap());
        return modelView;
    }

    /**
     * Process the business exception.
     *
     * @param businessException Occur exception.
     * @return The target page and data model.
     */
    @ExceptionHandler(value = BusinessException.class)
    public Object screenExceptionHandler(BusinessException businessException,
            HttpServletRequest request) {

        log.error("Invoking @handleDefaultException method", businessException);

        DetailError detailError = new DetailError();
        detailError.setErrorMessageTitle(localMessageSource.getMessage(businessExceptionTitle,
                new String[] {businessException.getResultObject().getDebugId()}));
        detailError.setErrorMessage(businessException.getResultObject().getMessage());
        detailError.setErrorCode(businessException.getResultObject().getDebugId());
        MessageType messageType = getMessageType(businessException.getResultObject().getDebugId());
        detailError.setMessageType(messageType.getType());

        if (isAjaxRequest(request)) {
            AjaxScreenException ajaxScreenException =
                    new AjaxScreenException(messageType, detailError);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            return new ResponseEntity<>(ajaxScreenException, headers, HttpStatus.BAD_REQUEST);
        }

        ModelAndView modelView = new ModelAndView();

        modelView.setViewName(businessExceptionErrorPage);

        CommonBaseForm commonBaseForm = FormBuilder.build(request);

        commonBaseForm.setDetailError(detailError);
        modelView.addObject("commonBaseForm", commonBaseForm);

        return modelView;
    }

    /**
     * Process the AjaxScreenException.
     * 
     * @param ajaxScreenException Occur exception.
     * @return error Detail object
     */
    @ExceptionHandler(value = AjaxScreenException.class)
    @ResponseBody()
    public ResponseEntity<AjaxScreenException> ajaxScreenExceptionHandler(
            AjaxScreenException ajaxScreenException) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(ajaxScreenException, headers, HttpStatus.BAD_REQUEST);
    }

    /**
     * Invoked to send a server error. Sets the status to 500 and also sets the request attribute
     * "javax.servlet.error.exception" to the exception. The default implementation sends a HTTP 500
     * error, and returns an empty.
     *
     * @param exception The exception to be handled.
     * @return ResultObject handled exception info.
     * @throws IOException I/O exception.
     */
    @ExceptionHandler({Exception.class})
    public Object handleDefaultException(Exception exception, HttpServletRequest request) {

        log.error("Invoking @handleDefaultException method", exception);

        DetailError detailError = new DetailError();
        detailError.setErrorMessageTitle(localMessageSource.getMessage(systemExceptionTitle));
        detailError.setErrorMessage(localMessageSource.getMessage(systemExceptionMessage));

        if (isAjaxRequest(request)) {
            AjaxScreenException ajaxScreenException =
                    new AjaxScreenException(MessageType.ERROR, detailError);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            return new ResponseEntity<>(ajaxScreenException, headers, HttpStatus.BAD_REQUEST);
        }

        ModelAndView modelView = new ModelAndView();

        modelView.setViewName(systemExceptionErrorPage);

        CommonBaseForm commonBaseForm = FormBuilder.build(request);

        commonBaseForm.setDetailError(detailError);
        modelView.addObject("commonBaseForm", commonBaseForm);

        return modelView;
    }

    /**
     * If the request is an aJax request, return true.
     * 
     * @param request Http reqeust.
     * @return True if is an aJax request.
     */
    private boolean isAjaxRequest(HttpServletRequest request) {
        return HTTP_HEADER_VALUE_XML_HTTP_REQUEST
                .equals(request.getHeader(HTTP_HEADER_X_REQUESTED_WITH));
    }

    /**
     * Warning[W], Error[E], Info[I].
     * 
     * @param errorId error id.
     * @return Message type.
     */
    private MessageType getMessageType(String errorId) {

        if (StringUtils.isNotEmpty(errorId)) {
            return MessageType.getTypeByValue(errorId.substring(0, 1));
        }
        return MessageType.ERROR;
    }
}
