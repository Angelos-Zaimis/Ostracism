package org.ostracismChain.web.service;

import org.ostracismChain.blockchain.VotingBlock;
import org.ostracismChain.blockchain.VotingBlockChain;
import org.ostracismChain.network.NetworkManager;
import org.ostracismChain.transaction.VotingTransaction;
import org.ostracismChain.web.service.validation.BlockValidationService;
import org.ostracismChain.web.service.validation.TransactionValidationService;

import java.util.ArrayList;
import java.util.List;

public class VoteService {

    private final VotingBlockChain votingBlockChain;
    private final NetworkManager networkManager;
    private final BlockValidationService blockValidationService;
    private final TransactionValidationService transactionValidationService;

    public VoteService() {
        this.votingBlockChain = new VotingBlockChain();
        this.networkManager = new NetworkManager();
        this.blockValidationService = new BlockValidationService();
        this.transactionValidationService = new TransactionValidationService();
    }

    public boolean handleCreateVote(String voterId, String candidateId) {
        List<VotingTransaction> votingTransactions = createTransaction(voterId, candidateId);

        VotingBlock newBlock = createNewVotingBlock(votingTransactions);

        votingBlockChain.addVotingBlock(newBlock);

        if (isBlockValid(newBlock, votingBlockChain)) {
            broadCastVoteToPeers(newBlock);
            return true;
        } else {
            return false;
        }
    }

    private List<VotingTransaction> createTransaction(String voterId, String candidateId) {
        List<VotingTransaction> transactions = new ArrayList<>();
        VotingTransaction votingTransaction = new VotingTransaction();
        votingTransaction.setVoter(voterId);
        votingTransaction.setCandidate(candidateId);
        votingTransaction.setAmount(1);
        transactions.add(votingTransaction);

        return transactions;
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

    private boolean isBlockValid(VotingBlock votingBlock, VotingBlockChain votingBlockChain) {
        if(!blockValidationService.validateBlock(votingBlock, votingBlockChain)){
            return false;
        }

        if (!transactionValidationService.validateTransactions(votingBlock.getVotingTransactions())) {
            return false;
        }

        return true;
    }

    private void broadCastVoteToPeers(VotingBlock newBlock) {
        String serializedBlock = newBlock.toJsonString();

        networkManager.broadcastBlock(serializedBlock);
    }
}
