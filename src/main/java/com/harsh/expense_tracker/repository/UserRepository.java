package com.harsh.expense_tracker.repository;

import com.harsh.expense_tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
    Optional<User> findByEmail(String email);
}
