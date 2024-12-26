package org.ostracismChain.blockchain;

import org.ostracismChain.transaction.VotingTrasaction;

import java.security.MessageDigest;
import java.util.Date;
import java.util.List;

public class VotingBlock {

    private final String blockHash;
    private final String previousBlockHash;
    private final List<VotingTrasaction> votingTransactions;
    private final int nonce;
    private final String proposedByValidator;

    public VotingBlock(int index, String previousBlockHash, List<VotingTrasaction> votingTrasactions, String proposedByValidator) {

        this.previousBlockHash = previousBlockHash;
        this.votingTransactions = votingTrasactions;
        this.nonce = 0;
        long timestamp = new Date().getTime();
        this.proposedByValidator = proposedByValidator;
        this.blockHash = calculateHash();
    }

    private String calculateHash() {
        try {
            StringBuilder blockHashBuilder = new StringBuilder(previousBlockHash);
            blockHashBuilder.append(nonce).append(proposedByValidator);

            for (VotingTrasaction transaction : votingTransactions) {
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

    public String getBlockHash() {
        return blockHash;
    }

    public String getPreviousBlockHash() {
        return previousBlockHash;
    }

    public List<VotingTrasaction> getTransactions() {
        return votingTransactions;
    }

    @Override
    public String toString() {
        return "Block: " + blockHash;
    }

    public String getProposedByValidator() {
        return proposedByValidator;
    }
}
