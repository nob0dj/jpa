package com.sh.entity.association._04.persist.cascade._01.club.student.repository;

import com.sh.entity.association._04.persist.cascade._01.club.student.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, String> {
}
