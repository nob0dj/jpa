package com.kh.spring.jpql;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.kh.spring.TestDataUtils;
import com.kh.spring.carrier.repository.CarrierRepository;
import com.kh.spring.member.domain.Member;
import com.kh.spring.member.repository.MemberRepository;
import com.kh.spring.team.domain.Team;
import com.kh.spring.team.repository.TeamRepository;

import lombok.extern.slf4j.Slf4j;


/**
 * jpa entity외의 빈을 사용할경우 @DataJpaTest를 사용할 수 없다.
 *
 */
@Slf4j
//@SpringBootTest 
//@Transactional
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class TeamJpqlTest {

	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	CarrierRepository carrierRepository;
	
	@Autowired
	TeamRepository teamRepository;
	
//	@Autowired
	TestDataUtils testDataUtils;
	
//	@BeforeEach
	void setUp() throws Exception {
		testDataUtils.initMemberData();
	}
	
//	@Disabled
	@Test
	@DisplayName("query method - Team")
	void test1() throws Exception {
		// A. 팀원 조회 - from Member
		Team team1 = teamRepository.findByName("happy together");
		log.debug("team1 = {}", team1);
		List<Member> team1Members = memberRepository.findAllByTeam(team1);
		log.debug("team1Members = {}", team1Members);
		assertTrue(() -> team1Members.stream().allMatch((m) -> m.getTeam().equals(team1)));
		
		// B. 팀 조회 - from Team
		//	1. 팀명 조회 uq_team_name
		String teamName2 = "world best pirate";
		Team team2 = teamRepository.findByName(teamName2);
		log.debug("team2 = {}", team2);
		log.debug("team2.members = {}", team2.getMembers());
		assertNotNull(team2);
		assertTrue(team2.getMembers().size() > 0);
		assertTrue(() -> team2.getMembers().stream().allMatch((m) -> m.getTeam().equals(team2)));
		
		//	2.팀원이 없는 팀 조회
		String teamName3 = "nasty eyes";
		Team team3 = teamRepository.findByName(teamName3);
		log.debug("team3 = {}", team3);
		assertNotNull(team3);
		assertThat("nasty eyes팀은 팀원이 없으므로 team3.members는 empty!", team3.getMembers(), is(empty()));
		
		log.debug("team3.members = {}", team3.getMembers());
		
	}


	
	@Test
	@DisplayName("jpql - Team")
	void test2() throws Exception {
		// 팀원이 없는 팀 조회 (외부조인)
		List<Team> teamList = teamRepository.findTeamWithNoMember();
		log.debug("teamList = {}", teamList);
		teamList.stream().forEach(team -> System.out.println(team.getMembers()));
		assertTrue(teamList.size() > 0);
		assertTrue(() -> teamList.stream().allMatch(team -> team.getMembers().isEmpty()));
		
		
		// 집합함수 : count sum avg max min
		List<Object[]> groupFunctionResultSet = teamRepository.calcTeam();
		Object[] row = groupFunctionResultSet.get(0);
		log.debug("groupFunctionResultSet = {} {} {} {} {}", row); // 3 7 2.3333333333333335 4 0
		log.debug("count = {}", row[0]);
		log.debug("sum = {}", row[1]);
		log.debug("avg = {}", row[2]);
		log.debug("max = {}", row[3]);
		log.debug("min = {}", row[4]);
		
		
		// fetch join : sql과 상관없이 jpql성능 최적화를 위해 지연로딩 없이 조인query를 통해 조회한다.
		List<Team> teams1 = teamRepository.findAll();
		/*
		    select
		        team0_.id as id1_12_,
		        team0_.name as name2_12_ 
		    from
		        team team0_
		 */
		log.debug("teams1 = {}", teams1);
		int i = 0;
		for(Team team : teams1)
			System.out.println(i++ + " " + team.getMembers());
		
		List<Team> teams2 = teamRepository.findTeamAndMember();
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
		
		log.debug("teams2 = {}", teams2);
		i = 0;
		for(Team team : teams2)
			System.out.println(i++ + " " + team.getMembers());
		
		
		
		
	}
	
}
