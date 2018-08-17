/**
 * @(#)GeneralDataDeleteMapperScanConfiguration.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.businessreceiver.repository;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Mapper scan configuration.
 */
@Configuration
@MapperScan(basePackages = {
        "com.fastretailing.dcp.storecommon.transaction.businessreceiver.repository"})
public class BusinessReceiverCommonMapperScanConfiguration {

}
