/**
 * @(#)S3ClientConfiguration.java
 *
 *                                Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.aws.s3;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.fastretailing.dcp.common.aws.s3.client.S3Client;
import com.fastretailing.dcp.common.aws.s3.client.S3ClientImpl;

/**
 * Configuration class of S3 client.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
@Configuration
public class S3ClientConfiguration {

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
     * Properties for S3 client.
     */
    @Autowired
    private S3ClientProperties s3Properties;

    /**
     * Initialize AmazonS3.
     * 
     * @return AmazonS3.
     */
    @Bean
    public AmazonS3 amazonS3() {

        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.withClientExecutionTimeout(s3Properties.getClientExecutionTimeout())
                .withConnectionMaxIdleMillis(s3Properties.getConnectionMaxIdleMilliSeconds())
                .withConnectionTimeout(s3Properties.getConnectionTimeout())
                .withMaxConnections(s3Properties.getMaxConnections())
                .withRequestTimeout(s3Properties.getRequestTimeout())
                .withSocketTimeout(s3Properties.getSocketTimeout());

        AmazonS3ClientBuilder amazonBuilder =
                AmazonS3ClientBuilder.standard().withClientConfiguration(clientConfiguration);

        if (StringUtils.isNoneBlank(serviceEndpoint, signingRegion)) {
            amazonBuilder.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                    serviceEndpoint, signingRegion));
        }

        return amazonBuilder.build();
    }

    /**
     * Initialize S3 Client.
     * 
     * @param amazonS3 AmazonS3.
     * @return S3 Client.
     */
    @Bean
    public S3Client s3Client(AmazonS3 amazonS3) {

        TransferManager transferManager =
                TransferManagerBuilder.standard().withS3Client(amazonS3).build();
        return new S3ClientImpl(amazonS3, transferManager, s3Properties.getExpirationSeconds());
    }

}
