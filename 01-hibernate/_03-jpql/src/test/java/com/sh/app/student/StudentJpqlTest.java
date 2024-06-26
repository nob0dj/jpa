package com.sh.app.student;

import com.sh.app.student.dto.StudentDto;
import com.sh.app.student.entity.Department;
import com.sh.app.student.entity.Student;
import jakarta.persistence.TypedQuery;
import org.assertj.core.api.Assertions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StudentJpqlTest {
    private static SessionFactory sessionFactory;
    private Session session;
    @BeforeAll
    public static void beforeAll() {
        String resourceName = "hibernate.cfg.xml";
        Configuration config = new Configuration();
        // resourceName이 기본값인 경우 생략가능
        config.configure(resourceName);
        sessionFactory = config.buildSessionFactory();
        insertData();
    }

    @BeforeEach
    public void setUp() {
        session = sessionFactory.openSession();
    }

    @AfterAll
    public static void afterAll() {
        sessionFactory.close();
    }

    @AfterEach
    public void tearDown() {
        session.close();
    }

    /**
     * 엔티티 프로젝션
     * @throws Exception
     */
    @DisplayName("id로 한건 조회")
    @Test
    public void test1() throws Exception {
        // given
        Long id = 1L;
        // when
        // from Student where id = :id 도 가능함.
        TypedQuery<Student> query = session.createQuery("select s from Student s where id = :id")
                                    .setParameter("id", id);
        Student student = query.getSingleResult();
        System.out.println(student);
        // then
        assertThat(student)
                .isNotNull()
                .satisfies(_student -> {
                   assertThat(_student.getId()).isNotNull();
                   assertThat(_student.getName()).isNotNull();
                   assertThat(_student.getDepartment()).isNotNull();
                });


    }
    @DisplayName("name으로 여러건 조회")
    @Test
    public void test2() throws Exception {
        // given
        String name = "길동";
        // when
        TypedQuery<Student> query = session.createQuery("select s from Student s where s.name like '%' || ?1 || '%'")
                                    .setParameter(1, "길동");
        List<Student> students = query.getResultList();
        System.out.println(students);
        // then
        Assertions.assertThat(students)
                .isNotEmpty()
                .allSatisfy(_student -> {
                   assertThat(_student.getName()).contains(name);
                });
    }
    @DisplayName("in연산자 조회")
    @Test
    public void test3() throws Exception {
        // given
        List<Department> departments = Arrays.asList(
            Department.ENGLISH_LITERATURE,
            Department.KOREAN_LITERATURE
        );
        // when
        TypedQuery<Student> query = session.createQuery("select s from Student s where s.department in :departments")
                                    .setParameter("departments", departments);
        List<Student> students = query.getResultList();
        System.out.println(students);
        // then
        Assertions.assertThat(students)
                .isNotEmpty()
                .allSatisfy(_student -> {
                   assertThat(_student.getDepartment()).satisfiesAnyOf(
                       d1 -> assertThat(d1).isEqualTo(Department.ENGLISH_LITERATURE),
                       d1 -> assertThat(d1).isEqualTo(Department.KOREAN_LITERATURE)
                   );
                });
    }

    /**
     * 검증을 위해 Student는 Comparable 인터페이스를 구현해야 한다.
     */
    @DisplayName("order by 적용")
    @Test
    public void test4() throws Exception {
        // given
        // when
        TypedQuery<Student> query = session.createQuery("select s from Student s order by s.id desc");
        List<Student> students = query.getResultList();
        System.out.println(students);
        // then
        Assertions.assertThat(students)
                .isNotEmpty()
//                .isSorted(); // asc인 경우
                .isSortedAccordingTo(Collections.reverseOrder()); // desc인 경우
    }

    /**
     * <pre>
     * 스칼라값 프로젝션
     * - 그룹함수의 반환 타입은 결과 값이 정수면 Long, 실수면 Double로 반환된다.
     * - 그룹함수의 값이 없는 경우도 오류없이 처리하려면, 기본형 대신 wrapper타입 사용할것
     *
     * </pre>
     * @throws Exception
     */
    @DisplayName("그룹함수 사용")
    @Test
    public void test5() throws Exception {
        // given
        // when
        // int가 아닌 long타입이 반환
        TypedQuery<Long> query = session.createQuery("select count(s) from Student s");
        Long cnt = query.getSingleResult();
        System.out.println(cnt);
        // then
        assertThat(cnt).isGreaterThan(0);
    }

    /**
     * <pre>
     * new 연산자 프로젝션
     * - 다중 열 컬럼을 조회하는 경우 타입을 지정하지 못한다.
     * - TypedQuery<Object[]> 또는 Query<Object[]>를 이용해서 Object를 일일히 인덱스별로 처리하거나
     * - new연산자를 활용한 dto객체로 프로젝션이 가능하다.
     * </pre>
     *
     * @throws Exception
     */
    @DisplayName("new 연산자 프로젝션")
    @Test
    public void test6() throws Exception {
        // given
        // when
        TypedQuery<StudentDto> query = session.createQuery("select new com.sh.app.student.dto.StudentDto(s.id, s.name) from Student s");
        List<StudentDto> students = query.getResultList();
        System.out.println(students);
        // then
        Assertions.assertThat(students)
                .isNotEmpty()
                .hasSize(5);
    }

    @DisplayName("페이징처리")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void test7(int page) throws Exception {
        // given
        int limit = 2;
        int offset = (page - 1) * limit;
        // when
        TypedQuery<Student> query = session.createQuery("from Student s order by s.id desc")
                .setFirstResult(offset)
                .setMaxResults(limit);
        List<Student> students = query.getResultList();
        System.out.println(students);
        // then
        Assertions.assertThat(students)
                .size().isLessThanOrEqualTo(limit);
    }

    @DisplayName("group by | having")
    @Test
    public void test8() throws Exception {
        // given
        int cnt = 1;
        // when
        TypedQuery<Object[]> query = session.createQuery("select s.department, count(s) from Student s group by s.department having count(s) > :cnt")
                .setParameter("cnt", cnt);
        List<Object[]> resultList = query.getResultList();
        System.out.println(resultList);
        // then
        assertThat(resultList)
                .allSatisfy(row -> {
                   assertThat(row[0]).isInstanceOf(Department.class);
                   assertThat(row[1]).isInstanceOf(Integer.class)
                           .satisfies(value -> assertThat(((Integer) value).intValue()).isGreaterThan(cnt));
                    System.out.println("%s | %s".formatted(row[0], row[1]));
                });
    }


    private static void insertData() {
        Session session = sessionFactory.openSession();
        Student honggd = Student.builder()
                .name("홍길동")
                .department(Department.COMPUTER_SCIENCE)
                .build();
        Student gogd = Student.builder()
                .name("고길동")
                .department(Department.ENGLISH_LITERATURE)
                .build();
        Student sinsa = Student.builder()
                .name("신사임당")
                .department(Department.COMPUTER_SCIENCE)
                .build();
        Student leess = Student.builder()
                .name("이순신")
                .department(Department.BUSINESS_ADMINISTRATION)
                .build();
        Student sejong = Student.builder()
                .name("세종")
                .department(Department.KOREAN_LITERATURE)
                .build();
        Transaction transaction = session.beginTransaction();
        session.persist(honggd);
        session.persist(gogd);
        session.persist(sinsa);
        session.persist(leess);
        session.persist(sejong);
        transaction.commit();
        session.clear();
    }

}