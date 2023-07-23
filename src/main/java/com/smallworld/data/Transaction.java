package com.smallworld.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    // Represent your transaction data here.
    private Long mtn;
    private Double amount;
    private String senderFullName;
    private String senderAge;
    private String beneficiaryFullName;
    private String beneficiaryAge;
    private Integer issueId;
    private String issueSolved;
    private String issueMessage;

}
