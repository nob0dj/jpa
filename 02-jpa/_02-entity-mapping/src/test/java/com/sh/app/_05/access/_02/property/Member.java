package com.sh.app._05.access._02.property;

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
 *      - @Id @GeneratedValue @Column 모두 getter위에 작성해야 한다.
 * 
 * </pre>
 */
@Entity(name = "Member0502")
@Table(name = "tbl_member0502")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Access(AccessType.PROPERTY) // 클래스레벨에 작성. 클래스의 모든 필드에 접근방식을 프로퍼티레벨로 결정
public class Member {

    @Access(AccessType.PROPERTY) // 필드레벨에 작성. 이 필드에 대한 접근방식만 프로퍼티레벨로 결정
    private Long code;
    private String id; // 사용자가 입력한 문자열 아이디
    private String password;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private boolean enabled;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_code")
    public Long getCode() {
        return this.code;
    }
    public void setCode(Long code) {
        this.code = code;
    }

    @Column(name = "member_id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "member_password", nullable = false, length = 20)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "member_name", columnDefinition = "varchar(100) default '홍길동'")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "member_email", unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "member_created_at")
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Column(name = "member_enabled")
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
