package org.ostracismChain.web.service.validation;

import org.ostracismChain.transaction.VotingTransaction;

import java.util.Date;
import java.util.List;

public class TransactionValidationService {


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

//        if (!validatorRegistry.isCanidateValid(transaction.getCandidate())) {
//            System.out.println("Invalid candidate: " + transaction.getCandidate());
//            return false;
//        }
//
        if (transaction.getAmount() != 1) {
            System.out.println("Invalid transaction amount: " + transaction.getAmount());
            return false;
        }

        if (!isValidTimestamp(transaction.getTimestamp())) {
            System.out.println("Invalid transaction timestamp: " + transaction.getTimestamp());
            return false;
        }

        return true;
    }

    private boolean isValidTimestamp(Date timestamp) {
        return timestamp != null && timestamp.getTime() > System.currentTimeMillis();
    }
}
