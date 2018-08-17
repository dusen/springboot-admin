/**
 * @(#)BusinessProcessorConfiguration.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.businessprocessor.config;

import java.util.List;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

/**
 * Manage batch parameters by spring cloud.
 */
@Component
@ConfigurationProperties(prefix = "transaction-dispatch")
@Data
public class BusinessProcessorConfiguration {

    /** Redis publish channel code. */
    @NotBlank
    private String channelCode;

    /** Read dynamoDB thread max count. */
    private int maxThreadCount;

    /** Current process business name. */
    @NotBlank
    private String businessProcessName;

    /** Capacity for the ThreadPoolExecutor's BlockingQueue. */
    private int queueCapacity;

    /** Accepting transaction type. */
    private List<String> acceptingTransactionType;
}
