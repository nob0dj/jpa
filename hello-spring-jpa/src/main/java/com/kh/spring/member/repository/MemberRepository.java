package com.kh.spring.member.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kh.spring.member.domain.Gender;
import com.kh.spring.member.domain.Member;
import com.kh.spring.member.domain.TelecomCompany;
import com.kh.spring.team.domain.Team;

public interface MemberRepository extends JpaRepository<Member, String> {

	List<Member> findByIdLike(String id);

	Optional<Member> findByIdAndPassword(String id, String password);

	List<Member> findByNameLike(String name);

	List<Member> findByBirthdayIsNull();

	List<Member> findByGender(Gender gender);

	List<Member> findByGenderAndPhoneCarrierTelecomCompany(Gender gender, TelecomCompany telecomCompany);

	List<Member> findByGenderAndPhoneCarrierTelecomCompanyIn(Gender gender, List<TelecomCompany> telecomCompanies);

	List<Member> findAllByBirthdayLessThanEqual(Date lastAdultBirthday);

	List<Member> findAllByTeam(Team team);

	@Query("select m from Member m where extract(month from m.birthday) = :monthValue")
	List<Member> findAllByBirthdayIsThisMonth(@Param("monthValue") int thisMonthValue);

	@Query("from Member m join m.team t where t.name = :teamName")
	List<Member> findAllByTeamName(@Param("teamName") String teamName);

	@Query("select m.team from Member m where m.id = :memberId")
	Team findTeamByMemberId(String memberId);
	
}
