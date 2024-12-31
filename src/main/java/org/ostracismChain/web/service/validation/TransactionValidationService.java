package org.ostracismChain.web.service.validation;

import org.ostracismChain.consensus.VoterRegistry;
import org.ostracismChain.transaction.VotingTransaction;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Date;
import java.util.List;

public class TransactionValidationService {

    private final VoterRegistry voterRegistry;

    public TransactionValidationService(VoterRegistry voterRegistry) {
        this.voterRegistry = voterRegistry;
    }

    public boolean validateTransactions(List<VotingTransaction> transactions) {
        for (VotingTransaction transaction : transactions) {
            if (!validateTransaction(transaction)){
                System.out.println("Transaction validation failed");
                return false;
            }
        }

        return true;
    }


    private boolean validateTransaction(VotingTransaction transaction) {
        return verifySignature(transaction);
    }

    private boolean verifySignature(VotingTransaction transaction) {
        PublicKey publicKey = retrievePublicKey(transaction.getVoterId());

        String data = transaction.getDataToSign();
        String algorithm = retrieveSignatureAlgorithm(publicKey.getAlgorithm());

        byte[] signatureBytes = retrieveSignatureBytes(transaction.getSignature());

        try {
            Signature signature = Signature.getInstance(algorithm);
            signature.initVerify(publicKey);
            signature.update(data.getBytes(StandardCharsets.UTF_8));
            return signature.verify(signatureBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
            return false;
        }
    }

    private byte[] retrieveSignatureBytes(String signature) {
        try {
            return Base64.getDecoder().decode(signature);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Signature verification failed");
        }
    }

    private String retrieveSignatureAlgorithm(String keyAlgorithm) {
        return switch (keyAlgorithm) {
            case "EC" -> "SHA256withECDSA";
            case "DSA" -> "SHA256withDSA";
            case "RSA" -> "SHA256withRSA";
            default -> null;
        };
    }

    private PublicKey retrievePublicKey(String voterId) {
        PublicKey publicKey = voterRegistry.getVoterPublicKey(voterId);

        if (publicKey == null) {
            System.out.println("Public key not found");
            throw new RuntimeException("Public key not found");
        }

        return publicKey;
    }

}
