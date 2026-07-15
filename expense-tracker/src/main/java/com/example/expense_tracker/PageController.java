package com.example.expense_tracker;

import com.example.expense_tracker.model.Transaction;
import com.example.expense_tracker.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

@Controller
public class PageController {

    private final TransactionService transactionService;

    public PageController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("message", "Hello from Thymeleaf!");
        return "hello";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "LoginPage";
    }

    @PostMapping("/login")
    public String loginhandle(@RequestParam String username, @RequestParam String email, HttpSession session) {
        session.setAttribute("username", username);
        session.setAttribute("email", email);
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        String email = (String) session.getAttribute("email");

        model.addAttribute("username", username);
        model.addAttribute("balance", transactionService.getBalance(username, email));
        model.addAttribute("income", transactionService.getTotalIncome(username, email));
        model.addAttribute("expense", transactionService.getTotalExpense(username, email));

        return "HomePage";
    }

    @PostMapping("/transactions")
    public String addTransaction(@RequestParam String description, @RequestParam double amount, HttpSession session) {
        String username = (String) session.getAttribute("username");
        String email = (String) session.getAttribute("email");

        Transaction transaction = new Transaction(description, amount, username, email);
        transactionService.addTransaction(transaction);
        return "redirect:/home";
    }

    @GetMapping("/history")
    public String history(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        String email = (String) session.getAttribute("email");

        model.addAttribute("transactions", transactionService.getTransactionsForUser(username, email));
        return "History";
    }
}