/**
 * @(#)TransactionMessageConverter.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction;

import com.fastretailing.dcp.storecommon.transaction.dto.ReceiveMessage;
import com.fastretailing.dcp.storecommon.transaction.dto.TransactionData;

/**
 * This class converts published transaction messages and transaction data.
 */
public interface TransactionMessageConverter {

    /**
     * Converts a published transaction message to a transaction message object.
     * 
     * @param plainTransactionMessage Published transaction message.
     * @param validation Boolean value to check whether to perform validation.
     * @return Transaction message object.
     */
    ReceiveMessage deserialize(String plainTransactionMessage, boolean validation);

    /**
     * Converts the transaction data contained in the published transaction message to the specified
     * class object.
     * 
     * @param <D> Transaction data destination class.
     * 
     * @param plainTransactionMessage Published transaction message.
     * @param destinationClass Class to be converted.
     * @param validation Boolean value to check whether to perform validation.
     * @return the specified class object.
     */
    <D> D deserializeWrappingTransactionData(String plainTransactionMessage,
            Class<D> destinationClass, boolean validation);

    /**
     * Converts the transaction data contained in the published transaction message to the specified
     * class object.
     * 
     * @param <D> Transaction data destination class.
     * 
     * @param receiveMessage Published transaction message object.
     * @param destinationClass Class to be converted.
     * @param validation Boolean value to check whether to perform validation.
     * @return the specified class object.
     */
    <D> D deserializeWrappingTransactionData(ReceiveMessage receiveMessage,
            Class<D> destinationClass, boolean validation);

    /**
     * Converts the string of transaction data to the specified class object.
     * 
     * @param <D> Transaction data destination class.
     * 
     * @param plainTransactionData Transaction data string.
     * @param destinationClass Class to be converted.
     * @param validation Boolean value to check whether to perform validation.
     * @return the specified class object.
     */
    <D> D deserializeTransactionData(String plainTransactionData, Class<D> destinationClass,
            boolean validation);

    /**
     * Converts the object of transaction data to the specified class object.
     * 
     * @param <D> Transaction data destination class.
     * 
     * @param transactionData Transaction data.
     * @param destinationClass Class to be converted.
     * @param validation Boolean value to check whether to perform validation.
     * @return the specified class object.
     */
    <D> D deserializeTransactionData(TransactionData transactionData, Class<D> destinationClass,
            boolean validation);

    /**
     * Converts the object of receive message to strings.
     * 
     * @param receiveMessage receive message.
     * @return String converted receive message.
     */
    String serializeReceiveMessage(ReceiveMessage receiveMessage);

    /**
     * Converts the object of transaction data to strings.
     * 
     * @param transactionData Transaction data.
     * @return String converted transaction data.
     */
    String serializeTransactionData(TransactionData transactionData);

}
