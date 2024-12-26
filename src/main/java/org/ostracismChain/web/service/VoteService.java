package org.ostracismChain.web.service;

import org.ostracismChain.blockchain.VotingBlock;
import org.ostracismChain.blockchain.VotingBlockChain;
import org.ostracismChain.consensus.ValidatorRegistry;
import org.ostracismChain.transaction.VotingTransaction;

import java.util.ArrayList;
import java.util.List;

public class VoteService {

    private final VotingBlockChain votingBlockChain;
    private final ValidatorRegistry validatorRegistry;
    private final VotingBlock votingBlock;

    public VoteService() {
        this.votingBlockChain = new VotingBlockChain();
        this.validatorRegistry = new ValidatorRegistry();
        this.votingBlock = new VotingBlock();
    }

    public void handleCreateVote(String voterId, String candidateId) {
        VotingTransaction votingTransaction = createTransaction(voterId, candidateId);
        List<VotingTransaction> transactions = new ArrayList<>();
        transactions.add(votingTransaction);

        VotingBlock newBlock = createNewVotingBlock(transactions);

        votingBlockChain.addVotingBlock(newBlock);
    }

    private VotingTransaction createTransaction(String voterId, String candidateId) {
        VotingTransaction votingTransaction = new VotingTransaction();
        votingTransaction.setVoter(voterId);
        votingTransaction.setCandidate(candidateId);
        votingTransaction.setAmount(1);
        return votingTransaction;
    }

    private VotingBlock createNewVotingBlock(List<VotingTransaction> votingTransactions) {
        VotingBlock newBlock = new VotingBlock();

        String previousBlockHash = votingBlockChain.getLastVotingBlock() == null
                ? ""
                : votingBlockChain.getLastVotingBlock().getBlockHash();

        newBlock.setPreviousBlockHash(previousBlockHash);
        newBlock.setProposedByValidator("validator");
        newBlock.setVotingTransactions(votingTransactions);
        newBlock.calculateHash();

        return newBlock;
    }
}
