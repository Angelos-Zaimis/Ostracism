package org.ostracismChain.consensus;

import java.util.HashSet;
import java.util.Set;

public class ValidatorRegistry {

    public ValidatorRegistry() {
    }


    public void registerValidator(String validator) {
        if (!validators.contains(validator)) {
            validators.add(validator);
            System.out.println("Registered validator: " + validator);

        }
    }

    public boolean isRegisteredValidator(String validator) {
        return validators.contains(validator);
    }

    public void unregisterValidator(String validator) {
        validators.remove(validator);
    }

    public boolean isValidatorRegistered(String validator) {
        return validators.contains(validator);
    }

    public Set<String> getValidators() {
        return validators;
    }
    
}
