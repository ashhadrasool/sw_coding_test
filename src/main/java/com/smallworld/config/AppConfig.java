package com.smallworld.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smallworld.repository.TransactionRepository;
import com.smallworld.repository.impl.TransactionJsonFileRepositoryImpl;
import com.smallworld.service.TransactionDataFetcher;
import com.smallworld.service.impl.TransactionDataFetcherImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.smallworld")
public class AppConfig {

    @Bean
    ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Bean("transactionRepository")
    TransactionRepository getTransactionRepository(ObjectMapper objectMapper){
        return new TransactionJsonFileRepositoryImpl(objectMapper);
    }

    @Bean("transactionDataFetcher")
    TransactionDataFetcher getTransactionRepository(TransactionRepository transactionRepository){
        return new TransactionDataFetcherImpl(transactionRepository);
    }

}