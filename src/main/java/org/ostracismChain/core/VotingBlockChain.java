package org.ostracismChain.core;

import java.util.ArrayList;
import java.util.List;

public class VotingBlockChain {

    private List<VotingBlock> votingChain;
    private static List<String> activeValidators = new ArrayList<>();

    public VotingBlockChain() {
        this.votingChain = new ArrayList<>();
    }

    public static void registerValidator(String validator) {
        if (!activeValidators.contains(validator)) {
            activeValidators.add(validator);
            System.out.println("Registered validator: " + validator);
        }
    }

    public static void unregisterValidator(String validator) {
        activeValidators.remove(validator);
    }

    public void addVotingBlock(VotingBlock votingBlock) {
        if (votingChain.size() == 0) {
            votingChain.add(votingBlock);
            System.out.println("Added first voting block: " + votingBlock);
        } else {
            VotingBlock lastVotingBlock = votingChain.get(votingChain.size() - 1);

            if (lastVotingBlock.getBlockHash().equals(votingBlock.getPreviousBlockHash())) {
                votingChain.add(votingBlock);
                System.out.println("Block added to the blockchain: " + votingBlock);
            } else {
                System.out.println("Invalid block. The block hash does not match the previous block.");
            }
        }
    }

    public VotingBlock getLastVotingBlock() {
        return votingChain.get(votingChain.size() - 1);
    }

    public void displayVotingChain() {
        for (VotingBlock votingBlock : votingChain) {
            System.out.println(votingBlock);
        }
    }

    public List<String> getActiveValidators() {
        return activeValidators;
    }
}
