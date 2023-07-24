package unit;

import com.smallworld.data.Transaction;
import com.smallworld.repository.TransactionRepository;
import com.smallworld.service.TransactionDataFetcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import org.mockito.Mockito;

import java.util.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransactionDataFetcherTest {

    TransactionRepository transactionRepository;
    TransactionDataFetcher transactionDataFetcher;
    List<Transaction> commonTransactions;

    @BeforeAll
    void init(){
        transactionRepository = Mockito.mock(TransactionRepository.class);

        commonTransactions = new ArrayList<>();
        commonTransactions.add(Transaction.builder().mtn(10l).senderFullName("Ramos").senderAge(20).beneficiaryFullName("Xavi").beneficiaryAge(43).amount(150d).build());
        commonTransactions.add(Transaction.builder().mtn(11l).senderFullName("Xavi").senderAge(43).beneficiaryFullName("Ineista").beneficiaryAge(39).amount(70d).build());
        commonTransactions.add(Transaction.builder().mtn(12l).senderFullName("Xavi").senderAge(43).beneficiaryFullName("Messi").beneficiaryAge(36).amount(30d).build());
        commonTransactions.add(Transaction.builder().mtn(13l).senderFullName("Bale").senderAge(34).beneficiaryFullName("Ronaldo").beneficiaryAge(38).amount(60d).build());
        commonTransactions.add(Transaction.builder().mtn(14l).senderFullName("Bale").senderAge(34).beneficiaryFullName("Ronaldo").beneficiaryAge(38).amount(40d).issueId(2).issueMessage("Never gonna give you a pass").issueSolved(Boolean.FALSE).build());
        commonTransactions.add(Transaction.builder().mtn(15l).senderFullName("Robben").senderAge(39).beneficiaryFullName("Lewandowski").beneficiaryAge(34).amount(50d).issueId(2).issueMessage("Never gonna give you a pass").issueSolved(Boolean.FALSE).build());
        commonTransactions.add(Transaction.builder().mtn(16l).senderFullName("Lewandowski").senderAge(34).beneficiaryFullName("Robben").beneficiaryAge(39).amount(50d).issueId(2).issueMessage("Never gonna give you a pass").issueSolved(Boolean.FALSE).build());
        commonTransactions.add(Transaction.builder().mtn(17l).senderFullName("Lewandowski").senderAge(34).beneficiaryFullName("Robben").beneficiaryAge(39).amount(50d).issueId(2).issueMessage("Never gonna give you a pass").issueSolved(Boolean.TRUE).build());

        transactionDataFetcher = new TransactionDataFetcher(transactionRepository);
    }
    @Test
    public void getTotalTransactionAmountTest() {
        Mockito.when(transactionRepository.findAll()).thenReturn(commonTransactions);
        Assertions.assertEquals(500d, transactionDataFetcher.getTotalTransactionAmount());
    }

    @Test
    public void getTotalTransactionAmountSentByTest() {
        Mockito.when(transactionRepository.findAll()).thenReturn(commonTransactions);
        Assertions.assertEquals(100d, transactionDataFetcher.getTotalTransactionAmountSentBy("Xavi"));
    }

    @Test
    public void getMaxTransactionAmountTest() {
        Mockito.when(transactionRepository.findAll()).thenReturn(commonTransactions);
        Assertions.assertEquals(150d, transactionDataFetcher.getMaxTransactionAmount());
    }
    
    @Test
    public void countUniqueClientsTest() {
        Mockito.when(transactionRepository.findAll()).thenReturn(commonTransactions);
        Assertions.assertEquals(8, transactionDataFetcher.countUniqueClients());
    }

    @Test
    public void hasOpenComplianceIssuesTest() {
        Mockito.when(transactionRepository.findAll()).thenReturn(commonTransactions);
        Assertions.assertEquals(true, transactionDataFetcher.hasOpenComplianceIssues("Robben"));
    }

    @Test
    public void getTransactionsByBeneficiaryNameTest() {

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(Transaction.builder().mtn(10l).senderFullName("Ramos").senderAge(20).beneficiaryFullName("Xavi").beneficiaryAge(43).amount(150d).build());
        transactions.add(Transaction.builder().mtn(11l).senderFullName("Xavi").senderAge(43).beneficiaryFullName("Ineista").beneficiaryAge(39).amount(70d).build());
        transactions.add(Transaction.builder().mtn(12l).senderFullName("Xavi").senderAge(43).beneficiaryFullName("Messi").beneficiaryAge(36).amount(30d).build());

        Mockito.when(transactionRepository.findAll()).thenReturn(transactions);

        Map<String, List<Transaction>> expectedMap = new HashMap<>();
        expectedMap.put("Ramos", List.of(Transaction.builder().senderFullName("Ramos").senderAge(20).beneficiaryFullName("Xavi").beneficiaryAge(43).amount(150d).build()));
        expectedMap.put("Xavi", List.of(
                Transaction.builder().senderFullName("Xavi").senderAge(43).beneficiaryFullName("Ineista").beneficiaryAge(39).amount(50d).build(),
                Transaction.builder().senderFullName("Xavi").senderAge(43).beneficiaryFullName("Messi").beneficiaryAge(36).amount(50d).build()
                )
        );
        /**
            we could have done this also like below
            expectedMap.put("Ramos", transactions.get(0));
            but the above approach is better in general, reason is we should compare same memory objects
            rather we should create new objects that may or may not hold same hashcode but must hold same equality
         **/
        Map<String, List<Transaction>> beneficiaryTransactionsMap = transactionDataFetcher.getTransactionsByBeneficiaryName();

        List<Transaction> ramosTransactions = beneficiaryTransactionsMap.get("Ramos");
        Assertions.assertEquals(1,ramosTransactions.size());
        Assertions.assertEquals(transactions.get(0), ramosTransactions.get(0));

        List<Transaction> xaviTransactions = beneficiaryTransactionsMap.get("Xavi");
        Assertions.assertEquals(2,xaviTransactions.size());
        Assertions.assertEquals(transactions.get(1), xaviTransactions.get(0));
        Assertions.assertEquals(transactions.get(2), xaviTransactions.get(1));
    }

    @Test
    public void getUnsolvedIssueIdsTest() {
        Mockito.when(transactionRepository.findAll()).thenReturn(commonTransactions);
        Assertions.assertEquals(Set.of(14l, 15l, 16l), transactionDataFetcher.getUnsolvedIssueIds());
    }

    @Test
    public void getAllSolvedIssueMessagesTest() {
        Mockito.when(transactionRepository.findAll()).thenReturn(commonTransactions);
        Assertions.assertEquals(Set.of(17), transactionDataFetcher.getAllSolvedIssueMessages());
    }

    @Test
    public void getTop3TransactionsByAmountTest() {
        Mockito.when(transactionRepository.findAll()).thenReturn(commonTransactions);

        List<Transaction> expectedTransactions = new ArrayList<>();
        expectedTransactions.add(Transaction.builder().mtn(10l).senderFullName("Ramos").senderAge(20).beneficiaryFullName("Xavi").beneficiaryAge(43).amount(150d).build());
        expectedTransactions.add(Transaction.builder().mtn(11l).senderFullName("Xavi").senderAge(43).beneficiaryFullName("Ineista").beneficiaryAge(39).amount(70d).build());
        expectedTransactions.add(Transaction.builder().mtn(13l).senderFullName("Bale").senderAge(34).beneficiaryFullName("Ronaldo").beneficiaryAge(38).amount(60d).build());

        List<Transaction> actualTransactions = transactionDataFetcher.getTop3TransactionsByAmount();

        Assertions.assertEquals(3, actualTransactions.size());

        Assertions.assertEquals(expectedTransactions.get(0), actualTransactions.get(0));
        Assertions.assertEquals(expectedTransactions.get(1), actualTransactions.get(1));
        Assertions.assertEquals(expectedTransactions.get(2), actualTransactions.get(2));
    }

    @Test
    public void getTopSenderTest() {
        Mockito.when(transactionRepository.findAll()).thenReturn(commonTransactions);

        Optional<String> optionalObj = transactionDataFetcher.getTopSender();
        Assertions.assertTrue(optionalObj.isPresent());
        Assertions.assertEquals("Ramos", transactionDataFetcher.getTopSender().get());
    }
}
