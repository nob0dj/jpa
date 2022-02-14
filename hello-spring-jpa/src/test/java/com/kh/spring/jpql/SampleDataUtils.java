package com.kh.spring.jpql;

import java.sql.Date;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kh.spring.carrier.domain.Carrier;
import com.kh.spring.carrier.domain.TelecomCompany;
import com.kh.spring.carrier.repository.CarrierRepository;
import com.kh.spring.member.domain.Address;
import com.kh.spring.member.domain.Gender;
import com.kh.spring.member.domain.Member;
import com.kh.spring.member.domain.Phone;
import com.kh.spring.member.repository.MemberRepository;
import com.kh.spring.team.domain.Team;
import com.kh.spring.team.repository.TeamRepository;

import lombok.Data;
import lombok.NoArgsConstructor;


@Component
@Data
@NoArgsConstructor
public class SampleDataUtils {
	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	CarrierRepository carrierRepository;
	
	@Autowired
	TeamRepository teamRepository;
	
	public void insertData() {
		// 영속성전이 Phone#carrier(cascade=CacadeType.All)로 생성하면 계속 insert into carrier를 실행하므로, 별도로 관리
		Carrier kt = new Carrier(TelecomCompany.KT); 
		Carrier sk = new Carrier(TelecomCompany.SK); 
		Carrier uplus = new Carrier(TelecomCompany.UPLUS); 
		carrierRepository.save(kt);
		carrierRepository.save(sk);
		carrierRepository.save(uplus);
		
		Team team1 = new Team("happy together");
		Team team2 = new Team("world best pirate");
		Team team3 = new Team("nasty eyes");
		teamRepository.save(team1);
		teamRepository.save(team2);
		teamRepository.save(team3);
		
		Member honggd = Member.builder()
				.id("honggd")
				.password("1234")
				.name("홍길동")
				.gender(Gender.M)
				.birthday(Date.valueOf("1988-08-08"))
				.email("honggd@naver.com")
				.team(team1)
				.hobby(Arrays.asList(new String[] {"음악", "영화", "독서"}))
				.address(new Address("08345", "서울시 강남구 역삼동"))
				.phone(new Phone(kt, "01012341234"))
				.build();
		Member sinsa = Member.builder()
				.id("sinsa")
				.password("1234")
				.name("신사임당")
				.gender(Gender.F)
				.birthday(Date.valueOf("1954-10-19"))
				.email("sinsa@gmail.com")
				.team(team1)
				.hobby(Arrays.asList(new String[] {"독서", "드라이브"}))
				.address(new Address("09345", "경상남도 함양군 안의면"))
				.phone(new Phone(uplus, "01033334444"))
				.build();
		Member sejong = Member.builder()
				.id("sejong")
				.password("1234")
				.name("세종대왕")
				.gender(Gender.M)
				.birthday(Date.valueOf("1946-04-14"))
				.email("sejong@naver.com")
				.team(team1)
				.hobby(Arrays.asList(new String[] {"뉴스시청", "드라이브"}))
				.address(new Address("09345", "경기도 남양주시 별내면"))
				.phone(new Phone(sk, "01055553333"))
				.build();
		Member jyoung = Member.builder()
				.id("jyoung")
				.password("1234")
				.name("장영실")
				.build();
		Member nonondog = Member.builder()
				.id("nonondog")
				.password("1234")
				.name("논개")
				.gender(Gender.F)
				.birthday(Date.valueOf("1990-09-19"))
				.email("nonondog@naver.com")
				.team(team1)
				.hobby(Arrays.asList(new String[] {"유투브", "영화", "음악", "독서", "드라이브"}))
				.address(new Address("07234", "서울시 강북구 번동"))
				.phone(new Phone(sk, "01033335555"))
				.build();		
		Member sindong = Member.builder()
				.id("sindong")
				.password("1234")
				.name("신동엽")
				.gender(Gender.M)
				.birthday(Date.valueOf("1970-01-13"))
				.email("sindong@gmail.com")
				.team(team2)
				.address(new Address("07234", "서울시 중구 마장동"))
				.phone(new Phone(uplus, "01044556677"))
				.build();
		Member soisoi = Member.builder()
				.id("soisoi")
				.password("1234")
				.name("박소이")
				.gender(Gender.F)
				.birthday(Date.valueOf("2012-03-12"))
				.email("soisoi@gmail.com")
				.team(team2)
				.address(new Address("08888", "서울시 구로구 신도림동"))
				.phone(new Phone(uplus, "01023435464"))
				.build();
		
		Member jongseo = Member.builder()
				.id("jongseo")
				.password("1234")
				.name("전종서")
				.gender(Gender.F)
				.birthday(Date.valueOf("1994-07-05"))
				.email("jongseo@gmail.com")
				.team(team2)
				.address(new Address("01230", "서울시 양천구 목동"))
				.phone(new Phone(uplus, "01023435464"))
				.build();
		
		memberRepository.save(honggd);
		memberRepository.save(sinsa); 
		memberRepository.save(sejong);
		memberRepository.save(jyoung);
		memberRepository.save(nonondog);
		memberRepository.save(sindong);
		memberRepository.save(soisoi);
		memberRepository.save(jongseo);
	}
	
}
