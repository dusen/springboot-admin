/**
 * @(#)BusinessReceiverConfiguration.java
 *
 *                                        Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.businessreceiver.config;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import lombok.Data;

/**
 * Manage batch parameters by spring cloud.
 */
@Component
@ConfigurationProperties(prefix = "transaction-dispatch")
@Data
@Validated
public class BusinessReceiverConfiguration {

    /** Redis publish channel code. */
    @NotBlank
    private String channelCode;

    /** SQS max number of message. */
    @Min(1)
    @Max(10)
    private int maxNumberOfMessage;

    /** Wait time out seconds for long polling to SQS. */
    @Min(0)
    @Max(20)
    private int waitTimeOutSeconds;

    /** AWS region. */
    @NotBlank
    private String awsRegion;

    /** Business receiver name. */
    @NotBlank
    private String businessReceiverName;

}
