package org.ostracismChain.blockchain;

import org.ostracismChain.transaction.VotingTrasaction;

import java.security.MessageDigest;
import java.util.Date;
import java.util.List;

public class VotingBlock {

    private int index;
    private String blockHash;
    private String previousBlockHash;
    private List<VotingTrasaction> votingTrasactions;
    private int nonce;
    private long timestamp;
    private String proposedByValidator;

    // Voting block class with POA implementation
    public VotingBlock(int index, String previousBlockHash, List<VotingTrasaction> votingTrasactions, String proposedByValidator) {
        this.index = index;
        this.previousBlockHash = previousBlockHash;
        this.votingTrasactions = votingTrasactions;
        this.nonce = 0;
        this.timestamp = new Date().getTime();
        this.proposedByValidator = proposedByValidator;
        this.blockHash = calculateHash();
    }

    public int getIndex() {
        return index;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public String getPreviousBlockHash() {
        return previousBlockHash;
    }

    public List<VotingTrasaction> getTrasactions() {
        return votingTrasactions;
    }

    @Override
    public String toString() {
        return "Block " + index + ": " + blockHash;
    }

    public String getProposedByValidator() {
        return proposedByValidator;
    }

    private String calculateHash() {
        try {
            StringBuilder blockHashBuilder = new StringBuilder(previousBlockHash);
            blockHashBuilder.append(index).append(nonce).append(proposedByValidator);

            for (VotingTrasaction transaction : votingTrasactions) {
                blockHashBuilder.append(transaction.toString());
            }

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(blockHashBuilder.toString().getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02X", b));
            }

            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error calculating hash", e);
        }
    }
}
