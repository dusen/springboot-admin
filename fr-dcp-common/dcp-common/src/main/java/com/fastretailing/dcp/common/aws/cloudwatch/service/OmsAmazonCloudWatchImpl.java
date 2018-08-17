/**
 * @(#)OmsAmazonCloudWatchImpl.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.aws.cloudwatch.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.InternalServiceException;
import com.amazonaws.services.cloudwatch.model.InvalidParameterCombinationException;
import com.amazonaws.services.cloudwatch.model.InvalidParameterValueException;
import com.amazonaws.services.cloudwatch.model.MetricDatum;
import com.amazonaws.services.cloudwatch.model.MissingRequiredParameterException;
import com.amazonaws.services.cloudwatch.model.PutMetricDataRequest;
import com.amazonaws.services.cloudwatch.model.PutMetricDataResult;
import com.amazonaws.services.cloudwatch.model.StandardUnit;
import com.fastretailing.dcp.common.exception.SystemException;

import lombok.extern.slf4j.Slf4j;

/**
 * OMS AmazonCloudWatch' service interface's implement class.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */

@Slf4j
@Component
public class OmsAmazonCloudWatchImpl implements OmsAmazonCloudWatch {

    /**
     * AmazonCloudWatch client's configuration.<br>
     *
     * @author Fast Retailing
     * @version $Revision$
     */
    @Autowired
    AmazonCloudWatch amazonCloudWatch;

    /**
     * TIME_STAMP_FORMATTER : yyyyMMdd.
     */
    private static final String TIME_STAMP_FORMATTER = "yyyy-MM-dd HH:mm:ss.SSS";

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
    @Override
    public PutMetricDataResult putMetricData(
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
                                                      SystemException {

        try {

            StandardUnit unit = StandardUnit.Count;
            if (StringUtils.isNotEmpty(metricUnitFromBatch)) {
                unit = StandardUnit.fromValue(metricUnitFromBatch);
            }

            double value = Double.valueOf(metricDatumValueFromBatch);

            SimpleDateFormat sdFormat = new SimpleDateFormat(TIME_STAMP_FORMATTER);
            Date date = sdFormat.parse(metricDatumTimestampFromBatch);

            Dimension dimension = new Dimension()
                    .withName(dimensionNameFromBatch)
                    .withValue(dimensionValueFromBatch);

            MetricDatum datum = new MetricDatum()
                    .withMetricName(metricDatumNameFromBatch)
                    .withDimensions(dimension)
                    .withTimestamp(date)
                    .withValue(value)
                    .withUnit(unit);

            PutMetricDataRequest request = new PutMetricDataRequest()
                    .withNamespace(requestNamespaceFromBatch)
                    .withMetricData(datum);

            PutMetricDataResult response = amazonCloudWatch.putMetricData(request);

            return response;

        } catch (InvalidParameterValueException e) {
            String errorMessage = "AmazonCloudWatch putMetricData error.";
            errorMessage += "The value of an input parameter is bad or out-of-range.";
            log.error(errorMessage, e);
            throw new InvalidParameterValueException(errorMessage);
        } catch (MissingRequiredParameterException e) {
            String errorMessage = "AmazonCloudWatch putMetricData error.";
            errorMessage += "An input parameter that is required is missing.";
            log.error(errorMessage, e);
            throw new MissingRequiredParameterException(errorMessage);
        } catch (InvalidParameterCombinationException e) {
            String errorMessage = "AmazonCloudWatch putMetricData error.";
            errorMessage += "Parameters were used together that cannot be used together.";
            log.error(errorMessage, e);
            throw new InvalidParameterCombinationException(errorMessage);
        } catch (InternalServiceException e) {
            String errorMessage = "AmazonCloudWatch putMetricData error.";
            errorMessage += "Request processing has failed due to some unknown error, exception, or failure.";
            log.error(errorMessage, e);
            throw new InternalServiceException(errorMessage);
        } catch (ParseException e) {
            String errorMessage = "AmazonCloudWatch putMetricData error.";
            errorMessage += "Unparseable date.";
            log.error(errorMessage, e);
            throw new SystemException(errorMessage);
        } catch (Exception e) {
            String errorMessage = "AmazonCloudWatch putMetricData error. Generate errors other than assumed.";
            log.error(errorMessage, e);
            throw new SystemException(errorMessage);
        }
    }
}
