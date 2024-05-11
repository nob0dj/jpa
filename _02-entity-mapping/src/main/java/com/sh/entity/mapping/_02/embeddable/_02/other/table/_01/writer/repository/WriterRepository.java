package com.sh.entity.mapping._02.embeddable._02.other.table._01.writer.repository;

import com.sh.entity.mapping._02.embeddable._02.other.table._01.writer.entity.Writer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WriterRepository extends JpaRepository<Writer, Long> {
}
