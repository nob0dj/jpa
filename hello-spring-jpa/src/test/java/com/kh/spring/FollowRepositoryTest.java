package com.kh.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.test.annotation.Rollback;

import com.kh.spring.follow.domain.Follow;
import com.kh.spring.follow.repository.FollowRepository;
import com.kh.spring.member.domain.Member;
import com.kh.spring.member.repository.MemberRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE) // 실제 db사용
class FollowRepositoryTest {

	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	FollowRepository followRepository;
	
	@Rollback(false)
	@Test
	@DisplayName("팔로우 팔로워 등록후 조회하기")
	void test() {
		// given
		Member honggd = Member.builder()
							.id("honggd")
							.password("2345")
							.name("홍길동")
							.build();
		Member sinsa = Member.builder()
						.id("sinsa")
						.password("1234")
						.name("신사임당")
						.build();
		Member balsamic = Member.builder()
							.id("balsamic")
							.password("1234")
							.name("발사믹")
							.build();
		honggd = memberRepository.save(honggd);
		sinsa = memberRepository.save(sinsa);
		balsamic = memberRepository.save(balsamic);
		
		// when
		Follow follow1 = new Follow(honggd, sinsa);
		Follow follow2 = new Follow(sinsa, balsamic);
		Follow follow3 = new Follow(honggd, balsamic);
		Follow follow4 = new Follow(sinsa, honggd);
		
		followRepository.save(follow1);
		followRepository.save(follow2);
		followRepository.save(follow3);
		followRepository.save(follow4);
		
		//then
		List<Follow> list = followRepository.findAll();
		System.out.println(list);
		assertEquals(4, list.size());
		
		
		/*
		 *  follower/followee 조회전략
		 *  1. @EmbeddedId findByIdFollowee(String) : FollowId#followee
		 *  2. @MapsId findByFollowee(Member) : Follow#followee
		 *  3. findAll(Example<Follow>) : query method는 이미 존재한다.  
		 */
		
		// honggd의 follower 조회 (followee가 honggd를 조회)
//		List<Follow> followerList = followRepository.findByIdFollowee(honggd.getId()); // @EmbeddedId통해 조회
//		List<Follow> followerList = followRepository.findByFollowee(honggd);
		
		Follow follower = new Follow();
		follower.getId().setFollowee(honggd.getId()); // FollowId#followee필드만 값설정
		Example<Follow> exampleFollower = Example.of(follower);
		List<Follow> followerList = followRepository.findAll(exampleFollower);
		
		System.out.println(followerList);
		assertEquals(2, followerList.size());
		assertEquals(honggd.getId(), followerList.get(0).getId().getFollowee());
		assertEquals(honggd.getId(), followerList.get(1).getId().getFollowee());
		
		// honggd의 followee 조회 (follower가 honggd를 조회)
//		List<Follow> followeeList = followRepository.findByIdFollower(honggd.getId()); // @EmbeddedId통해 조회
//		List<Follow> followeeList = followRepository.findByFollower(honggd);
		
		Follow followee = new Follow();
		followee.getId().setFollower(honggd.getId()); // FollowId#follower필드만 값설정
		Example<Follow> exampleFollowee = Example.of(followee);
		List<Follow> followeeList = followRepository.findAll(exampleFollowee);
		
		System.out.println(followeeList);
		assertEquals(1, followeeList.size());
		assertEquals(honggd.getId(), followeeList.get(0).getId().getFollower());
		
		
	}

}
