package com.sh.entity.mapping._02.embeddable._02.other.table._01.writer.repository;

import com.sh.entity.mapping._02.embeddable._02.other.table._01.writer.entity.Address;
import com.sh.entity.mapping._02.embeddable._02.other.table._01.writer.entity.Intro;
import com.sh.entity.mapping._02.embeddable._02.other.table._01.writer.entity.Writer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class WriterRepositoryTest {

    @Autowired
    WriterRepository writerRepository;

    @DisplayName("작가 등록 및 조회")
    @Test
    public void test() throws Exception {
        // given
        Intro intro = new Intro("text/html", "<h1>🍓🍓🍓안녕하세요, 딸기요정입니다.🍓🍓🍓</h1>");
        Address address = new Address("서울시 관악구", "청룡동 1234", "01234");
        Writer writer = new Writer(null, "홍길동", intro, address);
        // when
        // @GeneratedValue(GenerationType.IDENTITY)를 사용하는 writer객체 그대로 영속성컨텍스트에서 관리한다. save는 동일한 객체를 반환한다.
        Writer writer2 = writerRepository.save(writer);
        // then
        assertThat(writer.getId()).isNotNull().isNotZero();
        assertThat(writer).isSameAs(writer2);

        Writer writer3 = writerRepository.findById(writer.getId()).get(); // 영속성 컨텍스트에 entity객체가 있으므로 select쿼리 실행 안함
        assertThat(writer2).isSameAs(writer3);
    }
}