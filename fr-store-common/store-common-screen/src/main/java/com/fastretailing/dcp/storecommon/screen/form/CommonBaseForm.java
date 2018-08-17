/**
 * @(#)CommonBaseForm.java
 *
 *                         Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.form;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import org.springframework.util.ObjectUtils;
import com.fastretailing.dcp.storecommon.screen.authentication.AuthenticationUtil;
import com.fastretailing.dcp.storecommon.screen.message.MessageType;
import lombok.Data;
import lombok.Getter;

/**
 * The base form for screen application, that is used to save common item and error information.
 *
 */
@Data
public class CommonBaseForm implements Serializable {

    /**
     * Serial version uid.
     */
    private static final long serialVersionUID = 9053450217882604293L;

    /** Country code. */
    private String countryCode;
    /** Default locale. */
    private String defaultLocale;
    /** Selected locale. */
    private String specifyLocale;
    /** Brand code. */
    private String brandCode;
    /** Menu name. */
    private String menuName;
    /** Menu list. */
    private List<MenuElement> menuList;
    /** Error detail. */
    private DetailError detailError;

    /** Temporary data. */
    private String temporaryData;

    /** Url base path. */
    private String urlBasePath;

    /**
     * Returns the login user ID.
     * 
     * @return the login user ID.
     */
    public String getLoginUserId() {
        return AuthenticationUtil.getUserDetails().getUsername();
    }

    /**
     * Sets the login user ID.
     * 
     * @param loginUserId the login user ID.
     */
    @Deprecated
    public void setLoginUserId(String loginUserId) {
        // Do not process anything
    }

    /**
     * Returns the login store code.
     * 
     * @return the login store code.
     */
    public String getLoginStoreCode() {
        return AuthenticationUtil.getUserDetails().getStoreCode();
    }

    /**
     * Sets the login store code.
     * 
     * @param loginUserId the login store code.
     */
    @Deprecated
    public void setLoginStoreCode(String loginStoreCode) {
        // Do not process anything
    }

    /**
     * Common item for all forms.
     */
    public enum Item {

        /** User id. */
        LOGIN_USER("loginUser"),
        /** Store code. */
        LOGIN_STORE("loginStore"),
        /** Default locale. */
        DEFAULT_LOCALE("defaultLocale"),
        /** Specify locale. */
        SPECIFY_LOCALE("specifyLocale"),
        /** Brand code. */
        BRAND_CODE("brandCode"),
        /** Country code. */
        COUNTRY_CODE("countryCode"),
        /** Url base path. */
        URL_BASE_PATH("urlBasePath"),
        /** Menu name. */
        MENU_NAME("menuName");

        /** Item name. */
        @Getter
        private String name;

        /**
         * Constructor for class.
         * 
         * @param name Item name.
         */
        private Item(String name) {
            this.name = name;
        }
    }

    /**
     * Set a detail error.
     *
     * @param errorCode The error code.
     * @param errorMessageTitle The error message title.
     * @param errorMessage The error message.
     * @param errorFields The field where error occurred.
     * @param messageType The message type.
     */
    public void setDetailError(String errorCode, String errorMessageTitle, String errorMessage,
            String[] errorFields, String messageType) {
        DetailError detailError = new DetailError();
        detailError.setErrorCode(errorCode);
        detailError.setErrorMessageTitle(errorMessageTitle);
        detailError.setErrorMessage(errorMessage);
        if (!ObjectUtils.isEmpty(errorFields)) {
            detailError.setErrorFieldList(Arrays.asList(errorFields));
        }
        detailError.setMessageType(messageType);

        this.detailError = detailError;
    }

    /**
     * Set a detail error with default error message type.
     *
     * @param errorCode The error code.
     * @param errorMessageTitle The error message title.
     * @param errorMessage The error message.
     * @param errorFields The field where error occurred.
     */
    public void setDetailError(String errorCode, String errorMessageTitle, String errorMessage,
            String[] errorFields) {
        setDetailError(errorCode, errorMessageTitle, errorMessage, errorFields,
                MessageType.ERROR.getType());
    }
}
