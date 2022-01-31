package com.kh.spring.team.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kh.spring.team.domain.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {

	Team findByName(String name);

	/**
	 * m을 조회해도 {@link Team#addMember(com.kh.spring.member.domain.Member)}가 호출되는 것은 아니다.
	 * 
	 * 
	 * @return
	 */
	@Query("select t, m from Team t left join t.members m where t.members.size = 0")
	List<Team> findTeamWithNoMember();

	@Query("select count(t), sum(t.members.size), avg(t.members.size), max(t.members.size), min(t.members.size) from Team t")
	List<Object[]> calcTeam();

	
	List<Team> findAll(); 
	/*
		select
			team0_.id as id1_12_,
			team0_.name as name2_12_ 
		from
			team team0_
	 */
	
	/**
	 * jpql의 distinct는 sql에 distinct를 실행하고, java단에서 한번던 중복객체를 제거한다.
	 *	- 
	 * 
	 * 
	 * @return
	 */
	@Query("select distinct t from Team t left join fetch t.members")
	List<Team> findTeamAndMember(); // 팀이 존재하는 회원수만큼 튜플 반환
	/*
		 select
			team0_.id as id1_12_0_,
			members1_.id as id1_4_1_,
			team0_.name as name2_12_0_,
			members1_.address as address2_4_1_,
			members1_.post_code as post_cod3_4_1_,
			members1_.birthday as birthday4_4_1_,
			members1_.email as email5_4_1_,
			members1_.enabled as enabled6_4_1_,
			members1_.enroll_date as enroll_d7_4_1_,
			members1_.gender as gender8_4_1_,
			members1_.laptop_id as laptop_13_4_1_,
			members1_.name as name9_4_1_,
			members1_.password as passwor10_4_1_,
			members1_.carrier_id as carrier14_4_1_,
			members1_.phone_number as phone_n11_4_1_,
			members1_.point as point12_4_1_,
			members1_.team_id as team_id15_4_1_,
			members1_.team_id as team_id15_4_0__,
			members1_.id as id1_4_0__ 
		from
			team team0_ 
		left outer join
			member members1_ 
				on team0_.id=members1_.team_id

	 */
}
