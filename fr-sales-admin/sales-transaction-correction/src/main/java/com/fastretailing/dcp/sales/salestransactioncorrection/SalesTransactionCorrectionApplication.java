/**
 * @(#)SalesTransactionCorrectionApplication.java
 *
 *                                                Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactioncorrection;

import java.util.Currency;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Application for sales transaction correction.
 */
@SpringBootApplication(scanBasePackages = {"com.fastretailing.dcp.storecommon",
        "com.fastretailing.dcp.common.util", "com.fastretailing.dcp.sales.importtransaction",
        "com.fastretailing.dcp.sales.salestransactioncorrection",
        "com.fastretailing.dcp.storecommon.screen.config", "com.fastretailing.dcp.common.api.uri",
        "com.fastretailing.dcp.common.api.client", "com.fastretailing.dcp.common.api.hmac",
        "com.fastretailing.dcp.common.web.handler", "com.fastretailing.dcp.common.web.interceptor",
        "com.fastretailing.dcp.sales.common"})
public class SalesTransactionCorrectionApplication {

    /**
     * Application execute.
     * 
     * @param args Application parameter.
     * @throws Exception Exception that occurred.
     */
    public static void main(String[] args) {
        SpringApplication.run(SalesTransactionCorrectionApplication.class, args);
    }

    /**
     * Create model mapper and set MatchingStrategies.STRICT.
     *
     * @return Model mapper instance.
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setFullTypeMatchingRequired(true);
        Converter<String, Currency> toCurrency = new AbstractConverter<String, Currency>() {
            protected Currency convert(String source) {
                return source == null ? null : Currency.getInstance(source);
            }
        };
        modelMapper.addConverter(toCurrency);
        return modelMapper;
    }

}
