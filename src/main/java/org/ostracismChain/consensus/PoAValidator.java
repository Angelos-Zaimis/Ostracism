package org.ostracismChain.consensus;

import org.ostracismChain.blockchain.VotingBlock;

import javax.xml.validation.Validator;

public class PoAValidator {

    private String validator;
    private ValidatorRegistry validatorRegistry;

    public PoAValidator(String validator, ValidatorRegistry validatorRegistry) {
        this.validator = validator;
        this.validatorRegistry = validatorRegistry;
    }

    private boolean isValidatorAuthorized() {
        return validatorRegistry.isValidatorRegistered(validator);
    }

    public boolean validateVotingBlock(VotingBlock lastVotingBlock, VotingBlock newVotingBlock) {
        if (!isValidatorAuthorized()) {
            System.out.println("Validator is not authorized");
            return false;
        }

        if (!lastVotingBlock.getBlockHash().equals(newVotingBlock.getPreviousBlockHash())) {
            System.out.println("Previous block hash does not match");
            return false;
        }

        return true;
    }
}
