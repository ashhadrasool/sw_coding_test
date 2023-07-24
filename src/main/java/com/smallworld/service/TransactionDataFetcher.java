package com.smallworld.service;

import com.smallworld.data.Transaction;
import com.smallworld.repository.TransactionRepository;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

@AllArgsConstructor
public class TransactionDataFetcher {

    TransactionRepository transactionRepository;
    /**
     * Returns the sum of the amounts of all transactions
     */
    public double getTotalTransactionAmount() {
        return transactionRepository
                .findAll()
                .stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    /**
     * Returns the sum of the amounts of all transactions sent by the specified client
     */
    public double getTotalTransactionAmountSentBy(String senderFullName) {
        return transactionRepository
                .findAll()
                .stream()
                .filter(transaction -> transaction.getSenderFullName().equals(senderFullName))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    /**
     * Returns the highest transaction amount
     */
    public double getMaxTransactionAmount() {
        return transactionRepository
                .findAll()
                .stream()
                .mapToDouble(Transaction::getAmount)
                .max()
                .orElse(0d);
    }

    /**
     * Counts the number of unique clients that sent or received a transaction
     */
    public long countUniqueClients() {
        return transactionRepository
                .findAll()
                .stream()
                .flatMap(transaction -> Stream.of(transaction.getSenderFullName(), transaction.getBeneficiaryFullName()))
                .distinct()
                .count();
    }

    /**
     * Returns whether a client (sender or beneficiary) has at least one transaction with a compliance
     * issue that has not been solved
     */
    public boolean hasOpenComplianceIssues(String clientFullName) {
        return transactionRepository
                .findAll()
                .stream()
                .filter(transaction -> transaction.getIssueId() != null &&
                        Boolean.FALSE.equals(transaction.getIssueSolved())
                        && (transaction.getSenderFullName().equals(clientFullName)
                        || transaction.getBeneficiaryFullName().equals(clientFullName)
                        )
                )
                .findAny()
                .isPresent();
    }

    /**
     * Returns all transactions indexed by beneficiary name
     */
    public Map<String, List<Transaction>> getTransactionsByBeneficiaryName() {
        return transactionRepository
                .findAll()
                .stream()
                .collect(Collectors.groupingBy(Transaction::getBeneficiaryFullName));
    }

    /**
     * Returns the identifiers of all open compliance issues
     */
    public Set<Long> getUnsolvedIssueIds() {
        return transactionRepository
                .findAll()
                .stream()
                .filter(transaction -> transaction.getIssueId() != null &&
                        Boolean.FALSE.equals(transaction.getIssueSolved()))
                .map(Transaction::getMtn)
                .collect(Collectors.toSet());

    }

    /**
     * Returns a list of all solved issue messages
     */
    public List<String> getAllSolvedIssueMessages() {
        return transactionRepository
                .findAll()
                .stream()
                .filter(transaction -> transaction.getIssueId() != null &&
                        Boolean.FALSE.equals(transaction.getIssueSolved()))
                .map(Transaction::getIssueMessage)
                .collect(Collectors.toList());
    }

    /**
     * Returns the 3 transactions with the highest amount sorted by amount descending
     */
    public List<Transaction> getTop3TransactionsByAmount() {
        return transactionRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparingDouble(Transaction::getAmount).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    /**
     * Returns the senderFullName of the sender with the most total sent amount
     */
    public Optional<String> getTopSender() {
        Map<String, Double> map = transactionRepository
                .findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        Transaction::getSenderFullName,
                        Collectors.summingDouble(Transaction::getAmount)
                        )
                );

        return map.entrySet()
                .stream()
                .sorted((m1,m2) -> Double.compare(m2.getValue(), m1.getValue()))
                .map(Map.Entry::getKey)
                .findFirst();
    }

}
