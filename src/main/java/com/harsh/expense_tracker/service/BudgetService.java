package com.harsh.expense_tracker.service;

import com.harsh.expense_tracker.exceptions.InvalidRequestException;
import com.harsh.expense_tracker.exceptions.ResourceNotFoundException;
import com.harsh.expense_tracker.model.Budget;
import com.harsh.expense_tracker.model.Category;
import com.harsh.expense_tracker.model.Expense;
import com.harsh.expense_tracker.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    public Budget addBudget(Budget budget) {
        if (budget.getMonthlyLimit() < 0) {
            throw new InvalidRequestException("Monthly Limit cannot be negative");
        }
        return budgetRepository.save(budget);
    }

    public List<Budget> getBudgets(String username) {
        return budgetRepository.findByUserUsername(username);
    }

    public boolean deleteBudget(Long id) {
        if (!budgetRepository.existsById(id)) {
            return false;  // handled in controller
        }
        budgetRepository.deleteById(id);
        return true;
    }

    public Budget updateBudget(Budget budget) {
        Budget oldBudget = budgetRepository.findByUserUsernameAndCategoryAndMonthAndYear(budget.user.getUsername(), budget.getCategory(), budget.getMonth(), budget.getYear());
        if (oldBudget == null) {
            throw new ResourceNotFoundException("Expense with category " + budget.getCategory() + " not found!");
        }
        if (budget.getMonthlyLimit() < 0) {
            throw new InvalidRequestException("Negative amount can not be set to budget");
        }
        oldBudget.setMonthlyLimit(budget.getMonthlyLimit());
        oldBudget.setCategory(budget.getCategory());
        oldBudget.setYear(budget.getYear());
        oldBudget.setMonth(budget.getMonth());

        return budgetRepository.save(oldBudget);
    }

}
