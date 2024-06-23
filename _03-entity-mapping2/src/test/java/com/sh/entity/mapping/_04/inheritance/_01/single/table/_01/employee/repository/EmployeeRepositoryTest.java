package com.sh.entity.mapping._04.inheritance._01.single.table._01.employee.repository;

import com.sh.entity.mapping._04.inheritance._01.single.table._01.employee.entity.Developer;
import com.sh.entity.mapping._04.inheritance._01.single.table._01.employee.entity.Employee;
import com.sh.entity.mapping._04.inheritance._01.single.table._01.employee.entity.Manager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeRepositoryTest {
    @Autowired
    EmployeeRepository employeeRepository;

    /*
    -- ddl
    create table employee (
        id bigint not null auto_increment,
        emp_type varchar(31) not null,
        contact varchar(255),
        lang varchar(255),
        level varchar(255),
        name varchar(255),
        primary key (id)
    ) engine=InnoDB
     */
    @DisplayName("Manager 등록")
    @Test
    public void test() throws Exception {
        // given
        Manager manager = new Manager(null, "홍길동", "01012341234", "L2");
        // when
        manager = employeeRepository.save(manager);
        System.out.println(manager);
        // then
        assertThat(manager.getId()).isNotNull().isNotZero();
    }
    @DisplayName("Developer 등록")
    @Test
    public void test2() throws Exception {
        // given
        Developer developer = new Developer(null, "신사임당", "01012341234", "Java");
        // when
        developer = employeeRepository.save(developer);
        System.out.println(developer);
        // then
        assertThat(developer.getId()).isNotNull().isNotZero();
    }

    @DisplayName("Manager, Developer 조회")
    @Test
    @Rollback(false)
    void test3() {
        // given
        Developer developer = new Developer(null, "신사임당", "01012341234", "Java");
        employeeRepository.save(developer);
        Manager manager = new Manager(null, "홍길동", "01012341234", "L2");
        manager = employeeRepository.save(manager);
        // when
        List<Employee> employees = employeeRepository.findAll();
        System.out.println(employees);
        // then
        assertThat(employees)
                .allSatisfy(employee -> {
                    assertThat(employee).satisfiesAnyOf(
                            e -> assertThat(e).isInstanceOf(Manager.class),
                            e -> assertThat(e).isInstanceOf(Developer.class)
                    );
                });

    }
}