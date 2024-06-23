package com.sh.entity.association._04.persist.cascade._01.club.student.repository;

import com.sh.entity.association._04.persist.cascade._01.club.student.entity.Club;
import com.sh.entity.association._04.persist.cascade._01.club.student.entity.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ClubRepositoryTest {
    @Autowired
    ClubRepository clubRepository;
    @Autowired
    StudentRepository studentRepository;

    @DisplayName("Club - Student 영속성전이")
    @Test
    @Rollback(false)
    public void test() throws Exception {
        // given
        Student student1 = new Student("s1", "홍길동");
        Student student2 = new Student("s2", "신사임당");
        Club club = new Club("c1", "오륙도노래동아리", Set.of(student1, student2));
        // when
        clubRepository.saveAndFlush(club);
        // then
        assertThat(studentRepository.findById("s1")).isNotEmpty();
        assertThat(studentRepository.findById("s2")).isNotEmpty();
    }

}