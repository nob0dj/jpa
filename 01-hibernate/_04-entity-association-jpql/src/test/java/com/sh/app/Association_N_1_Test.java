package com.sh.app;

import com.sh.app.member.entity.Member;
import com.sh.app.member.entity.Team;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <pre>
 * 1:N 연관관계 학습테스트
 * - member `외래키` fk_member_team_id (연관관계의 주인)
 *  - @ManyToOne @JoinColum Team team
 * - team
 *  - @
 *
 * 단방향, 양방향 모두 구현
 *
 * </pre>
 */
public class Association_N_1_Test {
    private static SessionFactory sessionFactory;
    private Session session;

    /* Member, Team Data */
    Member honggd = Member.builder()
            .id("honggd")
            .name("홍길동")
            .build(); // 비영속(transient)
    Member sinsa = Member.builder()
            .id("sinsa")
            .name("신사임당")
            .build(); // 비영속(transient)
    Member leess = Member.builder()
            .id("leess")
            .name("이순신")
            .build(); // 비영속(transient)
    Member yoogs = Member.builder()
            .id("yoogs")
            .name("유관순")
            .build(); // 비영속(transient)
    Team team1 = Team.builder()
            .name("맨체스터 유나이티드")
            .build(); // 비영속(transient)
    Team team2 = Team.builder()
            .name("리버풀")
            .build(); // 비영속(transient)
    Team team3 = Team.builder()
            .name("토트넘")
            .build(); // 비영속(transient)

    @BeforeAll
    public static void beforeAll() {
        String resourceName = "hibernate.cfg.xml";
        Configuration config = new Configuration();
        // resourceName이 기본값인 경우 생략가능
        config.configure(resourceName);
        sessionFactory = config.buildSessionFactory();
    }

    @BeforeEach
    public void setUp() {
        session = sessionFactory.openSession();
    }

    @AfterAll
    public static void afterAll() {
        sessionFactory.close();
    }

    @AfterEach
    public void tearDown() {
        session.close();
    }

    /**
     * from member left outer join team 조인쿼리 실행
     *
     * @throws Exception
     */
    @DisplayName("단방향 연관관계 - 회원만 팀을 참조가능하다.")
    @Test
    public void test1() throws Exception {
        // given
        Member member = Member.builder()
                .id("honggd")
                .name("홍길동")
                .build(); // 비영속(transient)
        Team team = Team.builder()
                .name("맨체스터 유나이티드")
                .build(); // 비영속(transient)
        member.setTeam(team);
        // when
        Transaction transaction = session.beginTransaction();
//        session.persist(team); // 연관관계의 team부터 영속(managed)상태로 등록. @ManyToOne(cascade=CascadeType.PERSIST) 설정했다면 생략가능
        session.persist(member);
        transaction.commit();
        // 영속성 초기화를 하지 않으면 1차캐시에서 그대로 읽어오므로, select쿼리가 요청되지 않는다.
        session.clear(); // 영속성컨텍스트 초기화 (member, team 모두 비영속)

        // then
        Member member2 = session.get(Member.class, member.getId());
        assertThat(member2)
                .isNotNull()
                .satisfies((_member) -> {
                    assertThat(_member.getTeam()).isEqualTo(team);
                });
    }

    /**
     * 회원 조회시 from member left outer join team 조인쿼리 실행
     */
    @DisplayName("양방향 연관관계 - 회원에서 팀조회")
    @Test
    public void test2() throws Exception {
        // given
        setUpData();

        // when
        Member honggd2 = session.get(Member.class, "honggd");
        // Team#members @OneToMany(fetch = FetchType.LAZY) honggd2#team#members에 대한 조회가 지연된다.
        // Team#members @OneToMany(fetch = FetchType.EAGER) honggd2#team#members에 대해 즉시 조회처리 (쿼리 연속 조회 확인)
        System.out.println(honggd2);
        System.out.println(honggd2.getTeam().getMembers());

        // then
        assertThat(honggd2)
                .isNotNull()
                .satisfies((_member) -> {
                    assertThat(_member.getTeam()).satisfies((_team) -> {
                        assertThat(_team.getId()).isEqualTo(team1.getId());
                        assertThat(_team.getName()).isEqualTo(team1.getName());
//                        assertThat(_team.getMembers()).isEqualTo(team1.getMembers()); // false?
                        // 요소순서를 제외한 값비교
                        assertThat(_team.getMembers()).hasSameElementsAs(team1.getMembers());
                    });
                });

    }

    /**
     * @OneToMany(fetch = FetchType.LAZY) 기본값. team조회시 member 조회 안함.
     * @OneToMany(fetch = FetchType.EAGER) team조회시 team별로 members 조회쿼리 연속 요청
     */
    @DisplayName("양방향 연관관계 - 팀에서 회원목록조회")
    @Test
    public void test2_2() throws Exception {
        // given
        setUpData();

        // when
        Team team = session.get(Team.class, 1L);
        System.out.println(team);
        System.out.println(team.getMembers());

        // then
        assertThat(team)
                .isNotNull()
                .satisfies((_team) -> {
//                    assertThat(_team.getMembers()).isEqualTo(team1.getMembers());
                    // 요소순서를 제외한 값비교
                    assertThat(_team.getMembers()).hasSameElementsAs(team1.getMembers());
                });

    }

    /**
     * <pre>
     *
     * 1. tb_member 쿼리요청
     * 2. tb_team 쿼리요청
     * - @OneToMany(fetch = FetchType.LAZY)(기본값)인 경우, team조회시 members조회 안함.
     * - @OneToMany(fetch = FetchType.EAGER)인 경우, team조회시 members 조인쿼리 요청
     * </pre>
     *
     * <strong>N + 1 문제발생</strong>하고, 이를 해결하기 위해서는 join fetch를 사용해야 한다.
     *
     * {@link Association_N_1_Test#test8()} 참조할 것!
     */
    @DisplayName("양방향 연관관계 - jpql member entity 단독 조회")
    @Test
    public void test3() throws Exception {
        // given
        setUpData2();
        // when
        TypedQuery<Member> query = session.createQuery("select m from member m", Member.class);
        List<Member> members = query.getResultList();
        System.out.println(members);
        // then
        Assertions.assertThat(members)
                .hasSize(4)
                .hasSameElementsAs(Arrays.asList(honggd, sinsa, yoogs, leess));
    }

    /**
     * @OneToMany(fetch = FetchType.LAZY) 기본값. team조회시 member 조회 안함.
     * @OneToMany(fetch = FetchType.EAGER) team조회시 team별로 members 조회쿼리 연속 요청
     */
    @DisplayName("양방향 연관관계 - jpql team entity 단독 조회")
    @Test
    public void test4() throws Exception {
        // given
        setUpData2();
        // when
        TypedQuery<Team> query = session.createQuery("select t from team t", Team.class);
        List<Team> teams = query.getResultList();
        System.out.println(teams);
        // then
        Assertions.assertThat(teams)
                .hasSize(3)
                .hasSameElementsAs(Arrays.asList(team1, team2, team3));

    }

    @DisplayName("양방향 연관관계 - jpql 조인쿼리 (내부조인)")
    @Test
    public void test5() throws Exception {
        // given
        setUpData2();

        // when
        TypedQuery<Object[]> query = session.createQuery("select m, t from member m join m.team t", Object[].class);
        List<Object[]> resultList = query.getResultList();
        System.out.println(resultList); // [[m, t], [m, t], [m, t]]의 구조를 가진다. 팀 없는 이순신, 팀원 없는 토트넘 누락
        // then
        Assertions.assertThat(resultList)
                .hasSize(3)
                .allSatisfy(row -> {
                    Assertions.assertThat(row[0]).isInstanceOf(Member.class);
                    Assertions.assertThat(row[1]).isInstanceOf(Team.class);
                    System.out.println("%s | %s".formatted(row[0], row[1]));
                });
    }

    /**
     * @throws Exception
     */
    @DisplayName("양방향 연관관계 - jpql 조인쿼리 (LEFT 외부조인)")
    @Test
    public void test6() throws Exception {
        // given
        setUpData2();
        // when
        TypedQuery<Object[]> query = session.createQuery("select m, t from member m left join m.team t", Object[].class);
        List<Object[]> resultList = query.getResultList();
        System.out.println(resultList); // [[m, t], [m, t], [m, t], [m, t]]의 구조를 가진다. 팀 없는 이순신 포함, 팀원 없는 토트넘 누락
        // then
        Assertions.assertThat(resultList)
                .hasSize(4)
                .allSatisfy(row -> {
                    Assertions.assertThat(row[0]).isInstanceOf(Member.class);
                    Assertions.assertThat(row[1]).satisfiesAnyOf(
                            o1 -> Assertions.assertThat(o1).isNull(),
                            o2 -> Assertions.assertThat(o2).isInstanceOf(Team.class));
                    System.out.println("%s | %s".formatted(row[0], row[1]));
                });
        /*
            Member(id=honggd, name=홍길동, team=Team(id=2, name=맨체스터 유나이티드)) | Team(id=2, name=맨체스터 유나이티드)
            Member(id=sinsa, name=신사임당, team=Team(id=2, name=맨체스터 유나이티드)) | Team(id=2, name=맨체스터 유나이티드)
            Member(id=yoogs, name=유관순, team=Team(id=3, name=리버풀)) | Team(id=3, name=리버풀)
            Member(id=leess, name=이순신, team=null) | null
         */
    }

    @DisplayName("양방향 연관관계 - jpql 조인쿼리 (RIGHT 외부조인)")
    @Test
    public void test7() throws Exception {
        // given
        setUpData2();
        // when
        TypedQuery<Object[]> query = session.createQuery("select m, t from member m right join m.team t", Object[].class);
        List<Object[]> resultList = query.getResultList();
        System.out.println(resultList); // [[m, t], [m, t], [m, t], [m, t]]의 구조를 가진다. 팀원없는 토트넘 포함, 팀이 없는 이순신 누락
        // then
        Assertions.assertThat(resultList)
                .hasSize(4)
                .allSatisfy(row -> {
                    // null과 타입체크 동시에 하기
                    Assertions.assertThat(row[0]).satisfiesAnyOf(
                            o1 -> Assertions.assertThat(o1).isNull(),
                            o2 -> Assertions.assertThat(o2).isInstanceOf(Member.class));
                    Assertions.assertThat(row[1]).isInstanceOf(Team.class);
                    System.out.println("%s | %s".formatted(row[0], row[1]));
                });
        /*
            Member(id=honggd, name=홍길동, team=Team(id=2, name=맨체스터 유나이티드)) | Team(id=2, name=맨체스터 유나이티드)
            Member(id=sinsa, name=신사임당, team=Team(id=2, name=맨체스터 유나이티드)) | Team(id=2, name=맨체스터 유나이티드)
            Member(id=yoogs, name=유관순, team=Team(id=3, name=리버풀)) | Team(id=3, name=리버풀)
            null | Team(id=1, name=토트넘)
         */
    }

    /**
     * join fetch는 내부조인으로 처리된다. 
     * 각 팀정보를 조회하기 위한 추가적인 쿼리요청이 없다.
     * (N + 1문제 해결 : jpql member entity 단독 조회 {@link Association_N_1_Test#test3()}와 비교할 것)
     */
    @DisplayName("양방향 연관관계 - jpql 조인쿼리 fetch")
    @Test
    public void test8() throws Exception {
        // given
        setUpData2();
        // when
        Query<Member> query = session.createQuery("select m from member m join fetch m.team t", Member.class);
        List<Member> members = query.getResultList();
        System.out.println(members);
        // then
        Assertions.assertThat(members)
                .hasSize(3)
                .allSatisfy(member -> {
                   assertThat(member.getTeam()).isNotNull();
                });
    }

    private void setUpData() {
        honggd.setTeam(team1);
        sinsa.setTeam(team1);
        Transaction transaction = session.beginTransaction();
//        session.persist(team); // 연관관계의 team부터 영속(managed)상태로 등록. @ManyToOne(cascade=CascadeType.PERSIST) 설정했다면 생략가능
        session.persist(honggd);
        session.persist(sinsa);
        transaction.commit();
        session.clear(); // 영속성컨텍스트 초기화 (member, team 모두 비영속)
    }

    private void setUpData2() {
        honggd.setTeam(team1);
        sinsa.setTeam(team1);
        yoogs.setTeam(team2);
        Transaction transaction = session.beginTransaction();
//        session.persist(team1); // 연관관계의 team부터 영속(managed)상태로 등록해야 한다. @ManyToOne(cascade=CascadeType.PERSIST) 설정했다면 생략가능
//        session.persist(team2);
        session.persist(team3);
        session.persist(honggd);
        session.persist(sinsa);
        session.persist(yoogs);
        session.persist(leess);
        transaction.commit();
        // 영속성 초기화를 하지 않으면 1차캐시에서 그대로 읽어오므로, select쿼리가 요청되지 않는다.
        session.clear(); // 영속성컨텍스트 초기화 (member, team 모두 비영속)
    }
}
