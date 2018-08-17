/**
 * @(#)OmsAmazonS3ClientConfiguration.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.aws.s3;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * AmazonS3 client's configuration.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Configuration
@Component
@ConditionalOnProperty(prefix = "cloud.aws.region", name = "static")
public class OmsAmazonS3ClientConfiguration {

    /**
     * The AWS access key.<br>
     */
    @Value("${cloud.aws.credentials.accessKey:#{null}}")
    private String accessKey;

    /**
     * The AWS secret access key.<br>
     */
    @Value("${cloud.aws.credentials.secretKey:#{null}}")
    private String secretKey;

    /**
     * the service endpoint either with or without the protocol.<br>
     */
    @Value("${cloud.aws.region.serviceEndpoint}")
    private String serviceEndpoint;

    /**
     * the region to use for SigV4 signing of requests.<br>
     */
    @Value("${cloud.aws.region.static}")
    private String signingRegion;

    /**
     * AmazonS3 proxy's configuration.<br>
     */
    @Autowired(required = false)
    private ClientConfiguration clientConfiguration;

    /**
     * Initialize AmazonS3 client's environment.<br>
     *
     * @return AmazonS3 client
     */
    @Bean
    @Primary
    public AmazonS3 initAmazonS3() {

        AmazonS3ClientBuilder amazonBuilder = AmazonS3ClientBuilder.standard(
                ).withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(
                                this.serviceEndpoint, this.signingRegion
                        )
        ).withClientConfiguration(clientConfiguration);

        if (StringUtils.isNotEmpty(accessKey)
                && StringUtils.isNotEmpty(secretKey)) {
            amazonBuilder.setCredentials(
                    new AWSStaticCredentialsProvider(
                            new BasicAWSCredentials(this.accessKey, this.secretKey)
                    )
            );
        }

        return amazonBuilder.build();
    }

}
