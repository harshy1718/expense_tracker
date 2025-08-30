package com.harsh.expense_tracker.controller;

import com.harsh.expense_tracker.dto.DailyResponse;
import com.harsh.expense_tracker.dto.ExpenseResponse;
import com.harsh.expense_tracker.dto.MonthlyResponse;
import com.harsh.expense_tracker.exceptions.InvalidRequestException;
import com.harsh.expense_tracker.model.Category;
import com.harsh.expense_tracker.model.Expense;
import com.harsh.expense_tracker.model.User;
import com.harsh.expense_tracker.repository.UserRepository;
import com.harsh.expense_tracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<ExpenseResponse> addExpense(@RequestBody Expense expense, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        if (user == null) throw new InvalidRequestException("User Not Found: " + username);

        if (expense == null) {
            throw new IllegalArgumentException("Expense request body cannot be null");
        }
        expense.setUser(user);
        return ResponseEntity.ok(expenseService.addExpense(expense));
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getExpenses(Authentication authentication) {
        return ResponseEntity.ok(expenseService.getExpenses(authentication.getName()));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Expense>> getExpensesByCategory(@PathVariable Category category, Authentication authentication) {
        return ResponseEntity.ok(expenseService.getExpensesForCategory(authentication.getName(), category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @RequestBody Expense expense) {
        return ResponseEntity.ok(expenseService.updateExpense(id, expense));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/summary/monthly")
    public ResponseEntity<MonthlyResponse> getMonthlySummary(@RequestParam Integer month, @RequestParam Integer year, Authentication authentication) {
        return ResponseEntity.ok(expenseService.getMonthlySummary(authentication.getName(), month, year));
    }

    @GetMapping("/summary/daily")
    public ResponseEntity<DailyResponse> getDailySummary(@RequestParam Integer month, @RequestParam Integer year, Authentication authentication) {
        return ResponseEntity.ok(expenseService.getDailySummary(authentication.getName(), month, year));
    }
}
