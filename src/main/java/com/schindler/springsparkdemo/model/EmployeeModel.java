package com.schindler.springsparkdemo.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class EmployeeModel {

    private final String firstName;

    private final String lastName;

    private final int salary;

    private final String department;

    private final LocalDate hiringDate;

    private final int bonus;

    private final List<String> skills;
}
