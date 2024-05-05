package com.schindler.springsparkdemo.service;

import com.schindler.springsparkdemo.exception.exceptions.EmployeeNotExistException;
import com.schindler.springsparkdemo.model.EmployeeModel;
import com.schindler.springsparkdemo.repository.EmployeeRepository;
import com.schindler.springsparkdemo.repository.entitiy.Employee;
import lombok.RequiredArgsConstructor;
import org.apache.spark.sql.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final SparkSession sparkSession;

    @Transactional
    public void createEmployee(final EmployeeModel employeeModel) {
        employeeRepository.save(applySparkTransformationOnSingleEmployee(employeeModel));
    }

    @Transactional
    public Employee getEmployeeById(final long id) {
        final Optional<Employee> employee =  employeeRepository.findEmployeeById(id);
        if (employee.isPresent()) {
            return employee.get();
        }
        throw new EmployeeNotExistException("Employee with id %s not exists!".formatted(id));
    }

    @Transactional
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAllEmployee();
    }

    @Transactional
    public void deleteEmployee(final long id) {
        final Optional<Employee> employeeOptional = employeeRepository.findEmployeeById(id);
        if (employeeOptional.isPresent()) {
            final Employee employee = employeeOptional.get();
            employee.setDeleted(true);
            employeeRepository.save(employee);
        } else {
            throw new EmployeeNotExistException("Employee with id %s not exists!".formatted(id));
        }
    }

    private Employee applySparkTransformationOnSingleEmployee(final EmployeeModel employeeModel) {
        final Employee employee = Employee.builder()
                                          .setFirstName(employeeModel.getFirstName())
                                          .setLastName(employeeModel.getLastName())
                                          .setDepartment(employeeModel.getDepartment())
                                          .setSalary(employeeModel.getSalary())
                                          .setHiringDate(employeeModel.getHiringDate())
                                          .setExperience(0)
                                          .setSalaryWithBonus(0)
                                          .setSkills(employeeModel.getSkills())
                                          .setIsDeleted(false)
                                          .build();

        final Dataset<Employee> rawEmployeeDataSet = sparkSession.createDataset(Collections.singletonList(employee), Encoders.bean(Employee.class));

        final Dataset<Row> transformedDataSet = rawEmployeeDataSet
                                                          .withColumn("salaryWithBonus", functions.col("salary").plus(employeeModel.getBonus()))
                                                          .withColumn("experience", functions.col("experience").plus(getExperience(employeeModel)));

        transformedDataSet.show();

        return transformedDataSet.as(Encoders.bean(Employee.class)).first();
    }

    private long getExperience(final EmployeeModel employeeModel) {
        return ChronoUnit.YEARS.between(employeeModel.getHiringDate(), LocalDate.now());
    }

}
