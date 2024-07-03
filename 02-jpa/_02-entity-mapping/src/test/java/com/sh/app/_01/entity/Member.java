package com.sh.app._01.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * <pre>
 * @Entity 어노테이션은 JPA에서 사용되는 엔티티 클래스임을 표시한다.
 * 이 어노테이션을 사용하면 해당 클래스가 데이터베이스의 테이블과 매핑된다.
 * @Entity` 어노테이션은 클래스 선언 위에 위치해야 한다.
 * 또한, `name` 속성을 사용하여 엔티티 클래스와 매핑될 테이블의 이름을 지정할 수 있다.
 * 생략하면 자동으로 클래스 이름을 엔티티명으로 사용한다.
 *
 * 주의사항
 * - 프로젝트 내에 다른 패키지에도 동일한 엔티티가 존재하는 경우 해당 엔티티를 식별하기 위한 중복 되지 않는 name을 지정해주어야 한다.
 * - 기본 생성자는 필수로 작성해야 한다.
 * - final 클래스, enum, interface, inner class 에서는 사용할 수 없다.
 * - 저장할 필드에 final을 사용하면 안된다.
 * - @Id필드를 반드시 지정해야 한다.
 * </pre>
 */
@Entity(name = "Member01") // 영속성 컨텍스트에서 관리되는 이름 
@Table(name = "tbl_member01") // DB테이블 매핑
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @Column(name = "member_id")
    private String id;
    @Column(name = "member_password")
    private String password;
    @Column(name = "member_name")
    private String name;
    @Column(name = "member_created_at")
    private LocalDateTime createdAt;
    @Column(name = "member_enabled")
    private boolean enabled;
}
