package com.smallworld.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smallworld.data.Transaction;
import com.smallworld.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class TransactionJsonFileRepositoryImpl implements TransactionRepository {

    ObjectMapper objectMapper;
    @Override
    public List<Transaction> findAll() {
        throw new UnsupportedOperationException();
    }
}
