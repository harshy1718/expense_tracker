package com.harsh.expense_tracker.repository;

import com.harsh.expense_tracker.model.Category;
import com.harsh.expense_tracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Long> {
    List<Expense> findByUserUsername(String username);

    List<Expense> findByUserUsernameAndCategory(String username, Category category);

    @Query("SELECT SUM(e.amount) FROM Expense e " +
            "WHERE e.user.username = :username " +
            "AND e.category = :category " +
            "AND MONTH(e.date) = MONTH(CURRENT_DATE) " +
            "AND YEAR(e.date) = YEAR(CURRENT_DATE) ")
    Double getTotalSpentThisMonth(@Param("username") String username, @Param("category") Category category);

    @Query("SELECT SUM(e.amount) FROM Expense e " +
            "WHERE e.user.username = :username " +
            "AND e.category = :category " +
            "AND MONTH(e.date) =:month " +
            "AND YEAR(e.date) =:year")
    Double getTotalSpentThisMonthForEachCategory(@Param("username") String  username, @Param("category") Category category, @Param("month") Integer month, @Param("year") Integer year);

    @Query("SELECT e.date, SUM(e.amount) " +
            "FROM Expense e " +
            "Where e.user.username = :username " +
            "AND MONTH(e.date) = :month " +
            "AND YEAR(e.date) = :year " +
            "GROUP BY e.date " +
            "ORDER BY e.date ")
    List<Object[]> getTotalSpentThisMonthPerDay(@Param("username") String username, @Param("month") Integer month, @Param("year") Integer year);
}
