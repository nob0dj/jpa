package com.sh.entity.mapping._02.embeddable._03.attribute.override._01.client.event.repository;

import com.sh.entity.mapping._02.embeddable._03.attribute.override._01.client.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
