/**
 * @(#)FormBuilder.java
 *
 *                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.screen.form;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.util.StringUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fastretailing.dcp.common.exception.SystemException;
import com.fastretailing.dcp.storecommon.screen.menu.MenuComposer;
import lombok.extern.slf4j.Slf4j;

/**
 * Form helper class.
 *
 */
@Slf4j
public class FormBuilder {

    /**
     * Builds a form, sets common items.
     * 
     * @param formClass Form to build.
     * @param commonForm Common form.
     * @return Form built.
     */
    @SuppressWarnings("unchecked")
    public static <T extends CommonBaseForm> T build(Class<? extends T> formClass,
            CommonBaseForm commonForm) {

        CommonBaseForm form;
        try {
            form = formClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("Error in init form class {}", formClass.getName());
            throw new SystemException("Error in init form class", e);
        }

        if (ObjectUtils.allNotNull(form)) {

            form.setBrandCode(commonForm.getBrandCode());
            form.setCountryCode(commonForm.getCountryCode());
            form.setDefaultLocale(commonForm.getDefaultLocale());
            form.setMenuList(commonForm.getMenuList());
            form.setSpecifyLocale(commonForm.getSpecifyLocale());
            form.setUrlBasePath(commonForm.getUrlBasePath());
            form.setMenuName(commonForm.getMenuName());
            form.setMenuList(commonForm.getMenuList());
        }

        return (T) form;
    }

    /**
     * Builds a form, get common items from request.
     * 
     * @param request Http request.
     * @return Form built.
     */
    public static CommonBaseForm build(HttpServletRequest request) {

        CommonBaseForm form = new CommonBaseForm();
        form.setCountryCode(request.getParameter(CommonBaseForm.Item.COUNTRY_CODE.getName()));
        form.setDefaultLocale(request.getParameter(CommonBaseForm.Item.DEFAULT_LOCALE.getName()));
        form.setSpecifyLocale(request.getParameter(CommonBaseForm.Item.SPECIFY_LOCALE.getName()));
        form.setBrandCode(request.getParameter(CommonBaseForm.Item.BRAND_CODE.getName()));
        form.setUrlBasePath(request.getParameter(CommonBaseForm.Item.URL_BASE_PATH.getName()));
        form.setMenuName(request.getParameter(MenuComposer.MENU_NAME_REQUEST_PARAMETER));

        return form;
    }

    /**
     * Set temporary data.
     * 
     * @param key The key of data.
     * @param data Temporary data to keep.
     * @param commonForm CommonBaseForm.
     */
    public static void setTemporaryData(String key, Object data, CommonBaseForm commonForm) {
        try {
            ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
                    .modules(new JavaTimeModule())
                    .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
                            DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
                    .build();
            JsonNode jsonData = objectMapper.convertValue(data, JsonNode.class);
            String temporaryData = commonForm.getTemporaryData();
            ObjectNode objectNode;
            if (!StringUtils.isEmpty(temporaryData)) {
                JsonNode rootNode = objectMapper.readTree(temporaryData);
                objectNode = (ObjectNode) rootNode;
            } else {
                objectNode = objectMapper.createObjectNode();

            }
            objectNode.set(key, jsonData);
            temporaryData = objectMapper.writeValueAsString(objectNode);
            commonForm.setTemporaryData(temporaryData);
        } catch (IOException e) {
            throw new SystemException("Error in set temporary data to form", e);
        }
    }

    /**
     * Remove the data from temporary data by key.
     * 
     * @param key The key of data.
     * @param commonForm CommonBaseForm.
     */
    public static void removeTemporaryData(String key, CommonBaseForm commonForm) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String temporaryData = commonForm.getTemporaryData();
            if (!StringUtils.isEmpty(temporaryData)) {
                JsonNode rootNode = objectMapper.readTree(temporaryData);
                ObjectNode objectNode = (ObjectNode) rootNode;
                if (objectNode.has(key)) {
                    objectNode.remove(key);
                }
                temporaryData = objectMapper.writeValueAsString(objectNode);
                commonForm.setTemporaryData(temporaryData);
            }
        } catch (IOException e) {
            throw new SystemException("Error in remove temporary data from form", e);
        }
    }

    /**
     * Convert the key data to a target instance.
     * 
     * @param key The key of data.
     * @param valueType Converted target class type.
     * @param commonForm CommonBaseForm.
     * @return T Converted target instance.
     */
    public static <T> T getTemporaryData(String key, Class<T> valueType,
            CommonBaseForm commonForm) {
        try {
            ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
                    .modules(new JavaTimeModule())
                    .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
                            DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
                    .build();
            String temporaryData = commonForm.getTemporaryData();
            if (!StringUtils.isEmpty(temporaryData)) {
                JsonNode rootNode = objectMapper.readTree(temporaryData);
                ObjectNode objectNode = (ObjectNode) rootNode;
                if (objectNode.has(key)) {
                    String objectData = objectMapper.writeValueAsString(objectNode.get(key));
                    return objectMapper.readValue(objectData, valueType);
                }
            }
            return null;
        } catch (IOException e) {
            throw new SystemException("Error in get temporary data from form", e);
        }
    }
}
