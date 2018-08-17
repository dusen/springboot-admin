/**
 * @(#)BusinessReceiverDomainApplication.java
 *
 *                                            Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.importtransaction;


import java.util.Currency;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * This class launch the application.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.fastretailing.dcp.sales.importtransaction",
        "com.fastretailing.dcp.sales.common.type", "com.fastretailing.dcp.sales.common.constants",
        "com.fastretailing.dcp.sales.common.util", "com.fastretailing.dcp.storecommon.message",
        "com.fastretailing.dcp.common.api.log", "com.fastretailing.dcp.common.web.handler",
        "com.fastretailing.dcp.common.util", "com.fastretailing.dcp.common.web.controller",
        "com.fastretailing.dcp.storecommon"})
@MapperScan({"com.fastretailing.dcp.sales.importtransaction.repository"})
public class BusinessReceiverDomainApplication {

    /**
     * Start the application.
     * 
     * @param args Application arguments.
     */
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(BusinessReceiverDomainApplication.class, args);
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
