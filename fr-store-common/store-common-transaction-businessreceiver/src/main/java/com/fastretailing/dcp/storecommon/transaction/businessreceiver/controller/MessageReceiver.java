/**
 * @(#)MessageReceiver.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.businessreceiver.controller;

/**
 * Transaction message listener interface.
 */
public interface MessageReceiver {

    /**
     * Receives a transaction message.
     * 
     * @param message Received message.
     */
    void receive(String message);
}
