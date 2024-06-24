package com.sh.entity.mapping._02.embeddable._03.attribute.override._01.client.event.repository;

import com.sh.entity.mapping._02.embeddable._03.attribute.override._01.client.event.entity.Client;
import com.sh.entity.mapping._02.embeddable._03.attribute.override._01.client.event.entity.Event;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EventRepositoryTest {

    @Autowired
    EventRepository eventRepository;

    /*
        create table event (
            id bigint not null auto_increment,
            organizer_code bigint,
            participant_code bigint,
            organizer_name varchar(255),
            participant_name varchar(255),
            primary key (id)
        ) engine=InnoDB
     */

    @DisplayName("이벤트 등록")
    @Test
    public void test() throws Exception {
        // given
        Client organizer = new Client(1L, "홍길동");
        Client participant = new Client(2L, "신사임당");
        Event event = new Event(null, organizer, participant);
        // when
        event = eventRepository.saveAndFlush(event);
        // then
        assertThat(event.getId()).isNotNull().isNotZero();
    }
}