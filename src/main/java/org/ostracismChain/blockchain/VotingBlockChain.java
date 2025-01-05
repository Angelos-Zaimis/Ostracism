package org.ostracismChain.blockchain;

import org.ostracismChain.storage.VotingBlockDAO;

import java.util.List;

import java.util.ArrayList;

public class VotingBlockChain {

    private List<VotingBlock> votingChain;
    private final VotingBlockDAO votingBlockDAO;

    public VotingBlockChain(VotingBlockDAO votingBlockDAO) {
        this.votingBlockDAO = votingBlockDAO;
        this.votingChain = new ArrayList<>(votingBlockDAO.getAllBlocks());

        if (votingChain.isEmpty()) {
            VotingBlock genesisBlock = createGenesisBlock();
            addVotingBlock(genesisBlock);
        }
    }

    private VotingBlock createGenesisBlock() {
        VotingBlock genesisBlock = new VotingBlock(0, System.currentTimeMillis(), "Genesis Block", "0");
        genesisBlock.setHash(genesisBlock.calculateHash());
        return genesisBlock;
    }

    public void addVotingBlock(VotingBlock votingBlock) {
        votingChain.add(votingBlock);
        votingBlockDAO.saveBlock(votingBlock);
        System.out.println("Added voting block: " + votingBlock);
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
