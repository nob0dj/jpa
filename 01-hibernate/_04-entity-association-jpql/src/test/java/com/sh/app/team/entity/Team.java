package com.sh.app.team.entity;

import com.sh.app.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "team")
@Table(name = "tb_team")
@ToString(exclude = "members") // 순환참조 방지
@EqualsAndHashCode(of = {"id", "name"})
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy="team", fetch = FetchType.LAZY) // Memeber#team을 작성
	private List<Member> members = new ArrayList<>(); // @Builder로 생성시는 무시됨.
}
