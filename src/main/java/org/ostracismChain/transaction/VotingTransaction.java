package org.ostracismChain.transaction;

import java.util.Date;

public class VotingTransaction {
    private String voter;
    private String candidate;
    private int amount;
    private final Date timestamp;

    public VotingTransaction() {
        this.timestamp = new Date();
    }

    public void setVoter(String voter) {
        this.voter = voter;
    }

    public void setCandidate(String candidate) {
        this.candidate = candidate;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getVoter() {
        return voter;
    }

    public String getCandidate() {
        return candidate;
    }

    public int getAmount() {
        return amount;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "VotingTransaction{" +
                "voterId='" + voter + '\'' +
                ", candidateId='" + candidate + '\'' +
                ", amount=" + amount +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
