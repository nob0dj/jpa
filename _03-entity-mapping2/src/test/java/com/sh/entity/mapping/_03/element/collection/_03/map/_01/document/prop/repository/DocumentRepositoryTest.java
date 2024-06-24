package com.sh.entity.mapping._03.element.collection._03.map._01.document.prop.repository;

import com.sh.entity.mapping._03.element.collection._03.map._01.document.prop.entity.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DocumentRepositoryTest {
    @Autowired
    DocumentRepository documentRepository;

    Map<String, String> props = Map.of("extension", "hwp", "size", "2345kb");

    /*
        create table doc (
            id bigint not null auto_increment,
            content varchar(255),
            title varchar(255),
            primary key (id)
        ) engine=InnoDB

        create table doc_prop (
            enabled bit,
            doc_id bigint not null,
            name varchar(255) not null,
            value varchar(255),
            primary key (doc_id, name)
        ) engine=InnoDB

     */

    @DisplayName("Document-Props 등록")
    @Test
    @Rollback(false)
    public void test() throws Exception {
        // given
        Document document = new Document(null, "내가 살던 동산엔", "기억이 나지 않는다ㅠ", props);
        // when
        documentRepository.saveAndFlush(document);
        // then
        assertThat(document.getId()).isNotNull();
    }

    @DisplayName("Document-Props 조회")
    @Test
    public void test2() throws Exception {
        // given
        // when
        Document document = documentRepository.findById(1L).get();
        // then
        assertThat(document.getProps())
                .isNotNull()
                .isNotEmpty()
                .containsExactlyEntriesOf(props);
    }

    @DisplayName("Document-Props 수정")
    @Test
    public void test3() throws Exception {
        // given
        // when
        Document document = documentRepository.findById(1L).get();
        document.removeProp("size");
        documentRepository.flush();

        // then
        Document document2 = documentRepository.findById(1L).get();
        assertThat(document2.getProps())
                .isNotNull()
                .isNotEmpty()
                .containsOnlyKeys("extension");
    }

}