package org.ostracismChain.transaction;

import java.util.Date;

public class VotingTransaction {
    private String voterId;
    private String candidateId;
    private int amount;
    private final Date timestamp;
    private String signature;
    private String dataToSign;

    public VotingTransaction() {
        this.timestamp = new Date();
    }

    public void setVoter(String voter) {
        this.voterId = voter;
    }

    public void setCandidate(String candidate) {
        this.candidateId = candidate;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getVoterId() {
        return voterId;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public int getAmount() {
        return amount;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getDataToSign() {
        return "voterId=" + voterId +
                "&candidateId=" + candidateId +
                "&amount=" + amount +
                "&timestamp=" + timestamp;
    }

    public void setDataToSign(String dataToSign) {
        this.dataToSign = dataToSign;
    }



    @Override
    public String toString() {
        return "VotingTransaction{" +
                "voterId='" + voterId + '\'' +
                ", candidateId='" + candidateId + '\'' +
                ", amount=" + amount +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
