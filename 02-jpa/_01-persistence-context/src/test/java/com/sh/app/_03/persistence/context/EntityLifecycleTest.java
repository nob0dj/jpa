package com.sh.app._03.persistence.context;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * <pre>
 * 영속성 컨텍스트는 엔티티 매니저가 엔티티 객체를 저장하는 공간으로 엔티티 객체를 보관하고 관리한다.
 * 엔티티 매니저가 생성 될 때 하나의 영속성 컨텍스트가 만들어진다.
 *
 * 엔티티의 생명 주기
 * - 비영속, 영속, 준영속, 삭제 상태
 * </pre>
 */
public class EntityLifecycleTest {
    // application-scope: 1개만 만들어서 재사용 (thread-safe)
    private static EntityManagerFactory entityManagerFactory;
    // request-scope: 웹요청마다 1개씩 생성 (non-thread-safe)
    private EntityManager entityManager;

    @BeforeAll
    static void beforeAll() {
        // jpa설정정보를 읽어서 EntityManagerFactory를 생성
        entityManagerFactory = Persistence.createEntityManagerFactory("jpatest");
    }

    @BeforeEach
    void setUp() {
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterEach
    void tearDown() {
        this.entityManager.close();
    }

    @AfterAll
    static void afterAll() {
        entityManagerFactory.close();
    }

    @Test
    @DisplayName("비영속성 테스트")
    void test1() {
        // given
        Menu menu = entityManager.find(Menu.class, 10L); // 영속성컨텍스트에 등록
        System.out.println(menu);
        // when
        Menu menu2 = new Menu();
        menu2.setMenuCode(menu.getMenuCode());
        menu2.setMenuName(menu.getMenuName());
        menu2.setMenuPrice(menu.getMenuPrice());
        menu2.setCategoryCode(menu.getCategoryCode());
        menu2.setOrderableStatus(menu.getOrderableStatus());
        System.out.println(menu2);
        // then
        assertThat(menu).isNotSameAs(menu2);
        assertThat(entityManager.contains(menu)).isTrue(); // 영속
        assertThat(entityManager.contains(menu2)).isFalse(); // 비영속
    }
    @Test
    @DisplayName("영속컨텍스트에 추가")
    void test2() {
        // given
        Menu menu = new Menu();
        menu.setMenuName("강심장버거");
        menu.setMenuPrice(13000);
        menu.setCategoryCode(4L);
        menu.setOrderableStatus("Y");
        // menu객체는 아직 비영속
        
        // when
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin(); // 트랜잭션 시작 ~ commit/rollback까지가 트랜잭션 범위가 된다.
        try {
            entityManager.persist(menu); // 영속 (@GeneratedValue 쿼리 질의 insert into....)
            System.out.println("menu가 영속성 컨텍스트에 저장되었습니다...");
            transaction.commit(); // commit
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
        // then
        assertThat(menu.getMenuCode()).isNotZero(); // auto_increment로 id값을 할당받음
        assertThat(entityManager.contains(menu)).isTrue(); // 영속성 컨텍스테에 menu객체 저장
    }
    @Test
    @DisplayName("엔티티객체 동일성 보장")
    void test3() {
        // given
        Long menuCode = 10L;
        // when
        Menu menu1 = entityManager.find(Menu.class, menuCode); // 쿼리 질의. menu객체 영속
        Menu menu2 = entityManager.find(Menu.class, menuCode); // 쿼리 질의 없이 영속성컨텍스트에서 조회
        // then
        assertThat(menu1).isSameAs(menu2);
        assertThat(entityManager.contains(menu1)).isTrue();
    }

    /**
     * 영속상태의 entity객체를 더이상 영속컨텍스트에서 관리하지 않도록 하는 것이다.
     */
    @Test
    @DisplayName("준영속 detach")
    void test4() {
        // given
        Menu menu = entityManager.find(Menu.class, 10L);
        System.out.println(menu);
        // when
        entityManager.detach(menu);
        // then
        assertThat(entityManager.contains(menu)).isFalse();
    }

    /**
     * 모든 영속된 엔티티객체를 영속성 컨텍스트로부터 제거한다.
     * @throws Exception
     */
    @DisplayName("준영속 clear")
    @Test
    public void test5() throws Exception {
        // given
        Menu menu10 = entityManager.find(Menu.class, 10L);
        Menu menu11 = entityManager.find(Menu.class, 11L);
        assertThat(entityManager.contains(menu10)).isTrue();
        assertThat(entityManager.contains(menu11)).isTrue();
        // when
        entityManager.clear(); // 모든 영속객체 제거
        // 영속성컨텍스트 1차캐시 안에 존재하지 않으므로 DB질의를 다시 하게 된다.
        Menu menu10_2 = entityManager.find(Menu.class, 10L);
        // then
        assertThat(entityManager.contains(menu10)).isFalse();
        assertThat(entityManager.contains(menu11)).isFalse();
    }

    /**
     * remove를 호출하면, 단순히 영속성컨텍스트에서 제거뿐 아니라 db로 delete 요청까지 질의한다.
     * 
     * flush : 영속성컨텍스트의 변경사항으로 실제 DB에 동기화하는 작업.
     *  - 트랜잭션 커밋시에 flush처리된다.
     */
    @Test
    @DisplayName("삭제 remove")
    void test6() {
        // given
        Menu menu2 = entityManager.find(Menu.class, 2L);
        System.out.println(menu2);
        // when
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            entityManager.remove(menu2); // 영속성컨텍스트에서 삭제처리
//            entityManager.flush(); // delete 쿼리 질의
//            transaction.commit(); // 커밋
            transaction.commit(); // delete쿼리 & 커밋
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
        // then
        assertThat(entityManager.contains(menu2)).isFalse();
    }

    /**
     * 준영속객체를 다시 영속성컨텍스트에 포함시킬수 있다.
     * - 동일한 PK를 가진 엔티티객체가 없다면 우선 DB에서 조회후, 변경사항을 적용
     * - 동일한 PK를 가진 엔티티객체가 있다면 수정(갱신)
     */
    @Test
    @DisplayName("다시 영속시키기 merge")
    void test7() {
        // given
        Menu menu10 = entityManager.find(Menu.class, 10L);
        System.out.println(menu10);
        entityManager.detach(menu10);
        assertThat(entityManager.contains(menu10)).isFalse();
        // when
        String newMenuName = "수박죽";
        menu10.setMenuName(newMenuName); // 코다리마늘빵 -> 수박죽
        // PK가 10인 Menu객체를 다시 DB에서 조회후에 menu10객체와 병합 시도한다.
        entityManager.merge(menu10); // select질의
        System.out.println("병합 완료!!!");
        // then
        Menu menu10_2 = entityManager.find(Menu.class, 10L);
        assertThat(menu10_2.getMenuName()).isEqualTo(newMenuName);

        // 단순히 DB반영 목적
        entityManager.getTransaction().begin();
        entityManager.getTransaction().commit(); // 시작된 트랜잭션을 가져와서 커밋
    }

    @Test
    @DisplayName("영속성컨테스트 종료 close")
    void test8() {
        // given
        Menu menu10 = entityManager.find(Menu.class, 10L);
        // when
        entityManager.close();
        // then
        assertThatThrownBy(() -> {
            entityManager.find(Menu.class, 10L);
        }).isInstanceOf(IllegalStateException.class);
    }
}
