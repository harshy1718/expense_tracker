package com.harsh.expense_tracker.dto;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DailyResponse {
    private Integer month;
    private Integer year;
    private Double totalSpent;
    private Map<String, Double> dailySpent;

    public DailyResponse(Integer month, Integer year, Double  totalSpent, Map<String, Double> dailySpent) {
        this.month = month;
        this.year = year;
        this.totalSpent = totalSpent;
        this.dailySpent = dailySpent;
    }
}
