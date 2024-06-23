package com.sh.entity.association._01.one2one._02.identifying.vote.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "vote")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
//@AllArgsConstructor // User객체를 통해 @Id email설정을 위해 모든 필드 생성자를 생성하지 않는다. 실제로는 @Id만 채워져 있으면, db insert 등에는 문제가 없다.
public class Vote {
    @Id
    @Column(name = "user_email")
    private String email;

    @OneToOne(cascade = CascadeType.ALL) // vote저장시 null identifier 오류 방지용
    @PrimaryKeyJoinColumn(name = "user_email") // fk컬럼명
    private User2 user;

    @Column(name = "candidate_name")
    private String candidateName;

    public Vote(@NonNull User2 user, String candidateName) {
        this.email = user.getEmail();
        this.user = user;
        this.candidateName = candidateName;
    }
}
