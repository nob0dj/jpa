package com.kh.spring;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kh.spring.client.domain.Client;
import com.kh.spring.client.repository.ClientRepository;
import com.kh.spring.room.domain.Room;
import com.kh.spring.room.repository.RoomRepository;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED) // 자동롤백 하지 않음 
@AutoConfigureTestDatabase(replace = Replace.NONE) // 실제 db사용
class RoomRepositoryTest {

	@Autowired
	RoomRepository roomRepository;
	
	@Autowired
	ClientRepository clientRepository;
	
	@Disabled
	@Test
	void 룸등록() {
		Room room = new Room();
		roomRepository.save(room);
		assertThat("룸 등록 확인!", room.getId(), is(notNullValue()));
	}

	/**
	 * room이 외래키의 주인이다. 즉 room을 영속성컨텍스트에 저장해야한다.
	 */
	@Disabled
	@DisplayName("양방향 Room#setClient")
	@Test
	void 양방향1() {
		// given
		Client honggd = Client.builder()
							.name("홍길동")
							.build();
		honggd = clientRepository.save(honggd);
		
		Room room = new Room();
		room.setClient(honggd);
		room = roomRepository.save(room);
		
		// when
		Client m = clientRepository.findById(honggd.getId()).get();
		
		// then
		assertThat("client.room != null", m.getRoom(), is(notNullValue()));
		assertThat("client eq client.room.client", m, is(equalTo(m.getRoom().getClient())));
		
		// when
		Room r = roomRepository.findById(room.getId()).get();
		
		// then
		assertThat("room.client != null", r.getClient(), is(notNullValue()));
		assertThat("room eq room.client.room", r, is(equalTo(r.getClient().getRoom())));
	}
	
//	@Disabled
	@DisplayName("양방향 Client#setRoom")
	@Test
	void 양방향2() {
		// given
		Room room = new Room();
		room = roomRepository.save(room);
		
		Client honggd = Client.builder()
				.name("홍길동")
				.build();
		honggd.setRoom(room);
		honggd = clientRepository.save(honggd);
		
		// 외래키 주인인 객체를 영속성컨텍스트에 저장!
		room = roomRepository.save(room);
		
		// when
		Room r = roomRepository.findById(room.getId()).get();
		
		// then
		assertThat("room.client != null", r.getClient(), is(notNullValue()));
		assertThat("room eq room.client.room", r, is(equalTo(r.getClient().getRoom())));
	}

}
