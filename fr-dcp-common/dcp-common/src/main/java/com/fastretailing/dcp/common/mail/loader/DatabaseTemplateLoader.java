/**
 * @(#)DatabaseTemplateLoader.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.mail.loader;

import com.fastretailing.dcp.common.mail.entity.MailTemplateEntity;
import com.fastretailing.dcp.common.mail.service.OmsMailService;
import com.fastretailing.dcp.common.mail.util.MailUtility;
import freemarker.cache.TemplateLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.time.ZoneId;
import java.util.Map;

/**
 * Database's mail template's loader.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Component
public class DatabaseTemplateLoader implements TemplateLoader {

    /**
     * Mail service.<br>
     */
    @Autowired
    private OmsMailService service;

    /**
     * Finds the template in the backing storage and returns an object that
     * identifies the storage location where the
     * template can be loaded from. See the return value for more information.<br>
     *
     * @param name template's name , The name's format is: [template name]@[local]
     * @return MailTemplate
     * @throws IOException IOException
     */
    @Override
    public Object findTemplateSource(String name) throws IOException {
        Map<String, String> params = MailUtility.analyzeTemplateKey(name);
        if (params.size() > 0) {
            return service.findTemplate(
                params.get(MailUtility.KEY_BRAND_CODE),
                params.get(MailUtility.KEY_REGION_CODE),
                params.get(MailUtility.KEY_LANGUAGE_CODE),
                params.get(MailUtility.KEY_MAIL_TYPE),
                params.get(MailUtility.KEY_PAYMENT_TYPE),
                params.get(MailUtility.KEY_DELIVERY_TYPE),
                params.get(MailUtility.KEY_RECEIVE_TYPE)
            );
        }
        return null;
    }

    /**
     * Returns the time of last modification of the specified template source.<br>
     * @param templateSource an object representing a template source
     * @return  the time of last modification of the specified template source,
     *              or -1 if the time is not known.
     */
    @Override
    public long getLastModified(Object templateSource) {

        if (templateSource == null) {
            return -1L;
        }

        MailTemplateEntity entity = (MailTemplateEntity) templateSource;
        if (entity.getUpdatedDatetime() == null) {
            return -1L;
        }

        return entity.getUpdatedDatetime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * Returns the character stream of a template represented by the specified template source.<br>
     * @param templateSource templateSource
     * @param encoding an object representing a template source
     * @return the character encoding used to translate source bytes to characters
     * @throws IOException IOException
     */
    @Override
    public Reader getReader(Object templateSource, String encoding) throws IOException {
        return new StringReader(((MailTemplateEntity) templateSource).getTextTemplate());
    }

    /**
     * Closes the template source,
     * releasing any resources held that are only required for reading the template and/or
     * its metadata.
     * @param templateSource the template source that should be closed.
     * @throws IOException IOException
     */
    @Override
    public void closeTemplateSource(Object templateSource) throws IOException {
        // Nothing to do here
    }

}
