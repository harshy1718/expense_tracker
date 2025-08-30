package com.harsh.expense_tracker.dto;

import com.harsh.expense_tracker.model.Expense;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ExpenseResponse {
    public Expense expense;
    public boolean budgetExceeded;

    public ExpenseResponse(Expense expense, boolean budgetExceeded) {
        this.expense = expense;
        this.budgetExceeded = budgetExceeded;
    }
}
