package com.sh.app.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user") // entity명 (동명의 entity 구분가능)
@Table(name = "tb_user") // table명
@DynamicInsert // 값이 null인 필드는 insert때 제외한다. 기본형 대신 wrapper를 사용할것.
@DynamicUpdate // 영속한 entity 필드에 변경사항이 없을때 제외
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // 오라클 GenerationType.Sequence, Mysql GenerationType.Identity(db에 위임)
    private Long id;

    @Column(unique = true, nullable = false) // UQ 제약조건
    private String username;

    // 컬럼명 커스텀설정 - 자료형부터 모두 기입, 기본값은 요청하는 insert문에서 제외될때 작동한다. 클래스레벨 @DynamicInsert와 함께 사용
    @Column(columnDefinition = "number default 1000 check(point >= 0)")
    private Integer point = 1000; // @DynamicInsert이후 default로 대입된 값을 PersistentContext에 바로 적용이 안되므로 기본값을 지정. 단, builder패턴 사용시 적용안됨.

    @CreationTimestamp
    private LocalDateTime createdAt;

    /**
     * 기본값 지정할때 추천방식
     */
    @PrePersist
    public void prePersist(){
        if(this.point == null)
            this.point = 1000;
    }
}
