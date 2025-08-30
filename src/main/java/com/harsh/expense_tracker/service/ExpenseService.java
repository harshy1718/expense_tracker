package com.harsh.expense_tracker.service;

import com.harsh.expense_tracker.dto.DailyResponse;
import com.harsh.expense_tracker.dto.MonthlyResponse;
import com.harsh.expense_tracker.exceptions.InvalidRequestException;
import com.harsh.expense_tracker.exceptions.ResourceNotFoundException;
import com.harsh.expense_tracker.model.Budget;
import com.harsh.expense_tracker.model.Category;
import com.harsh.expense_tracker.model.Expense;
import com.harsh.expense_tracker.model.User;
import com.harsh.expense_tracker.dto.ExpenseResponse;
import com.harsh.expense_tracker.repository.BudgetRepository;
import com.harsh.expense_tracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private BudgetRepository budgetRepository;

    public ExpenseResponse addExpense(Expense expense) {
        if (expense.getUser() == null) {
            throw new InvalidRequestException("User must be provided in expense");
        }
        if (expense.getAmount() < 0) {
            throw new  InvalidRequestException("Amount cannot be negative");
        }
        String username = expense.getUser().getUsername();
        Double amount = expense.getAmount();
        Category category = expense.getCategory();
        Double totalSpent = expenseRepository.getTotalSpentThisMonth(username, category);
        if (totalSpent == null) totalSpent = 0.0;

        Budget budgetOfUser = budgetRepository.findByUserUsernameAndCategoryAndMonthAndYear(username, category, expense.getDate().getMonthValue(), expense.getDate().getYear());

        boolean budgetExceeded = false;

        if (budgetOfUser != null) {
            if (totalSpent + amount > budgetOfUser.getMonthlyLimit()) {
                budgetExceeded = true;
            }
        }

        Expense saved =  expenseRepository.save(expense);

        return new ExpenseResponse(saved, budgetExceeded);
    }

    public List<Expense> getExpenses(String username) {
        return expenseRepository.findByUserUsername(username);
    }

    public List<Expense> getExpensesForCategory(String username, Category category) {
        return expenseRepository.findByUserUsernameAndCategory(username, category);
    }

    public Expense updateExpense(Long id, Expense expense) {
        Expense oldExpense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense with id " + id + " not found!"));

        if (expense.getAmount() < 0) {
            throw new  InvalidRequestException("Amount cannot be negative");
        }

        oldExpense.setAmount(expense.getAmount());
        oldExpense.setCategory(expense.getCategory());
        oldExpense.setDate(expense.getDate());
        oldExpense.setPaymentMode(expense.getPaymentMode());
        oldExpense.setRemarks(expense.getRemarks());

        return expenseRepository.save(oldExpense);
    }

    public void deleteExpense (Long id) {
        if (!expenseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Expense with Id: " + id + " not found");
        }
        expenseRepository.deleteById(id);
    }

    public MonthlyResponse getMonthlySummary(String username, Integer month, Integer year) {
        Map<Category, Double> perCategory = new HashMap<>();
        Double monthlySpent = 0.0;
        for (Category category : Category.values()) {
            Double spent = expenseRepository.getTotalSpentThisMonthForEachCategory(username, category, month, year);
            if (spent == null) spent = 0.0;
            perCategory.put(category, spent);
            monthlySpent += spent;
        }
        return new MonthlyResponse(month, year, monthlySpent, perCategory);
    }

    public DailyResponse getDailySummary(String username, Integer month, Integer year) {
        Map<String, Double> dailySummary = new HashMap<>();
        Double totalSpent = 0.0;
        List<Object[]> dailySpent = expenseRepository.getTotalSpentThisMonthPerDay(username, month, year);
        for (Object[] row :  dailySpent) {
            LocalDate day = (LocalDate) row[0];
            Double amount = (Double)row[1];
            dailySummary.put(day.toString(), amount);
            totalSpent += amount;
        }
        return new DailyResponse(month, year, totalSpent, dailySummary);
    }

}
