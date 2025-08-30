package com.harsh.expense_tracker.repository;

import com.harsh.expense_tracker.model.Budget;
import com.harsh.expense_tracker.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findByUserUsername(String username);

    Budget findByUserUsernameAndCategoryAndMonthAndYear(String username, Category category, Integer month, Integer year);
}

