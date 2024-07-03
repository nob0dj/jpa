package com.sh.app._03.primary.key._02.table.strategy;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * <pre>
 * @GeneratedValue
 * - 항상 @Id와 함께 작성한다.
 * - strategy : 자동 생성 전략을 지정
 *     - GenerationType.IDENTITY : 기본 키 생성을 데이터베이스에 위임(MySQL의 AUTO_INCREMENT)
 *     - GenerationType.SEQUENCE : 데이터베이스 시퀀스 객체 사용(ORACLE의 SEQUENCE)
 *     - GenerationType.TABLE : 키 생성 테이블 사용
 *     - GenerationType.AUTO : 자동 선택 (MySQL이라면 IDENTITY, ORACLE이라면 SEQUENCE로 선택)
 * - generator : strategy 값을 GenerationType.TABLE로 지정한 경우 사용되는 테이블 이름을 지정
 * - initialValue : strategy 값을 GenerationType.SEQUENCE로 지정한 경우 시퀀스 초기값을 지정
 * - allocationSize : strategy 값을 GenerationType.SEQUENCE로 지정한 경우 시퀀스 증가치를 지정
 * </pre>
 */
@Entity(name = "Member0302")
@Table(name = "tbl_member0302")
@TableGenerator(
    name="table_sequence_generator",
    table = "tbl_sequence",
    allocationSize = 50, // 기본값 50, 한번 읽어서 메모리(DB가 아닌 앱)에서 배포할 크기
    pkColumnName = "table_name" // 테이블별 식별자, next_val컬럼을 통해 채번할 숫자를 관리한다.
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @Column(name = "member_code")
    @GeneratedValue(
        strategy = GenerationType.TABLE,
        generator = "table_sequence_generator")
    private Long code;
    @Column(name = "member_id")
    private String id; // 사용자가 입력한 문자열 아이디
    @Column(name = "member_password", nullable = false, length = 20)
    private String password;
    @Column(name = "member_name", columnDefinition = "varchar(100) default '홍길동'")
    private String name;
    @Column(name = "member_email", unique = true)
    private String email;
    @Column(name = "member_created_at")
    private LocalDateTime createdAt;
    @Column(name = "member_enabled")
    private boolean enabled;
    

}
