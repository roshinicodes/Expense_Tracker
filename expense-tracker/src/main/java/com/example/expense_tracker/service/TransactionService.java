package com.example.expense_tracker.service;

import com.example.expense_tracker.model.Transaction;
import com.example.expense_tracker.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public void addTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionsForUser(String username, String email) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction t : transactionRepository.findAll()) {
            if (t.getUsername().equals(username) && t.getEmail().equals(email)) {
                result.add(t);
            }
        }
        return result;
    }

    public double getTotalIncome(String username, String email) {
        double total = 0;
        for (Transaction t : getTransactionsForUser(username, email)) {
            if (t.getAmount() > 0) {
                total += t.getAmount();
            }
        }
        return total;
    }

    public double getTotalExpense(String username, String email) {
        double total = 0;
        for (Transaction t : getTransactionsForUser(username, email)) {
            if (t.getAmount() < 0) {
                total += t.getAmount();
            }
        }
        return total;
    }

    public double getBalance(String username, String email) {
        return getTotalIncome(username, email) + getTotalExpense(username, email);
    }
}