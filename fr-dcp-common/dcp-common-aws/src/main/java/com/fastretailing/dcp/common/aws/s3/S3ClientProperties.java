/**
 * @(#)S3ClientProperties.java
 *
 *                             Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.aws.s3;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import com.amazonaws.ClientConfiguration;
import lombok.Data;

/**
 * Configuration properties for AWS S3 client.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
@Component
@ConfigurationProperties(prefix = "cloud.aws.s3")
public class S3ClientProperties {

    /**
     * the amount of time (in milliseconds) to allow the client to complete the execution of an API
     * call.
     */
    private int clientExecutionTimeout = ClientConfiguration.DEFAULT_CLIENT_EXECUTION_TIMEOUT;

    /**
     * the amount of time to wait (in milliseconds) when initially establishing a connection before
     * giving up and timing out.
     */
    private int connectionTimeout = ClientConfiguration.DEFAULT_CONNECTION_TIMEOUT;

    /**
     * the maximum amount of time that an idle connection may sit in the connection pool and still
     * be eligible for reuse.
     */
    private long connectionMaxIdleMilliSeconds = ClientConfiguration.DEFAULT_CONNECTION_MAX_IDLE_MILLIS;

    /**
     * the maximum number of allowed open HTTP connections.
     */
    private int maxConnections = ClientConfiguration.DEFAULT_MAX_CONNECTIONS;

    /**
     * the amount of time to wait (in milliseconds) for the request to complete before giving up and
     * timing out.
     */
    private int requestTimeout = ClientConfiguration.DEFAULT_REQUEST_TIMEOUT;

    /**
     * the amount of time to wait (in milliseconds) for data to be transferred over an established,
     * open connection before the connection times out and is closed.
     */
    private int socketTimeout = ClientConfiguration.DEFAULT_SOCKET_TIMEOUT;

    /**
     * the number of seconds before Pre-Signed URL expires.
     */
    private Integer expirationSeconds;
}
