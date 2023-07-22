package com.udacity.jdnd.course3.critter.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Method for employee request
    List<Employee> findByDaysAvailable(DayOfWeek date);
}
