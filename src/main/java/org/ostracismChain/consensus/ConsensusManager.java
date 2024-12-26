package org.ostracismChain.consensus;

public class ConsensusManager {

    private final ValidatorRegistry validatorRegistry;

    public ConsensusManager(ValidatorRegistry validatorRegistry) {
        this.validatorRegistry = validatorRegistry;
    }

    public void registerValidator(String validator) {
        if (!validatorRegistry.isValidatorRegistered(validator)) {
            validatorRegistry.registerValidator(validator);
        }
    }

    public void removeValidator(String validator) {
        validatorRegistry.unregisterValidator(validator);
    }
}
