package com.harsh.expense_tracker.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Budget")
@Getter @Setter
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long Id;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username", nullable = false)
    public User user;

    @Enumerated(EnumType.STRING)
    public Category category;

    public Double monthlyLimit;

    @Column(nullable = false)
    public Integer month;

    @Column(nullable = false)
    public Integer year;
}
