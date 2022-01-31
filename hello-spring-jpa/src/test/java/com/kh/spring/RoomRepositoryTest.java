package com.kh.spring;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kh.spring.member.domain.Member;
import com.kh.spring.member.repository.MemberRepository;
import com.kh.spring.room.domain.Room;
import com.kh.spring.room.repository.RoomRepository;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED) // 자동롤백 하지 않음 
@AutoConfigureTestDatabase(replace = Replace.NONE) // 실제 db사용
class RoomRepositoryTest {

	@Autowired
	RoomRepository roomRepository;
	
	@Autowired
	MemberRepository memberRepository;
	
	@Test
	void 룸등록() {
		Room room = new Room();
		roomRepository.save(room);
		assertThat("룸 등록 확인!", room.getId(), is(notNullValue()));
	}
	
	@Test
	void 양방향() {
		// given
		Member honggd = Member.builder()
							.id("honggd")
							.password("2345")
							.name("홍길동")
							.build();
		honggd = memberRepository.save(honggd);
		
		Room room = new Room();
		room.setMember(honggd);
		room = roomRepository.save(room);
		
		
		// when
		Member m = memberRepository.findById(honggd.getId()).get();
		
		// then
		assertThat("member.room != null", m.getRoom(), is(notNullValue()));
		assertThat("member eq member.room.member", m, is(equalTo(m.getRoom().getMember())));
		
		// when
		Room r = roomRepository.findById(room.getId()).get();
		
		// then
		assertThat("room.member != null", r.getMember(), is(notNullValue()));
		assertThat("room eq room.member.room", r, is(equalTo(r.getMember().getRoom())));
	}

}
