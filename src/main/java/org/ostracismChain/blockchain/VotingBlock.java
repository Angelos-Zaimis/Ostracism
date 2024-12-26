package org.ostracismChain.blockchain;

import org.ostracismChain.transaction.VotingTransaction;
import java.security.MessageDigest;
import java.util.List;

public class VotingBlock {
    private String blockHash;
    private String previousBlockHash;
    private List<VotingTransaction> votingTransactions;
    private String proposedByValidator;

    public VotingBlock() {
        // default constructor
    }

    public void calculateHash() {
        try {
            StringBuilder blockString = new StringBuilder();
            blockString.append(previousBlockHash)
                    .append(proposedByValidator);

            if (votingTransactions != null) {
                for (VotingTransaction tx : votingTransactions) {
                    blockString.append(tx.toString());
                }
            }

            // Just an example with SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(blockString.toString().getBytes());

            // convert bytes to hex
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02X", b));
            }
            this.blockHash = hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error calculating hash", e);
        }
    }

    public String getBlockHash() { return blockHash; }
    public void setBlockHash(String blockHash) { this.blockHash = blockHash; }

    public String getPreviousBlockHash() { return previousBlockHash; }
    public void setPreviousBlockHash(String previousBlockHash) {
        this.previousBlockHash = previousBlockHash;
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
                "blockHash='" + blockHash + '\'' +
                ", previousBlockHash='" + previousBlockHash + '\'' +
                ", proposedByValidator='" + proposedByValidator + '\'' +
                '}';
    }
}
