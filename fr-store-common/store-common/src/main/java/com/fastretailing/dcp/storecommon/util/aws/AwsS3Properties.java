/**
 * @(#)AwsS3Properties.java
 *
 *                          Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.util.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import com.amazonaws.ClientConfiguration;
import lombok.Data;

/**
 * Configuration properties for AWS S3 client.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
@ConfigurationProperties(prefix = "aws.s3")
public class AwsS3Properties {

    /**
     * The amount of time (in milliseconds) to allow the client to complete the execution of an API
     * call.
     */
    private int clientExecutionTimeout = ClientConfiguration.DEFAULT_CLIENT_EXECUTION_TIMEOUT;

    /**
     * The amount of time to wait (in milliseconds) when initially establishing a connection before
     * giving up and timing out.
     */
    private int connectionTimeout = ClientConfiguration.DEFAULT_CONNECTION_TIMEOUT;

    /**
     * The maximum amount of time that an idle connection may sit in the connection pool and still
     * be eligible for reuse.
     */
    private long connectionMaxIdleMillis = ClientConfiguration.DEFAULT_CONNECTION_MAX_IDLE_MILLIS;

    /**
     * The maximum number of allowed open HTTP connections.
     */
    private int maxConnections = ClientConfiguration.DEFAULT_MAX_CONNECTIONS;

    /**
     * The amount of time to wait (in milliseconds) for the request to complete before giving up and
     * timing out.
     */
    private int requestTimeout = ClientConfiguration.DEFAULT_REQUEST_TIMEOUT;

    /**
     * Tthe amount of time to wait (in milliseconds) for data to be transfered over an established,
     * open connection before the connection times out and is closed.
     */
    private int socketTimeout = ClientConfiguration.DEFAULT_SOCKET_TIMEOUT;

    /**
     * The expiration time of Pre-Signed URL.
     */
    @Value("${url.expiration:300}")
    private long urlExpiration;
}
