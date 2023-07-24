package com.smallworld;

import com.smallworld.config.AppConfig;
import com.smallworld.repository.TransactionRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DebugMainClass {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        TransactionRepository transactionRepository = context.getBean(TransactionRepository.class);
        System.out.println(transactionRepository.findAll().size());
    }

}
