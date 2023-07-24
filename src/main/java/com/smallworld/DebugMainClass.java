package com.smallworld;

import com.smallworld.config.AppConfig;
import com.smallworld.repository.TransactionRepository;
import com.smallworld.service.TransactionDataFetcher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DebugMainClass {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        TransactionRepository transactionRepository = context.getBean(TransactionRepository.class);
        System.out.println(transactionRepository.findAll().size());

        TransactionDataFetcher transactionDataFetcher = context.getBean(TransactionDataFetcher.class);
        System.out.println("Top Sender: "+ transactionDataFetcher.getTopSender().orElse(""));
    }

}
