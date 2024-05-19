package com.sh.entity.mapping._04.inheritance._01.single.table._01.employee.repository;

import com.sh.entity.mapping._04.inheritance._01.single.table._01.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
