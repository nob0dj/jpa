package com.kh.spring.jpql;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.kh.spring.carrier.domain.TelecomCompany;
import com.kh.spring.carrier.repository.CarrierRepository;
import com.kh.spring.member.domain.Gender;
import com.kh.spring.member.domain.Member;
import com.kh.spring.member.repository.MemberRepository;
import com.kh.spring.team.domain.Team;
import com.kh.spring.team.repository.TeamRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest 
@Transactional
class MemberJpqlTest {

	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	CarrierRepository carrierRepository;
	
	@Autowired
	TeamRepository teamRepository;
	
	@Autowired
	SampleDataUtils sampleDataUtils;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		// JUnit4의 @BeforeClass와 동일
		
	}

	
	/**
	 * sample data 최초 1회 생성후 hibernate.ddl-auto=none 설정후 테스트한다.
	 * @throws Exception
	 */
//	@Rollback(false)
//	@Test
//	void testName() throws Exception {
//		testDataUtils.insertData();
//	}

//	@Disabled
	@Test
	@DisplayName("쿼리메소드")
	@Rollback(false)
	void test() throws Exception {
		// 전체조회
		List<Member> members = memberRepository.findAll();
		assertTrue(members.size() > 0);
		
		// pk 한건 조회
		Member m1 = memberRepository.findById("nonondog").get();
		assertNotNull(m1);
		
		// id like연산
		List<Member> m2 = memberRepository.findByIdLike("%ng%");
		log.debug("{}", m2);
		assertTrue(() -> m2.stream().allMatch(m -> m.getId().contains("ng")));
		
		// name like연산
		List<Member> m3 = memberRepository.findByNameLike("%동%");
		log.debug("{}", m3);
		assertTrue(() -> m3.stream().allMatch(m -> m.getName().contains("동")));
		
		// 성별
		List<Member> maleMembers = memberRepository.findByGender(Gender.M);
		assertTrue(() -> maleMembers.stream().allMatch(m -> m.getGender() == Gender.M));
		
		// birthday is null
		List<Member> birthdayNullMembers = memberRepository.findByBirthdayIsNull();
		assertTrue(() -> birthdayNullMembers.stream().allMatch(m -> m.getBirthday() == null));
		
		// 성인 조회 
		Year minAdultBirthYear = getCurrentMinAdultBirthYear();
		LocalDate _lastAdultBirthday = minAdultBirthYear.atMonthDay(MonthDay.of(12, 31)); // 2003-12-31
		Date lastAdultBirthday = Date.valueOf(_lastAdultBirthday);
		List<Member> adultMembers = memberRepository.findAllByBirthdayLessThanEqual(lastAdultBirthday);
		log.debug("adultMembers = {}", adultMembers);
		
		java.util.Date when = new SimpleDateFormat("yyyy-MM-dd").parse("2004-01-01");
		assertTrue(() -> adultMembers.stream().allMatch(m -> m.getBirthday().before(when)));
		
		// 성별이 여 && 통신사가 SK
		List<Member> resultList1 = memberRepository.findByGenderAndPhoneCarrierTelecomCompany(Gender.F, TelecomCompany.SK);
//		log.debug("resultList1 = {}", resultList1);
		assertTrue(() -> resultList1.stream().allMatch((m) -> 
							m.getGender() == Gender.F && (m.getPhone().getCarrier().getTelecomCompany() == TelecomCompany.SK)));
		
		// 성별이 여 && 통신사가 SK 또는 UPLUS
		List<Member> resultList2 = memberRepository.findByGenderAndPhoneCarrierTelecomCompanyIn(Gender.F, Arrays.asList(TelecomCompany.SK, TelecomCompany.UPLUS));
		log.debug("resultList2 = {}", resultList2);
		assertTrue(() -> resultList2.stream().allMatch((m) -> 
						m.getGender() == Gender.F && 
							(m.getPhone().getCarrier().getTelecomCompany() == TelecomCompany.SK ||
								m.getPhone().getCarrier().getTelecomCompany() == TelecomCompany.UPLUS)));
		
		
		
		// 로그인 검사
		Member loginSuccessMember = memberRepository.findByIdAndPassword("honggd", "1234").get();
		assertNotNull(loginSuccessMember);
		Member loginFailureMember = memberRepository.findByIdAndPassword("honggd", "xxxx").orElse(null);
		assertNull(loginFailureMember);
		
		
	}

	
	
	/**
	 * <pre>
	 * 올해 성년의 출생년도는? 
	 * 2022년 - 2003년이 20세 성인이 되었다.
	 * 
	 * 2003 1살
	 * 2004 2살
	 * 2005 3살
	 * 2006 4살
	 * 2007 5살
	 * 2008 6살
	 * 2009 7살
	 * 2010 8살
	 * 2011 9살
	 * 2012 10살
	 * 2013 11살
	 * 2014 12살
	 * 2015 13살
	 * 2016 14살
	 * 2017 15살
	 * 2018 16살
	 * 2019 17살
	 * 2020 18살
	 * 2021 19살
	 * 2022 20살
     * 
     * </pre>
     * 
	 * @throws ParseException 
	 * 
	 */
	private Year getCurrentMinAdultBirthYear() {
		return Year.of(Year.now().getValue() - 19); // 출생년이 1살이므로 20이 아닌 19를 감산한다.
	}

//	@Disabled
	@Test
	@DisplayName("jpql테스트")
	@Rollback(false)
	void test3() throws Exception {
		// birthday가 이번달인 회원 조회 
//		int thisMonthValue = LocalDate.now().getMonthValue();
		int thisMonthValue = 7;
		List<Member> membersWhoseBirthdayIsThisMonth = memberRepository.findAllByBirthdayIsThisMonth(thisMonthValue);
		log.debug("membersWhoseBirthdayIsThisMonth = {}", membersWhoseBirthdayIsThisMonth);
		
		// 생성시에 java.sql.Date를 사용해서 java.sql.Date -> java.time.LocalDate 방식을 사용해야 한다.
		assertTrue(() -> membersWhoseBirthdayIsThisMonth.stream().allMatch(
							(m) -> new Date(m.getBirthday().getTime()).toLocalDate().getMonthValue() == thisMonthValue));
		
		
		// 팀원 조회 (내부조인)
		String teamName1 = "happy together";
		List<Member> team1Members = memberRepository.findAllByTeamName(teamName1);
		log.debug("team1Members = {}", team1Members);
		assertTrue(() -> team1Members.stream().allMatch((m) -> m.getTeam().getName().equals(teamName1)));
		
		// 팀 조회 (내부조인)
		Team team2 = memberRepository.findTeamByMemberId("jongseo");
		log.debug("team2 = {}", team2);
		assertTrue(() -> Optional.of(team2).isPresent());
		
	
		
	}

}
