package com.sh.app.jdbc;

import com.sh.app.menu.entity.Menu;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <pre>
 * JDBC저수준 API를 직접 사용하는 경우 문제점.
 * 1. SQL 직접 작성, 필요한 데이터 변환, 관련코드 중복
 * 2. SQL 의존 개발 : 요구사항이 변환면 반드시 SQL을 수정해야 한다.
 * 3. 패러다임 불일치 (연관관계, 자바진영의 상속, 객체 그래프 탐색등에 구현에 있어 DB Table과 매칭되지 않음)
 * 4. 동일성 보장 문제 : pk로 조회한 두 객체가 다를수 있다.
 * </pre>
 */
public class JdbcTest {
    private Connection conn;
    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/menudb";
    String user = "sh";
    String password = "sh";


    @BeforeEach
    void setUp() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        this.conn = DriverManager.getConnection(url, user, password);
        this.conn.setAutoCommit(false);
    }

    @AfterEach
    void tearDown() throws SQLException {
        this.conn.rollback();
        this.conn.close();
    }
    
    @Test
    @DisplayName("JDBC 저수준 API로 메뉴 목록 조회하기")
    void test1() throws SQLException {
        // given
        String sql = """
                select
                    *
                from
                    tbl_menu
                """;
        // when
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rset = ps.executeQuery();

        List<Menu> menus = new ArrayList<>();
        while (rset.next()) {
            Menu menu = new Menu();
            menu.setMenuCode(rset.getLong("MENU_CODE"));
            menu.setMenuName(rset.getString("MENU_NAME"));
            menu.setMenuPrice(rset.getInt("MENU_PRICE"));
            menu.setCategoryCode(rset.getInt("CATEGORY_CODE"));
            menu.setOrderableStatus(rset.getString("ORDERABLE_STATUS"));
            menus.add(menu);
        }
        System.out.println(menus);
        // then
        assertThat(menus).isNotEmpty();
    }

    @Test
    @DisplayName("JDBC 저수준 API로 메뉴 등록하기")
    void test2() throws SQLException {
        //given
        Menu menu = new Menu();
        menu.setMenuName("민트초코짜장면");
        menu.setMenuPrice(12000);
        menu.setCategoryCode(1);
        menu.setOrderableStatus("Y");

        String sql = """
            insert into
                tbl_menu(menu_name, menu_price, category_code, orderable_status)
            values(
                ?, ?, ?, ?
            )
            """;

        //when
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, menu.getMenuName());
        pstmt.setInt(2, menu.getMenuPrice());
        pstmt.setInt(3, menu.getCategoryCode());
        pstmt.setString(4, menu.getOrderableStatus());
        int result = pstmt.executeUpdate(); // 처리된 행수를 반환

        // then
        assertThat(result).isPositive();
    }

    /**
     * <pre>
     * DB관점에서는 상속이 존재하지 않는다. 
     * DB관점의 상속은 서브타입엔티티를 의미한다. 
     * 
     * - tbl_member: id, name
     * - tbl_admin_member: member_id, admin_role
     * - tbl_shop_member: member_id, address
     * 
     * AdminMember 조회시 tbl_member & tbl_admin_member 조인쿼리
     * ShopMember 조회시 tbl_member & tbl_shop_member 조인쿼리
     * 
     * AdminMember 등록시 insert into tbl_member & insert into tbl_admin_member 트랜잭션
     * ShopMember 등록시 insert into tbl_member & insert into tbl_shop_member 트랜잭션
     * </pre>
     */
    @Test
    @DisplayName("객체지향 관점의 상속을 DB에서는 똑같이 매칭할 수 없다.")
    void test3() {
        // given
        // when
        // then
    }

    /**
     * <pre>
     * 동일한 PK컬럼값을 조회한 경우, 객체는 동일해야 한다. (주소값이 같은 하나의 객체여야한다)
     * </pre>
     */
    @Test
    @DisplayName("동일객체를 보장할 수 없다.")
    void test4() throws SQLException {
        // given
        Menu menu1 = getMenu(1L);
        Menu menu2 = getMenu(1L);
        System.out.println(menu1);
        System.out.println(menu2);
        // then
        assertThat(menu1).usingRecursiveComparison().isEqualTo(menu2); // 모든 값은 동등하다.
        assertThat(menu1).isNotSameAs(menu2); // 하지만 같은 객체는 아니다.
    }

    private Menu getMenu(Long menuCode) throws SQLException {
        String sql = """
                select
                    *
                from
                    tbl_menu
                where
                    menu_code = ?
                """;
        // when
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, menuCode);
        ResultSet rset = ps.executeQuery();

        Menu menu = new Menu();
        while (rset.next()) {
            menu.setMenuCode(rset.getLong("MENU_CODE"));
            menu.setMenuName(rset.getString("MENU_NAME"));
            menu.setMenuPrice(rset.getInt("MENU_PRICE"));
            menu.setCategoryCode(rset.getInt("CATEGORY_CODE"));
            menu.setOrderableStatus(rset.getString("ORDERABLE_STATUS"));
        }
        return menu;
    }


}
