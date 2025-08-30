package com.harsh.expense_tracker.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "Expense")
@Getter @Setter
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long Id;

    @ManyToOne
    @JoinColumn(name="username", referencedColumnName = "username", nullable = false)
    public User user;

    @Column(nullable = false)
    public Double amount;

    @Enumerated(EnumType.STRING)
    public PaymentMode paymentMode;

    @Enumerated(EnumType.STRING)
    public Category category;

    public LocalDate date;

    public String remarks;
}
