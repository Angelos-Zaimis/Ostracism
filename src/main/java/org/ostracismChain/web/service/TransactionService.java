package org.ostracismChain.web.service;

import org.ostracismChain.transaction.VotingTransaction;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class TransactionService {

    private final KeyStore keyStore;

    public TransactionService() {
        try {
            keyStore = KeyStore.getInstance("JKS");
            FileInputStream keyStoreData = new FileInputStream("keystore.jks");
            keyStore.load(keyStoreData, "keystorePassword".toCharArray());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize TransactionService", e);
        }
    }

    public List<VotingTransaction> createTransaction(String voterId, String candidateId) {
        List<VotingTransaction> transactions = new ArrayList<>();
        VotingTransaction votingTransaction = new VotingTransaction();

        votingTransaction.setVoter(voterId);
        votingTransaction.setCandidate(candidateId);
        votingTransaction.setAmount(1);
        String dataToSign = votingTransaction.getDataToSign();

        try {
            String signature = signData(dataToSign, voterId);
            votingTransaction.setSignature(signature);
        } catch (RuntimeException e) {
            System.err.println("Failed to sign transaction: " + e.getMessage());
            return transactions;
        }

        transactions.add(votingTransaction);

        return transactions;
    }

    private String signData(String data, String voterId) {
        try {
            PrivateKey privateKey = retrievePrivateKey(voterId);

            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);

            signature.update(data.getBytes(StandardCharsets.UTF_8));

            byte[] signatureBytes = signature.sign();

            return Base64.getEncoder().encodeToString(signatureBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
            throw new RuntimeException("Error signing data", e);
        }
    }

    private PrivateKey retrievePrivateKey(String voterId) {
        try {
            return (PrivateKey) keyStore.getKey(voterId, "keyPassword".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving private key for voter ID: " + voterId, e);
        }
    }

}
