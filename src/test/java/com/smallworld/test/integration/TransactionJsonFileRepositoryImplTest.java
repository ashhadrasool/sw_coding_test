package com.smallworld.test.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smallworld.data.Transaction;
import com.smallworld.repository.impl.TransactionJsonFileRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransactionJsonFileRepositoryImplTest {
    TransactionJsonFileRepositoryImpl transactionJsonFileRepositoryImpl;

    @BeforeAll
    public void init(){
        transactionJsonFileRepositoryImpl = new TransactionJsonFileRepositoryImpl(new ObjectMapper());
    }

    @Test
    public void findAllTest(){
        List<Transaction> transactionList = transactionJsonFileRepositoryImpl.findAll();
        Assertions.assertNotNull(transactionList);
        /**
         * The below assertion would be wrong in a case when our database doesn't have any transaction**/
        Assertions.assertNotEquals(0, transactionList.size());
    }
}
