package org.ostracismChain.web.service;

import org.ostracismChain.blockchain.VotingBlock;
import org.ostracismChain.blockchain.VotingBlockChain;
import org.ostracismChain.network.NetworkManager;
import org.ostracismChain.transaction.VotingTransaction;
import org.ostracismChain.web.service.validation.BlockValidationService;
import org.ostracismChain.web.service.validation.TransactionValidationService;

import java.util.List;

public class VoteService {

    private final VotingBlockChain votingBlockChain;
    private final NetworkManager networkManager;
    private final BlockValidationService blockValidationService;
    private final TransactionValidationService transactionValidationService;
    private final TransactionService transactionService;

    public VoteService(VotingBlockChain votingBlockChain,
                       NetworkManager networkManager,
                       BlockValidationService blockValidationService,
                       TransactionValidationService transactionValidationService,
                       TransactionService transactionService) {
        this.votingBlockChain = votingBlockChain;
        this.networkManager = networkManager;
        this.blockValidationService = blockValidationService;
        this.transactionValidationService = transactionValidationService;
        this.transactionService = transactionService;
    }

    public boolean handleCreateVote(String voterId, String candidateId) {

        List<VotingTransaction> votingTransactions = transactionService.createTransaction(voterId, candidateId);

        VotingBlock newBlock = createNewVotingBlock(votingTransactions);

        votingBlockChain.addVotingBlock(newBlock);

        if (isBlockValid(newBlock, votingBlockChain)) {
            broadCastVoteToPeers(newBlock);
            return true;
        } else {
            return false;
        }
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

        return transactionValidationService.validateTransactions(votingBlock.getVotingTransactions());
    }

    private void broadCastVoteToPeers(VotingBlock newBlock) {
        String serializedBlock = newBlock.toJsonString();

        networkManager.broadcastBlock(serializedBlock);
    }
}
