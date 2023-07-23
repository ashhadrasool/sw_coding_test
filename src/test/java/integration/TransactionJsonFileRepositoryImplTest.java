package integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smallworld.repository.impl.TransactionJsonFileRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransactionJsonFileRepositoryImplTest {
    TransactionJsonFileRepositoryImpl transactionJsonFileRepositoryImpl;

    @BeforeAll
    public void init(){
        transactionJsonFileRepositoryImpl = new TransactionJsonFileRepositoryImpl(new ObjectMapper());
    }

    @Test
    public void findAllTest(){
        Assertions.assertTrue(transactionJsonFileRepositoryImpl.findAll() != null );
    }
}
