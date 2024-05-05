package com.schindler.springsparkdemo.repository.entitiy;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder(setterPrefix = "set")
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee")
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "salary")
    private int salary;

    @Column(name = "department")
    private String department;

    @Column(name = "hiring_date")
    private LocalDate hiringDate;

    @Column(name = "salary_with_bonus")
    private int salaryWithBonus;

    @Column(name = "experience")
    private long experience;

    @Column(name = "skills")
    @ElementCollection
    private List<String> skills;

    @Column(name = "is_deleted")
    private boolean isDeleted;
}
