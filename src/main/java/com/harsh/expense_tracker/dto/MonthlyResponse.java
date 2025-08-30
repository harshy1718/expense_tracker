package com.harsh.expense_tracker.dto;

import com.harsh.expense_tracker.model.Category;
import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter @Setter
public class MonthlyResponse {
    private Integer month;
    private Integer year;
    private Double monthlySpent;
    private Map<Category, Double> perCategory;

    public MonthlyResponse(Integer month, Integer year, Double monthlySpent, Map<Category, Double> perCategory) {
        this.month = month;
        this.year = year;
        this.monthlySpent = monthlySpent;
        this.perCategory = perCategory;
    }

}
