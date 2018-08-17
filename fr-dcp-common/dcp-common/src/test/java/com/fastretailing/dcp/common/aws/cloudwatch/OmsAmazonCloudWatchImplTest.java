/**
 * @(#)OmsAmazonCloudWatchImplTest.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.aws.cloudwatch;

import java.io.IOException;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.util.ReflectionTestUtils;

import com.amazonaws.http.HttpResponse;
import com.amazonaws.http.SdkHttpMetadata;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.InvalidParameterValueException;
import com.amazonaws.services.cloudwatch.model.MetricDatum;
import com.amazonaws.services.cloudwatch.model.PutMetricDataRequest;
import com.amazonaws.services.cloudwatch.model.PutMetricDataResult;
import com.amazonaws.services.cloudwatch.model.StandardUnit;
import com.fastretailing.dcp.common.aws.cloudwatch.service.OmsAmazonCloudWatchImpl;

@ContextHierarchy({
        @ContextConfiguration("classpath*:com/fastretailing/dcp/common/aws/cloudwatch/test-context.xml")
})
@RunWith(MockitoJUnitRunner.class)
public class OmsAmazonCloudWatchImplTest {

    @InjectMocks
    @Autowired
    OmsAmazonCloudWatchImpl target;

    @Mock
    private AmazonCloudWatch amazonCloudWatch;
    /**
     * Dimension name.<br>
     */
    private String dimensionName = "topic";

    /**
     * Dimension Value.<br>
     */
    private String dimensionValue = "oms-core-consumer-order-commit2-us-east-1";

    /**
     * MetricDatum name.<br>
     */
    private String metricDatumName = "Produced";

    /**
     * MetricDatum Timestamp.<br>
     */
    private String metricDatumTimestamp = "2018-05-26 08:03:29.539";

    /**
     * request namespace.<br>
     */
    private String requestNamespace = "KafkaTest";

    /**
     * metric Value.<br>
     */
    private int metricValue = 1;

    /**
     * metric Unit.<br>
     */
    private String metricUnit = "";


    @Before
    public void setup() throws IOException{

    }

    /**
     * normal case(return PutMetricDataResult)
     */
    @Test
    public void putMetricDataTest001() throws IOException {
        PutMetricDataResult result = new PutMetricDataResult();
        try {

            ReflectionTestUtils.setField(target, "amazonCloudWatch", amazonCloudWatch);

            Dimension dimension = new Dimension()
                    .withName("topic")
                    .withValue("oms-core-consumer-order-commit2-us-east-1");

            MetricDatum datum = new MetricDatum()
                    .withMetricName("Produced")
                    .withDimensions(dimension)
                    .withTimestamp(new Date())
                    .withValue(1.0)
                    .withUnit(StandardUnit.Count);

            PutMetricDataRequest request = new PutMetricDataRequest()
                    .withNamespace("KafkaTest")
                    .withMetricData(datum);

            PutMetricDataResult response = new PutMetricDataResult();
            HttpResponse httpResponse = new HttpResponse(null, null);
            httpResponse.setStatusCode(200);
            response.setSdkHttpMetadata(SdkHttpMetadata.from(httpResponse));
            Mockito.when(amazonCloudWatch.putMetricData(request)).thenReturn(response);

            result = target.putMetricData(dimensionName, dimensionValue,
                    metricDatumName, metricValue,
                    metricDatumTimestamp, metricUnit, requestNamespace);
        } catch (Exception e) {
            // nothing
            System.out.println(e);
        }

        Assert.assertEquals(result, null);
    }

    /**
     * abnormal case(Timestamp error)
     */
    @Test
    public void putMetricDataTest002() throws IOException {
        try {

            ReflectionTestUtils.setField(target, "amazonCloudWatch", amazonCloudWatch);

            Dimension dimension = new Dimension()
                    .withName("topic")
                    .withValue("oms-core-consumer-order-commit2-us-east-1");

            MetricDatum datum = new MetricDatum()
                    .withMetricName("Produced")
                    .withDimensions(dimension)
                    .withTimestamp(new Date())
                    .withValue(1.0)
                    .withUnit(StandardUnit.Count);

            PutMetricDataRequest request = new PutMetricDataRequest()
                    .withNamespace("KafkaTest")
                    .withMetricData(datum);

            PutMetricDataResult response = new PutMetricDataResult();
            HttpResponse httpResponse = new HttpResponse(null, null);
            httpResponse.setStatusCode(200);
            response.setSdkHttpMetadata(SdkHttpMetadata.from(httpResponse));
            Mockito.when(amazonCloudWatch.putMetricData(request)).thenReturn(response);
            metricDatumTimestamp = "abcn";
            target.putMetricData(dimensionName, dimensionValue,
                    metricDatumName, metricValue,
                    metricDatumTimestamp, metricUnit, requestNamespace);
        } catch (Exception e) {
            // nothing
            Assert.assertEquals(e.getMessage(), "AmazonCloudWatch putMetricData error.Unparseable date");
        }
    }

    /**
     * abnormal case(Timestamp error)
     */
    @Test(expected = InvalidParameterValueException.class)
    public void putMetricDataTest003() throws IOException {
        try {

            ReflectionTestUtils.setField(target, "amazonCloudWatch", amazonCloudWatch);
            Dimension dimension = new Dimension()
                    .withName("topic")
                    .withValue("oms-core-consumer-order-commit2-us-east-1");

            MetricDatum datum = new MetricDatum()
                    .withMetricName("Produced")
                    .withDimensions(dimension)
                    .withTimestamp(new Date())
                    .withValue(1.0)
                    .withUnit(StandardUnit.Count);

            PutMetricDataRequest request = new PutMetricDataRequest()
                    .withNamespace("KafkaTest")
                    .withMetricData(datum);
            PutMetricDataResult response = new PutMetricDataResult();
            HttpResponse httpResponse = new HttpResponse(null, null);
            httpResponse.setStatusCode(200);
            response.setSdkHttpMetadata(SdkHttpMetadata.from(httpResponse));
            Mockito.when(amazonCloudWatch.putMetricData(request)).thenThrow(InvalidParameterValueException.class);
            target.putMetricData(dimensionName, dimensionValue,
                    metricDatumName, metricValue,
                    metricDatumTimestamp, metricUnit, requestNamespace);
        } catch (Exception e) {
            // nothing
            Assert.assertEquals(e.getMessage(), "AmazonCloudWatch putMetricData error.The value of an input parameter is bad or out-of-range.");
        }
    }
}
