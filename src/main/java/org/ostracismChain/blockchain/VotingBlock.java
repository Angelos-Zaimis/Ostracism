package org.ostracismChain.blockchain;

import com.google.gson.Gson;
import org.ostracismChain.transaction.VotingTransaction;

import java.security.MessageDigest;
import java.util.List;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

public class VotingBlock {
    private int index;
    private long timestamp;
    private String blockHash;
    private String previousBlockHash;
    private List<VotingTransaction> votingTransactions;
    private String proposedByValidator;

    public VotingBlock(int index, long timestamp, String proposedByValidator, String previousBlockHash, List<VotingTransaction> votingTransactions) {
        this.index = index;
        this.timestamp = timestamp;
        this.proposedByValidator = proposedByValidator;
        this.previousBlockHash = previousBlockHash;
        this.votingTransactions = votingTransactions;
        calculateHash();
    }

    public VotingBlock(int index, long timestamp, String proposedByValidator, String previousBlockHash) {
        this(index, timestamp, proposedByValidator, previousBlockHash, null);
    }

    public VotingBlock() {

    }

    public String calculateHash() {
        try {
            StringBuilder blockString = new StringBuilder();
            blockString.append(index)
                    .append(timestamp)
                    .append(previousBlockHash)
                    .append(proposedByValidator);

            if (votingTransactions != null) {
                for (VotingTransaction tx : votingTransactions) {
                    blockString.append(tx.toString());
                }
            }

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(blockString.toString().getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error calculating hash", e);
        }
    }

    public int getIndex() { return index; }

    public void setIndex(int index) { this.index = index; }

    public long getTimestamp() { return timestamp; }

    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public String getBlockHash() { return blockHash; }

    public void setBlockHash(String blockHash) { this.blockHash = blockHash; }

    public String getPreviousBlockHash() { return previousBlockHash; }

    public void setPreviousBlockHash(String previousBlockHash) {
        this.previousBlockHash = previousBlockHash;
    }

    public void setHash(String hash) {
        this.blockHash = hash;
    }

    public List<VotingTransaction> getVotingTransactions() { return votingTransactions; }

    public void setVotingTransactions(List<VotingTransaction> votingTransactions) {
        this.votingTransactions = votingTransactions;
    }

    public String getProposedByValidator() { return proposedByValidator; }

    public void setProposedByValidator(String proposedByValidator) {
        this.proposedByValidator = proposedByValidator;
    }

    @Override
    public String toString() {
        return "VotingBlock{" +
                "index=" + index +
                ", timestamp=" + timestamp +
                ", blockHash='" + blockHash + '\'' +
                ", previousBlockHash='" + previousBlockHash + '\'' +
                ", proposedByValidator='" + proposedByValidator + '\'' +
                '}';
    }

    public String toJsonString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
