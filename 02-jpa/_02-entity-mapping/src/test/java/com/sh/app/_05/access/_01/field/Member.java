package com.sh.app._05.access._01.field;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * <pre>
 * @Access JPA가 엔티티의 필드값을 읽거나 쓰기하는 방식을 결정한다.
 * - @Access(AccessType.FIELD) (기본값) 필드에 직접 접근해서 읽기/쓰기 처리
 * - @Access(AccessType.PROPERTY) Getter/Setter를 사용해 읽기/쓰기 처리
 * 
 * </pre>
 */
@Entity(name = "Member0501")
@Table(name = "tbl_member0501")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Access(AccessType.FIELD) // 클래스레벨에 작성. 클래스의 모든 필드에 접근방식을 필드레벨로 결정
public class Member {
    @Id
    @Column(name = "member_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Access(AccessType.FIELD) // 필드레벨에 작성. 이 필드에 대한 접근방식만 필드레벨로 결정
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
