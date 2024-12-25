package org.ostracismChain;

import org.ostracismChain.core.VotingBlock;
import org.ostracismChain.core.VotingBlockChain;
import org.ostracismChain.core.VotingTrasaction;

import java.util.List;
import java.util.Date;

public class OstracismChainMain {

    private final VotingBlockChain votingBlockChain;

    // Constructor
    public OstracismChainMain() {
        this.votingBlockChain = new VotingBlockChain();
    }

    public static void main(String[] args) {
        // Initialize the main class
        OstracismChainMain main = new OstracismChainMain();

        // Register some validators
        VotingBlockChain.registerValidator("ValidatorA");
        VotingBlockChain.registerValidator("ValidatorB");
        VotingBlockChain.registerValidator("ValidatorC");

        // Create some example vote transactions
        VotingTrasaction vote1 = new VotingTrasaction("Alice", "Candidate A", 1, new Date());
        VotingTrasaction vote2 = new VotingTrasaction("Bob", "Candidate B", 1, new Date());

        // Create and add the first block
        VotingBlock block1 = new VotingBlock(1, "0000abcd1234efgh", List.of(vote1, vote2), "ValidatorA");
        main.votingBlockChain.addVotingBlock(block1);

        // Create another block and add it
        VotingTrasaction vote3 = new VotingTrasaction("Charlie", "Candidate C", 1, new Date());
        VotingBlock block2 = new VotingBlock(2, block1.getBlockHash(), List.of(vote3), "ValidatorB");
        main.votingBlockChain.addVotingBlock(block2);

        // Display the blockchain
        main.votingBlockChain.displayVotingChain();
    }
}
