package com.kh.spring;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kh.spring.member.domain.Member;
import com.kh.spring.member.repository.MemberRepository;
import com.kh.spring.team.domain.Team;
import com.kh.spring.team.repository.TeamRepository;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED) // 자동롤백 하지 않음 
@AutoConfigureTestDatabase(replace = Replace.NONE) // 실제 db사용
class TeamRepositoryTest {

	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private TeamRepository teamRepository;
	
	
	@Disabled
	@Test
	void test1() {
		// given
		Team team1 = new Team("happy hour");
		// when
		teamRepository.save(team1); // @Id @Generated 컬럼값이 채워진다. 리턴불필요 
		// then
		assertThat("team 등록시 pk가 발급된다.", team1.getId(), is(1L));
		
		// given
		Team team2 = new Team("one piece");
		// when
		teamRepository.save(team2);
		// then
		assertThat("team 등록시 pk가 발급된다.", team2.getId(), is(2L));

	}
	
	@Disabled
	@Test
	public void test2() {
		// given
		Team team = new Team("happy hour");
//		// when
		// Member.team에 @ManyToOne(cascade = CascadeType.ALL) 지정하지 않았다면 먼저 save해서 영속상태로 만들어야 한다.
//		teamRepository.save(team);
		
		// given
		Member member = Member.builder()
						.id("honggd")
						.password("1234")
						.name("홍길동")
						.team(team)
						.build();
		// when
		member = memberRepository.save(member);

		// then
		assertThat("회원 조회시 팀 entity도 조회되어야 한다.", member.getTeam().getName(), is(equalTo("happy hour")));
	}
	
	/**
	 * 
	 * Member - Team 양방향
	 */
	@Disabled
	@Test
	void test3() throws Exception {
		// given
		Team team = new Team("happy hour");
		team = teamRepository.save(team);
		
		// given
		Member honggd = Member.builder()
						.id("honggd")
						.password("1234")
						.name("홍길동")
						.team(team)
						.build();
		Member sinsa = Member.builder()
						.id("sinsa")
						.password("1234")
						.name("신사임당")
						.team(team)
						.build();
		// when
		honggd = memberRepository.save(honggd);
		sinsa = memberRepository.save(sinsa);
		
		System.out.println(honggd);
		
		assertThat("회원의 team은 생성한 team객체와 동일하다.", honggd.getTeam(), is(equalTo(team)));
		
	}
	
	@Test
	@Transactional
	void test4() throws Exception {
		// given
		Team team = new Team("happy hour");
		team = teamRepository.save(team);
		
		// given
		Member honggd = Member.builder()
						.id("honggd")
						.password("1234")
						.name("홍길동")
						.team(team)
						.build();
		Member sinsa = Member.builder()
						.id("sinsa")
						.password("1234")
						.name("신사임당")
						.team(team)
						.build();
		honggd.setTeam(team);
		sinsa.setTeam(team);
		
		// when
		Team foundTeam = teamRepository.findById(team.getId()).get();
		List<Member> members = foundTeam.getMembers();
		
		// then
		System.out.println(members);
		assertEquals(2, members.size());
		
		
		
		
	}
	
	/**
	 * 양방향 연관관계일때 객체참조그래프를 완성한다.
	 * 
	 * member.setTeam(team)
	 * team.getMembers().add(member)
	 * 
	 * 위코드를 하나처럼 사용해야 누락되는 실수를 예방할 수 있다.
	 * 
	 * {@link Member#setTeam(Team)}
	 * 
	 * @throws Exception
	 */
	@Test
	@Disabled
	void 객체참조그래프완성1() throws Exception {
		// given
		Team team = new Team("happy hour");
		team = teamRepository.save(team);
		
		Member honggd = Member.builder()
						.id("honggd")
						.password("1234")
						.name("홍길동")
						.build();
		
		honggd = memberRepository.save(honggd);
		honggd.setTeam(team);
//		team.getMembers().add(honggd);
		honggd = memberRepository.save(honggd);
		
		// then
		assertThat("팀의 회원(0)로 honggd 조회", team.getMembers().get(0), is(equalTo(honggd)));
		assertThat("honggd.team은 team과 동일", honggd.getTeam(), is(equalTo(team)));
		
		// toString StackOverflowError 유발확인
		System.out.println(honggd);
		
	}
	
	@Disabled
	@Test
	void 객체참조그래프완성2() throws Exception {
		// given
		Team team1 = new Team("happy hour");
		Team team2 = new Team("dawn fm");
		team1 = teamRepository.save(team1);
		team2 = teamRepository.save(team2);
		
		Member honggd = Member.builder()
				.id("honggd")
				.password("1234")
				.name("홍길동")
				.build();
		honggd.setTeam(team1);
		memberRepository.save(honggd);
		
		// when : 팀 변경
		honggd.setTeam(team2);
		memberRepository.save(honggd);
		
		// then
		assertThat("team1.members.size == 0", team1.getMembers().size(), is(equalTo(0)));
		assertThat("team2.members.size == 1", team2.getMembers().size(), is(equalTo(1)));
		
		// when : 팀 삭제
		honggd.setTeam(null);
		memberRepository.save(honggd);
		
		// then
		assertThat("team2.members[0]도 삭제되었다.", team1.getMembers().size(), is(equalTo(0)));
	}
	
	/**
	 * member.setTeam(team)을 두번이상 했을때 한번만 처리되어야 한다.
	 * - 기존팀에서 제거후 추가하므로 테스트에 통과한다.
	 * 
	 * @see {@linkplain Member#setTeam(Team)} 
	 * 
	 * 
	 * @throws Exception
	 */
	@Disabled
	@Test
	void 객체참조그래프완성3() throws Exception {
		// given
		Team team1 = new Team("happy homepod");
		team1 = teamRepository.save(team1);
		
		Member honggd = Member.builder()
						.id("honggd")
						.password("1234")
						.name("홍길동")
						.build();
		honggd.setTeam(team1);
		honggd.setTeam(team1);
		
		memberRepository.save(honggd);
		
		Long count = containsHowManyThisMember(honggd, team1);
		assertThat("team1.members내에 honggd는 한명만 존재해야한다.", count, is(equalTo(1L)));
		
		Team team2 = new Team("crazy homepod");
		team2 = teamRepository.save(team2);
		team2.addMember(honggd);
		
		count = containsHowManyThisMember(honggd, team1);
		assertThat("team1.members내에 honggd는 0명만 존재해야한다.", count, is(equalTo(0L)));
		count = containsHowManyThisMember(honggd, team2);
		assertThat("team2.members내에 honggd는 1명만 존재해야한다.", count, is(equalTo(1L)));
	}

	private Long containsHowManyThisMember(Member member, Team team) {
		return team.getMembers().stream()
				.filter(member::equals)
				.count();
	}
	
	
}
