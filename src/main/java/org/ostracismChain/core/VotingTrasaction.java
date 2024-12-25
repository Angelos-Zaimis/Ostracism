package org.ostracismChain.core;

import java.util.Date;

public class VotingTrasaction {

    private String voter;
    private String candidate;
    private int amount;
    private Date timestamp;

    public VotingTrasaction(String voter, String candidate, int amount, Date timestamp) {
        this.voter = voter;
        this.candidate = candidate;
        this.amount = amount;
        this.timestamp = timestamp;
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

    public String toString() {
        return voter + " votes " + amount + " for " + candidate + " at " + timestamp.toString();
    }
}
