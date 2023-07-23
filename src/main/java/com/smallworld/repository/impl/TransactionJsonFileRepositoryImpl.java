package com.smallworld.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smallworld.data.Transaction;
import com.smallworld.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
public class TransactionJsonFileRepositoryImpl implements TransactionRepository {

    ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public List<Transaction> findAll() {
        return objectMapper.readValue(new String(ClassLoader.getSystemResourceAsStream("transactions.json").readAllBytes()), new TypeReference<List<Transaction>>() {});
    }
}
