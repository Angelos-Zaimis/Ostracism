package org.ostracismChain.consensus;

import java.security.PublicKey;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class VoterRegistry {
    private final Map<String, PublicKey> voterPublicKeys = new ConcurrentHashMap<>();

    public void registerVoter(String voterId, PublicKey publicKey) {
        voterPublicKeys.put(voterId, publicKey);
    }

    public PublicKey getVoterPublicKey(String voterId) {
        return voterPublicKeys.get(voterId);
    }

    public boolean isVoterRegistered(String voterId) {
        return voterPublicKeys.containsKey(voterId);
    }
}
