package com.harsh.expense_tracker.controller;

import com.harsh.expense_tracker.exceptions.ResourceNotFoundException;
import com.harsh.expense_tracker.model.Budget;
import com.harsh.expense_tracker.model.Expense;
import com.harsh.expense_tracker.model.User;
import com.harsh.expense_tracker.repository.UserRepository;
import com.harsh.expense_tracker.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Budget> addBudget(@RequestBody Budget budget,
                                            Authentication authentication) throws Exception {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        if (user == null) throw new Exception("User Not Found: " + username);

        if (budget == null) {
            throw new IllegalArgumentException("Budget request body cannot be null");
        }
        budget.setUser(user);
        return ResponseEntity.ok(budgetService.addBudget(budget));
    }

    @GetMapping
    public ResponseEntity<List<Budget>> getBudgets(Authentication authentication) {
        List<Budget> budgets = budgetService.getBudgets(authentication.getName());
        if (budgets.isEmpty()) {
            throw new ResourceNotFoundException("No budgets found for user: " + authentication.getName());
        }
        return ResponseEntity.ok(budgets);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(@PathVariable Long id) throws Exception {
        boolean deleted = budgetService.deleteBudget(id);
        if (!deleted) {
            throw new ResourceNotFoundException("Budget with id " + id + " not found");
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Budget> updateBudget(@RequestBody Budget budget, Authentication authentication) throws Exception {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        if (user == null) throw new Exception("User Not Found: " + username);

        if (budget == null) {
            throw new IllegalArgumentException("Budget request body cannot be null");
        }
        budget.setUser(user);
        return ResponseEntity.ok(budgetService.updateBudget(budget));
    }
}
