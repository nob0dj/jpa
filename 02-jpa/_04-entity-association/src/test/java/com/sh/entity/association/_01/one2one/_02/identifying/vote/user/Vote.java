package com.sh.entity.association._01.one2one._02.identifying.vote.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vote")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor // User객체를 통해 @Id email설정을 하므로, 모든 필드 생성자를 생성하지 않는다. 실제로는 @Id만 채워져 있으면, db insert 등에는 문제가 없다.
@Getter
@ToString
public class Vote {
    @Id
    @Column(name = "user_email")
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(name = "user_email") // fk컬럼명은 지정되지 않는다.
    private User user;

    @Column(name = "candidate_name")
    private String candidateName;

    public Vote(@NonNull User user, String candidateName) {
        assert user != null : "User는 null일수 없습니다.";

        this.email = user.getEmail();
        this.user = user;
        this.candidateName = candidateName;
    }
}
