package com.sh.entity.association._05.aggregate.association._02.department.student;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

public class Student2DeapartmentTest {
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeAll
    static void beforeAll() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpatest");
    }

    @BeforeEach
    void setUp() {
        this.entityManager = entityManagerFactory.createEntityManager();
        this.entityManager.getTransaction().begin();
    }

    @AfterEach
    void tearDown() {
        this.entityManager.getTransaction().commit();
        this.entityManager.close();
    }

    @AfterAll
    static void afterAll() {
        entityManagerFactory.close();
    }
    
    @Test
    @DisplayName("ddl-auto=create 확인")
    void test() {
        /*
            create table tbl_department (
                capacity integer not null,
                id bigint not null auto_increment,
                name varchar(255),
                primary key (id)
            ) engine=InnoDB
        
            create table tbl_student (
                department_id bigint,
                id bigint not null auto_increment,
                address1 varchar(255),
                address2 varchar(255),
                name varchar(255),
                zipCode varchar(255),
                primary key (id)
            ) engine=InnoDB
         */
    }
    
    @Test
    @DisplayName("Student Entity 등록")
    void test2() {
        // given
        Department department = new Department(null, "컴퓨터 공학과", 30);
        this.entityManager.persist(department);
        /*
            Hibernate:
                insert
                into
                    tbl_department
                    (capacity, name)
                values
                    (?, ?)
         */
        // when
        Student student = new Student(null, "홍길동", department.getId(),
                new Address("서울시 강남구", "삼성동 1234", "01234"));
        this.entityManager.persist(student);
        /*
            Hibernate:
                insert
                into
                    tbl_student
                    (address1, address2, zipCode, department_id, name)
                values
                    (?, ?, ?, ?, ?)
         */
        // then
        assertThat(student.getId()).isNotNull();
        assertThat(student.getDepartmentId()).isEqualTo(department.getId());

//        this.entityManager.clear();
//        Student student2 = this.entityManager.find(Student.class, student.getId());
//        String jpql = """
//			SELECT
//				d
//			FROM
//				Department d
//			WHERE
//				d.id = ?1
//			""";
//        TypedQuery<Department> query = entityManager.createQuery(jpql, Department.class)
//                .setParameter(1, student2.getDepartmentId());
//        Department department2 = query.getSingleResult();
//        System.out.println(department2);
        /*
        Hibernate:
            select
                s1_0.id,
                s1_0.address1,
                s1_0.address2,
                s1_0.zipCode,
                s1_0.department_id,
                s1_0.name
            from
                tbl_student s1_0
            where
                s1_0.id=?
        Hibernate:
            select
                d1_0.id,
                d1_0.capacity,
                d1_0.name
            from
                tbl_department d1_0
            where
                d1_0.id=?
         */
        this.entityManager.clear();
        String jpql2 = """
			SELECT 
				d
			FROM 
				Student s LEFT JOIN Department d
	              ON s.departmentId = d.id
			WHERE 
				s.id = ?1
			""";
        TypedQuery<Object[]> query2 = entityManager.createQuery(jpql2, Object[].class)
                .setParameter(1, 1L);
        System.out.println(query2.getSingleResult());



    }
    

}
