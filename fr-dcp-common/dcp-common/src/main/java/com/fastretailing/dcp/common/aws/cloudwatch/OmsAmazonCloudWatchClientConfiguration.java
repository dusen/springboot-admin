/**
 * @(#)OmsAmazonCloudWatchClientConfiguration.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.aws.cloudwatch;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;

/**
 * AmazonCloudWatch client's configuration.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Configuration
@Component
public class OmsAmazonCloudWatchClientConfiguration {

    /**
     * The AWS access key.<br>
     */
    @Value("${cloud.aws.cloudwatch.accessKey:#{null}}")
    private String accessKey;

    /**
     * The AWS secret access key.<br>
     */
    @Value("${cloud.aws.cloudwatch.secretKey:#{null}}")
    private String secretKey;

    /**
     * the service endpoint either with or without the protocol.<br>
     */
    @Value("${cloud.aws.cloudwatch.serviceEndpoint:#{null}}")
    private String serviceEndpoint;

    /**
     * the region to use for SigV4 signing of requests.<br>
     */
    @Value("${cloud.aws.cloudwatch.signingRegion:#{null}}")
    private String signingRegion;

    /**
     * AmazonCloudWatch proxy's configuration.<br>
     */
    @Autowired(required = false)
    private ClientConfiguration clientConfiguration;

    /**
     * Initialize AmazonCloudWatch client's environment.<br>
     *
     * @return AmazonCloudWatch client
     */
    @Bean
    @Primary
    public AmazonCloudWatch initAmazonCloudWatch() {

        AmazonCloudWatchClientBuilder clientBuilder = AmazonCloudWatchClientBuilder.standard()
                .withClientConfiguration(clientConfiguration);

        if (StringUtils.isNotEmpty(serviceEndpoint)
                && StringUtils.isNotEmpty(signingRegion)) {
            clientBuilder.setEndpointConfiguration(
                    new EndpointConfiguration(
                            this.serviceEndpoint, this.signingRegion));
        }

        if (StringUtils.isNotEmpty(accessKey)
                && StringUtils.isNotEmpty(secretKey)) {
            clientBuilder.setCredentials(
                    new AWSStaticCredentialsProvider(
                            new BasicAWSCredentials(this.accessKey, this.secretKey)));
        }

        return clientBuilder.build();
    }
}
