package com.sh.entity.mapping._02.embeddable._01.single.table._01.hotel.repository;

import com.sh.entity.mapping._02.embeddable._01.single.table._01.hotel.entity.Address;
import com.sh.entity.mapping._02.embeddable._01.single.table._01.hotel.entity.Grade;
import com.sh.entity.mapping._02.embeddable._01.single.table._01.hotel.entity.Hotel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
// embedded db 또는 대체할 db를 작성해야 한다.
// replace = AutoConfigureTestDatabase.Replace.NONE -> 실제 db사용 설정
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class HotelRepositoryTest {
    @Autowired
    HotelRepository hotelRepository;

    @DisplayName("호텔 등록 및 조회")
    @Test
    public void test() throws Exception {
        // given
        Address address = new Address("서울시 강남구", "테헤란로 1234", "0321");
        Hotel hotel = new Hotel("H1234", "강남쉴라", 2024, Grade.S1, address, null, null);
        // when
        hotel = hotelRepository.save(hotel); // 영속성 컨텍스트에 포함된 새 Hotel객체가 반환된다.
        // test trasaction에서는 dml쿼리를 실제 실행하지 않으므로, flush를 호출해서 쿼리 확인 (역시 rollback 된다.)
        hotelRepository.flush();
        // then
        Hotel hotel2 = hotelRepository.findById(hotel.getId()).get(); // 영속성 컨텍스트에 entity객체가 있으므로 select쿼리 실행 안함
        System.out.println(hotel2); // Hotel(id=H1234, name=강남쉴라, year=2024, grade=S1, address=Address(address1=서울시 강남구, address2=테헤란로 1234, zipcode=0321), createdAt=2024-05-15T15:09:23.570125, updatedAt=null)
        // equals 비교(재귀)
        assertThat(hotel)
                .usingRecursiveComparison()
                .ignoringFields("createdAt") // @CreationStamp에 의해 값을 주지 않았지만, insert시 시각이 기록되어 있다.
                .isEqualTo(hotel2);
        // == 비교
        assertThat(hotel).isSameAs(hotel2); // 같은 instance인가?

    }

}