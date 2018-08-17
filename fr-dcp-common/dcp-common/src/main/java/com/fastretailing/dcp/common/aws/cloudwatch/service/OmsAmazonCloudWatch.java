/**
 * @(#)OmsAmazonCloudWatch.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.aws.cloudwatch.service;

import java.text.ParseException;

import com.amazonaws.services.cloudwatch.model.InternalServiceException;
import com.amazonaws.services.cloudwatch.model.InvalidParameterCombinationException;
import com.amazonaws.services.cloudwatch.model.InvalidParameterValueException;
import com.amazonaws.services.cloudwatch.model.MissingRequiredParameterException;
import com.amazonaws.services.cloudwatch.model.PutMetricDataResult;
import com.fastretailing.dcp.common.exception.SystemException;

/**
 * OMS AmazonCloudWatch' service interface.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public interface OmsAmazonCloudWatch {

    /**
     * Put Metric Data to the AmazonCloudWatchClient.<br>
     *
     * @param dimensionNameFromBatch Dimension Name From Batch
     * @param dimensionValueFromBatch Dimension Value From Batch
     * @param metricDatumNameFromBatch MetricDatum Name From Batch
     * @param metricDatumValueFromBatch MetricDatum Value From Batch
     * @param metricDatumTimestampFromBatch MetricDatum Timestamp From Batch
     * @param metricUnitFromBatch MetricDatum Unit From Batch
     * @param requestNamespaceFromBatch Name Space Timestamp From Batch
     *
     * @return PutMetricDataResult.
     *
     * @throws InvalidParameterValueException input parameter is bad
     * @throws MissingRequiredParameterException input parameter is required but missing
     * @throws InvalidParameterCombinationException input parameter cannot be used together
     * @throws InternalServiceException request processing has failed
     * @throws ParseException unparseable date
     * @throws SystemException other than those above
     */
    PutMetricDataResult putMetricData(
            String dimensionNameFromBatch,
            String dimensionValueFromBatch,
            String metricDatumNameFromBatch,
            int metricDatumValueFromBatch,
            String metricDatumTimestampFromBatch,
            String metricUnitFromBatch,
            String requestNamespaceFromBatch) throws InvalidParameterValueException,
                                                      MissingRequiredParameterException,
                                                      InvalidParameterCombinationException,
                                                      InternalServiceException,
                                                      ParseException,
                                                      SystemException;
}
