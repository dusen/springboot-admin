/**
 * @(#)TransactionPublishingServiceImpl.java
 *
 *                                           Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.businessreceiver.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import com.fastretailing.dcp.storecommon.transaction.TransactionMessageConverter;
import com.fastretailing.dcp.storecommon.transaction.businessreceiver.config.BusinessReceiverConfiguration;
import com.fastretailing.dcp.storecommon.transaction.businessreceiver.type.TransactionPublishState;
import com.fastretailing.dcp.storecommon.transaction.dto.ReceiveMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * This class publishes a transaction message to Redis.
 */
@Slf4j
@Service
public class TransactionPublishingServiceImpl implements TransactionPublishingService {

    /** Parts for publish to Redis. */
    @Autowired
    private StringRedisTemplate template;

    /** String and object converter. */
    @Autowired
    private TransactionMessageConverter transactionMessageConverter;

    /** Current batch configuration. */
    @Autowired
    private BusinessReceiverConfiguration businessReceiverConfiguration;

    /**
     * {@inheritDoc}
     */
    @Override
    public TransactionPublishState publish(ReceiveMessage receiveMessage) {

        try {

            String publishData =
                    transactionMessageConverter.serializeReceiveMessage(receiveMessage);

            if (StringUtils.isEmpty(publishData)) {
                log.error("Published data is null or empty. ReceiveMessage={}/publishData={}",
                        receiveMessage, publishData);
                return TransactionPublishState.EMPTYDATA;
            }

            template.convertAndSend(businessReceiverConfiguration.getChannelCode(), publishData);

            return TransactionPublishState.PUBLISHED;

        } catch (Exception e) {

            log.error("Any Redis API execution failed.", e);
            return TransactionPublishState.FAILED;

        }
    }

}
