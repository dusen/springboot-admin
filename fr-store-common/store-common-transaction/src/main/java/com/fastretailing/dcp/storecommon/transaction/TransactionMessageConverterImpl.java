/**
 * @(#)TransactionMessageConverterImpl.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction;

import java.io.IOException;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fastretailing.dcp.common.exception.SystemException;
import com.fastretailing.dcp.storecommon.transaction.dto.ReceiveMessage;
import com.fastretailing.dcp.storecommon.transaction.dto.TransactionData;
import lombok.extern.slf4j.Slf4j;

/**
 * This class converts published transaction messages and transaction data.
 */
@Slf4j
@Component
public class TransactionMessageConverterImpl implements TransactionMessageConverter {

    /** Mapping JSON data. */
    private static ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    /**
     * Class validation service.
     */
    @Autowired
    private TransactionDataValidator transactionDataValidator;

    /**
     * {@inheritDoc}
     */
    @Override
    public ReceiveMessage deserialize(String plainTransactionMessage, boolean validation) {

        if (plainTransactionMessage == null) {
            log.info("Plain transaction message is null.");
            return null;
        }

        ReceiveMessage transactionMessage = null;
        try {
            transactionMessage = mapper.readValue(plainTransactionMessage, ReceiveMessage.class);
            if (validation) {
                transactionDataValidator.validate(transactionMessage);
            }
        } catch (IOException e) {
            throw new SystemException("Failed to return by Jackson.", e);
        }

        return transactionMessage;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <D> D deserializeWrappingTransactionData(String plainTransactionMessage,
            Class<D> destinationClass, boolean validation) {

        ReceiveMessage subscribedInfo = deserialize(plainTransactionMessage, validation);

        D destinationInfo =
                deserializeWrappingTransactionData(subscribedInfo, destinationClass, validation);

        return destinationInfo;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <D> D deserializeWrappingTransactionData(ReceiveMessage receiveMessage,
            Class<D> destinationClass, boolean validation) {

        if (receiveMessage == null || receiveMessage.getTransactionData() == null) {
            log.info("Transaction message is null.");
            return null;
        }
        return deserializeTransactionData(
                receiveMessage.getTransactionData().getBusinessTransactionData(), destinationClass,
                validation);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <D> D deserializeTransactionData(String plainTransactionData, Class<D> destinationClass,
            boolean validation) {

        D destinationInfo = null;

        try {
            destinationInfo = mapper.readValue(plainTransactionData, destinationClass);

        } catch (IOException e) {
            throw new SystemException("Failed to return by Jackson.", e);
        }

        if (validation) {
            transactionDataValidator.validate(destinationInfo);
        }

        return destinationInfo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <D> D deserializeTransactionData(TransactionData transactionData,
            Class<D> destinationClass, boolean validation) {

        if (transactionData == null || transactionData.getBusinessTransactionData() == null) {
            log.info("Transaction data is null.");
            return null;
        }
        return deserializeTransactionData(transactionData.getBusinessTransactionData(),
                destinationClass, validation);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String serializeReceiveMessage(ReceiveMessage receiveMessage) {

        if (Objects.isNull(receiveMessage)) {
            return null;
        }

        String receiveMessageStr = null;
        try {
            receiveMessageStr = mapper.writeValueAsString(receiveMessage);
        } catch (IOException e) {
            log.info("Failed to input or output in parse from transaction data", e);
        }

        return receiveMessageStr;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String serializeTransactionData(TransactionData transactionData) {

        if (Objects.isNull(transactionData)) {
            return null;
        }

        String transactionDataStr = null;
        try {
            transactionDataStr = mapper.writeValueAsString(transactionData);
        } catch (IOException e) {
            log.info("Failed to input or output in parse from transaction data", e);
        }

        return transactionDataStr;
    }

}
