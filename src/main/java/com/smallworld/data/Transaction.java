package com.smallworld.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    // Represent your transaction data here.
    private Long mtn;
    private Double amount;
    private String senderFullName;
    private Integer senderAge;
    private String beneficiaryFullName;
    private Integer beneficiaryAge;
    private Integer issueId;
    private Boolean issueSolved;
    private String issueMessage;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(mtn, that.mtn) && Objects.equals(amount, that.amount) && Objects.equals(senderFullName, that.senderFullName) && Objects.equals(senderAge, that.senderAge) && Objects.equals(beneficiaryFullName, that.beneficiaryFullName) && Objects.equals(beneficiaryAge, that.beneficiaryAge) && Objects.equals(issueId, that.issueId) && Objects.equals(issueSolved, that.issueSolved) && Objects.equals(issueMessage, that.issueMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mtn, amount, senderFullName, senderAge, beneficiaryFullName, beneficiaryAge, issueId, issueSolved, issueMessage);
    }
}
