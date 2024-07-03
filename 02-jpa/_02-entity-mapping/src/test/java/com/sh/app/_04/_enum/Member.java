package com.sh.app._04._enum;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * <pre>
 *  @Enumerated 어노테이션은 Enum 타입 매핑을 위해 사용된다.
 *  - EnumType.ORDINAL : Enum 타입을 순서로 매핑한다. (default)
 *  - EnumType.STRING : Enum 타입을 문자열로 매핑한다.
 *
 *  ORDINAL 사용 시의 장점은 데이터 베이스에 저장 되는 데이터의 크기가 작다는 것이고
 *  단점은 이미 저장 된 enum의 순서를 변경할 수 없다는 것이다.
 *
 *  STRING 사용 시의 장점은 저장된 enum의 순서가 바뀌거나 enum이 추가 되어도 안전하다는 것이고
 *  단점은 데이터 베이스에 저장 되는 데이터의 크기가 ordinal에 비해 크다는 것이다.
 *  (mysql의 enum타입을 사용하면, 노출되는 값 name값이지만, 내부적으로는 숫자값으로 관리하기때문에 효율성 역시 좋다.)
 * </pre>
 */
@Entity(name = "Member04")
@Table(name = "tbl_member04")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @Column(name = "member_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;
    @Column(name = "member_id")
    private String id; // 사용자가 입력한 문자열 아이디
    @Column(name = "member_password", nullable = false, length = 20)
    private String password;
    @Column(name = "member_name", columnDefinition = "varchar(100) default '홍길동'")
    private String name;

    @Column(name = "member_gender")
    @Enumerated(EnumType.ORDINAL) // enum name 선언 인덱스를 db에서 관리
    private Gender gender;

    @Column(name = "member_role")
    @Enumerated(EnumType.STRING) // enum name값은 문자열로 db에서 관리
    private MemberRole memberRole;

    @Column(name = "member_email", unique = true)
    private String email;
    @Column(name = "member_created_at")
    private LocalDateTime createdAt;
    @Column(name = "member_enabled")
    private boolean enabled;
    

}
