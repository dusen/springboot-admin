/**
 * @(#)TransactionPublishState.java
 *
 *                          Copyright (c) 2018 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.storecommon.transaction.businessreceiver.type;

/**
 * An published state of transaction to Redis.
 */
public enum TransactionPublishState {
    
    /** The state when message was published. */
    PUBLISHED, 
    
    /** The state when message was empty. */
    EMPTYDATA,
    
    /** The state when message publication failed. */
    FAILED;

}
