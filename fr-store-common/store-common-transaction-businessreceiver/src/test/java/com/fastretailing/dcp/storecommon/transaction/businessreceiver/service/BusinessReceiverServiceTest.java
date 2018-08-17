package com.fastretailing.dcp.storecommon.transaction.businessreceiver.service;

import org.springframework.stereotype.Service;

import com.fastretailing.dcp.storecommon.transaction.BusinessStatus;
import com.fastretailing.dcp.storecommon.transaction.dto.TransactionData;

@Service
public class BusinessReceiverServiceTest implements BusinessReceiverService {

    @Override
    public BusinessStatus receive(String transactionId, TransactionData transactionData) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean checkTransactionType(String transactionId, TransactionData transactionData) {
        // TODO Auto-generated method stub
        return false;
    }

}
