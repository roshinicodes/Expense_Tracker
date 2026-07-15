package com.example.expense_tracker;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.expense_tracker.service.TransactionService;
@RestController
public class ExpenseTrackerController {
    @GetMapping("/")

    public String controller()
    {
        return("controller is running");
    }

    private final TransactionService transactionService;

    public ExpenseTrackerController(TransactionService transactionService)
    {
        this.transactionService=transactionService;
    }
}
