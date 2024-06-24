package com.sh.entity.mapping._03.element.collection._03.map._02.document.prop.embeddable.repository;

import com.sh.entity.mapping._03.element.collection._03.map._02.document.prop.embeddable.entity.Document2;
import com.sh.entity.mapping._03.element.collection._03.map._02.document.prop.embeddable.entity.PropValue;
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
class Document2RepositoryTest {
    @Autowired
    Document2Repository document2Repository;
    Map<String, PropValue> props = Map.of("view", new PropValue("everyone", true), "edit", new PropValue("owner", true));

    /*
        create table doc2 (
            id bigint not null auto_increment,
            content varchar(255),
            title varchar(255),
            primary key (id)
        ) engine=InnoDB

       create table doc_prop2 (
            enabled bit,
            doc_id bigint not null,
            name varchar(255) not null,
            value varchar(255),
            primary key (doc_id, name)
        ) engine=InnoDB
     */

    @DisplayName("Document-PropValue 등록")
    @Test
    @Rollback(false)
    public void test() throws Exception {
        // given
        Document2 document = new Document2(null, "커피는 빈속에", "부제: 내과의사로 부자되는 법", props);
        // when
        document2Repository.saveAndFlush(document);
        // then
        assertThat(document.getId()).isNotNull();
    }
    @DisplayName("Document-PropValue 조회")
    @Test
    public void test2() throws Exception {
        // given
        Document2 document = new Document2(null, "커피는 빈속에", "부제: 내과의사로 부자되는 법", props);
        document2Repository.saveAndFlush(document);
        // when
        Document2 document2 = document2Repository.findById(1L).get();
        // then
        assertThat(document2.getProps()).containsExactlyEntriesOf(props);
    }
}