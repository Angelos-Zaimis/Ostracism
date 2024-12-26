package org.ostracismChain.blockchain;

import org.ostracismChain.consensus.PoAValidator;
import org.ostracismChain.consensus.ValidatorRegistry;

import java.util.ArrayList;
import java.util.List;

public class VotingBlockChain {

    private List<VotingBlock> votingChain;
    private final PoAValidator poAValidator;

    public VotingBlockChain() {
        this.poAValidator = new PoAValidator();
    }

    public void addVotingBlock(VotingBlock votingBlock) {
        if (votingChain.isEmpty()) {
            votingChain.add(votingBlock);
            System.out.println("Added first voting block: " + votingBlock);
        } else {
            VotingBlock lastVotingBlock = votingChain.get(votingChain.size() - 1);

            if (poAValidator.validateVotingBlock(lastVotingBlock, votingBlock)) {
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
}
