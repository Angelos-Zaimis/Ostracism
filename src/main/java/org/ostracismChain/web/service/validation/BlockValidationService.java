package org.ostracismChain.web.service.validation;

import org.ostracismChain.blockchain.VotingBlock;
import org.ostracismChain.blockchain.VotingBlockChain;
import org.ostracismChain.transaction.VotingTransaction;

import java.security.MessageDigest;

public class BlockValidationService {

    public boolean validateBlock(VotingBlock votingBlock, VotingBlockChain votingBlockChain) {
        String expectedHast = calculateExpectedHash(votingBlock);

        if (!expectedHast.equals(votingBlock.getBlockHash())){
            System.out.println("Block hash does not match expected hash");
            return false;
        }

        if (!votingBlock.getPreviousBlockHash().equals(votingBlockChain.getLastVotingBlock().getBlockHash())){
            System.out.println("Previous block hash does not match expected hash");
            return false;
        }

        return true;
    }

    private String calculateExpectedHash(VotingBlock block) {
        StringBuilder blockContent = new StringBuilder(block.getPreviousBlockHash() + block.getProposedByValidator());

        for (VotingTransaction tx : block.getVotingTransactions()) {
            blockContent.append(tx.toString());
        }

        return hashContent(blockContent.toString());
    }

    private String hashContent(String content) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(content.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02X", b));
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
