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

import com.kh.spring.follow.domain.AppUser;
import com.kh.spring.follow.domain.Follow;
import com.kh.spring.follow.repository.AppUserRepository;
import com.kh.spring.follow.repository.FollowRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE) // 실제 db사용
class FollowRepositoryTest {

	@Autowired
	AppUserRepository appUserRepository;
	
	@Autowired
	FollowRepository followRepository;
	
	@Rollback(false)
	@Test
	@DisplayName("팔로우 팔로워 등록후 조회하기")
	void test() {
		// given
		AppUser honggd = new AppUser("honggd");
		AppUser sinsa = new AppUser("sinsa");
		AppUser balsamic = new AppUser("balsamic");
		honggd = appUserRepository.save(honggd);
		sinsa = appUserRepository.save(sinsa);
		balsamic = appUserRepository.save(balsamic);
		
		// when
		Follow follow1 = new Follow(honggd, sinsa); 	// sinsa -> honggd
		Follow follow2 = new Follow(sinsa, balsamic); 	// balsamic -> sinsa
		Follow follow3 = new Follow(honggd, balsamic); 	// balsamic -> honggd
		Follow follow4 = new Follow(sinsa, honggd);		// honggd -> sinsa
		
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
		 *  2. @MapsId findByFollowee(AppUser) : Follow#followee
		 *  3. findAll(Example<Follow>) : query method는 이미 존재한다.  
		 */
		
		// A. honggd의 follower 조회 (followee가 honggd인 레코드 조회)
		List<Follow> followerList = followRepository.findByIdFollowee(honggd.getId()); 	// 1.@EmbeddedId 통해 조회
//		List<Follow> followerList = followRepository.findByFollowee(honggd); 			// 2.@MapsId 조회
		
//		Follow follower = new Follow();
//		follower.getId().setFollowee(honggd.getId()); // FollowId#followee필드만 값설정
//		Example<Follow> exampleFollower = Example.of(follower);
//		List<Follow> followerList = followRepository.findAll(exampleFollower);			// 3.Example객체를 활용한 조회
		
		System.out.println(followerList);
		assertEquals(2, followerList.size()); // "honggd의 follower는 두명 sinsa, balsamic이다."
		assertEquals(honggd.getId(), followerList.get(0).getId().getFollowee());
		assertEquals(honggd.getId(), followerList.get(1).getId().getFollowee());
		
		// B. honggd의 followee 조회 (follower가 honggd를 조회)
//		List<Follow> followeeList = followRepository.findByIdFollower(honggd.getId()); 	// 1.@EmbeddedId 통해 조회
//		List<Follow> followeeList = followRepository.findByFollower(honggd);			// 2.@MapsId 조회
		
		Follow followee = new Follow();
		followee.getId().setFollower(honggd.getId()); // FollowId#follower필드만 값설정
		Example<Follow> exampleFollowee = Example.of(followee);
		List<Follow> followeeList = followRepository.findAll(exampleFollowee);			// 3.Example객체를 활용한 조회
		
		System.out.println(followeeList);
		assertEquals(1, followeeList.size());
		assertEquals(honggd.getId(), followeeList.get(0).getId().getFollower());
		
		
	}

}
